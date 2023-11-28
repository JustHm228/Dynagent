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

import com.github.justhm228.dynagent.agent.*;
import static java.lang.Class.*;
import static java.lang.StackWalker.*;
import static java.lang.StackWalker.Option.*;
import java.lang.instrument.*;

public final class DynagentImpl {

	private static volatile Instrumentation agent = null; // A valid `Instrumentation` instance

	private static volatile boolean startup = false; // If Dynagent is installed at startup (via JVM options)

	private static final Object LOCK = new Object(); // An "installation lock"

	private DynagentImpl() throws Error, UnsupportedOperationException {

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!");
	}

	/**
	 * Checks if Dynagent is installed.
	 *
	 * @return true if Dynagent is installed; otherwise - false.
	 * @throws Error If something went wrong in the JVM.
	 */
	public static boolean isLoaded() throws Error {

		synchronized (LOCK) {

			final Instrumentation agent = DynagentImpl.agent; // <- Efficient use of `volatile` fields

			return agent != null; // <- If Dynagent is installed, then `agent` is non-null
		}
	}

	/**
	 * Returns a valid instance of Instrumentation to be used.
	 *
	 * <p>
	 *     <b>
	 *         Please note that this is method can be called only from the internal API.
	 *         On any call not from the internal API, this method will throw an IllegalCallerException!
	 *     </b>
	 * </p>
	 *
	 * @return
	 * @throws Error If something went wrong in the JVM.
	 * @throws IllegalStateException If Dynagent isn't installed yet.
	 * @throws IllegalCallerException If the caller doesn't exist, can't be found or doesn't belong to the internal API.
	 */
	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Instrumentation getAgent() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return getAgent(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return getAgent(null); // Call without caller
		}
	}

	/**
	 * Checks if Dynagent was installed on JVM startup (via JVM option).
	 *
	 * <p>
	 *     <b>Please note that this is a very experimental feature, which is even not provided in the stable API.</b>
	 * </p>
	 *
	 * The use of this method isn't recommended because it can be deleted at any time for 2 reasons:
	 *
	 * <ul>
	 *     <li>Obvious pointlessness (at least for now, I simply don't see the point in the program knowing at what stage the Java agent was attached).</li>
	 *     <li>Complicating the data structure, which could potentially lead to difficult-to-debug errors (long to explain).</li>
	 * </ul>
	 *
	 * @return true if Dynagent was installed on JVM startup (via JVM option); otherwise - false.
	 * @throws Error If something went wrong in the JVM.
	 * @throws IllegalStateException If Dynagent isn't installed yet.
	 */
	public static boolean isStartup() throws Error, IllegalStateException {

		synchronized (LOCK) {

			requireInstalled(); // Require installation of Dynagent (or throw an exception)
			return startup;
		}
	}

	/**
	 * Installs a Java agent dynamically (via the Attach API).
	 *
	 * <p>
	 *     This is an internal implementation of dynamic Dynagent installation.
	 * 	   This method will be called automatically on attach to the JVM (by the Attach API).
	 * </p>
	 *
	 * <p>
	 *     <b>You shouldn't call it directly!</b>
	 * </p>
	 *
	 * @param options Options passed to the Java agent.
	 * @param agent An Instrumentation instance passed to the Java agent.
	 * @throws Error If something went wrong in the JVM.
	 */
	public static void agentmain(final String options, final Instrumentation agent) throws Error {

		if (agent != null) { // If we can install Dynagent right now...

			synchronized (LOCK) {

				final Instrumentation installed = DynagentImpl.agent; // <- Efficient use of `volatile` fields

				if (installed == null) { // And if Dynagent isn't installed yet...

					DynagentImpl.agent = agent; // Install Dynagent
				}
			}
		}
	}

	/**
	 * Installs a Java agent via JVM option (on JVM startup).
	 *
	 * <p>
	 *     This is an internal implementation of Dynagent installation on JVM startup (via JVM option).
	 *     This method will be called automatically and only once.
	 * </p>
	 *
	 * <p>
	 *     <b>You shouldn't call it directly!</b>
	 * </p>
	 *
	 * @param options Options passed to the Java agent.
	 * @param agent An Instrumentation instance passed to the Java agent.
	 * @throws Error If something went wrong in the JVM.
	 */
	public static void premain(final String options, final Instrumentation agent) throws Error {

		agentmain(options, agent); // Install Dynagent like there's nothing special

		{

			final Instrumentation installed = DynagentImpl.agent; // <- Efficient use of `volatile` fields

			if (installed != null) { // If Dynagent was successfully installed...

				synchronized (LOCK) {

					final boolean startup = DynagentImpl.startup; // <- Efficient use of `volatile` fields

					if (!startup) { // <- To avoid useless changes of a `volatile` field

						DynagentImpl.startup = true; // Mark Dynagent as installed at JVM startup (via JVM option)
					}
				}
			}
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Instrumentation getAgent(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation of Dynagent (or throw an exception)

		try {

			forName("com.github.justhm228.dynagent.agent.Dynagent");

			// This state will be reached if we're in a valid environment to return an `Instrumentation` instance
			// to someone. If the above code will throw `ClassNotFoundException`, then we shouldn't do anything.

		} catch (final ClassNotFoundException notFound) {

			// This will happen if this method is called from a temporary jar file generated at runtime
			// by the program itself. If this method is called by an error from the file, it won't do anything
			// which will prevent `NoClassDefFoundError` to be thrown. This is just a precaution, nothing more.
			return null;
		}

		if (caller == null) {

			throw new IllegalCallerException(); // The method must have a valid caller to return a valid instance.
		}

		synchronized (LOCK) {

			final Instrumentation agent = DynagentImpl.agent; // <- Efficient use of `volatile` fields

			// Just a security check to make sure this internal API was called from the stable API
			// and not from the user. This should be the last check in the method.
			return caller == DynagentImpl.class || caller == Dynagent.class ? agent : null;
		}
	}

	private static void requireInstalled() throws Error, IllegalStateException {

		synchronized (LOCK) {

			if (!isLoaded()) {

				throw new IllegalStateException(); // Require installation
			}
		}
	}
}
