# Intellij IDEA plugin for EOLANG (LSP)

This is an Intellij plugin for EOLANG that uses the EOLANG LSP server.

Install it from the JetBrains marketplace [TODO: publish it there].

## Structure

The project is divided into two parts: the client and the server.

The client is written in Java, and is located in src/main.

The server is written in Typescript. It is a submodule located in `eo-lsp-server`, and is published as a standalone repository in https://github.com/GeorgySabaev/eo-lsp-server **[TODO: move to objectionary]**

We use Rultor for publishing **[TODO: set up Rultor]**

## How to contribute

Note: this project contains a git-submodule. Clone the repository with the `--recurse-submodules` flag, or initialise the submodules afterwards via:

```
git submodule update --init --recursive
```

Fork repository, make changes, send us a pull request. We will review your changes and apply them to the master branch shortly, provided they don't violate our quality standards. 

To avoid frustration, before sending us your pull request please run full gradle build:

```
./gradlew build
```

You will need Gradle, Maven, Java 17+, Node 20 and NPM installed.

## Try it out

When editing the source code in IntelliJ IDEA, it's often useful to see your changes in action. You can try the plugin out directly in the IDE via the `runide` (`Run plugin`) gradle run configuration:

```
./gradlew runIde
```

This will open a new IDEA window with your build of the plugin installed and enabled.