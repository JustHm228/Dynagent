# Dynagent

**Dynagent** is a small library which allows you to dynamically load a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) to
the current VM. This allows you to dynamically create a valid `Instrumentation` instance and use all of
its functionality without having to separately compile the Java agent as a separate file and specify
the path to it via a JVM option or to restart the current process (which can be very useful for
bytecode manipulation). In order to access `Instrumentation`, all you need to do is this:

```java
if (Dynagent.install()) {
	
	final Instrumentation agent = Dynagent.getAgent();
	
	// Do anything you want with `agent`...
	
} else {
	
	// Failed to install!
}
```

Dynagent will take care of the rest, allowing you to completely focus your attention on your task!

## How Does It Work?

[Java agents](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)
can be very useful in solving some very special cases. In particular, they're useful for
the bytecode manipulation. It's this task, as luck would have it, that happens most often and
requires attaching a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) to
the current process. The problem of attaching a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) to
the current process is usually solved either by initially specifying the appropriate JVM option (which
isn't very practical for a number of reasons), or by restarting the current process in order to attach
the Java agent. This library was developed by me as an alternative solution when I was working on
another project that required a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) to be
dynamically attached to the current process, but with conditions that prevented me from using
ready-made solutions. Initially, I came up with the idea of using the
[Attach API](https://www.baeldung.com/java-instrumentation#dynamic-load), but in the progress I found that
it can't attach a Java agent to the current process. Then the idea came to my mind to launch
a separate process and through it attach a Java agent to the current process. It sounded difficult,
but I put in the work and... it worked. After several improvements I was able to optimize the code and save
it all in a single jar file. This library uses a slightly modified original implementation of that thing,
but the algorithm hasn't changed significantly. The attaching is still works this way:

1. Find a valid `java` to launch a temporary process.
2. Dynamically create a temporary jar file which will be a Java agent to be attached.
3. Dynamically create a temporary jar file which will attach a Java agent to the current process.
4. Launch a temporary process of an "installer" jar file and wait until it finishes.
5. Check if we have a valid `Instrumentation` instance now.
6. Delete temporary files.

The [Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)
itself is stored in the `DynagentImpl` class from the internal implementation of the library, and
the "installer" is stored in the `DynagentInstaller` class from the same package. **Direct access to
the internal API isn't recommended** (but only because the internal API can change a lot - direct access to
it won't cause any other problems, because it was designed with a big focus on stability).

It's planned in the future to create a custom implementation of `Implementation` that uses not
[Java agents](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html), but
native JVM functions, so that you can use it even without
[Java agents](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)
(this is useful at least because you don't have to create temporary files and hope for compatibility with
the platform). I don't know if it will be possible to do this, but if it does, then the `Dynagent` class
will have a new "optimized installation" method, which will certainly work faster than
the "default installation" method!

## How to Use It?

**Please note that Dynagent has a simple built-in security system that allows you to explicitly set
which classes will have access to the loaded
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)'s
functions, because the functions of
[Java agents](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) always
bypasses all Java security checks and can be very dangerous. Therefore, when calling any method except
`Dynagent.install()` and `Dynagent.isInstalled()`, the caller class will be checked. If the caller class
doesn't exist, cannot be checked, or isn't on the "security whitelist", an `IllegalCallerException` will be
thrown!** When calling the `Dynagent.install()` method, you can specify, separated by commas, the classes
that will be allowed use to the installed
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)
(the caller class always can use it), and if the Java agent hasn't yet been installed, it'll be installed
and the specified classes will have permission to use it!

To attach a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html)
to the current process, you should call the `Dynagent.install()` method. If the
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) hasn't
yet been attached, it will attach it and return `true` if the installation was successful (otherwise,
`false`), but if it has already been attached, it will always return `true`. If you need to find out
whether a
[Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) has
already been attached to the current process without attaching it, then you can call
the `Dynagent.isInstalled()` method. Then, to get the `Instrumentation` instance, you just need to call
the `Dynagent.getAgent()` method. After this, you can do whatever your heart desires with
the resulting copy of `Instrumentation`!

But also, the `Dynagent` class itself contains several static methods that allow you to perform
any operation with `Instrumentation` without having its instance:

- `Dynagent.getObjectSize(Object)` - returns the memory size allocated for the specified object.
- `Dynagent.isModifiableModule(Module)` - checks if you can redefine the specified module.
- `Dynagent.redefineModule(Module, Set, Map, Map, Set, Map)` - adds the specified dependencies to
  the module if it's a modifiable module.
- `Dynagent.appendToBootstrapClassLoaderSearch(JarFile)` - adds the specified jar file to the
  boot class path.
- `Dynagent.appendToSystemClassLoaderSearch(JarFile)` - adds the specified jar file to the class path.
- `Dynagent.getAllLoadedClasses()` - returns an array of all loaded classes currently loaded by the JVM,
  including hidden classes, anonymous classes, lambda classes, primitive types and array types.
- `Dynagent.getInitiatedClasses(ClassLoader)` - returns an array of all loaded classes which can be
  discovered by its name from the specified class loader and its parents, which doesn't include
  hidden classes, interfaces and array types whose element type is a hidden class or an interface.
- `Dynagent.isModifiableClass(Class)` - checks if you can redefine the specified class.
- `Dynagent.isRetransformClassesSupported()` - checks if the class retransformation is supported in
  the current environment.
- `Dynagent.isRedefineClassesSupported()` - checks if the class redefinition is supported in
  the current environment.
- `Dynagent.isNativeMethodPrefixSupported()` - checks if the native method prefix is supported in
  the current environment.
- `Dynagent.redefineClasses(ClassDefinition[])` - redefines all specified classes with
  the specified bytecode.
- `Dynagent.redefineClasses(ClassDefinition)` - redefines a single class with the specified bytecode.
- `Dynagent.redefineClasses(Class, File)` - redefines a single class with a bytecode from
  the specified file.
- `Dynagent.redefineClasses(Class, InputStream)` - redefines a single class with a bytecode from
  the specified input stream.
- `Dynagent.redefineClasses(Class, ByteBuffer)` - redefines a single class with a bytecode from
  the specified byte buffer.
- `Dynagent.redefineClasses(Class, byte[], int, int)` - redefines a single class with a bytecode from
  the specified byte array (with offset and length).
- `Dynagent.redefineClasses(Class, byte[])` - redefines a single class with a bytecode from
  the specified byte array.

More such methods will be added in the future!

## Future Plans

Long-term support for such a simple project shouldn't be difficult, so it'll definitely not be abandoned
and will continue to develop (at least as long as my other projects uses it). You just have to wait!

- [x] Come up with an idea.
- [x] Implement the idea.
- [ ] Improve/optimize the current implementation.
- - [ ] _Should I [lock](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/FileLock.html)
    temporary files?.._
- [ ] Make the built-in security system more flexible.
- [ ] Add built-in support for bytecode manipulation frameworks like [ASM](https://asm.ow2.io),
  [ByteBuddy](https://bytebuddy.net) and [Javassist](https://www.javassist.org).
- [ ] Ensure in full Android support (because the default implementation mayn't work on Android).
- [ ] Publish to Maven.
- [ ] Try to create a custom, more performant `Instrumentation` implementation using native JVM functions,
  i.e. without resource-intensive dynamic loading of the
  [Java agent](https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html) to
  the current process.
