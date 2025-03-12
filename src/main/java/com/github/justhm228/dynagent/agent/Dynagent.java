/*
 * The MIT License
 *
 * Copyright (c) 2023-2025 JustHuman228
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

package com.github.justhm228.dynagent.agent;

import com.github.justhm228.dynagent.internal.agent.*;
import java.io.*;
import static java.lang.StackWalker.*;
import static java.lang.StackWalker.Option.*;
import java.lang.instrument.*;
import java.nio.*;
import java.util.*;
import static java.util.Objects.*;
import static java.util.Set.*;
import java.util.jar.*;

public final class Dynagent {

	// Whitelisted classes:
	private static final Set<Class<?>> WHITELIST = new HashSet<>(1); // <- At least a single class WILL be whitelisted

	private Dynagent() throws UnsupportedOperationException {

		// This constructor is required only to fully prevent the default instantiation of this class.
		// This class isn't needed to have any instances of it, so they're useless. Of course, an instance of
		// this class is still can be instantiated with some "hacks", but who will do this and for what?

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!"); // Prevent instantiation
	}

	// --------------------------- Stable API ---------------------------

	// ...
	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean install(final Class<?>[] whitelisted) throws NullPointerException, IllegalStateException, IllegalCallerException {

		// Call to internal implementation:

		try {

			return install(whitelisted, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return install((Class<?>) null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean install() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:

		try {

			return install(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return install((Class<?>) null); // Call without caller
		}
	}

	public static boolean isInstalled() {

		return DynagentInstaller.isInstalled(); // Call to internal API
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Instrumentation getAgent() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return getAgent(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return null; // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static long getObjectSize(final Object instance) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Call to internal implementation:
		try {

			return getObjectSize(instance, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return getObjectSize(instance, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isModifiableModule(final Module module) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Call to internal implementation:
		try {

			return isModifiableModule(module, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isModifiableModule(module, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineModule(final Module module, final Set<Module> reads, final Map<String, Set<Module>> exports, final Map<String, Set<Module>> opens, final Set<Class<?>> uses, final Map<Class<?>, List<Class<?>>> provides) throws IllegalStateException, IllegalCallerException, IllegalArgumentException, UnmodifiableModuleException {

		// Call to internal implementation:
		try {

			redefineModule(module, reads, exports, opens, uses, provides, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineModule(module, reads, exports, opens, uses, provides, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void appendToBootstrapClassLoaderSearch(final JarFile jar) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Call to internal implementation:
		try {

			appendToBootstrapClassLoaderSearch(jar, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			appendToBootstrapClassLoaderSearch(jar, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void appendToSystemClassLoaderSearch(final JarFile jar) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Call to internal implementation:
		try {

			appendToSystemClassLoaderSearch(jar, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			appendToSystemClassLoaderSearch(jar, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Class<?>[] getAllLoadedClasses() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return getAllLoadedClasses(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

 		} catch (final SecurityException inaccessible) {

			return getAllLoadedClasses(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Class<?>[] getInitiatedClasses(final ClassLoader classloader) throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return getInitiatedClasses(classloader, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return getInitiatedClasses(classloader, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isModifiableClass(final Class<?> aClass) throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return isModifiableClass(aClass, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isModifiableClass(aClass, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isRetransformClassesSupported() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return isRetransformClassesSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isRetransformClassesSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isRedefineClassesSupported() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return isRedefineClassesSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isRedefineClassesSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isNativeMethodPrefixSupported() throws IllegalStateException, IllegalCallerException {

		// Call to internal implementation:
		try {

			return isNativeMethodPrefixSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isNativeMethodPrefixSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final ClassDefinition[] classes) throws NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(classes, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(classes, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final ClassDefinition aClass) throws NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final File bytecode) throws NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final InputStream bytecode) throws NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final ByteBuffer bytecode) throws NullPointerException, IllegalStateException, IllegalCallerException,
			ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final int offset, final int length) throws NullPointerException, IndexOutOfBoundsException, IllegalStateException, ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, bytecode, offset, length, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, offset, length, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final byte[] bytecode) throws NullPointerException, IllegalStateException, ClassNotFoundException, UnmodifiableClassException {

		// Call to internal implementation:
		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	// --------------------------- Internal Implementation ---------------------------

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean install(final Class<?>[] whitelisted, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException {

		// Some checks:
		requireNonNull(whitelisted); // Require non-null whitelist

		final boolean filled = whitelisted.length > 0; // If there's any class to be whitelisted

		if (filled) { // If there's any class to be whitelisted...

			// ...

			// Check every class for nullability:
			for (final Class<?> aClass : whitelisted) {

				requireNonNull(aClass);
			}
		}

		requireNotInstalled(); // Require not installed

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Install Dynagent:
		synchronized (WHITELIST) {

			if (install(caller)) { // If internal installation succeed...

				if (filled) { // If there's any class to be whitelisted...

					try {

						WHITELIST.addAll(of(whitelisted)); // Whitelist all the specified classes
						return true; // If installation is successfully finished

					} catch (final UnsupportedOperationException | IllegalArgumentException failed) {

						return false; // If installation is failed
					}
				}

				return true; // If there's nothing to whitelist - a successful finish
			}

			return false; // If installation failed
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean install(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireNotInstalled(); // Require not installed

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Install Dynagent:
		synchronized (WHITELIST) {

			if (!DynagentInstaller.install()) {

				// ...

				return false; // If installation is failed
			}

			try {

				WHITELIST.add(caller);
				return true; // If installation is successfully finished

			} catch (final UnsupportedOperationException | IllegalArgumentException failed) {

				return false; // If installation is failed
			}
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Instrumentation getAgent(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Do a security check and return an `Instrumentation`:
		synchronized (WHITELIST) {

			if (WHITELIST.contains(caller)) {

				return DynagentImpl.getAgent(); // Return an `Instrumentation` if the caller is whitelisted
			}

			throw new IllegalCallerException(); // Require a whitelisted caller
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static long getObjectSize(final Object instance, final Class<?> caller) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Find the in-memory size of an object:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.getObjectSize(instance); // Find the in-memory size
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isModifiableModule(final Module module, final Class<?> caller) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Check for module modifiability:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.isModifiableModule(module); // Do the check
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineModule(final Module module, final Set<Module> reads, final Map<String, Set<Module>> exports, final Map<String, Set<Module>> opens, final Set<Class<?>> uses, final Map<Class<?>, List<Class<?>>> provides, final Class<?> caller) throws IllegalStateException, IllegalCallerException, IllegalArgumentException, UnmodifiableModuleException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Redefine module:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			agent.redefineModule(module, reads, exports, opens, uses, provides); // Do the redefinition
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void appendToBootstrapClassLoaderSearch(final JarFile jar, final Class<?> caller) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Append to boot classpath:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			agent.appendToBootstrapClassLoaderSearch(jar); // Append
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void appendToSystemClassLoaderSearch(final JarFile jar, final Class<?> caller) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Append to classpath:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			agent.appendToSystemClassLoaderSearch(jar); // Append
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Class<?>[] getAllLoadedClasses(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Find all loaded classes:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.getAllLoadedClasses(); // Do the search
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Class<?>[] getInitiatedClasses(final ClassLoader classloader, final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Find initiated classes:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.getInitiatedClasses(classloader); // Do the search
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isModifiableClass(final Class<?> aClass, final Class<?> caller) throws IllegalStateException, IllegalCallerException, NullPointerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Check for class modifiability:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.isModifiableClass(aClass); // Do the check
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isRetransformClassesSupported(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Check for class retransformation support:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.isRetransformClassesSupported(); // Do the check
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isRedefineClassesSupported(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Check for class redefinition support:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.isRedefineClassesSupported(); // Do the check
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isNativeMethodPrefixSupported(final Class<?> caller) throws IllegalStateException, IllegalCallerException {

		// Some checks:
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Check for native method prefix support:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			return agent.isNativeMethodPrefixSupported(); // Do the check
		}
	}

	// ...
	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final ClassDefinition[] classes, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Some checks:
		requireNonNull(classes); // Require a non-null array

		final boolean filled = classes.length > 0; // If there's any classes to be redefined

		if (filled) { // If there's any classes to be redefined...

			if (classes.length == 1) { // If there's only a single class to be redefined...

				// ...

				redefineClasses(requireNonNull(classes[0]), caller); // Require it to be non-null and redirect the call for optimization
				return;
			}

			// Check every class for nullability:
			for (final ClassDefinition aClass : classes) {

				// ...

				requireNonNull(aClass);
			}
		}

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Redefine classes:
		if (filled) { // If there's any classes to be redefined...

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			agent.redefineClasses(classes); // Redefine the classes directly
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final ClassDefinition aClass, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Some checks:
		requireNonNull(aClass); // Require non-null definition
		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Redefine classes:
		{

			// Try access an `Instrumentation`:
			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				// ...

				throw new IllegalCallerException(); // Require a whitelisted caller
			}

			agent.redefineClasses(aClass); // Redefine the class directly
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final File bytecode, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		// Some checks:

		// Require non-null arguments:
		requireNonNull(aClass);
		requireNonNull(bytecode);

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Read all data from the file and call with it:
		try (final FileInputStream in = new FileInputStream(bytecode)) {

			// ...

			redefineClasses(aClass, in, caller);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final InputStream bytecode, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		// Some checks:

		// Require non-null arguments:
		requireNonNull(aClass);
		requireNonNull(bytecode);

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		redefineClasses(aClass, bytecode.readAllBytes(), caller); // Read all data and call with it
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final ByteBuffer bytecode, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException,
			ClassNotFoundException, UnmodifiableClassException {

		// Some checks:

		// Require non-null arguments:
		requireNonNull(aClass);
		requireNonNull(bytecode);

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Redefine classes:
		{

			// ...

			final byte[] bytes = new byte[bytecode.remaining()]; // A byte array of the data to be used

			// Write the data to be used to a byte array:
			for (int i = 0; bytecode.hasRemaining(); i++) {

				bytes[i] = bytecode.get();
			}

			redefineClasses(aClass, bytes, caller); // Call with "clipped" data
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final int offset, final int length, final Class<?> caller) throws NullPointerException, IndexOutOfBoundsException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Some checks:

		// Require non-null arguments:
		requireNonNull(aClass);
		requireNonNull(bytecode);

		if ((offset < 0 || offset > bytecode.length || offset > length) || length > bytecode.length) {

			// ...

			throw new IndexOutOfBoundsException();
		}

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		// Redefine classes:
		{

			// ...

			final byte[] bytes = new byte[length]; // Data to be used

			System.arraycopy(bytecode, offset, bytes, 0, length); // Clip unused data
			redefineClasses(aClass, bytes, caller); // Call with clipped data
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final Class<?> caller) throws NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		// Some checks:

		// Require non-null arguments:
		requireNonNull(aClass);
		requireNonNull(bytecode);

		requireInstalled(); // Require installation

		if (caller == null) {

			// ...

			throw new IllegalCallerException(); // Require a valid caller
		}

		redefineClasses(new ClassDefinition(aClass, bytecode), caller); // Wrap a valid byte array to `ClassDefinition`
	}

	private static void requireInstalled() throws IllegalStateException {

		if (!isInstalled()) {

			throw new IllegalStateException(); // Require installation
		}
	}

	private static void requireNotInstalled() throws IllegalStateException {

		if (isInstalled()) {

			throw new IllegalStateException(); // Require not installed
		}
	}
}
