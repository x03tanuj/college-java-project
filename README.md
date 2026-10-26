# CLI Flash Card Quiz (College Java Lab Project)

A pure core Java command-line Flashcard Quiz application designed and built for an academic Java Programming Laboratory.

---

## 1. Project Overview

The **CLI Flash Card Quiz** is a full-featured, interactive terminal educational utility that demonstrates the entire core Java syllabus across 8 progressive academic units:
- **No external frameworks or build tools** (built strictly with plain `javac` and run with `java`).
- **JDK 8+ compatible** pure Java code (compatible with Java 8, 11, 17, 21, and beyond).
- **Zero third-party dependencies**, no GUI frameworks, and no database engines.
- **Robust error resilience**: completely handles invalid inputs, empty strings, out-of-range choices, and file system discrepancies without crashing.

---

## 2. How to Build and Run

### On Linux / macOS

#### 1. Compile with the provided build script:
```bash
./build.sh
```
*(Alternatively: `javac -d out $(find src -name "*.java")`)*

#### 2. Run the application:
```bash
./run.sh
```
*(Alternatively: `java -cp out com.flashquiz.Main`)*

---

### On Windows

#### 1. Compile with `build.bat`:
```cmd
build.bat
```
*(Alternatively: `if not exist out mkdir out && dir /s /b src\*.java > sources.txt && javac -d out @sources.txt && del sources.txt`)*

#### 2. Run with `run.bat`:
```cmd
run.bat
```
*(Alternatively: `java -cp out com.flashquiz.Main`)*

---

## 3. Syllabus Unit Mapping Table

Every unit from the academic syllabus is implemented, clearly commented, and demonstrated in the codebase:

