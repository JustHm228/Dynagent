/*
 * The MIT License
 *
 * Copyright (c) 2023 Chirkunov Egor
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.justhm228.dynagent.internal.agent;

import com.github.justhm228.dynagent.base.*;
import com.github.justhm228.dynagent.internal.file.*;
import static com.github.justhm228.dynagent.internal.file.FileCleaner.addTarget;
import static com.github.justhm228.dynagent.internal.file.FileCleaner.clean;
import com.sun.tools.attach.*;
import static com.sun.tools.attach.VirtualMachine.*;
import java.io.*;
import static java.io.File.*;
import static java.lang.Class.*;
import static java.lang.Integer.parseInt;
import static java.lang.ProcessHandle.*;
import static java.lang.StackWalker.*;
import static java.lang.StackWalker.Option.*;
import static java.lang.String.valueOf;
import static java.lang.System.err;
import static java.lang.System.out;
import static java.lang.System.exit;
import java.util.*;
import java.util.jar.*;
import java.util.jar.Attributes.*;
import static java.util.jar.Attributes.Name.*;
import java.util.regex.*;
import static java.util.regex.Pattern.*;

public final class DynagentInstaller {

	private DynagentInstaller() throws Error, UnsupportedOperationException {

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!");
	}

	public static void main(final String... args) throws Error {

		if (args.length != 2) { // If the specified argument list length isn't a length of valid argument list...

			if (args.length == 1) { // If there's a single argument...

				final String arg = args[0];

				// Check if it's a help request:
				if (arg.equalsIgnoreCase("-help") || arg.equalsIgnoreCase("-h") || arg.equalsIgnoreCase("-?")) {

					usage(err); // Print help to stderr
					exit(0); // Exit successfully
					return;
				}

				if (arg.equalsIgnoreCase("--help") || arg.equalsIgnoreCase("help") || arg.equalsIgnoreCase("--h") || arg.equalsIgnoreCase("--?") || arg.equalsIgnoreCase("?") || arg.equalsIgnoreCase("/?") || arg.equalsIgnoreCase("h")) {

					usage(out); // Print help to stdout
					exit(0); // Exit successfully
					return;
				}

				// Or else...
			}

			usage(err); // Print help to stderr
			exit(2); // Exit with errors
			return;
		}

		// Check arguments:
		if (!args[0].matches("^(\\d{1,10})$")) { // If invalid...

			err.println("A PID you specified isn't a positive integer ('^(\\d{1,10})$')!");
			usage(err); // Print help to stderr
			exit(2); // Exit with errors
			return;
		}

		try {

			parseInt(args[0]);

		} catch (final NumberFormatException notNumber) { // If invalid...

			err.println("Can't parse a PID you specified as a positive integer:");
			notNumber.printStackTrace();
			usage(err); // Print help to stderr
			exit(1); // Exit with errors
			return;
		}

		{

			final File javaagent = new File(args[1]);

			if (!javaagent.exists() || !javaagent.canRead()) { // If invalid...

				err.println("An agentpath you specified can't be accessed from the program (or it doesn't exist, or it isn't a valid filepath)!");
				usage(err); // Print help to stderr
				exit(1); // Exit with errors
				return;
			}
		}

		// Proceed an attach:
		try {

			out.println("Attaching...");
			attach(args[0]).loadAgent(args[1]); // Attach
			out.println("Attached successfully!");

		} catch (final SecurityException | AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException agent) { // If something went wrong...

			err.println("Failed to attach because of:");
			agent.printStackTrace(); // Print a stack trace
			exit(1); // Exit with errors
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean install() throws Error, IllegalCallerException {

		try {

			return install(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return install(null); // Call without caller
		}
	}

	public static boolean isInstalled() throws Error {

		return DynagentImpl.isLoaded();
	}

	private static void usage(final PrintStream out) throws Error {

		// Print the help message to the specified stream if it's non-null:
		if (out != null) {

			out.println("Usage: java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -jar dynagentInstaller.jar <jvm-pid> <agentpath>");
			out.println("\t(to attach the javaagent from <agentpath> file to an already running <jvm-pid> JVM)");
			out.println("       java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -jar dynagentInstaller.jar [-help|-h|-?]");
			out.println("\t(to print this help message to stderr)");
			out.println("       java -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -jar dynagentInstaller.jar <--help|help|--h|--?|?|/?|h>");
			out.println("\t(to print this help message to stdout)");
			out.flush();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean install(final Class<?> caller) throws Error, IllegalCallerException {

		if (isInstalled()) {

			return true; // Do nothing if it's already installed
		}

		if (caller == null) {

			throw new IllegalCallerException(); // Require a valid caller
		}

		final String pid = findPid(); // Get PID

		if (pid == null) {

			return false; // If failed to get PID
		}

		final String java = findJava(); // Get java executable path

		if (java == null) {

			return false; // If failed to get java executable path
		}

		final String agent = buildAgent(); // Build a javaagent to attach

		if (agent == null) {

			return false; // If failed to build a javaagent
		}

		final String installer = buildInstaller(); // Build an installer to attach a generated javaagent

		if (installer == null) {

			return false; // If failed to build an installer
		}

		try {

			// Install:
			return new ProcessBuilder(java, "-Dfile.encoding=UTF-8", "-Dsun.stdout.encoding=UTF-8", "-Dsun.stderr.encoding=UTF-8", "-jar", installer, pid, agent).start().waitFor() == 0;

		} catch (final UnsupportedOperationException | SecurityException | IOException | InterruptedException failed) {

			return false; // If something went wrong

		} finally {

			// Safe deletion of temporary files:
			if (FileCleaner.isInstalled()) {

				clean(agent);
				clean(installer);
			}
		}
	}

	private static String findPid() throws Error {

		try {

			return valueOf(current().pid()); // Find current PID

		} catch (final UnsupportedOperationException | SecurityException inaccessible) {

			return null; // If something went wrong
		}
	}

	private static String buildAgent() throws Error {

		try {

			forName("com.github.justhm228.dynagent.internal.agent.DynagentImpl");

		} catch (final ClassNotFoundException unavailable) {

			return null;
		}

		final String classname = DynagentImpl.class.getTypeName();

		final Manifest manifest = new Manifest();

		{

			final Attributes attributes = manifest.getMainAttributes();

			attributes.put(MANIFEST_VERSION, "1.0"); // Manifest-Version: 1.0
			attributes.put(new Name("Premain-Class"), classname); // Premain-Class: com.github.justhm228.dynagent.internal.agent.DynagentImpl
			attributes.put(new Name("Agent-Class"), classname); // Agent-Class: com.github.justhm228.dynagent.internal.agent.DynagentImpl
			attributes.put(new Name("Can-Retransform-Classes"), "true"); // Can-Retransform-Classes: true
			attributes.put(new Name("Can-Redefine-Classes"), "true"); // Can-Redefine-Classes: true
			attributes.put(new Name("Can-Set-Native-Method-Prefix"), "true"); // Can-Set-Native-Method-Prefix: true
			attributes.put(SPECIFICATION_TITLE, Library.NAME); // Specification-Title: Dynagent
			attributes.put(SPECIFICATION_VERSION, Library.VERSION); // Specification-Version: 0.1-build.1
			attributes.put(SPECIFICATION_VENDOR, Library.AUTHOR); // Specification-Vendor: JustHuman228
			attributes.put(IMPLEMENTATION_TITLE, Library.INTERNAL_NAME); // Implementation-Title: dynagent
			attributes.put(IMPLEMENTATION_VERSION, Library.VERSION); // Implementation-Version: 0.1-build.1
			attributes.put(IMPLEMENTATION_VENDOR, Library.AUTHOR); // Implementation-Vendor: JustHuman228
		}

		try {

			final File agent = createTempFile("dynagentImpl", ".jar").getAbsoluteFile();

			if (FileCleaner.install()) {

				addTarget(agent);

			} else {

				agent.deleteOnExit();
			}

			try (final JarOutputStream jar = new JarOutputStream(new FileOutputStream(agent), manifest)) {

				final String resource = classname.replace('.', '/') + ".class";

				final String[] entries = resource.split("/");

				final StringBuilder path = new StringBuilder(entries.length);

				for (int i = 0; i < entries.length - 1; i++) {

					path.append(entries[i]).append("/");

					final JarEntry entry = new JarEntry(path.toString());

					try {

						jar.putNextEntry(entry);

					} catch (final IOException ignored) {

					} finally {

						jar.closeEntry();
					}
				}

				final JarEntry entry = new JarEntry(path.append(entries[entries.length - 1]).toString());

				try {

					jar.putNextEntry(entry);

					final InputStream bytecode = DynagentImpl.class.getResourceAsStream("/" + path);

					if (bytecode != null) {

						try (bytecode) {

							bytecode.transferTo(jar);

						} catch (final IOException ignored) {

						} finally {

							jar.flush();
						}
					}

				} catch (final IOException ignored) {

				} finally {

					jar.closeEntry();
				}

				return agent.getPath();

			} catch (final SecurityException | IOException failed) {

				return null;
			}

		} catch (final IOException failed) {

			return null;
		}
	}

	private static String buildInstaller() throws Error {

		final String classname = DynagentInstaller.class.getTypeName(); // A main class to be included in a jar file

		// Write manifest file:
		final Manifest manifest = new Manifest();

		{

			final Attributes attributes = manifest.getMainAttributes();

			attributes.put(MANIFEST_VERSION, "1.0"); // Manifest-Version: 1.0
			attributes.put(MAIN_CLASS, classname); // Main-Class: com.github.justhm228.dynagent.internal.agent.DynagentInstaller
			attributes.put(SPECIFICATION_TITLE, Library.NAME); // Specification-Title: Dynagent
			attributes.put(SPECIFICATION_VERSION, Library.VERSION); // Specification-Version: 0.1-build.1
			attributes.put(SPECIFICATION_VENDOR, Library.AUTHOR); // Specification-Vendor: JustHuman228
			attributes.put(IMPLEMENTATION_TITLE, Library.INTERNAL_NAME); // Implementation-Title: dynagent
			attributes.put(IMPLEMENTATION_VERSION, Library.VERSION); // Implementation-Version: 0.1-build.1
			attributes.put(IMPLEMENTATION_VENDOR, Library.AUTHOR); // Implementation-Vendor: JustHuman228
		}

		try {

			// Generate a temporary file:
			final File installer = createTempFile("dynagentInstaller", ".jar").getAbsoluteFile();

			if (FileCleaner.install()) { // If we can delete the file safely:

				addTarget(installer); // Add it as a target to "safe deletion algorithm"

			} else { // Or else...

				installer.deleteOnExit(); // Use Java's built-in "deletion on exit algorithm"
			}

			// Write a jar file:
			try (final JarOutputStream jar = new JarOutputStream(new FileOutputStream(installer), manifest)) {

				final String resource = classname.replace('.', '/') + ".class"; // A zip path of a main class in a jar file

				final String[] entries = resource.split("/");

				final StringBuilder path = new StringBuilder(entries.length); // A temporary "path builder"

				// Write packages to a jar file:
				for (int i = 0; i < entries.length - 1; i++) {

					path.append(entries[i]).append("/");

					final JarEntry entry = new JarEntry(path.toString());

					try {

						jar.putNextEntry(entry); // Write a package to a jar file

					} catch (final IOException ignored) {

						// Do nothing if something went wrong

					} finally {

						jar.closeEntry(); // Flush entry
					}
				}

				// Write a main class to a jar file:
				final JarEntry entry = new JarEntry(path.append(entries[entries.length - 1]).toString());

				try {

					jar.putNextEntry(entry); // Prepare a main class to be written

					final InputStream bytecode = DynagentInstaller.class.getResourceAsStream("/" + path);

					if (bytecode != null) { // If a main class is accessible...

						try (bytecode) {

							bytecode.transferTo(jar); // Write a main class

						} catch (final IOException ignored) {

							// Do nothing if something went wrong

						} finally {

							jar.flush(); // Flush the data
						}
					}

				} catch (final IOException ignored) {

					// Do nothing if something went wrong

				} finally {

					jar.closeEntry(); // Flush entry
				}

				return installer.getPath(); // If build is successfully ended

			} catch (final SecurityException | IOException failed) {

				return null; // If something went wrong
			}

		} catch (final IOException failed) {

			return null; // If something went wrong
		}
	}

	private static String findJava() throws Error {

		try {

			final Optional<String> executable = current().info().command(); // Find current process

			if (executable.isPresent()) {

				final String java = executable.get();

				if (java.matches("^(?<path>.*java)(?<extension>\\..*)?$")) { // If the current process is already launched with a valid `java`...

					return java; // Return a valid `java` executable path
				}

				{

					final Pattern jawaw = compile("^(?<path>.*javaw)(?<extension>\\..*)?$");

					final Matcher match = jawaw.matcher(java);

					if (match.matches()) { // If the current process is launched with `javaw` (not `java` we need)...

						// Find a valid `java` to be used:
						final String path = match.group("path");

						final String extension = match.group("extension");

						final File javaCandidate = new File(extension == null ? path.substring(0, java.lastIndexOf('w')) : path.substring(0, 'w') + extension).getAbsoluteFile();

						if (javaCandidate.exists() && javaCandidate.canExecute()) {

							return javaCandidate.getPath(); // If a valid `java` is found
						}
					}
				}
			}

		} catch (final UnsupportedOperationException | SecurityException ignored) {

			// If the current process isn't found
		}

		return null;
	}
}
