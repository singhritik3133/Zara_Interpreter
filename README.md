# ZARA Interpreter
> **Z**ero-ceremony **A**rithmetic and **R**easoning **A**ssembler — A mini scripting language interpreter built entirely in pure Java.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![OOP](https://img.shields.io/badge/Advanced_OOP-Project-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-success?style=for-the-badge)
![University](https://img.shields.io/badge/Sitare-University-1A3C5E?style=for-the-badge)

---

## 📌 What is ZARA?

ZARA is a custom toy programming language with clean, minimal syntax. This project builds a **fully working interpreter** — write code in ZARA, run it, and see real output on screen.

The interpreter works as a **3-stage pipeline:**

```
Source Code  ──►  Tokenizer  ──►  Parser  ──►  Evaluator  ──►  Output
```

| Stage | Description |
|-------|-------------|
| **Tokenizer** | Reads source code character by character and breaks it into labelled tokens |
| **Parser** | Reads tokens and builds a tree that captures the program structure |
| **Evaluator** | Walks the tree and executes each instruction to produce output |

---

## ⚙️ How to Compile & Run

### Prerequisites
Make sure Java is installed:
```bash
java -version
javac -version
```

### Compile
```bash
javac *.java
```

### Run a ZARA file
```bash
java Main yourfile.zara
```

---

## 📝 ZARA Language Syntax

| Feature | Syntax | Example |
|---------|--------|---------|
| Assign variable | `set <name> = <value>` | `set x = 10` |
| Print output | `show <value>` | `show x` |
| Conditional | `when <condition>:` | `when x > 50:` |
| Loop | `loop <number>:` | `loop 4:` |

**Supported Operators:** `+`  `-`  `*`  `/`  `>`  `<`  `==`

---

## 💻 Sample Programs & Output

**Program 1 — Arithmetic**
```
set x = 10
set y = 3
set result = x + y * 2
show result
```
```
Output: 16
```

**Program 2 — String Output**
```
set name = "Sitare"
show name
show "Hello from ZARA"
```
```
Output:
Sitare
Hello from ZARA
```

**Program 3 — Conditional**
```
set score = 85
when score > 50:
    show "Pass"
```
```
Output: Pass
```

**Program 4 — Loop**
```
set i = 1
loop 4:
    show i
    set i = i + 1
```
```
Output: 1  2  3  4
```

---

## 🏗️ Project Structure

```
zara-interpreter/
├── TokenType.java       — All token types (SET, SHOW, NUMBER, etc.)
├── Token.java           — Immutable token object (type + value + line)
├── Tokenizer.java       — Converts source code into list of tokens
├── Expression.java      — Interface for all expression nodes
├── NumberNode.java      — Represents a number literal
├── StringNode.java      — Represents a string literal
├── VariableNode.java    — Represents a variable reference
├── BinaryOpNode.java    — Represents an operation (x + y * 2)
├── Environment.java     — Variable store (HashMap of name → value)
├── Parser.java          — Builds instruction tree from tokens
├── Instruction.java     — All 4 instruction classes (Assign, Print, If, Repeat)
├── Interpreter.java     — Chains all 3 stages together
├── Main.java            — Entry point — reads .zara file and runs it
├── test1.zara           — Sample: Arithmetic
├── test2.zara           — Sample: Strings
├── test3.zara           — Sample: Conditional
└── test4.zara           — Sample: Loop
```

---

## 👨‍💻 Team Members

| Member | Role | Files Owned |
|--------|------|-------------|
| **Person 1** | Tokenizer | TokenType.java, Token.java, Tokenizer.java |
| **Person 2** | Parser | Expression.java, \*Node.java, Parser.java |
| **Person 3** | Evaluator | Instruction.java, Interpreter.java, Main.java |
| **Person 4** | Support + Testing | Environment.java, README.md, Test files |

---

## 🎓 Course Details

| | |
|-|-|
| **University** | Sitare University |
| **Course** | Advanced Object-Oriented Programming in Java |
| **Project** | Take-Home Group Project |
| **Marks** | 12.5 |

---

<div align="center">
<strong>Built with ❤️ by Team ZARA — Sitare University</strong>
</div>
