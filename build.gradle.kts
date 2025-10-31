plugins {
    id("java")
    id("org.jsonschema2pojo") version "1.2.2"
    id("maven-publish")
}

group = "com.navapbc.piqi"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("joda-time:joda-time:2.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${group}"
            artifactId = "piqi-model"
            version = "${version}"

            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

jsonSchema2Pojo {
    targetDirectory = file("${project.rootDir}/src/main/java") // Output directory for generated classes
    targetPackage = "com.navapbc.piqi.model" // Package name for generated classes
    generateBuilders = false // Generate builder methods for classes
    includeAdditionalProperties = false
    classNamePrefix = "Piqi"
    removeOldOutput = true
}

tasks.register("myPostGenerationTask") {
    dependsOn("generateJsonSchema2Pojo") // Replace with the actual jsonschema2pojo generation task name
    doLast {
        println("Executing my custom task after jsonschema2pojo generation!")
        copy {
            from("${project.rootDir}/PiqiSimpleAttribute.java")
            into("${project.rootDir}/src/main/java/com/navapbc/piqi/model")
        }
    }
}

tasks.named("compileJava") { // Or "compileKotlin" for Kotlin projects
    dependsOn("myPostGenerationTask")
}
