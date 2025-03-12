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

package com.github.justhm228.dynagent.internal.file;

import java.io.*;
import static java.lang.Runtime.*;
import java.util.*;

public final class FileCleaner {

	private static final Thread HOOK = new Thread(FileCleaner::cleanup, "Dynagent Cleaner");

	private static final List<File> TARGETS = new ArrayList<>(1);

	private FileCleaner() throws UnsupportedOperationException {

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!");
	}

	public static boolean install() {

		try {

			getRuntime().addShutdownHook(HOOK);
			return true;

		} catch (final SecurityException | IllegalStateException failed) {

			return false;

		} catch (final IllegalArgumentException alreadyInstalled) {

			return true;
		}
	}

	public static boolean isInstalled() {

		return install() && uninstall(false);
	}

	public static boolean uninstall() {

		return uninstall(true);
	}

	public static void addTarget(final File target) {

		if (target == null) {

			return;
		}

		synchronized (TARGETS) {

			TARGETS.add(target.getAbsoluteFile());
		}
	}

	public static void addTarget(final String target) {

		addTarget(target == null ? null : new File(target));
	}

	public static void cleanup() {

		synchronized (TARGETS) {

			for (final File target : TARGETS) {

				try {

					final boolean ignored = target.delete();

				} catch (final SecurityException ignored) {

				}
			}
		}
	}

	public static void clean(File target) {

		if (target == null) {

			return;
		}

		synchronized (TARGETS) {

			{

				final boolean ignored = (target = target.getAbsoluteFile()).delete();
			}

			TARGETS.remove(target);
		}
	}

	public static void clean(final String target) {

		clean(target == null ? null : new File(target));
	}

	public static void removeTarget(final File target) {

		if (target == null) {

			return;
		}

		synchronized (TARGETS) {

			TARGETS.remove(target.getAbsoluteFile());
		}
	}

	public static void removeTarget(final String target) {

		removeTarget(target == null ? null : new File(target));
	}

	private static boolean uninstall(final boolean cleanup) {

		try {

			getRuntime().removeShutdownHook(HOOK);

			if (cleanup) {

				cleanup();
			}

		} catch (final SecurityException | IllegalStateException ignored) {

		}

		return true;
	}
}
