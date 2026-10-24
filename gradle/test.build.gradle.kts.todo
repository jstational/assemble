import org.gradle.jvm.tasks.Jar
import java.nio.file.*
import java.io.*
import java.util.zip.*
import java.util.*

plugins {
    java
}

// Mindustry version to depend on.
// Valid values:
// - latest: depend on the latest release of mindustry
// - be: depend on the very latest commit of mindustry
// - v<number>: depend on a specific commit
val mindustryVersion = "latest"

val javaVersion = "mindustryJavaVersion"

object dirs {
    val coreDir = "core"
    val sourceDir = dirs.coreDir + "/src"
}

sourceSets.main.get().java.srcDirs(dirs.sourceDir)
val isWindows = System.getProperty("os.name").lowercase().contains("windows")

java {
    val ver = if(javaVersion == "latest") JavaVersion.entries.last() else if(javaVersion == "mindustryJavaVersion") JavaVersion.VERSION_17 else try {
        JavaVersion.toVersion(javaVersion)
    } catch(e: IllegalArgumentException) {
        JavaVersion.VERSION_17
    }

    targetCompatibility = ver
    sourceCompatibility = ver
}

dependencies {
    compileOnly(if(mindustryVersion == "be") "Anuken:MindustryBuilds:latest" else "Anuken:Mindustry:" + mindustryVersion)
}

fun buildDir(fi: String): File {
    return layout.buildDirectory.file(fi).get().asFile
}

fun buildDirProv(fi: String): Provider<RegularFile> {
    return layout.buildDirectory.file(fi)
}

val jar = tasks.named<Jar>("jar") { // override jar task -> jar
    archiveFileName.set("jar.jar")
    from(sourceSets.main.get().output)
}

val test = tasks.register("testAssemble") {
    dependsOn(jar)

    val dependenciesJarFile = configurations.compileOnly.get().singleFile
    val dependenciesZip = ZipFile(dependenciesJarFile)

    
}

repositories {
    mavenCentral()

    //Downloads the dependencies JAR file from Mindustry releases; does not use any real repository. Surprisingly, this is the most reliable option.
    ivy {
        url = uri("https://github.com/")

        patternLayout {
            artifact("/[organisation]/[module]/releases/download/[revision]/dependencies.jar")
        }

        metadataSources {
            artifact()
        }
    }

    //If the version is set to "latest", downloads the latest Mindustry *release* as a dependency
    ivy {
        url = uri("https://github.com/")

        patternLayout {
            artifact("/[organisation]/[module]/releases/[revision]/download/dependencies.jar")
        }

        metadataSources {
            artifact()
        }
    }

    //For depending on the absolute newest commit for Mindustry
    ivy {
        url = uri("https://github.com/")

        patternLayout {
            artifact("/[organisation]/[module]/releases/download/master/[revision].jar")
        }

        metadataSources {
            artifact()
        }
    }
}