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

import org.junit.jupiter.api.*;
import org.opentest4j.TestAbortedException;
import com.github.justhm228.dynagent.agent.Dynagent;
import com.github.justhm228.dynagent.internal.agent.DynagentImpl;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AttachTest {

	public AttachTest() {

		super();
	}

	@DisplayName("Dynamic Installation")
	@Test()
	@Order(0)
	public void testInstall() throws TestAbortedException, IllegalStateException {

		Assumptions.assumeFalse(Dynagent.isInstalled());
		Dynagent.install(TestConstants.WHITELIST);
		Assertions.assertNotNull(Dynagent.getAgent());
	}

	@DisplayName("Installation Check")
	@Test()
	@Order(1)
	public void testInstallCheck() throws AssertionError {

		Assertions.assertTrue(Dynagent.isInstalled());
	}

	@DisplayName("Internal State")
	@Test()
	@Order(2)
	@Tag("internal")
	public void testInternal() {

		Assertions.assertTrue(DynagentImpl.isLoaded());
	}

	@DisplayName("Startup Phase")
	@Test()
	@Order(3)
	@Tag("internal")
	public void testState() {

		DynagentImpl.isStartup();
	}
}
