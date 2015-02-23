# Introspect

Introspect is a Clojure library that helps library builders to track 
input and output values and their types to avoid unnecessary type 
errors.

## Example

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
...         
```

We can't make any assumptions on which call stack is correct. We can only say you
called it once one way, and now you're callng it some other way for some reason. 

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
