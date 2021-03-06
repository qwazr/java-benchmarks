Java For loop iteration performance
===================================

There is few ways to iterate through a collection with Java.

This benchmark compares the speed performance of several methods.

Benchmark results
-----------------

| Benchmark                          |      Score  |  Error   | Units |
| -----------------------------------| ----------- | -------- | ----- |
| usingForEachMethod                 |  7242.168 ± | 1940.501 | ns/op |
| usingForEachMethodSync             |  7666.807 ± | 1883.873 | ns/op |
| usingIteratorMethods               |  7130.437 ± |   68.214 | ns/op |
| usingIteratorMethodsSync           |  7180.647 ± | 1606.271 | ns/op |
| usingIteratorWithFor               |  6622.754 ± |  862.600 | ns/op |
| usingIteratorWithForSync           |  6413.721 ± |   33.421 | ns/op |
| usingSizeAndGetMethod              |  6419.240 ± |  318.117 | ns/op |
| usingSizeAndGetMethodSync          | 49199.728 ± | 2651.059 | ns/op |
| usingSizeVariableAndGetMethod      |  6419.165 ± |  255.867 | ns/op |
| usingSizeVariableAndGetMethodSync  | 39540.630 ± | 1632.151 | ns/op |
| usingStreamForEachMethod           |  6687.496 ± |  698.062 | ns/op |
| usingStreamForEachMethodSync       |  6690.605 ± |  886.787 | ns/op |


Benchmark code
--------------

### using list.forEach() method

A natural way to iterate is the use of the list.forEach() method.

Performances are similar to the iterator based methods.

```java
private long usingForEachMethod(final List<Integer> list) {
    final class Summer {
        long total = 0;
    }
    final Summer summer = new Summer();
    list.forEach(value -> summer.total += value);
    return summer.total;
}
```
    
### Using Iterator methods

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

### using iterator with for

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

### using list.size() And list.get() methods

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

### using a size local variable and the list.get() method

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

###  using list.stream() and stream.forEach() methods

List provides also a stream and its forEach method.

Performances are similar to the enhanced for loop form.

```java
private long usingStreamForEachMethod(final List<Integer> list) {
    final class Summer {
        long total = 0;
    }
    final Summer summer = new Summer();
    list.stream().forEach(value -> summer.total += value);
    return summer.total;
}
```

Benchmark analysis
------------------

Quite all methods are similar in term of performances.

However, two forms should be avoid, due to the especially bad performances when using
synchronized collections:

- usingSizeAndGetMethod
- usingSizeVariableAndGetMethod
