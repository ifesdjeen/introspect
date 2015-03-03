# Introspect

```
 _       _                                 _
(_)_ __ | |_ _ __ ___  ___ _ __   ___  ___| |_
| | '_ \| __| '__/ _ \/ __| '_ \ / _ \/ __| __|
| | | | | |_| | | (_) \__ \ |_) |  __/ (__| |_
|_|_| |_|\__|_|  \___/|___/ .__/ \___|\___|\__|
                          |_|
```

Introspect is a Clojure library that helps library builders to track 
input and output values and their types to avoid unnecessary type 
errors.

## Usage 

In order to use introspect, just add an Introspect dependency:

```
:dependencies      [introspect "1.0.0-alpha1"]]
```

Also, for the best experience you'll need a jvm agent: 

```
:jvm-opts     ["-noverify"
               ~(str
                  "-javaagent:"
                  (System/getProperty "user.home")
                  ".m2/repository/introspect/introspect/1.0.0-alpha1/introspect-1.0.0-alpha1.jar=!!!YOUR_NAMESPACES!!!")]
```

Instead of `!!!YOUR_NAMESPACES!!!`, put a comma-separated list of your namespaces.

`-noverify` option is required in order to avoid strict bytecode verification, since 
bytecode is being modified at runtime.

## Features

There's a function in your program that is (accidentally) being called 
with incorrect params:

```clojure
(some-crazy-function 1 2)
(some-crazy-function "a" "b")
(some-crazy-function (int 1) (int 2))
```

Introspect works in runtime. In Clojure, pretty much everything is 
downcasted to Object, so no bytecode analysis will help us much. In 
the end, the easiest way is obtain all that informatin is during
runtime. 

So after running your program (actually, while running it), you will
get an output:

```
Detected unusual method call:introspect_tests/core$some_crazy_functionwas called with arguments
        (class java.lang.String class java.lang.String)
previous calls were with
        (class java.lang.Long class java.lang.Long)
Stacktrace:
                                 java.lang.Thread                            Thread.java:                       1552
        introspect.visitor$generic_method_entered                            visitor.clj:                         70
                introspect.visitor$_methodEntered                            visitor.clj:                         85
                               introspect.Visitor                                       :                         -1
        introspect_tests.core$some_crazy_function                               core.clj:                         -1
              introspect_tests.core_test$fn__2945                          core_test.clj:                          7
                                              ...


Detected unusual method call:introspect_tests/core$some_crazy_functionwas called with arguments
        (class java.lang.Integer class java.lang.Integer)
previous calls were with
        (class java.lang.Long class java.lang.Long)
        (class java.lang.String class java.lang.String)
Stacktrace:
                                 java.lang.Thread                            Thread.java:                       1552
        introspect.visitor$generic_method_entered                            visitor.clj:                         70
                introspect.visitor$_methodEntered                            visitor.clj:                         85
                               introspect.Visitor                                       :                         -1
        introspect_tests.core$some_crazy_function                               core.clj:                         -1
              introspect_tests.core_test$fn__2945                          core_test.clj:                          8
```

We can't make any assumptions on which call stack is correct. We can only say you
called it once one way, and now you're callng it some other way for some reason. 

## Query type

You can also query the type of the function after it's been invoked at least once,
for all the types it's been called with:

```clojure
(ns my-ns
  (:require [introspect.core :refer t])
  
(t introspect.helpers.simple-functions/constant-return-value-fn)
```

Which would yield something like:

```
(java.lang.Long -> java.lang.Long -> java.lang.Long)
(java.lang.Long -> java.lang.String -> java.lang.String)
```

## Goals

  * track values, include stack of the "untypical" values 
  * allow type-hinting and giving advise on return types and best 
  practices
  * track calling threads, possibly tracking interleaving
  * add ignores for cases when untypical calls are intended 

## Credit

Inspired by codecentric [Allocation Tracker](https://github.com/codecentric/allocation-tracker/), 
props to it's [authors](https://github.com/codecentric/allocation-tracker/graphs/contributors).

## License

Copyright © 2013 Alex Petrov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
