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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.github.justhm228.dynagent.agent.Dynagent;

public class UtilityTest {

	public UtilityTest() {

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

	@DisplayName("Dynagent.isModifiableModule(Module)")
	@Test()
	public void test_isModifiableModule() {

		System.out.println(getAgent().isModifiableModule(getClass().getModule()));
	}

	@DisplayName("Dynagent.isModifiableClass(Class)")
	@Test()
	public void test_isModifiableClass() {

		System.out.println(getAgent().isModifiableClass(getClass()));
	}

	@DisplayName("Dynagent.isRetransformClassesSupported()")
	@Test()
	public void test_isRetransformClassesSupported() {

		System.out.println(getAgent().isRetransformClassesSupported());
	}

	@DisplayName("Dynagent.isRedefineClassesSupported()")
	@Test()
	public void test_isRedefineClassesSupported() {

		System.out.println(getAgent().isRedefineClassesSupported());
	}

	@DisplayName("Dynagent.isNativeMethodPrefixSupported()")
	@Test()
	public void test_isNativeMethodPrefixSupported() {

		System.out.println(getAgent().isNativeMethodPrefixSupported());
	}
}
