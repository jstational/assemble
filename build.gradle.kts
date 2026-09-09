import org.gradle.jvm.tasks.Jar
import java.nio.file.*
import java.io.*
import java.util.zip.*
import java.util.*

// Mindustry version to depend on.
// Valid values:
// - latest: depend on the latest release of mindustry
// - be: depend on the very latest commit of mindustry
// - v<number>: depend on a specific commit
val mindustryVersion = "latest"

plugins {
    java
}

val javaVersion = "mindustryJavaVersion"

object dirs {
    val coreDir = "core"
    val assetDir = dirs.coreDir + "/assets"

    object source {
        val sourceDir = dirs.coreDir + "/mod/src"

        val javaSourceDir = dirs.source.sourceDir + "/java"
    }
}

sourceSets.main.get().java.srcDirs(dirs.source.javaSourceDir)
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

val jar = tasks.named<Jar>("jar") { // override jar task -> jar
    archiveFileName.set("jar.jar")
    from(sourceSets.main.get().output)
}

val dex = tasks.register("dex") {
    dependsOn(jar)

    val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
    
    val output = layout.buildDirectory.file("libs/dex.zip")
    outputs.file(output)

    doLast {
        val d8 = if(isWindows) "d8.bat" else "d8"
        val d8Path = if(sdkRoot.isNotEmpty()) sdkRoot + "/build-tools/30.0.3/" + d8 else d8
        val androidJar = if(sdkRoot.isNotEmpty()) sdkRoot + "/platforms/android-30/android.jar" else "android.jar"

        val classpaths = configurations.compileClasspath.get().files + configurations.runtimeClasspath.get().files + File(androidJar)
        
        val commands = mutableListOf(
            d8Path, "--min-api", "14", "--output", output.get().asFile.absolutePath, jar.get().archiveFile.get().asFile.absolutePath
        )

        classpaths.forEach { file ->
            commands.add("--classpath")
            commands.add(file.absolutePath)
        }

        val process = ProcessBuilder(commands).directory(layout.buildDirectory.asFile.get()).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT).start()

        val result = process.waitFor()
    }
}

tasks.register<Jar>("deploy") { // include jar and dex -> jar
    archiveFileName.set(project.name + ".jar")

    from(zipTree(jar.get().archiveFile))
    from(zipTree(layout.buildDirectory.file("libs/dex.zip")))

    from(dirs.coreDir) {
        include("assets/**")
    }

    from(projectDir) {
        include("mod.json")
    }
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