plugins {
    id("java")
    id("org.jsonschema2pojo") version "1.2.2"
    id("maven-publish")
}

group = "com.navapbc.piqi"
version = "0.3.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.22.4")
    implementation("joda-time:joda-time:2.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

jsonSchema2Pojo {
    // Subfolders under "source" become sub-packages of targetPackage:
    //   schema/eob/patient.json      -> com.navapbc.piqi.model.eob.Patient
    //   schema/clinical/patient.json -> com.navapbc.piqi.model.clinical.Patient
    setSource(files("$projectDir/src/main/resources/schema"))
    targetPackage = "com.navapbc.piqi.model" // Package name for generated classes
    setSourceType("jsonschema")
    generateBuilders = false // Generate builder methods for classes
    includeAdditionalProperties = false
    classNamePrefix = "Piqi"
    removeOldOutput = false
    includeJsr303Annotations = true
    useJakartaValidation = true
}

// Safely lookup sourceSets using extensions container to avoid dynamic string lookup failures in Gradle 9
val sourceSets = extensions.getByType<SourceSetContainer>()

val sourcesJar by tasks.registering(Jar::class) {
    dependsOn("generateJsonSchema2Pojo")
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = group.toString()
            artifactId = "piqi-model"
            version = version.toString()
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
