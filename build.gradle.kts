import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.jvm.tasks.Jar

buildscript {
    val extra = project.extensions.extraProperties
    extra["kotlinVersion"] = "1.1.0-dev-1159"
    extra["repo"] = "https://repo.gradle.org/gradle/repo"

    repositories {
        maven { setUrl(extra["repo"]) }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${extra["kotlinVersion"]}")
    }
}

apply {
    plugin("kotlin")
    plugin<ApplicationPlugin>()
}

configure<ApplicationPluginConvention> {
    mainClassName = "example.MainKt"
}

jar {
    baseName = "kotlin-jetty-jersey-example"
    version = "0.1.0"
}

repositories {
    maven { setUrl(extra["repo"]) }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib:${extra["kotlinVersion"]}")
    compile("org.glassfish.jersey.containers:jersey-container-jetty-http:2.23.1")
    compile("org.glassfish.jersey.media:jersey-media-json-jackson:2.23.1")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.5.5-2")

    testCompile("org.spockframework:spock-core:1.0-groovy-2.4")
    testCompile("org.jetbrains.spek:spek:1.0.25")
}

inline fun Project.jar(crossinline configuration: Jar.() -> Unit) = tasks.withType(configuration)
