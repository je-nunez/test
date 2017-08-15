# test

testing the Gulava library in Java

# WIP

This project is a *work in progress*. The implementation is *incomplete* and subject to change. The documentation can be inaccurate.

# Brief

Gulava (at [https://github.com/google/gulava](https://github.com/google/gulava)) is a relational programming library in Java, with the ideas of Prolog. (It also supports functional programming like in Lisp: see [https://github.com/google/gulava/blob/master/java/gulava/lisp/Lisp.java](https://github.com/google/gulava/blob/master/java/gulava/lisp/Lisp.java).)

Since Gulava offers Java annotations to client-classes (like `@MakePredicates`), then this library supports querying for a logical model at runtime. E.g., in long-running Java system which embeds Gulava and uses its annotations, then, at run-time, this long-running Java program can run a Prolog-style query in Gulava to check that some objects in the heap of this program satisfy some pre-defined logical model -so the heap is still valid for that pre-defined logical model. This is somehow similar to the relational [Object Query Language](https://en.wikipedia.org/wiki/Object_Query_Language), but in logical Prolog-style, not a-la-SQL, to verify that the objects in the heap still satisfy some query, so the heap is not corrupted. Probably, this self-consistency thread in the long-running Java program may need to dump the Java heap first: [https://blogs.oracle.com/sundararajan/programmatically-dumping-heap-from-java-applications](https://blogs.oracle.com/sundararajan/programmatically-dumping-heap-from-java-applications)

(Of course, Gulava supports many other uses in Java, like the general uses of Prolog.)

# Running this program

To run this program:

    mvn

To compile the Gulava library itself:

    bazel clean
    bazel build //java/gulava:gulava
 
