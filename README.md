# ![ToyIDE logo](doc/images/toyide-logo.png?raw=true "ToyIDE")

__Note:__ This is a fork from [here](https://github.com/pavelfatin/toyide). All changes
by Hanns Holger Rutz, released under the same license (GPL v3+). This fork refactors the
project into sbt sub-modules, cross-building against Scala 2.11 and 2.12,
applies some code style changes, and investigates the possibility
of adding Scala as a supported language (in progress). To run the demo application,
use `sbt app/run`.

Known issues:

- `sbt test` fails with several cases, also different between Scala 2.11 and 2.12.
  This must be either a race condition or dirty global state, because running, for
  example, `sbt 'testOnly com.pavelfatin.toyide.languages.lisp.library.CoreTest'`
  works without problem, although that spec fails when running all tests.

Below is the original README:

----------

ToyIDE is an imitation of a full-featured IDE plus
[toy languages](https://en.wikipedia.org/wiki/Toy_language "Toy language - Wikipedia")
(imperative, functional) with complete IDE support, interpreters and compilers.
All the parts were built from scratch in [Scala](https://www.scala-lang.org/ "The Scala Programming Language"),
without relying on any existing code or libraries.

The project is purely educational. It is a product of a long vacation in
the country and a desire to learn how all this stuff really works.

Although I applied [evolutionary design](https://www.artima.com/intv/evolution.html "Evolutionary Design - Artima")
in the development (to uncover the reasons behind architecture), it turns out
that many techniques in the code come close to commonly used patterns.
I learned a lot from the project and hope it might be useful to the people
who want to know more about [lexers](https://en.wikipedia.org/wiki/Lexical_analysis "Lexical analysis - Wikipedia"),
[parsers](https://en.wikipedia.org/wiki/Parsing "Parsing - Wikipedia"),
[AST](https://en.wikipedia.org/wiki/Abstract_syntax_tree "Abstract syntax tree - Wikipedia"),
[Java bytecode](https://en.wikipedia.org/wiki/Java_bytecode "Java bytecode - Wikipedia")
and other similar fun, but tricky things.

*Update: [Jason Zaugg](https://www.linkedin.com/in/jason-zaugg-0428b521) endorsed the project as ["open-source Scala application with simplicity and taste"](http://grokbase.com/p/gg/scala-user/141y2yf9yc/open-source-scala-applications-with-simplicity-and-taste)*

[![ToyIDE: Main window](doc/images/toyide-main-window.jpg?raw=true)](doc/images/toyide-main-window-large.png?raw=true)

Source code is 100% Scala. There are ~800 unit tests (run in < 3 seconds).

Download binaries: [toyide-1.2.4-bin.zip](https://github.com/pavelfatin/toyide/releases/download/v1.2.4/toyide-1.2.4-bin.zip) (7.5 MB)

# Features

* [Imperative language](#imperative-language)
* [Functional language](#functional-language)
* [Syntax highlighting](#syntax-highlighting)
* [Advanced editor](#advanced-editor)
* [Split editing](#split-editing)
* [Brace matching](#brace-matching)
* [Show usages](#show-usages)
* [Autocomplete](#autocomplete)
* [Code formatting](#code-formatting)
* [Rename refactoring](#rename-refactoring)
* [Control / data flow analysis](#control--data-flow-analysis)
* [Static code optimization](#static-code-optimization)
* [Structure pane](#structure-pane)
* [Interpreter](#interpreter)
* [Compiler](#compiler)
* [Class export](#class-export)

## Imperative language

Imperative, statically-typed, C-like language:

* common data types & operators,
* comprehensive static analysis,
* data & control flow inspections,
* error reporting & stack traces,
* expression optimization,
* JVM bytecode emitter.

Project Euler code examples:

![ToyIDE: Imperative Language](doc/images/toyide-imperative-language.png?raw=true)

## Functional language

Functional, dynamically-typed, Clojure-like language:

* macros, variadic functions,
* quasi-quotations, anonymous function literals,
* unique symbol name generation,
* structural binding,
* tail call optimization,
* error reporting & stack traces,
* comprehensive standard library,

Minimalistic web server code as an example:

![ToyIDE: Functional Language](doc/images/toyide-functional-language.png?raw=true)

## Syntax highlighting

Expressive syntax and error highlighting, code inspections, customizable schemes:

![ToyIDE: Highlighting](doc/images/toyide-highlighting.png?raw=true)
![ToyIDE: Coloring](doc/images/toyide-coloring.png?raw=true)

## Advanced editor

Editor with selection, copy/paste, undo/redo, braces balancing,
auto-indentation:

![ToyIDE: Edit Menu](doc/images/toyide-edit-menu.png?raw=true)
![ToyIDE: Code Menu](doc/images/toyide-code-menu.png?raw=true)

## Split editing

Editor can be split horizontally, so that viewports, cursors and selections are separate, while text is common:

![ToyIDE: Split Editing](doc/images/toyide-split-editor.png?raw=true)

## Brace matching

Brace pairs are highlighted, complement braces are added / removed automatically:

![ToyIDE: Brace Matching](doc/images/toyide-brace-matching.png?raw=true)

## Show usages

Highlighting of declaration identifier and all related references:

![ToyIDE: Show Usages](doc/images/toyide-show-usages.png?raw=true)

## Autocomplete

Automatic completion of identifiers and keywords:

![ToyIDE: Autocomplete](doc/images/toyide-autocomplete.png?raw=true)

## Code formatting

Automatic formatting of arbitrary code blocks (spacing, indentation, line breaks):

![ToyIDE: Code Formatting (before)](doc/images/toyide-code-formatting-before.png?raw=true)
---
![ToyIDE: Code Formatting (after)](doc/images/toyide-code-formatting-after.png?raw=true)

## Rename refactoring

For languages that rely on built-in concept of references, renaming works out-of-the-box:

![ToyIDE: Rename Refactoring (before)](doc/images/toyide-rename-refactoring-before.png?raw=true)
---
![ToyIDE: Rename Refactoring (after)](doc/images/toyide-rename-refactoring-after.png?raw=true)

## Control / data flow analysis

Code is checked for unused declarations and unreachable statements:

![ToyIDE: Control Flow Analysis](doc/images/toyide-control-flow-analysis.png?raw=true)

## Static code optimization

Code expressions are automatically analyzed for possible simplifications:

![ToyIDE: Code Optimization](doc/images/toyide-code-optimization.png?raw=true)

## Structure pane

Internal code representation is reflected in a structure pane (with synchronous highlighting):

![ToyIDE: Structure Pane](doc/images/toyide-structure-pane.png?raw=true)

## Interpreter

Programs can be run by interpreter, without previously compiling them into bytecode:

![ToyIDE: Interpreter](doc/images/toyide-interpreter.png?raw=true)

## Compiler

Programs can also be compiled and run as JVM bytecode:

![ToyIDE: Compiler](doc/images/toyide-compile-command.png?raw=true)

## Class export

The bytecode can be exported as JVM .class files:

![ToyIDE: Class Export](doc/images/toyide-compiler.png?raw=true)

---
Pavel Fatin, [https://pavelfatin.com](https://pavelfatin.com)