| Syllabus Unit | Core Topics Demonstrated | Source Files & Classes | Key Methods / Demonstrations |
|:---:|---|---|---|
| **Unit 1** | Bytecode, data types (all 8 primitives), variables, arrays, operators, decision & control statements (`while`, `for`, `switch`, `if/else`, bitwise, ternary) | [Main.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/Main.java)<br>[BytecodeDemo.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/util/BytecodeDemo.java)<br>[docs/bytecode.md](file:///Users/tanuj/Downloads/Java%20Project/docs/bytecode.md) | `Main.main()`, `Main.runQuickQuiz()`, `Main.displayDiagnostics()`, `BytecodeDemo.calculateBonus()`, `BytecodeDemo.sumArray()` |
| **Unit 2** | Classes, objects, constructors (default, parameterized, overloaded, copy), `this` keyword, passing & returning objects, arrays of objects | [Flashcard.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/Flashcard.java)<br>[Deck.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/Deck.java)<br>[Quiz.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/Quiz.java) | `Flashcard(Flashcard other)`, `Deck.addCard(Card)`, `Deck.getCardAt(int)`, `Deck.getRandomCard()`, `Quiz(Deck)` |
| **Unit 3** | Inheritance, access control (`protected`), `super(...)`, `final` (method, class, variable), method overloading (`checkAnswer(String)` vs `checkAnswer(char)`), overriding, abstract classes | [Card.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/Card.java)<br>[TextCard.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/TextCard.java)<br>[MultipleChoiceCard.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/MultipleChoiceCard.java)<br>[TrueFalseCard.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/TrueFalseCard.java)<br>[CardMetadata.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/CardMetadata.java) | `abstract checkAnswer()`, `abstract display()`, `final getFormattedHeader()`, `super(id, question, topic, points)`, `MultipleChoiceCard.checkAnswer(char)` |
| **Unit 4** | Packages (`model`, `service`, `io`, `util`, `exception`), `CLASSPATH` configuration, access modifiers (`public`, `protected`, `default`, `private`), interfaces (`Scorable`, `Persistable`) | [Scorable.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/Scorable.java)<br>[Persistable.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/io/Persistable.java)<br>[DeckValidator.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/DeckValidator.java)<br>[docs/classpath.md](file:///Users/tanuj/Downloads/Java%20Project/docs/classpath.md) | `interface Scorable`, `interface Persistable`, package-private class `DeckValidator`, `Quiz implements Scorable`, `Deck implements Persistable` |
| **Unit 5** | Strings: constructors, character extraction (`charAt`), searching/comparing (`trim`, `equalsIgnoreCase`, `compareTo`, `contains`, `indexOf`, `substring`), hint feature, `StringBuffer` (`append`, `insert`, `reverse`) | [StringUtil.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/util/StringUtil.java)<br>[Card.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/Card.java)<br>[Quiz.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/Quiz.java) | `StringUtil.buildHint()`, `StringUtil.compareAnswers()`, `StringUtil.formatBannerWithDecoration()`, `Quiz.printResults()` (StringBuffer operations) |
| **Unit 6** | Exceptions: custom checked (`InvalidDeckException`) and unchecked (`InvalidInputException`), `try/catch` with multiple catch blocks, `throw`, `throws`, `finally` | [InvalidDeckException.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/exception/InvalidDeckException.java)<br>[InvalidInputException.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/exception/InvalidInputException.java)<br>[Main.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/Main.java)<br>[Deck.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/model/Deck.java) | `Deck.validateDeck() throws InvalidDeckException`, `throw new InvalidInputException(...)`, `Main.java` try-catch catching NumberFormatException, ArrayIndexOutOfBoundsException, InvalidInputException, etc. |
| **Unit 7** | File handling: `File` class, character streams (`FileReader`, `BufferedReader`, `FileWriter`, `BufferedWriter`), loading decks (`data/*.txt`), appending cards, score history (`scores.txt`) | [DeckFileReader.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/io/DeckFileReader.java)<br>[ScoreManager.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/io/ScoreManager.java)<br>[data/](file:///Users/tanuj/Downloads/Java%20Project/data/) | `DeckFileReader.loadDeckFromFile()`, `DeckFileReader.appendCardToFile()`, `ScoreManager.saveScore()`, `ScoreManager.displayFileScores()`, 3 shipped sample decks |
| **Unit 8** | Multithreading: `Thread` class, `Runnable` interface, thread priority configuration, per-question countdown timer, timeout question skipping, `synchronized` blocks on shared state | [QuestionTimer.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/QuestionTimer.java)<br>[QuizTracker.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/QuizTracker.java)<br>[Quiz.java](file:///Users/tanuj/Downloads/Java%20Project/src/com/flashquiz/service/Quiz.java) | `class QuestionTimer implements Runnable`, `timerThread.setPriority(Thread.MIN_PRIORITY)`, `synchronized (lock) { ... }` in `QuizTracker.recordAnswer()` |

---

## 4. Screenshots-as-Text (Sample Interactive Session)

### Main Menu Display
```text
==========================================
  WELCOME TO CLI FLASH CARD QUIZ (LAB)    
  Core Java Academic Lab Project (JDK 8+) 
==========================================

---------------- MAIN MENU ----------------
1.  Start Quick Starter Quiz (Unit 1 Basics)
2.  Start OOP Flashcard Quiz (Unit 2 Objects & Decks)
3.  Start Inheritance & Polymorphism Quiz (Unit 3 Abstract & Subclasses)
4.  Load & Play Deck from File (Unit 7 File Handling)
5.  Start Timed Quiz Challenge (Unit 8 Multithreading Countdown)
6.  Add New Card to Deck File (Unit 7 File Appending)
7.  View Persistent Score History (Unit 7 scores.txt)
8.  View In-Memory Score History (Unit 1 Arrays)
9.  System & Variable Diagnostics (Unit 1 Primitives & Ops)
10. Run Exception Handling Lab Test (Unit 6 Demo)
11. Exit
Select an option (1-11): 
```

### Timed Quiz Challenge Session (Unit 5 Hints, Unit 8 Threads & Synchronized State)
```text
Select an option (1-11): 5

--- TIMED QUIZ CHALLENGE (Unit 8 Multithreading) ---
Enter countdown seconds per question (e.g. 5, 10, 15): 10

--- SELECT DECK FILE ---
Available Decks:
  [1] oop_concepts.txt (1534 bytes)
  [2] java_basics.txt (1301 bytes)
  [3] exceptions_and_io.txt (1719 bytes)
Select deck number (1-3): 1
[File I/O] Reading oop_concepts.txt using BufferedReader...
[File I/O] Successfully loaded deck with 12 cards!

>>>---* === STARTING QUIZ: OOP CONCEPTS ===  *--->>>
Total Cards in Deck : 12
Timer Mode Enabled  : 10 seconds per card (Question skips on expiry)
Available Commands  : ':hint' for clue, ':skip' to pass card

=== [Card #1 | OOP Core | TEXT | 10 pts] ===
Question: What keyword refers to the current invoking object instance?
Type    : Direct Text Answer (Type ':hint' for a hint)
Your Answer [10s limit]: :hint
>> Hint: th** (4 chars)
Your Answer [10s limit]: this
>> Correct! (+10 points)

=== [Card #2 | Inheritance | TEXT | 10 pts] ===
Question: Which keyword invokes the superclass constructor from a subclass?
Type    : Direct Text Answer (Type ':hint' for a hint)
Your Answer [10s limit]: super
>> Correct! (+10 points)

=== [Card #3 | Encapsulation | TEXT | 10 pts] ===
Question: Which access modifier makes a member accessible only within its own class?
Type    : Direct Text Answer (Type ':hint' for a hint)
Your Answer [10s limit]: 
[TIME EXPIRED!] Time limit of 10s exceeded! Question skipped.

=== [Card #4 | Inheritance | TEXT | 10 pts] ===
Question: What keyword prevents a class from being inherited or subclassed?
Type    : Direct Text Answer (Type ':hint' for a hint)
Your Answer [10s limit]: :skip
>> Skipped by user.

============================================
             QUIZ PERFORMANCE REPORT        
============================================
[STATUS: QUALIFIED] Deck Name          : OOP CONCEPTS
Total Cards        : 12
Cards Correct      : 2 / 12
Cards Skipped      : 2
Cards Incorrect    : 8
Points Scored      : 20 / 140 pts
Accuracy Score     : 14.29%
Academic Rating    : Grade F (Fail / Needs Practice)
Pass Status        : FAILED
Timer Mode         : 10s limit
Hints Consulted    : 1
Completion Time    : 12 second(s)
--------------------------------------------
Header Style: ~~~***>>> FlashQuiz Lab <<<***~~~
Mirror Style: ~~~***<<< baL ziuQhsalF >>>***~~~
============================================

[ScoreManager] Saved score entry to scores.txt
```

### Persistent Score History (`scores.txt`)
```text
Select an option (1-11): 7

==========================================================================
                   PERSISTENT SCORE HISTORY (scores.txt)
==========================================================================
Date / Time          | Deck Name                | Points   | Accuracy   | Rating      
---------------------+--------------------------+----------+------------+-------------
2026-10-26 22:18:17  | OOP CONCEPTS             | 30/140   | 21.43%     | Grade F (Fail / Needs Practice)
2026-10-26 22:24:53  | OOP CONCEPTS             | 20/140   | 14.29%     | Grade F (Fail / Needs Practice)
==========================================================================
Total Recorded Attempts: 2
```

---

## 5. Assumptions

The following design decisions and assumptions were adopted during development:

1. **Target Java Version:** The source code adheres strictly to standard Java 8+ language features (plain classes, standard inheritance, classic for/while loops, arrays). No language features from modern Java (such as records, pattern matching, `var`, lambdas, or Stream API) are used, ensuring 100% compliance with college lab examination rubrics.
2. **File Delimiter & Deck Storage:** Decks in `data/*.txt` utilize pipe delimiters (`|`) for card structure:
   - `TEXT|<question>|<answer>|<topic>|<points>`
   - `MCQ|<question>|<correctOptionLetter>|<option1;option2;option3;option4>|<topic>|<points>`
   - `TF|<question>|<true/false>|<topic>|<points>`
   Lines beginning with `#` and blank lines are treated as comments and ignored.
3. **Score Persistence:** Scores are logged to `scores.txt` in the root workspace directory. If `scores.txt` does not exist, `ScoreManager` automatically creates it upon the completion of the first quiz attempt.
4. **Timer Cancellation & Thread Priority:** The timer thread is set to `Thread.MIN_PRIORITY` (priority 1) to prioritize the main thread's responsiveness for standard input (`System.in`). When the countdown expires before the user presses Enter, the card is automatically marked as skipped and 0 points are awarded.
5. **Array Resizing:** As mandated by the syllabus constraints prohibiting collection tricks, `Deck` manages an array of `Card[]` and implements manual array expansion doubling when capacity is reached.

---

## 6. Documentation Index

- [docs/bytecode.md](file:///Users/tanuj/Downloads/Java%20Project/docs/bytecode.md): Bytecode disassembly using `javap -c` and explanation of 10 JVM instructions.
- [docs/classpath.md](file:///Users/tanuj/Downloads/Java%20Project/docs/classpath.md): Guide to packages, directory hierarchy, and running with `java -cp out`.
- [docs/viva-notes.md](file:///Users/tanuj/Downloads/Java%20Project/docs/viva-notes.md): 2–3 high-yield viva questions with short answers for all 8 syllabus units.
