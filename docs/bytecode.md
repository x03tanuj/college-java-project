# Unit 1: Java Bytecode Analysis

This document fulfills the Unit 1 requirement to compile a Java class, disassemble it with `javap -c`, and explain 5–8 fundamental JVM bytecode instructions.

---

## 1. Source Code (`src/com/flashquiz/util/BytecodeDemo.java`)

```java
package com.flashquiz.util;

public class BytecodeDemo {
    public int calculateBonus(int baseScore, int streak) {
        int bonus = 0;
        if (streak > 3) {
            bonus = baseScore * 2;
        } else {
            bonus = baseScore + streak;
        }
        return bonus;
    }

    public int sumArray(int[] numbers) {
        int total = 0;
        for (int i = 0; i < numbers.length; i++) {
            total += numbers[i];
        }
        return total;
    }
}
```

---

## 2. Disassembled Bytecode (`javap -c out/com/flashquiz/util/BytecodeDemo.class`)

```text
Compiled from "BytecodeDemo.java"
public class com.flashquiz.util.BytecodeDemo {
  public com.flashquiz.util.BytecodeDemo();
    Code:
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return

  public int calculateBonus(int, int);
    Code:
         0: iconst_0
         1: istore_3
         2: iload_2
         3: iconst_3
         4: if_icmple     14
         7: iload_1
         8: iconst_2
         9: imul
        10: istore_3
        11: goto          18
        14: iload_1
        15: iload_2
        16: iadd
        17: istore_3
        18: iload_3
        19: ireturn

  public int sumArray(int[]);
    Code:
         0: iconst_0
         1: istore_2
         2: iconst_0
         3: istore_3
         4: iload_3
         5: aload_1
         6: arraylength
         7: if_icmpge     22
        10: iload_2
        11: aload_1
        12: iload_3
        13: iaload
        14: iadd
        15: istore_2
        16: iinc          3, 1
        19: goto          4
        22: iload_2
        23: ireturn
}
```

---

## 3. Explanation of Key Bytecode Instructions

The JVM is a stack-based abstract machine. All arithmetic and logic operations manipulate the **Operand Stack** and **Local Variable Table**.

| # | Instruction | Operand/Format | Description & Operation in Code |
|---|---|---|---|
| **1** | `aload_0` | - | **Load reference from local variable 0**: Pushes `this` (reference to current object) onto the operand stack. Used here before calling the superclass constructor `Object.<init>()`. |
| **2** | `invokespecial` | `#1 (Method ref)` | **Invoke instance initialization or private method**: Calls `java/lang/Object."<init>":()V` to initialize the superclass state. Does not perform dynamic method dispatch. |
| **3** | `iconst_0` / `iconst_3` | - | **Push integer constant**: Pushes immediate integer values (like `0` or `3`) directly onto the operand stack. Optimized single-byte opcodes for common constants (-1 through 5). |
| **4** | `istore_3` / `istore_2` | index | **Store int into local variable**: Pops the top `int` from the operand stack and writes it into local variable table slot 3 (which represents the `bonus` variable in `calculateBonus`). |
| **5** | `iload_1` / `iload_2` | index | **Load int from local variable**: Pushes the integer value from local variable slot 1 (`baseScore`) or slot 2 (`streak`) onto the operand stack for upcoming operations. |
| **6** | `if_icmple` | branch offset | **Integer comparison and branch (<=)**: Pops two integers from the stack and compares them (`streak` vs `3`). If `streak <= 3`, control jumps to bytecode offset 14 (the `else` branch). Otherwise execution falls through to offset 7. |
| **7** | `imul` | - | **Integer multiplication**: Pops two integers from the stack, computes their product (`baseScore * 2`), and pushes the product onto the operand stack. |
| **8** | `iadd` | - | **Integer addition**: Pops two integers from the stack, adds them (`baseScore + streak`), and pushes the sum onto the operand stack. |
| **9** | `iinc` | index, const | **Direct local variable increment**: Increments the local variable at slot 3 (`i` in loop) by `1` directly without pushing it to or popping it from the operand stack. Highly optimized for loop counters. |
| **10**| `ireturn` | - | **Return integer from method**: Pops the integer at the top of the operand stack and returns it to the caller method. |

---

## 4. Key Takeaways for Lab Exam
- Java source code (`.java`) is compiled by `javac` into architecture-independent bytecode stored in `.class` files.
- The Java Virtual Machine (JVM) executes bytecode using an execution engine (Interpreter and Just-In-Time compiler).
- `javap -c` disassembles class files to expose the underlying JVM instructions, showing how high-level constructs (`if`, `for`, `+`, `*`) translate to stack operations.
