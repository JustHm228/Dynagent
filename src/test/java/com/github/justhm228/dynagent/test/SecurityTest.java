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
import com.github.justhm228.dynagent.agent.Dynagent;
import com.github.justhm228.dynagent.internal.agent.DynagentImpl;

public class SecurityTest {

	public SecurityTest() {

		super();
	}

	@BeforeAll()
	public static void setup() {

		if (!Dynagent.isInstalled()) {

			Dynagent.install(TestConstants.WHITELIST);
		}
	}

	@DisplayName("Valid access")
	@Test()
	public void testValidAccess() {

		Assertions.assertNotNull(Dynagent.getAgent());
	}

	static final class WhitelistedZone {

		public static void callWhitelisted() {

			Assertions.assertNotNull(Dynagent.getAgent());
		}
	}

	@DisplayName("Whitelisted access")
	@Test()
	public void testWhitelistedAccess() {

		WhitelistedZone.callWhitelisted();
	}

	@DisplayName("Invalid Access")
	@Test()
	public void testInvalidAccess() {

		final class RestrictedZone {

			public static void callRestricted() {

				Assertions.assertNotNull(Dynagent.getAgent());
			}
		}

		Assertions.assertThrows(IllegalCallerException.class, RestrictedZone::callRestricted);
	}

	@DisplayName("Internal Access")
	@Test()
	@Tag("internal")
	public void testInternalAccess() {

		try {

			Assertions.assertNull(DynagentImpl.getAgent());

		} catch (final IllegalCallerException ignored) {

		}
	}
}
