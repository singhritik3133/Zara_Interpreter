# 🔵 ZARA Interpreter

> **Z**ero-ceremony **A**rithmetic and **R**easoning **A**ssembler — A mini scripting language interpreter built entirely in pure Java.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![OOP](https://img.shields.io/badge/Advanced_OOP-Project-blue?style=for-the-badge)
![University](https://img.shields.io/badge/Sitare-University-1A3C5E?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-success?style=for-the-badge)

---

## 📌 What is ZARA?

ZARA is a custom-designed toy programming language with a clean, minimal syntax. This project implements a **fully working interpreter** for ZARA — meaning you can write code in ZARA, run it through our program, and see real output on your screen.

The interpreter is built as a **3-stage pipeline**:

```
Source Code  ──►  Tokenizer  ──►  Parser  ──►  Evaluator  ──►  Output
```

| Stage | What it does |
|-------|-------------|
| **Tokenizer** | Reads source code character by character and breaks it into labelled tokens |
| **Parser** | Reads tokens and builds a tree of objects capturing the program structure |
| **Evaluator** | Walks the tree and executes each instruction, producing final output |

---

## 🚀 Getting Started

### Prerequisites
Make sure Java is installed on your machine:
```bash
java -version
javac -version
```
If not installed, download **JDK 17 or above** from [jdk.java.net](https://jdk.java.net)

---

### 📥 Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/zara-interpreter.git
cd zara-interpreter
```

---

### ⚙️ Compile
```bash
javac *.java
```

---

### ▶️ Run a ZARA Program
```bash
java Main yourfile.zara
```

---

## 📝 ZARA Language — Quick Reference

| Feature | Syntax | Example |
|---------|--------|---------|
| Assign variable | `set <name> = <value>` | `set x = 10` |
| Print output | `show <value>` | `show x` |
| Conditional | `when <condition>:` | `when x > 5:` |
| Loop | `loop <number>:` | `loop 3:` |

### Supported Operators
| Type | Operators |
|------|-----------|
| Arithmetic | `+`  `-`  `*`  `/` |
| Comparison | `>`  `<`  `==` |

---

## 💻 Sample Programs

### Program 1 — Arithmetic & Variables
```
set x = 10
set y = 3
set result = x + y * 2
show result
```
**Output:**
```
16
```

---

### Program 2 — String Output
```
set name = "Sitare"
show name
show "Hello from ZARA"
```
**Output:**
```
Sitare
Hello from ZARA
```

---

### Program 3 — Conditional
```
set score = 85
when score > 50:
    show "Pass"
```
**Output:**
```
Pass
```

---

### Program 4 — Loop
```
set i = 1
loop 4:
    show i
    set i = i + 1
```
**Output:**
```
1
2
3
4
```

---

## 🏗️ Project Structure

```
zara-interpreter/
│
├── 📄 TokenType.java        — Enum of all token types (SET, SHOW, NUMBER, etc.)
├── 📄 Token.java            — Immutable token object (type + value + line number)
├── 📄 Tokenizer.java        — Converts raw source code into a list of tokens
│
├── 📄 Expression.java       — Interface for all expression nodes
├── 📄 NumberNode.java       — Represents a number literal (e.g. 42)
├── 📄 StringNode.java       — Represents a string literal (e.g. "hello")
├── 📄 VariableNode.java     — Represents a variable reference (e.g. x)
├── 📄 BinaryOpNode.java     — Represents an operation (e.g. x + y * 2)
├── 📄 Environment.java      — Variable store — maps names to values
├── 📄 Parser.java           — Builds instruction tree from token list
│
├── 📄 Instruction.java      — Interface + all 4 instruction classes
│                              (AssignInstruction, PrintInstruction,
│                               IfInstruction, RepeatInstruction)
├── 📄 Interpreter.java      — Chains all 3 stages together
├── 📄 Main.java             — CLI entry point — reads .zara file and runs it
│
├── 📄 test1.zara            — Sample: Arithmetic
├── 📄 test2.zara            — Sample: Strings
├── 📄 test3.zara            — Sample: Conditional
└── 📄 test4.zara            — Sample: Loop
```

---

## 🔑 Key Design Decisions

### Why a Tree (AST)?
A flat list of tokens cannot capture operator precedence. The tree structure handles it automatically — multiply nodes sit deeper in the tree, so they evaluate first.

```
  x + y * 2   →      Add
                     /   \
                    x   Multiply
                        /     \
                       y       2
```

### How Operator Precedence Works
The parser uses a **call chain** to enforce precedence without any special tricks:

```
parseExpression()  →  handles + and -
    └── parseTerm()  →  handles * and /
            └── parsePrimary()  →  handles numbers, strings, variables
```

Because `parseTerm()` is called inside `parseExpression()`, multiplication and division always bind tighter than addition and subtraction.

### Why does Environment throw RuntimeException?
If a variable is used before it is defined, the program should crash with a **clear, helpful message** like `Variable not defined: x` rather than silently returning null and producing a confusing error later.

---

## 👨‍💻 Team Members

| Member | Role | Files |
|--------|------|-------|
| **Person 1** | Tokenizer | TokenType.java, Token.java, Tokenizer.java |
| **Person 2** | Parser | Expression.java, \*Node.java, Parser.java |
| **Person 3** | Evaluator | Instruction.java, Interpreter.java, Main.java |
| **Person 4** | Support + Testing | Environment.java, README.md, Test files |

---

## 🎓 Course Details

| Field | Info |
|-------|------|
| **University** | Sitare University |
| **Course** | Advanced Object-Oriented Programming in Java |
| **Project Type** | Take-Home Group Project |
| **Marks** | 12.5 |
| **Group Size** | 4 Members |

---

## 📖 How to Write Your Own ZARA Program

1. Create a new file with `.zara` extension
2. Write your program using ZARA syntax
3. Compile the interpreter: `javac *.java`
4. Run your program: `java Main myprogram.zara`

**Example — your own program:**
```
set a = 5
set b = 10
set sum = a + b
show "The sum is:"
show sum
when sum > 10:
    show "Sum is greater than 10"
loop 3:
    show "Hello World"
```

---

## ⚠️ Error Handling

| Error | Message |
|-------|---------|
| Undefined variable | `Runtime error: Variable not defined: x` |
| Unexpected character | `Unexpected character '#' on line 3` |
| Unterminated string | `Unterminated string starting on line 2` |
| Division by zero | `Runtime error: Division by zero.` |

---

## 📜 License
This project was built for educational purposes as part of the Advanced OOP course at Sitare University.

---

<div align="center">
  <strong>Built with ❤️ by Team ZARA — Sitare University</strong>
</div>
