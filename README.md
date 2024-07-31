# Dynagent

**Dynagent** _(**Dyn**amic Java **agent**)_ is a small, very lightweight library which allows you to
dynamically attach a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) to
the current VM. This allows you to dynamically create a valid `Instrumentation` instance and use all of
its functionality without having to separately compile the Java agent as a separate file and specify
the path to it via a JVM option or to restart the current process (so this can be very useful for the
bytecode manipulation). To access `Instrumentation`, you need to do this:

```java
if (Dynagent.install()) {
	
	final Instrumentation agent = Dynagent.getAgent();
	
	// Do anything you want with `agent`...
	
} else {

        // Failed to attach!
}
```

Dynagent will take care of the rest, allowing you to completely focus on your code.

## How Does It Work?

[Java agents](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)
can be very useful in solving some very special cases. In particular, they're useful for the bytecode
manipulation. These tasks require attaching a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) to
the current process. The problem of attaching a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) to
the current process is usually solved either by initially specifying the appropriate JVM option (which
may not be very practical because JVM options aren't up to you), or by restarting the current process
to attach it. This library was developed by me as an alternative solution when I was working on another
project that required a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) to be
dynamically attached to the current process, but with conditions that prevented me from using the above
solutions. Initially, I thought to use the
[Attach API](<https://www.baeldung.com/java-instrumentation#dynamic-load>), but I found that it can't
attach a Java agent to the current process. Then I thought to launch a separate process and through it
attach a Java agent to the current process. It sounded difficult, but... it worked. After several
improvements I was able to optimize the code and save it all in a single jar file. This library uses
a slightly modified implementation of that thing, but the algorithm hasn't changed significantly.
The attaching is still works this way:

1. Find a valid `java` binary to launch a temporary process.
2. Dynamically create a temporary jar file which will be a Java agent to be attached.
3. Dynamically create a temporary jar file which will attach a Java agent to the current process.
4. Launch a temporary process of an "attacher" jar file and wait until it finishes.
5. Check if we have a valid `Instrumentation` instance now.
6. Delete temporary files.

The [Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)
itself is stored in the class `DynagentImpl` from the library's `internal` package, and the "attacher"
is stored in the class `DynagentInstaller` from the same package. **Direct access to the internal API
isn't recommended** because the internal API can change a lot.

## How to Use It?

**Please note that Dynagent has a simple built-in security system that allows you to explicitly set
which classes will have access to the attached
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)'s
interface, because the implementation of
[Java agents](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)
bypasses all Java security checks and can be very dangerous. Therefore, when calling any method except
`Dynagent.install()` and `Dynagent.isInstalled()`, the caller class will be checked. If the caller class
doesn't exist, cannot be checked, or isn't white-listed, an `IllegalCallerException` will be thrown!**
When calling the `Dynagent.install()` method, you can specify, separated by commas, the classes that
will be allowed use to the installed
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)
(the caller class always can use it), and if the Java agent hasn't yet been installed, it'll be
installed and the specified classes will have permission to use it!

To attach a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>),
you should call the `Dynagent.install()` method. If the
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>)
hasn't yet been attached, it will attach it and return `true` if the installation was successful,
but if it has already been attached, it will always return `true`. If you need to find out whether a
[Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) has
already been attached without attaching it, then you should call the `Dynagent.isInstalled()` method.
Then, to get the `Instrumentation` instance, you just need to call the `Dynagent.getAgent()` method.
After this, you can do whatever your heart desires with the resulting copy of `Instrumentation`!

But also, the class `Dynagent` itself contains several static methods that allow you to perform any
operation with `Instrumentation` without having its instance:

- `Dynagent.getObjectSize(Object)`. Returns the memory size allocated for the specified object.
- `Dynagent.isModifiableModule(Module)`. Checks if you can redefine the specified module.
- `Dynagent.redefineModule(Module, Set, Map, Map, Set, Map)`. Adds the specified dependencies to the
  module if it's a modifiable module.
- `Dynagent.appendToBootstrapClassLoaderSearch(JarFile)`. Adds the specified jar file to the boot class
  path.
- `Dynagent.appendToSystemClassLoaderSearch(JarFile)`. Adds the specified jar file to the class path.
- `Dynagent.getAllLoadedClasses()`. Returns an array of all loaded classes currently loaded by the JVM,
  including hidden classes, anonymous classes, lambda classes, primitive types and array types.
- `Dynagent.getInitiatedClasses(ClassLoader)`. Returns an array of all loaded classes which can be
  discovered by its name from the specified class loader and its parents, which doesn't include hidden
  classes, interfaces and array types whose element type is a hidden class or an interface.
- `Dynagent.isModifiableClass(Class)`. Checks if you can redefine the specified class.
- `Dynagent.isRetransformClassesSupported()`. Checks if the class retransformation is supported in the
  current environment.
- `Dynagent.isRedefineClassesSupported()`. Checks if the class redefinition is supported in the current
  environment.
- `Dynagent.isNativeMethodPrefixSupported()`. Checks if the native method prefix is supported in the
  current environment.
- `Dynagent.redefineClasses(ClassDefinition[])`. Redefines all specified classes with the specified
  bytecode.
- `Dynagent.redefineClasses(ClassDefinition)`. Redefines a single class with the specified bytecode.
- `Dynagent.redefineClasses(Class, File)`. Redefines a single class with a bytecode from the specified
  file.
- `Dynagent.redefineClasses(Class, InputStream)`. Redefines a single class with a bytecode from the
  specified input stream.
- `Dynagent.redefineClasses(Class, ByteBuffer)`. Redefines a single class with a bytecode from the
  specified byte buffer.
- `Dynagent.redefineClasses(Class, byte[], int, int)`. Redefines a single class with a bytecode from the
  specified byte array (with offset and length).
- `Dynagent.redefineClasses(Class, byte[])`. Redefines a single class with a bytecode from the specified
  byte array.

More such methods will be added in the future!

## Future Plans

Long-term support for such a simple project shouldn't be difficult and will continue to develop (at
least as long as my other projects uses it). You just have to wait!

- [x] Come up with an idea.
- [x] Implement the idea.
- [ ] Improve/optimize the current implementation.
-
  - [ ] _Should I [lock](<https://docs.oracle.com/javase/8/docs/api/java/nio/channels/FileLock.html>)
    temporary files?.._
- [ ] Make the built-in security system more flexible.
- [ ] ~~Add built-in support for bytecode manipulation frameworks like [ASM](<https://asm.ow2.io>),
  [ByteBuddy](<https://bytebuddy.net>) and [Javassist](<https://www.javassist.org>).~~
- [ ] Ensure in full Android support (because the default implementation mayn't work on Android).
- [ ] Publish to Maven.
- [ ] Try to create a custom, more performant `Instrumentation` implementation using native JVM functions,
  i.e. without resource-intensive dynamic attaching of the
  [Java agent](<https://docs.oracle.com/javase/8/docs/api/java/lang/instrument/package-summary.html>) to
  the current process.
