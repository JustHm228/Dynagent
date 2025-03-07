/*
 * The MIT License
 *
 * Copyright (c) 2025 JustHuman228
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

package com.github.justhm228.dynagent.test;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import org.junit.jupiter.api.*;
import com.github.justhm228.dynagent.agent.Dynagent;

import static java.util.Arrays.deepToString;

public class LoaderTest {

	public LoaderTest() {

		super();
	}

	@BeforeAll()
	public static void setup() {

		if (!Dynagent.isInstalled()) {

			Dynagent.install(TestConstants.WHITELIST);
		}
	}

	private static Instrumentation getAgent() {

		final Instrumentation agent = Dynagent.getAgent();

		Assertions.assertNotNull(agent);
		return agent;
	}

	@DisplayName("Dynagent.appendToBootstrapClassLoaderSearc(JarFile)")
	@Test()
	@Disabled("Implement later")
	public void test_appendToBootstrapClassLoaderSearch() {

		// TODO 07.03.2025: Implement later.
//		getAgent().appendToBootstrapClassLoaderSearch();
	}

	@DisplayName("Dynagent.appendToBootstrapClassLoaderSearc(JarFile)")
	@Test()
	@Disabled("Implement later")
	public void test_appendToSystemClassLoaderSearch() {

		// TODO 07.03.2025: Implement later.
//		getAgent().appendToSystemClassLoaderSearch();
	}

	@DisplayName("Dynagent.getAllLoadedClasses()")
	@Test()
	public void test_getAllLoadedClasses() {

		final Class<?>[] loaded = getAgent().getAllLoadedClasses();

		Assertions.assertNotNull(loaded);
		Assertions.assertNotEquals(0, loaded.length);
		Assertions.assertTrue(Arrays.stream(loaded).anyMatch((cls) -> cls.getName().startsWith("java.lang.")));
		System.out.println(deepToString(loaded));
	}

	@DisplayName("Dynagent.getInitiatedClasses(ClassLoader)")
	@Test()
	public void test_getInitiatedClasses() {

		final Class<?>[] initiated = getAgent().getInitiatedClasses(getClass().getClassLoader());

		Assertions.assertNotNull(initiated);
		System.out.println(deepToString(initiated));
	}
}
