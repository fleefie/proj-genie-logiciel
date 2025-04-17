# proj-genie-logiciel

## General information

Directory structure:

```plaintext
proj-genie-logiciel
├── doc (Extra documentation)
├── src (Source)
│   ├── main (Main source code)
│   └── test (Test source code)
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
- Lombok 1.18.38

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
./mvnw compile exec:java 
# Generate docs
./mvnw javadoc:javadoc
```
