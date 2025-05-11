# Intellij IDEA plugin for EOLANG (LSP)

This is an Intellij plugin for EOLANG that uses the EOLANG LSP server.

Install it from source through the following steps:

1. Clone the repository with the `--recurse-submodules` flag
2. Build the project (from project root):
    ```
    ./gradlew build
    ```
   (You will need Gradle, Maven, Java 17+, Node 20 and NPM installed.)
3. In your IDE, open the plugin window and install the plugin from disk 

   (build output is in `build/distributions/eo-intellij-lsp-plugin-1.0-SNAPSHOT.zip`)

## Structure

The project is divided into two parts: the client and the server.

The client is written in Java, and is located in src/main.

The server is written in Typescript. It is a submodule located in `eo-lsp-server`, and is published as a standalone repository in https://github.com/GeorgySabaev/eo-lsp-server

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
