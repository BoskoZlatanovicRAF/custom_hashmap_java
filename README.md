# Custom HashMap implementation in Java

This is a custom implementation of a HashMap in Java. It provides basic functionalities such as adding, removing, and retrieving key-value pairs. The implementation uses an array of linked lists to handle collisions through chaining.
The HashMap is designed to be simple and educational, demonstrating the underlying principles of how a HashMap works.
## Features

- Basic operations: `put`, `get`, `remove`
- Collision handling using separate chaining with linked lists
- Dynamic resizing of the underlying array when the load factor exceeds a certain threshold
- Customizable initial capacity and load factor
- Iterators for traversing the keys and values in the HashMap
- Custom hash function for keys without using Java's built-in `hashCode()` method