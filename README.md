[![](https://jitpack.io/v/FahimHossain2186/FileX.svg)](https://jitpack.io/#FahimHossain2186/FileX)

# 📂 FileX

> **File handling in Java shouldn't require a PhD.**

FileX is an ultra-simple, human-readable file handling library for Java designed specifically for beginners.

Instead of forcing new Java developers to learn `BufferedReader`, `Scanner`, `Path`, `Files`, loops, and complicated exception structures just to read a text file, FileX wraps those concepts into commands that read like plain English.

```java
var writer = FileX.write("notes.txt");
writer.write("Hello World");

var reader = FileX.read("notes.txt");
System.out.println(reader.readLine());
```

Simple.

Readable.

Beginner-friendly.

---

## Table of Contents

* [Why FileX Exists](#why-filex-exists)
* [Who Is FileX For?](#who-is-filex-for)
* [Our Philosophy](#our-philosophy)
* [Requirements](#requirements)
* [Two Ways to Create a File Handle](#two-ways-to-create-a-file-handle)
* [Features](#features)
  * [Reading](#reading)
  * [Writing](#writing)
  * [Appending](#appending)
  * [Custom Charsets](#custom-charsets)
  * [Utilities](#utilities)
* [Installation](#installation)
  * [Method 1: Download the JAR Manually (Beginner Friendly)](#method-1-download-the-jar-manually-beginner-friendly)
  * [Method 2: Terminal Installation](#method-2-terminal-installation)
  * [Method 3: JitPack — Maven / Gradle (Advanced)](#method-3-jitpack--maven--gradle-advanced)
* [Quick Start](#quick-start)
* [Design Principles](#design-principles)
* [Limitations](#limitations)
* [Contributing](#contributing)
* [License](#license)

---

## Why FileX Exists

Java's built-in file APIs are powerful.

They're also intimidating.

To perform something as simple as reading a file, beginners often encounter:

* `BufferedReader`
* `FileReader`
* `Scanner`
* loops
* `Path`
* `Files`
* nested exceptions
* multiple imports

—all at the same time.

FileX removes that overwhelming boilerplate so beginners can focus on understanding **what their code is doing** before worrying about how Java does it internally.

---

## Who Is FileX For?

FileX was built:

* ✅ by a beginner,
* ✅ for beginners,
* ✅ with beginners in mind.

It is ideal for:

* Students learning Java
* People transitioning from Python to Java
* First-year CSE students
* Self-taught programmers
* Junior developers who want cleaner file operations
* Educators teaching Java fundamentals

If you're already comfortable with Java NIO, Apache Commons, or Guava, FileX probably isn't for you.

And that's okay.

---

## Our Philosophy

FileX does **not** try to compete with:

* Apache Commons IO
* Google Guava
* Enterprise frameworks

We deliberately avoid:

* Massive utility classes
* Complex factory patterns
* Advanced optimizations
* Clever abstractions

Instead, FileX values:

* Simplicity
* Readability
* Safety
* Discoverability
* Beginner confidence

The API should read like a story.

```java
var writer = FileX.write("essay.txt");
writer.write("Introduction");

var appender = FileX.append("essay.txt");
appender.append("Conclusion");

var reader = FileX.read("essay.txt");
reader.readLine();
```

Even before typing it, you can imagine what the code does.

---

## Requirements

> [!IMPORTANT]
> **Java 11 or higher is required.**
> FileX uses `var` (Java 10+) and `String.isBlank()` (Java 11+) internally.
> If your JDK is set below 11, you'll hit compile errors that have nothing
> to do with FileX itself — check your project's JDK version first.

---

## Two Ways to Create a File Handle

`Read`, `Write`, and `Append` can each be created two ways — pick whichever
feels more natural to you. Both produce the exact same object, run through
the exact same path/charset validation, and behave identically afterward.

**Factory method** — reads like a sentence, matches the rest of the FileX API:

```java
var reader = FileX.read("notes.txt");
var reader = FileX.read("notes.txt", StandardCharsets.UTF_8);
```

```java
FileX.Read reader = FileX.read("notes.txt");
FileX.Read reader = FileX.read("notes.txt", StandardCharsets.UTF_8);
```

**Constructor** — the classic `new ClassName(...)` pattern you already know
from `Scanner`, `ArrayList`, and `Random`:

```java
var reader = new FileX.Read("notes.txt");
var reader = new FileX.Read("notes.txt", StandardCharsets.UTF_8);
```

```java
FileX.Read reader = new FileX.Read("notes.txt");
FileX.Read reader = new FileX.Read("notes.txt", StandardCharsets.UTF_8);
```

The same applies to `FileX.write(...)` / `new FileX.Write(...)` and
`FileX.append(...)` / `new FileX.Append(...)`. Use whichever you find
easier to read — neither is "more correct."

---

# Features

## Reading

```java
var reader = FileX.read("notes.txt");
```

### Read the next line

```java
reader.readLine();
```

### Read a specific line

```java
reader.readLine(3);
```

### Read all lines

```java
reader.readAllLines();
```

### Check if another line exists

```java
reader.hasNextLine();
```

### Reset sequential reading

```java
reader.resetReader();
```

### Get the next line number

```java
reader.nextLineNumber();
```

### Force a fresh read from disk

```java
reader.refresh();
```

> FileX caches lines in memory after the first read, so repeated calls
> don't keep hitting the filesystem. If the file changes on disk after
> you've started reading, call `refresh()` to pick up the new content.

---

## Writing

```java
var writer = FileX.write("notes.txt");
```

### Overwrite with one line

```java
writer.write("Hello");
```

### Overwrite with multiple lines

```java
writer.write(List.of(
    "Math",
    "Physics",
    "Chemistry"
));
```

---

## Appending

```java
var appender = FileX.append("notes.txt");
```

### Append one line

```java
appender.append("Biology");
```

### Append multiple lines

```java
appender.append(List.of(
    "English",
    "History"
));
```

---

## Custom Charsets

Every entry point defaults to UTF-8, but accepts an optional `Charset` if
you need something else.

```java
var reader = FileX.read("notes.txt", StandardCharsets.ISO_8859_1);
var writer = FileX.write("notes.txt", StandardCharsets.UTF_16);
var appender = FileX.append("notes.txt", StandardCharsets.US_ASCII);
```

> Constructor style takes a charset the same way —
> see [Two Ways to Create a File Handle](#two-ways-to-create-a-file-handle).

---

## Utilities

### Check if a file exists

```java
FileX.exists("notes.txt");
```

### Create a file

```java
FileX.create("notes.txt");
```

### Create parent directories

```java
FileX.createParentDirectories(
    "docs/projects/notes.txt"
);
```

### Delete a file

```java
FileX.delete("notes.txt");
```

---

# Installation

## Method 1: Download the JAR Manually (Beginner Friendly)

1. Go to the [latest release](https://github.com/FahimHossain2186/FileX/releases/latest).
2. Under **Assets**, download `filex-1.0.0.jar` — **not** `filex-1.0.0-sources.jar` or `filex-1.0.0-javadoc.jar`. Those two are extras for browsing the source code and documentation inside your IDE; they aren't what you actually run your program against.
3. Add it to your Java project using one of the steps below.

---

### IntelliJ IDEA

* File
* Project Structure
* Libraries
* Click +
* Java
* Select the JAR you downloaded `filex-1.0.0.jar`
* Apply

Done.

---

### Eclipse

* Right-click project
* Build Path
* Configure Build Path
* Libraries
* Add External JARs
* Select the JAR you downloaded `filex-1.0.0.jar`
* Apply and Close

Done.

---

### VS Code

Place the JAR `filex-1.0.0.jar` inside:

```
project/lib/
```

Then add it to your Java dependencies.

Done.

---

## Method 2: Terminal Installation

1. Go to the [latest release](https://github.com/FahimHossain2186/FileX/releases/latest).
2. Right-click the plain `.jar` asset (not `-sources` or `-javadoc`) and
   choose **Copy Link** (or **Copy Link Address**) to get its direct
   download URL.
3. Paste that link in place of `<JAR_DOWNLOAD_URL>` below.

This way the commands always work, even after future releases change the
exact filename.

---

### Windows (PowerShell)

Download:

```powershell
curl -L -o FileX.jar <JAR_DOWNLOAD_URL>
```

Compile:

```powershell
javac -cp FileX.jar Main.java
```

Run:

```powershell
java -cp ".;FileX.jar" Main
```

---

### macOS / Linux

Download:

```bash
curl -L -o FileX.jar <JAR_DOWNLOAD_URL>
```

Compile:

```bash
javac -cp FileX.jar Main.java
```

Run:

```bash
java -cp ".:FileX.jar" Main
```

---

## Method 3: JitPack — Maven / Gradle (Advanced)

If you're using a build tool, FileX is published via
[JitPack](https://jitpack.io), so you can add it as a normal dependency
without downloading anything manually. JitPack builds directly from the
tagged source, so this works regardless of what release assets exist.

The current release is **`v1.0.0`**.

### Maven

Add the JitPack repository:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Add the dependency:

```xml
<dependency>
  <groupId>com.github.FahimHossain2186</groupId>
  <artifactId>FileX</artifactId>
  <version>v1.0.0</version>
</dependency>
```

### Gradle (Groovy DSL)

```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.github.FahimHossain2186:FileX:v1.0.0'
}
```

### Gradle (Kotlin DSL)

```kotlin
repositories {
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation("com.github.FahimHossain2186:FileX:v1.0.0")
}
```

> For a future release, just swap `v1.0.0` for the new tag name in the
> snippets above.

---

> [!TIP]
> **For most beginners, Method 1 is the easiest.**
> For Maven/Gradle projects, Method 3 is the most convenient and professional option.

---

# Quick Start

```java
import com.github.espresso.FileX;

import java.util.List;

public class Main {

  public static void main(String[] args)
          throws Exception {

    FileX.create("notes.txt");

    var writer = FileX.write("notes.txt");

    writer.write(List.of(
            "Math",
            "Physics",
            "Chemistry"
    ));

    var appender = FileX.append("notes.txt");

    appender.append("Biology");

    var reader = FileX.read("notes.txt");

    while (reader.hasNextLine()) {
      System.out.println(reader.readLine());
    }
  }
}
```

Output:

```
Math
Physics
Chemistry
Biology
```

---

# Design Principles

FileX follows the Principle of Least Privilege.

A reader should read.

A writer should write.

An appender should append.

Instead of:

```java
file.doEverything();
```

FileX encourages:

```java
var reader = FileX.read(...);
var writer = FileX.write(...);
var appender = FileX.append(...);
```

Each object only has the responsibilities it needs.

This teaches good API design practices from the very beginning.

---

# Limitations

> [!WARNING]
> FileX intentionally avoids advanced functionality. It is **not** designed for:
> - Gigabyte-sized files
> - High-performance streaming
> - Concurrent file access (`Read` instances are not thread-safe)
> - Enterprise applications

If you eventually outgrow FileX, you'll already understand the concepts needed to transition into Java's native file APIs.

And that was always the goal.

---

# Contributing

Suggestions, bug reports, and beginner feedback are always welcome.

If FileX helped make Java feel a little less scary, consider starring the repository ⭐.

Every beginner deserves tools built with empathy.

---

# License

Released under the MIT License.
