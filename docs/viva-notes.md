# Flash Card Quiz - Comprehensive Viva Notes (Units 1 to 8)

This guide provides 2 to 3 high-yield viva questions with concise, technically accurate answers for each syllabus unit, grounded directly in this project's code.

---

## Unit 1: Bytecode, Data Types, Variables, Arrays, Operators, Decision & Control Statements

### Q1. What is the difference between source code (`.java`) and bytecode (`.class`), and how is platform independence achieved?
> **Answer:** Java source code is compiled by `javac` into an intermediate, architecture-neutral binary format called **bytecode**. Bytecode does not target physical hardware; instead, it targets the **Java Virtual Machine (JVM)**. The slogan *"Write Once, Run Anywhere"* (WORA) works because any platform that has a JVM compatible with the target bytecode specification can interpret or JIT-compile the `.class` file into native machine instructions without recompiling the source code.

### Q2. How are arrays represented in memory in Java, and what happens when an invalid index is accessed?
> **Answer:** In Java, arrays are true heap-allocated **objects** (even if they store primitive elements). When an array is declared (e.g., `int[] scoreHistory = new int[10];`), memory for 10 integer slots plus object header metadata (such as `.length`) is allocated on the heap. If code attempts to access an index `< 0` or `>= length`, the JVM runtime automatically throws an `ArrayIndexOutOfBoundsException`.

### Q3. What is the difference between logical `&&` and bitwise `&` operators in Java?
> **Answer:** The logical `&&` operator is a **short-circuiting** operator that works only on `boolean` operands; if the left-hand expression evaluates to `false`, the right-hand operand is never evaluated. The bitwise `&` operator operates bit-by-bit on integer types (`byte`, `short`, `int`, `long`), or acts as a non-short-circuiting boolean operator where both sides are always evaluated regardless of the left-hand outcome.

---

## Unit 2: Classes, Objects, Constructors, Passing & Returning Objects

### Q1. What is the role of the `this` keyword in Java constructors and methods?
> **Answer:** `this` is an implicit reference variable pointing to the current class instance. In this project:
1. It resolves variable shadowing between instance fields and constructor parameters: `this.name = name;` in `Deck`.
2. It facilitates **constructor chaining**: `this(0, "Default", ...);` in `Flashcard`, allowing overloaded constructors to invoke other constructors within the same class.

### Q2. Does Java pass objects by value or by reference?
> **Answer:** Java is **strictly pass-by-value at all times**. When an object (such as a `Deck` or `Flashcard`) is passed as a method argument, Java passes a **copy of the reference value** (the heap address). The caller and callee point to the exact same object on the heap, allowing the callee to mutate internal fields (e.g., `deck.addCard(card)`), but reassigning the parameter variable itself inside the method does not change the caller's reference.

### Q3. What is a copy constructor and how is it implemented?
> **Answer:** A copy constructor creates a new object by copying the field values of an existing object of the same class. In `Flashcard`:
```java
public Flashcard(Flashcard other) {
    if (other != null) {
        this.id = other.id;
        this.question = other.question;
        this.answer = other.answer;
        this.topic = other.topic;
    }
}
```
This ensures a separate object identity while replicating content.

---

## Unit 3: Inheritance, Access Control, super, final, Overloading, Overriding, Abstract Classes

### Q1. What is the difference between method overloading and method overriding?
> **Answer:**
- **Method Overloading (Compile-Time / Static Polymorphism):** Multiple methods in the same class share the same name but have different parameter lists (count, types, or order). Example in `Card`: `checkAnswer(String)` vs. `checkAnswer(char)`. The compiler selects the method based on argument signature.
- **Method Overriding (Runtime / Dynamic Polymorphism):** A subclass provides a specific implementation of a method already declared in its superclass, keeping the exact same name, return type, and parameters. Example in `TextCard`, `MultipleChoiceCard`, and `TrueFalseCard` overriding `public abstract void display()`.

