JAVA loop iteration performance
===============================

There is few ways to iterate through a collection using JAVA.

This benchmark compare the speed performance of several methods.

Benchmark results
-----------------

| Benchmark                                           | Mode    | Cnt |       Score  |  Error    | Units |
| --------------------------------------------------- | ------- | --- | ------------ | --------- | ----- |
| ForLoopBenchmark.usingIteratorMethods               | avgt    | 3   |   7132.112 ± |   224.659 | ns/op |
| ForLoopBenchmark.usingIteratorMethodsSync           | avgt    | 3   |   7134.256 ± |   306.494 | ns/op |
| ForLoopBenchmark.usingIteratorWithFor               | avgt    | 3   |   7116.887 ± |   229.993 | ns/op |
| ForLoopBenchmark.usingIteratorWithForSync           | avgt    | 3   |   7138.115 ± |    20.138 | ns/op |
| ForLoopBenchmark.usingSizeAndGetMethod              | avgt    | 3   |   6417.133 ± |   348.184 | ns/op |
| ForLoopBenchmark.usingSizeAndGetMethodSync          | avgt    | 3   | 373067.587 ± | 19568.628 | ns/op |
| ForLoopBenchmark.usingSizeVariableAndGetMethod      | avgt    | 3   |   6408.397 ± |   476.604 | ns/op |
| ForLoopBenchmark.usingSizeVariableAndGetMethodSync  | avgt    | 3   | 189366.168 ± | 58446.647 | ns/op |


Using Iterator methods
----------------------

Using the iterator is the natural way to iterate on a collection.

This is the **third fastest** method either used with a synchronized collection or not.

```java
private long usingIteratorMethods(final List<Integer> list) {
    long total = 0;
    final Iterator<Integer> it = list.iterator();
    while (it.hasNext())
        total += it.next();
    return total;
}
```
using iterator with for
-----------------------

The [enhanced Java loop](https://blogs.oracle.com/corejavatechtips/using-enhanced-for-loops-with-your-classes)
uses the iterator provided by the collection.

This is the **second fastest** method either used with a synchronized collection or not.

```java
private long usingIteratorWithFor(final List<Integer> list) {
    long total = 0;
    for (final Integer value : list)
        total += value;
    return total;
}
```

using list.size() And list.get() methods
----------------------------------------

On each iteration both the list.size() and list.get() methods are called.

This method is the second fastest until you use a synchronized collection.

**When the collection is synchronized, this is the worse choice.**

```java
private long usingSizeAndGetMethod(final List<Integer> list) {
    long total = 0;
    for (int i = 0; i < list.size(); i++)
        total += list.get(i);
    return total;
}
```

using a size local variable and the list.get() method
-----------------------------------------------------

On each iteration the list.get() method is called.

The size of the collection is stored in a local variable.

This method is **the fastest** until you use a synchronized collection.

**When the collection is synchronized, this is the second worse choice**

```java
private long usingSizeVariableAndGetMethod(final List<Integer> list) {
    long total = 0;
    final int size = list.size();
    for (int i = 0; i < size; i++)
        total += list.get(i);
    return total;
}
```