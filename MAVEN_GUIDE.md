# Maven: A Complete Guide

## What is Maven?

Maven is a **build automation and project management tool** primarily used for Java projects. It helps manage the entire lifecycle of a project from compilation to testing to deployment.

> [!info] Maven's Full Name
> "Maven" comes from Yiddish and means "accumulator of knowledge." As a tool, it accumulates knowledge about your project structure, dependencies, and build process.

## Key Problems Maven Solves

### 1. **Dependency Management**
Without Maven, you'd have to:
- Manually download JAR files (libraries)
- Add them to your project's classpath
- Manage version conflicts yourself
- Share JAR files across projects

With Maven, you declare what you need in `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

Maven automatically downloads and manages all your dependencies and their transitive dependencies.

### 2. **Build Consistency**
Maven enforces a **standard directory structure** across all Java projects:

```
project/
├── src/
│   ├── main/
│   │   └── java/          # Your source code
│   └── test/
│       └── java/          # Your test code
├── target/                # Compiled output
└── pom.xml               # Project configuration
```

### 3. **Build Lifecycle Management**
Maven defines a standard build lifecycle with predictable phases.

> [!note] Build Lifecycle Phases
> Maven builds go through these phases in order:
> - `validate` - Validate project is correct
> - `compile` - Compile source code
> - `test` - Run tests
> - `package` - Package compiled code
> - `verify` - Run integration tests
> - `install` - Install package to local repository
> - `deploy` - Deploy to remote repository

## What Happened When You Ran `mvnd test`

When you executed:

```bash
cd tutorial-2
mvnd test
```

Here's what Maven did behind the scenes:

### Step 1: Validation
Maven verified that your `pom.xml` is valid and contains all required information.

### Step 2: Compilation
Before running tests, Maven:
- Compiled all source code in `src/main/java/`
- Compiled all test code in `src/test/java/`

### Step 3: Dependency Resolution
Maven:
- Read your `pom.xml` and identified that you need `junit-jupiter:5.10.0`
- Downloaded it (if not already cached) from Maven Central Repository
- Added it to the classpath for compilation and testing

### Step 4: Test Execution
Maven:
- Found all test classes (matching `*Test.java` pattern)
- Ran them using the `maven-surefire-plugin` (defined in your pom.xml)
- Generated test reports

### Step 5: Output
Maven placed all compiled code and test results in the `target/` directory:

```
tutorial-2/target/
├── classes/                    # Compiled main code
├── test-classes/               # Compiled test code
├── surefire-reports/           # Test results
└── ...
```

## Common Maven Commands

```bash
# Clean build - removes target/ directory
mvnd clean

# Compile source code
mvnd compile

# Run tests
mvnd test

# Package your project into a JAR
mvnd package

# Clean and package
mvnd clean package

# Skip tests during build
mvnd clean package -DskipTests

# Run with debug logging
mvnd clean test -X
```

## Why Use Maven Instead of Manually Managing Everything?

> [!success] Benefits of Maven
> - **Reproducible Builds**: Same build, every time, every machine
> - **Dependency Management**: Automatic download and conflict resolution
> - **Consistent Structure**: All Maven projects follow the same layout
> - **Plugin Ecosystem**: Powerful plugins for compilation, testing, reporting
> - **CI/CD Integration**: Easy to integrate with Jenkins, GitHub Actions, etc.
> - **Convention Over Configuration**: Works great out of the box with minimal setup

## Your pom.xml Breakdown

```xml
<project>
  <!-- Project identification -->
  <groupId>tutorial-2</groupId>        <!-- Organization/namespace -->
  <artifactId>tut2</artifactId>        <!-- Project name -->
  <version>1.0</version>               <!-- Project version -->

  <!-- External libraries needed -->
  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.10.0</version>
      <scope>test</scope>               <!-- Only used for testing -->
    </dependency>
  </dependencies>

  <!-- Build configuration -->
  <build>
    <plugins>
      <!-- Plugin for running tests -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.1.2</version>
      </plugin>
      
      <!-- Plugin for compiling Java code -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.11.0</version>
        <configuration>
          <source>19</source>  <!-- Compile for Java 19 -->
          <target>19</target>  <!-- Target Java 19 -->
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

## Maven Repository

Maven uses repositories to store and retrieve dependencies. By default:

- **Maven Central Repository**: `https://repo.maven.apache.org/maven2/`
  - The main public repository with millions of open-source libraries
  - Automatically searched by Maven

```bash
# Local repository location (cached on your machine)
~/.m2/repository/

# Example: JUnit cache
~/.m2/repository/org/junit/jupiter/junit-jupiter/5.10.0/
```

> [!tip] First Time Build
> The first time you run a Maven build, downloading dependencies can take a while. Subsequent builds are much faster because Maven caches everything in `~/.m2/repository/`.

## Maven Daemon (mvnd) vs Maven (mvn)

```bash
# Regular Maven - starts JVM each time (slower)
$ mvn test

# Maven Daemon - keeps JVM warm in background (faster)
$ mvnd test
```

> [!info] Why mvnd is Faster
> Maven Daemon keeps a Java process running in the background, so subsequent builds don't waste time starting the JVM. You saw this with the "Daemon 6e76fca3" message!

## Summary

Maven is essentially a **smart build orchestrator** that:
1. Manages all your project's dependencies
2. Compiles your code consistently
3. Runs your tests automatically
4. Packages your application for distribution
5. Enforces best practices through conventions

When you ran `mvnd test`, Maven handled all these steps automatically so you didn't have to manually manage JAR files, classpath configurations, or build processes.

---

**Further Reading:**
- [Maven Official Documentation](https://maven.apache.org/)
- [Maven Central Repository](https://mvnrepository.com/)
- [POM Reference](https://maven.apache.org/pom.html)