### Q2. What are the three distinct usages of the `final` keyword in Java?
> **Answer:**
1. **Final Variable:** Cannot be reassigned once initialized (acts as a constant, e.g., `final int points`).
2. **Final Method:** Cannot be overridden by any subclass (e.g., `public final String getFormattedHeader()` in `Card`).
3. **Final Class:** Cannot be extended/subclassed by any class (e.g., `public final class CardMetadata`).

### Q3. Can an abstract class have constructors if it cannot be instantiated with `new`?
> **Answer:** **Yes.** Although abstract classes cannot be directly instantiated, they have constructors (such as `public Card(int id, String question, ...)`). These constructors are executed via `super(...)` when concrete subclass instances (such as `TextCard` or `MultipleChoiceCard`) are created, ensuring the base class fields are properly initialized.

---

## Unit 4: Packages, CLASSPATH, Access Modifiers, Importing, Interfaces

### Q1. How do Java access modifiers differ across class, package, subclass, and world?
> **Answer:**
- `private`: Accessible only within the declaring class (e.g., fields in `Deck`, `Card`).
- `default` (package-private): Accessible only within classes in the same package (e.g., `DeckValidator` class in `com.flashquiz.model`).
- `protected`: Accessible within the same package AND by subclasses in different packages (e.g., `protected String question` in `Card`).
- `public`: Accessible everywhere across all packages (e.g., `Scorable`, `Persistable`).

### Q2. What is an interface and how do its members differ from regular classes?
> **Answer:** An `interface` defines a pure contract. In Java:
- All methods declared without bodies are implicitly `public abstract` (e.g., `calculatePercentage()` in `Scorable`).
- All variables declared in interfaces are implicitly `public static final` constants (e.g., `double PASSING_THRESHOLD = 50.0;` in `Scorable`).
- A class uses `implements` to fulfill the contract and can implement multiple interfaces, overcoming Java's single inheritance restriction.

### Q3. Why does running `java Main` from inside `out/com/flashquiz/` produce `NoClassDefFoundError`?
> **Answer:** Because `Main` declares `package com.flashquiz;`, its fully qualified binary class name is `com.flashquiz.Main`. The JVM requires the classpath to point to the directory containing the top-level package folder (`out/`). When executed as `java -cp out com.flashquiz.Main`, the JVM locates `com/flashquiz/Main.class` starting from `out`.

---

## Unit 5: Strings, Character Extraction, Comparison, and StringBuffer

### Q1. Why is `String` immutable in Java, and why is `StringBuffer` used for report building?
> **Answer:**
- `String` objects are immutable; any modification (concatenation, replacement) creates a new string object on the heap, increasing garbage collection overhead in tight loops. Immutability provides thread safety and allows string pooling.
- `StringBuffer` is a mutable sequence of characters. It provides synchronized, in-place modification methods such as `append()`, `insert()`, and `reverse()`, making it memory-efficient for constructing large multi-line quiz reports without intermediate string allocations.

### Q2. What is the difference between `==` and `.equals()` / `.equalsIgnoreCase()` when comparing strings?
> **Answer:**
- `==` compares object references (memory addresses on the heap). It checks whether both variables point to the exact same object.
- `.equals()` and `.equalsIgnoreCase()` compare the **content** (character sequence). For user quiz answers, `.equalsIgnoreCase()` must always be used so `"class"` matches `"CLASS"`.

### Q3. What character extraction methods does `String` provide?
> **Answer:**
- `charAt(int index)`: Returns the `char` at the specified 0-based index. Used to extract option choices ('A', 'B', 'C') and initial letters for hints.
- `substring(int beginIndex, int endIndex)`: Returns a new string containing characters from `beginIndex` (inclusive) to `endIndex` (exclusive). Used to generate prefix hint masks.

---

## Unit 6: Exception Handling, try/catch, multiple catch, throw, throws, finally

