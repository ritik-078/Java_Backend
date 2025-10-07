# Core Java Short Notes

## 1. Basics
- **Java** is an object-oriented, platform-independent programming language.
- **JVM (Java Virtual Machine):** Executes Java bytecode.
- **JRE (Java Runtime Environment):** JVM + libraries.
- **JDK (Java Development Kit):** JRE + development tools.



## 2. Data Types
- **Primitive:** int, float, double, char, boolean, byte, short, long.
- **Reference:** Objects, arrays.

### Java Data Types Table

| Data Type | Size (bits) | Size (bytes) | Default Value | Range (Formula) |
|-----------|-------------|--------------|---------------|-----------------|
| byte      | 8           | 1            | 0             | -2⁷ to 2⁷-1 |
| short     | 16          | 2            | 0             | -2¹⁵ to 2¹⁵-1 |
| int       | 32          | 4            | 0             | -2³¹ to 2³¹-1 |
| long      | 64          | 8            | 0L            | -2⁶³ to 2⁶³-1 |
| float     | 32          | 4            | 0.0f          | ±(1.4 × 10⁻⁴⁵) to ±(3.4 × 10³⁸) |
| double    | 64          | 8            | 0.0d          | ±(4.9 × 10⁻³²⁴) to ±(1.7 × 10³⁰⁸) |
| char      | 16          | 2            | '\u0000'      | 0 to 2¹⁶-1 (Unicode) |
| boolean   | 1 (JVM)     | JVM          | false        | true or false |


### Type Conversion & Typecasting
- **Type Conversion (Implicit):** Automatic conversion by Java (e.g., int to double).
- **Typecasting (Explicit):** Manual conversion by programmer (e.g., double to int: `(int) value`).
- Used to convert between compatible data types.
- May cause data loss (e.g., casting double to int).
- Example:
    ```java
    int a = 10;
    double b = a; // implicit conversion
    double c = 9.7;
    int d = (int) c; // explicit typecasting, d = 9
    ```

## 3. OOP Concepts
- **Class & Object:** Blueprint and instance.
- **Inheritance:** Reuse code (extends).
- **Polymorphism:** Many forms (overloading, overriding).
- **Encapsulation:** Hide data (private, getters/setters).
- **Abstraction:** Hide complexity (abstract class, interface).

## 4. Access Modifiers
- `public`, `private`, `protected`, default (package-private).

## 5. Control Statements
- if, else, switch, for, while, do-while, break, continue.

## 6. Exception Handling
- try, catch, finally, throw, throws.

## 7. Collections Framework
- List, Set, Map, Queue.
- ArrayList, HashSet, HashMap, LinkedList.

## 8. Multithreading
- Thread class, Runnable interface.
- Synchronization, wait/notify.

## 9. Input/Output (I/O)
- Streams: FileInputStream, FileOutputStream, BufferedReader, Scanner.

## 10. Important Keywords
- static, final, super, this, new, instanceof.

## 11. Packages
- Organize classes (`package` keyword).
- Import with `import` keyword.

## 12. Miscellaneous
- String handling (`String`, `StringBuilder`).
- Wrapper classes (`Integer`, `Double`, etc.).
- Autoboxing/unboxing.

---
**Tip:** Practice coding and explore Java documentation for deeper understanding.