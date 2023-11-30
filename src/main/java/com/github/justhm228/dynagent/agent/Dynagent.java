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

	private static final Set<Class<?>> WHITELIST = new HashSet<>(1);

	private Dynagent() throws Error, UnsupportedOperationException {

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!");
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean install(final Class<?>[] whitelisted) throws Error, NullPointerException, IllegalStateException, IllegalCallerException {

		try {

			return install(whitelisted, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return install((Class<?>) null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean install() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return install(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return install((Class<?>) null); // Call without caller
		}
	}

	public static boolean isInstalled() throws Error {

		return DynagentInstaller.isInstalled(); // <- Use of internal API
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Instrumentation getAgent() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return getAgent(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return null; // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static long getObjectSize(final Object instance) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		try {

			return getObjectSize(instance, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return getObjectSize(instance, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isModifiableModule(final Module module) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		try {

			return isModifiableModule(module, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isModifiableModule(module, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineModule(final Module module, final Set<Module> reads, final Map<String, Set<Module>> exports, final Map<String, Set<Module>> opens, final Set<Class<?>> uses, final Map<Class<?>, List<Class<?>>> provides) throws Error, IllegalStateException, IllegalCallerException, IllegalArgumentException, UnmodifiableModuleException {

		try {

			redefineModule(module, reads, exports, opens, uses, provides, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineModule(module, reads, exports, opens, uses, provides, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void appendToBootstrapClassLoaderSearch(final JarFile jar) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		try {

			appendToBootstrapClassLoaderSearch(jar, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			appendToBootstrapClassLoaderSearch(jar, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void appendToSystemClassLoaderSearch(final JarFile jar) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		try {

			appendToSystemClassLoaderSearch(jar, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			appendToSystemClassLoaderSearch(jar, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Class<?>[] getAllLoadedClasses() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return getAllLoadedClasses(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

 		} catch (final SecurityException inaccessible) {

			return getAllLoadedClasses(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static Class<?>[] getInitiatedClasses(final ClassLoader classloader) throws Error, IllegalStateException, IllegalCallerException {

		try {

			return getInitiatedClasses(classloader, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return getInitiatedClasses(classloader, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isModifiableClass(final Class<?> aClass) throws Error, IllegalStateException, IllegalCallerException {

		try {

			return isModifiableClass(aClass, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isModifiableClass(aClass, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isRetransformClassesSupported() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return isRetransformClassesSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isRetransformClassesSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isRedefineClassesSupported() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return isRedefineClassesSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isRedefineClassesSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static boolean isNativeMethodPrefixSupported() throws Error, IllegalStateException, IllegalCallerException {

		try {

			return isNativeMethodPrefixSupported(getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			return isNativeMethodPrefixSupported(null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final ClassDefinition[] classes) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(classes, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(classes, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final ClassDefinition aClass) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final File bytecode) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final InputStream bytecode) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final ByteBuffer bytecode) throws Error, NullPointerException, IllegalStateException, IllegalCallerException,
			ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final int offset, final int length) throws Error, NullPointerException, IndexOutOfBoundsException, IllegalStateException, ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, bytecode, offset, length, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, offset, length, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitive()
	@jdk.internal.vm.annotation.ForceInline()
	public static void redefineClasses(final Class<?> aClass, final byte[] bytecode) throws Error, NullPointerException, IllegalStateException, ClassNotFoundException, UnmodifiableClassException {

		try {

			redefineClasses(aClass, bytecode, getInstance(RETAIN_CLASS_REFERENCE).getCallerClass()); // Call with caller

		} catch (final SecurityException inaccessible) {

			redefineClasses(aClass, bytecode, null); // Call without caller
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean install(final Class<?>[] whitelisted, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException {

		requireNonNull(whitelisted);

		final boolean filled = whitelisted.length > 0;

		if (filled) {

			for (final Class<?> aClass : whitelisted) {

				requireNonNull(aClass);
			}
		}

		requireNotInstalled();

		if (caller == null) {

			throw new IllegalCallerException();
		}

		synchronized (WHITELIST) {

			if (install(caller)) {

				if (filled) {

					try {

						WHITELIST.addAll(of(whitelisted));
						return true;

					} catch (final UnsupportedOperationException | IllegalArgumentException failed) {

						return false;
					}
				}

				return true;
			}

			return false;
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean install(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireNotInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		synchronized (WHITELIST) {

			if (!DynagentInstaller.install()) {

				return false;
			}

			try {

				WHITELIST.add(caller);
				return true;

			} catch (final UnsupportedOperationException | IllegalArgumentException failed) {

				return false;
			}
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Instrumentation getAgent(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		synchronized (WHITELIST) {

			if (WHITELIST.contains(caller)) {

				return DynagentImpl.getAgent();
			}

			throw new IllegalCallerException();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static long getObjectSize(final Object instance, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.getObjectSize(instance);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isModifiableModule(final Module module, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.isModifiableModule(module);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineModule(final Module module, final Set<Module> reads, final Map<String, Set<Module>> exports, final Map<String, Set<Module>> opens, final Set<Class<?>> uses, final Map<Class<?>, List<Class<?>>> provides, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, IllegalArgumentException, UnmodifiableModuleException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			agent.redefineModule(module, reads, exports, opens, uses, provides);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void appendToBootstrapClassLoaderSearch(final JarFile jar, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			agent.appendToBootstrapClassLoaderSearch(jar);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void appendToSystemClassLoaderSearch(final JarFile jar, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			agent.appendToSystemClassLoaderSearch(jar);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Class<?>[] getAllLoadedClasses(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.getAllLoadedClasses();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static Class<?>[] getInitiatedClasses(final ClassLoader classloader, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.getInitiatedClasses(classloader);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isModifiableClass(final Class<?> aClass, final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException, NullPointerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.isModifiableClass(aClass);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isRetransformClassesSupported(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.isRetransformClassesSupported();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isRedefineClassesSupported(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.isRedefineClassesSupported();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static boolean isNativeMethodPrefixSupported(final Class<?> caller) throws Error, IllegalStateException, IllegalCallerException {

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			return agent.isNativeMethodPrefixSupported();
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final ClassDefinition[] classes, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(classes);

		final boolean filled = classes.length > 0;

		if (filled) {

			if (classes.length == 1) {

				redefineClasses(requireNonNull(classes[0]), caller);
				return;
			}

			for (final ClassDefinition aClass : classes) {

				requireNonNull(aClass);
			}
		}

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		if (filled) {

			final Instrumentation agent = getAgent(caller);

			if (agent == null) {

				throw new IllegalCallerException();
			}

			agent.redefineClasses(classes);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final ClassDefinition aClass, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		final Instrumentation agent = getAgent(caller);

		if (agent == null) {

			throw new IllegalCallerException();
		}

		agent.redefineClasses(aClass);
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final File bytecode, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireNonNull(bytecode);
		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		try (final FileInputStream in = new FileInputStream(bytecode)) {

			redefineClasses(aClass, in, caller);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final InputStream bytecode, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, IOException,
			ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireNonNull(bytecode);
		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		redefineClasses(aClass, bytecode.readAllBytes(), caller);
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final ByteBuffer bytecode, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException,
			ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireNonNull(bytecode);
		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final byte[] bytes = new byte[bytecode.remaining()];

			for (int i = 0; bytecode.hasRemaining(); i++) {

				bytes[i] = bytecode.get();
			}

			redefineClasses(aClass, bytes, caller);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final int offset, final int length, final Class<?> caller) throws Error, NullPointerException, IndexOutOfBoundsException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireNonNull(bytecode);

		if ((offset < 0 || offset > bytecode.length || offset > length) || length > bytecode.length) {

			throw new IndexOutOfBoundsException();
		}

		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		{

			final byte[] bytes = new byte[length];

			System.arraycopy(bytecode, offset, bytes, 0, length);
			redefineClasses(aClass, bytes, caller);
		}
	}

	@jdk.internal.reflect.CallerSensitiveAdapter()
	private static void redefineClasses(final Class<?> aClass, final byte[] bytecode, final Class<?> caller) throws Error, NullPointerException, IllegalStateException, IllegalCallerException, ClassNotFoundException, UnmodifiableClassException {

		requireNonNull(aClass);
		requireNonNull(bytecode);
		requireInstalled(); // Require installation

		if (caller == null) {

			throw new IllegalCallerException();
		}

		redefineClasses(new ClassDefinition(aClass, bytecode), caller);
	}

	private static void requireInstalled() throws Error, IllegalStateException {

		if (!isInstalled()) {

			throw new IllegalStateException(); // Require installation
		}
	}

	private static void requireNotInstalled() throws Error, IllegalStateException {

		if (isInstalled()) {

			throw new IllegalStateException(); // Require not installed
		}
	}
}
