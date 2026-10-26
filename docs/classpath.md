# Unit 4: Understanding Java CLASSPATH & Package Architecture

This document fulfills the Unit 4 syllabus requirement to explain **packages**, **access control**, and the **Java CLASSPATH**, including concrete terminal execution steps using `-cp out`.

---

## 1. What is CLASSPATH?

The `CLASSPATH` is a parameter or environment variable that instructs the Java Virtual Machine (JVM) and the `javac` compiler where to look for compiled user-defined classes and packages (`.class` files).

By default, the JVM looks in the current working directory (`.`). However, when code is structured into **packages**, classes must reside in directory structures matching their package declarations.

---

## 2. Package-to-Directory Mapping

In Java, packages provide **namespace management** and **access protection**. The package hierarchy strictly mirrors the directory hierarchy:

| Package Declaration | Corresponding File Path on Disk |
|---|---|
| `package com.flashquiz;` | `out/com/flashquiz/Main.class` |
| `package com.flashquiz.model;` | `out/com/flashquiz/model/Card.class` |
| `package com.flashquiz.service;` | `out/com/flashquiz/service/Scorable.class` |
| `package com.flashquiz.io;` | `out/com/flashquiz/io/Persistable.class` |

---

## 3. Compiling with `-d out`

When compiling with `javac`, the `-d` (destination) flag tells the compiler to generate the directory tree for packages inside the designated output folder:

```bash
# Compiles all sources into the 'out/' directory respecting package folders
javac -d out src/com/flashquiz/**/*.java
```

Resulting directory tree in `out/`:
```text
out/
└── com
    └── flashquiz
        ├── Main.class
        ├── io
        │   └── Persistable.class
        ├── model
        │   ├── Card.class
        │   ├── CardMetadata.class
        │   ├── Deck.class
        │   ├── DeckValidator.class
        │   ├── Flashcard.class
        │   ├── MultipleChoiceCard.class
        │   ├── TextCard.class
        │   └── TrueFalseCard.class
        ├── service
        │   ├── Quiz.class
        │   └── Scorable.class
        └── util
            └── BytecodeDemo.class
```

---

## 4. Running with `java -cp out`

To execute the program, the JVM must be told where the root of the package hierarchy is. This is passed via the `-cp` (or `-classpath`) flag:

```bash
# Set classpath root to 'out' and execute the fully qualified main class
java -cp out com.flashquiz.Main
```

### Why Running `cd out/com/flashquiz && java Main` Fails
A common beginner error is navigating inside the package folder and attempting to run `java Main`:
```text
Error: Could not find or load main class Main
Caused by: java.lang.NoClassDefFoundError: com/flashquiz/Main (wrong name: Main)
```
**Reason**: Because `Main` declares `package com.flashquiz;`, its full binary name is `com.flashquiz.Main`. The JVM expects to find `Main.class` at `<classpath-root>/com/flashquiz/Main.class`. Running from inside the folder breaks this expectation because the package prefix is missing from the lookup path.

---

## 5. Access Modifiers Summary (Demonstrated in Project)

| Modifier | Class | Package | Subclass | World | Demonstrated In |
|---|:---:|:---:|:---:|:---:|---|
| `public` | Yes | Yes | Yes | Yes | `Card`, `Deck`, `Quiz`, `Scorable`, `Persistable` |
| `protected` | Yes | Yes | Yes | No | `Card.id`, `Card.question`, `Card.points` |
| `default` (no keyword) | Yes | Yes | No | No | `DeckValidator` class and package-private methods |
| `private` | Yes | No | No | No | `cards`, `expectedAnswer`, `scoreHistory` |

---

## 6. Verification Terminal Log

```bash
$ javac -d out src/com/flashquiz/model/*.java src/com/flashquiz/service/*.java src/com/flashquiz/io/*.java src/com/flashquiz/util/*.java src/com/flashquiz/*.java
$ java -cp out com.flashquiz.Main
==========================================
  WELCOME TO CLI FLASH CARD QUIZ (LAB)    
  Demonstrating Java Unit 1 Core Concepts 
==========================================
```
