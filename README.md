# proj-genie-logiciel

## General information

Directory structure:

```plaintext
proj-genie-logiciel
├── doc (Extra documentation)
├── src (Source)
│   └── main (Main source code)
├── target (Output)
│   ├── proj-genie-logiciel-VERSION.jar
│   └── javadoc (Generated documentation)
├── pom.xml (Maven configuration)
└── README.md (This file)
```

Compilation dependencies:
- Java 21 (Full JDK)

Development dependencies:
- Java 21 (Full JDK)

Runtime dependenvies:
- Java 21 (JRE)
- JavaFX 21.0.7 (if using the jar)


## Compilation instructions
```sh
# clean
./mvnw clean
# compile
./mvnw compile
# test
./mvnw test
# build
./mvnw package
# run
./mvnw compile javafx:run
# Generate docs
./mvnw javadoc:javadoc
```
