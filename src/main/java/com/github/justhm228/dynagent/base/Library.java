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

package com.github.justhm228.dynagent.base;

import static java.util.Objects.*;

public final class Library {

	public static final String AUTHOR = requireNonNullElse(Library.class.getPackage().getSpecificationVendor(), "JustHuman228");

	public static String URL = "https://github.com/JustHm228/Dynagent";

	public static final String NAME = requireNonNullElse(Library.class.getPackage().getSpecificationTitle(), "Dynagent");

	public static final String INTERNAL_NAME = "dynagent";

	public static final String DESCRIPTION = "A small library which allows you to dynamically load a Java agent to the current VM";

	public static final String VERSION = requireNonNullElse(Library.class.getPackage().getSpecificationVersion(), "0.1-build.1");

	public static final int VERSION_CODE = 0;

	private Library() throws Error, UnsupportedOperationException {

		super();
		throw new UnsupportedOperationException("An instance of this type (" + getClass().getTypeName() + ") can't be instantiated with a constructor!");
	}
}
