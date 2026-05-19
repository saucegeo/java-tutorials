# Understanding Unit Testing: How testAdd() Works

## The Big Picture

When you write a unit test, you're testing **code that exists elsewhere** in your project. The test itself doesn't define the methods it's testing—it *calls* them and verifies they work correctly.

## Your Project Structure

```
tutorial-2/
├── src/
│   ├── main/java/
│   │   └── Calculator.java       ← Contains the actual methods
│   └── test/java/
│       └── CalculatorTest.java   ← Contains the tests that verify those methods
├── pom.xml
└── README.md
```

> [!info] Key Concept
> **Main code** (what your application does) lives in `src/main/java/`
> **Test code** (what verifies your application works) lives in `src/test/java/`

## The Two Files Working Together

### File 1: Calculator.java (The Code Being Tested)

This file **defines** the actual methods:

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }
}
```

### File 2: CalculatorTest.java (The Tests)

This file **tests** those methods:

```java
public class CalculatorTest {
    Calculator calc = new Calculator();  // Create an instance to test
    
    @Test
    public void testAdd() {
        assertEquals(5, calc.add(2, 3));  // Call the add() method and verify it returns 5
    }
}
```

## How testAdd() Works: Step by Step

### Step 1: Create a Calculator Instance

```java
Calculator calc = new Calculator();
```

This line creates a new `Calculator` object. Now `calc` has access to all the public methods in the `Calculator` class (like `add()`, `subtract()`, and `divide()`).

> [!note] Why Create an Instance?
> You can't call methods directly on a class—you need an instance (object) of that class. This is why you create `calc` first.

### Step 2: Call the add() Method

Inside `testAdd()`, you write:

```java
calc.add(2, 3)
```

This **calls** the `add()` method from the `Calculator` class with arguments `2` and `3`.

### Step 3: The add() Method Executes

Java finds the `add()` method in the `Calculator` class:

```java
public int add(int a, int b) {
    return a + b;
}
```

It substitutes the values:
- `a` = 2
- `b` = 3
- Returns: 2 + 3 = **5**

### Step 4: Assert the Result

The test checks if the result is what we expect:

```java
assertEquals(5, calc.add(2, 3));
```

This means: "Assert that the result equals 5"

### Step 5: Test Result

✅ The method returns 5, which matches our expectation, so the test **PASSES**

## Visual Flow Diagram

```
CalculatorTest.java                  Calculator.java
┌─────────────────────────────────┐  ┌──────────────────────────┐
│ @Test                           │  │ public class Calculator  │
│ void testAdd() {                │  │ {                        │
│   Calculator calc =             │  │   public int add(a, b) { │
│     new Calculator();           │──→└──┐  return a + b;        │
│                                 │      │ }                     │
│   calc.add(2, 3)  ─────────────────→  │ Executes:             │
│   (calls the method)            │      │ 2 + 3 = 5             │
│                                 │      │ Returns: 5            │
│   assertEquals(5, result) ◄─────┼──────┘                       │
│   (comparison happens here)     │                              │
│ }                               │                              │
└─────────────────────────────────┘  └──────────────────────────┘
     Test Code                             Production Code
```

## The Complete Execution Flow

```
1. JUnit detects @Test annotation
   ↓
2. Creates instance: Calculator calc = new Calculator()
   ↓
3. Calls: calc.add(2, 3)
   ↓
4. Java looks up add() method in Calculator class
   ↓
5. Executes: return 2 + 3
   ↓
6. Result (5) comes back to the test
   ↓
7. assertEquals(5, 5) checks if they match
   ↓
8. They match! Test PASSES ✅
```

## Why This Design?

> [!success] Benefits of Separating Code and Tests
> - **Organization**: Production code separate from test code
> - **Reusability**: The `add()` method can be used anywhere in your app
> - **Verification**: Tests confirm the method works as expected
> - **Isolation**: Tests don't affect production code
> - **Maintenance**: If you change `add()`, you can quickly see if tests still pass

## Common Misconception

> [!warning] Wrong Understanding
> ❌ "I need to define add() in the test file to test it"
> 
> ✅ "The add() method is already defined in Calculator.java, the test just calls it"

## A Real-World Analogy

Imagine you're testing a calculator app:

```
The Calculator (Program)          Your Test Procedure
┌──────────────────────┐         ┌─────────────────────┐
│ Has an add button    │         │ 1. Press 2          │
│ Has a subtract btn   │◄────────│ 2. Press + button   │
│ Has a divide btn     │         │ 3. Press 3          │
│ etc.                 │         │ 4. Press =          │
└──────────────────────┘         │ 5. Check: did it    │
                                 │    show 5?          │
                                 └─────────────────────┘
```

The **calculator** contains the actual functionality (like the `add()` method).
The **test procedure** uses that functionality to verify it works.

## What Happens When You Run Maven Test

When you run `mvnd test`:

```
1. Maven finds all @Test methods in CalculatorTest.java
2. For each test:
   a. Creates a fresh Calculator instance
   b. Calls the test method (e.g., testAdd)
   c. The test method calls Calculator's add() method
   d. Verifies the result
3. Reports which tests passed ✅ and which failed ❌
```

## Your Incomplete Tests

Looking at your test file, you have incomplete tests:

```java
@Test
public void testSubstract() {
    assert  // ← Incomplete! Needs to compare something
}
```

This should be completed like:

```java
@Test
public void testSubtract() {
    assertEquals(1, calc.subtract(3, 2));  // ← Calls subtract() from Calculator
}
```

## Summary

| Concept | Explanation |
|---------|-------------|
| **Calculator.java** | Contains the actual `add()` method that performs the calculation |
| **CalculatorTest.java** | Contains the `testAdd()` method that calls `add()` and verifies it works |
| **calc.add(2, 3)** | This **calls** the method defined in Calculator, doesn't define it |
| **assertEquals()** | JUnit compares the expected result (5) with actual result |
| **Test passes** | When the method returns what we expect, the test succeeds |

The `add()` method isn't "magically" defined in the test—it's explicitly defined in the `Calculator` class, and the test simply uses it to verify it works correctly! 🎯