### Q1. What is the difference between checked and unchecked exceptions?
> **Answer:**
- **Checked Exceptions:** Subclasses of `java.lang.Exception` (excluding `RuntimeException`). The compiler forces the programmer to either catch them with `try/catch` or declare them with `throws` (e.g., `InvalidDeckException`, `IOException`).
- **Unchecked Exceptions:** Subclasses of `java.lang.RuntimeException` or `Error`. They represent logical or programmatic bugs (e.g., `InvalidInputException`, `NumberFormatException`, `ArrayIndexOutOfBoundsException`) and do not require mandatory compile-time handling.

### Q2. What are the ordering rules when using multiple `catch` blocks?
> **Answer:** Catch blocks must be ordered from the **most specific subclass to the most general superclass**. If a superclass catch block (such as `catch (Exception e)`) is placed before a subclass catch block (such as `catch (NumberFormatException nfe)`), compilation fails with an *"unreachable code"* error.

### Q3. Under what circumstances does a `finally` block execute?
> **Answer:** The `finally` block is guaranteed to execute regardless of whether an exception was thrown, caught, or not thrown at all, and even if a `return` statement is encountered in the `try` or `catch` block. It is only bypassed if `System.exit(0)` is invoked or the JVM crashes. In this project, `finally` ensures streams are closed (`reader.close()`, `writer.close()`) and quiz durations are calculated.

---

## Unit 7: File Handling, File Class, Character Streams (Reader/Writer)

### Q1. What is the difference between byte streams and character streams in Java I/O?
> **Answer:**
- **Byte Streams** (`InputStream` / `OutputStream`): Read and write raw binary data byte-by-byte (8 bits).
- **Character Streams** (`Reader` / `Writer`): Specifically designed for text I/O. They handle 16-bit Unicode characters and automatically manage platform character encodings (e.g., UTF-8). Classes like `FileReader` and `FileWriter` are character streams.

### Q2. Why should `BufferedReader` and `BufferedWriter` wrap `FileReader` and `FileWriter`?
> **Answer:** Disk I/O is slow. Unbuffered streams trigger system calls for every single byte or character read/written. `BufferedReader` and `BufferedWriter` maintain internal memory buffers (typically 8 KB). They perform bulk I/O operations and offer convenient utility methods such as `readLine()` and `newLine()`, dramatically improving performance.

### Q3. How does `FileWriter(file, true)` enable file appending?
> **Answer:** Passing the boolean flag `true` as the second argument to `FileWriter` opens the underlying file descriptor in **append mode**. New data written via `writer.write(...)` is appended to the end of the existing file contents rather than overwriting from offset 0. This is demonstrated when saving quiz scores to `scores.txt` and adding cards to `data/*.txt`.

---

## Unit 8: Multithreading, Thread, Runnable, Priority, Synchronized Block

### Q1. What is the difference between extending `Thread` and implementing `Runnable`?
> **Answer:**
- Extending `Thread` uses up Java's single inheritance slot, preventing the class from extending any other domain model.
- Implementing `Runnable` separates the task (job to be executed) from the runner (the `Thread` execution mechanism). It allows the implementing class (e.g., `QuestionTimer`) to extend another class if needed and promotes cleaner object-oriented design.

### Q2. Why is thread priority set on the countdown timer, and does it guarantee strict timing?
> **Answer:** Thread priority is set via `timerThread.setPriority(Thread.MIN_PRIORITY)`. This hints to the JVM thread scheduler that the background countdown timer (which mostly sleeps) should yield CPU cycles to higher-priority threads (such as the main thread managing user terminal input). Note that thread priority is an advisory hint to the underlying OS scheduler and does not guarantee strict real-time execution.

### Q3. How does a `synchronized` block work and why is it used on shared quiz counters?
> **Answer:** Every object in Java has an internal **intrinsic lock (monitor)**. When a thread enters `synchronized (lock) { ... }`, it acquires the monitor lock. Any other thread attempting to enter a synchronized block guarded by the same lock is suspended until the holding thread exits the block. In `QuizTracker`, this prevents **race conditions** when updating shared metrics (`totalPointsEarned`, `correctCount`, `skippedCount`).
