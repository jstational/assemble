import org.gradle.jvm.tasks.Jar
import java.nio.file.*
import java.net.HttpURLConnection
import java.net.URL
import java.io.*
import java.util.zip.*
import kotlin.random.Random
import java.util.*;

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

    object source {
        val sourceDir = dirs.coreDir + "/mod/src"

        val javaSourceDir = dirs.source.sourceDir + "/java"
    }

    object assets {
        val assetDir = dirs.coreDir + "/assets"
    }
}

sourceSets.main.get().java.srcDirs(dirs.source.javaSourceDir)

java {
    val ver = if(javaVersion == "latest") JavaVersion.entries.last() else if(javaVersion == "mindustryJavaVersion") JavaVersion.VERSION_17 else try {
        JavaVersion.toVersion(javaVersion)
    } catch(e: IllegalArgumentException) {
        JavaVersion.VERSION_17
    }

    targetCompatibility = ver
    sourceCompatibility = ver
}

val isWindows = System.getProperty("os.name").lowercase().contains("windows")
val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
val desktopJar = layout.buildDirectory.file("libs/" + project.name + "Desktop.jar").get().asFile
val androidDexZip = layout.buildDirectory.file("libs/" + project.name + "androidDex.zip").get().asFile

dependencies {
    compileOnly(if(mindustryVersion == "be") "Anuken:MindustryBuilds:latest" else "Anuken:Mindustry:" + mindustryVersion)
}

tasks.register("jarAndroid") {
    dependsOn("jar")
    val noAndroidSDK: String = "No valid Android SDK found. Ensure that ANDROID_HOME is set to your Android SDK directory."
    val noAndroidJar: String = "No android.jar found. Ensure that you have an Android platform installed."

    inputs.file(desktopJar)
    outputs.file(androidDexZip)

    doLast {
        if(sdkRoot.isNullOrEmpty() || !File(sdkRoot).exists()) throw GradleException(noAndroidSDK)

        val platformRoot = File(sdkRoot +"/platforms/").listFiles().sortedDescending() ?.toList() ?.find {
                File(it, "android.jar").exists()
            } ?: throw GradleException(noAndroidJar)

        if(platformRoot == null) throw GradleException(noAndroidJar)

        //collect dependencies needed for desugaring
        val dependencies = (configurations.compileClasspath.get().files + configurations.runtimeClasspath.get().files + File(platformRoot, "android.jar")).joinToString(" ") { "--classpath " + it.path }

        //dex and desugar files - this requires d8 in your PATH
        val commands = (if(isWindows) "d8.bat" else "d8") + " " + dependencies + " --min-api 14 --output " + androidDexZip.absolutePath + " " + desktopJar.absolutePath
        val dexAndDesugar = ProcessBuilder(commands.split(" ")).directory(File("build/libs")).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT).start()
        
        val dr = dexAndDesugar.waitFor()

        if(dr != 0) {
            throw GradleException("dexAndDesugar returned " + dr)
        }
    }
}

tasks.jar {
    archiveFileName = project.name + "Desktop.jar"

    from({
        configurations.runtimeClasspath.get().map {
            if(it.isDirectory()) it else zipTree(it)
        }
    })

    from(dirs.assets.assetDir + "/") {
        include("**")
    }

    from(projectDir) {
        include("mod.json")
    }
}

tasks.register<Jar>("deploy") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn("jarAndroid")

    archiveFileName = project.name + ".jar"

    from(zipTree(desktopJar))

    from(zipTree(androidDexZip)) {
        include("classes.dex")
    }

    doLast {
        delete(androidDexZip)
        delete(desktopJar)
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