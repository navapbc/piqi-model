# piqi-model
The [Patient Information Quality Improvement (PIQI)](https://build.fhir.org/ig/HL7/piqi/index.html) Framework is an evolving standard that wants to serve as a vehicle for promoting trust in the quality and clinical relevance of the healthcare data that transits our systems.

This project exists in part to promote engagement and accelerate adoption and education by providing a quick and easy method for generating the domain model classes that represent the [PIQI data model](https://build.fhir.org/ig/HL7/piqi/piqi_framework.html#piqi-data-models).

While this project only builds a jar file library intended to be used with Java or Kotlin, the JSON schema could be utilized to build objects for any language that can generate code from a schema (C#, Python, etc.).

## Building It

This project uses [jsonschema2pojo](https://github.com/joelittlejohn/jsonschema2pojo) to generate Java classses from the JSON schema located in `src/main/resources/Patient.json`.  The following command will create a jar file containing the generated class files and place it in the local Maven repository (located in `~/.m2`) where it can be used as a dependency for other projects.

```
./gradlew clean publishToMavenLocal
```

## Using It
The jar file may be included in a Gradle project as a dependency in this way in the `build.gradle.kts` file.:

```
...
dependencies {
    implementation("com.navapbc.piqi:piqi-model:0.1.0")
...

```

For Maven in `pom.xml`:

```
...
<dependency>
    <groupId>com.navapbc.piqi</groupId>
    <artifactId>piqi-model</artifactId>
    <version>0.1.0</version>
</dependency>
...
```


## Notes
* *jsonschema2pojo* has many different flags that may be set.  `build.gradle.kts` configures it to use mostly defaults.  It may be necessary to modify the configuration to suit your needs.
* `SimpleAttribute` is the one class that was not able to be generated in a way that serialized properly according to the PIQI data model.  An additional Gradle task was introduced in `build.gradle.kts` to substitute a hand-coded version that was better suited.



