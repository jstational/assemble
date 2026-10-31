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
    return getProvInBuildDir(fi).get().asFile
}

fun buildDirProv(fi: String): Provider<RegularFile> {
    return layout.buildDirectory.file(fi)
}

val buildSettingString: Array<String> = Files.readString(buildDir("buildSettings").toPath()).split("\\r\\n")

fun getBuildSetting(name: String): String {
    for(curSetting in buildSettingString) {
        if(curSetting.trim().startsWith(name)) {
            return curSetting.substring(0, name.length()).trim()
        }
    }
    return "";
}

val tools = tasks.register("tools") {}

val jar = tasks.named<Jar>("jar") { // override jar task -> jar
    archiveFileName.set("jar.jar")
    from(sourceSets.main.get().output)
}

val dex = tasks.register("dex") {
    dependsOn(jar)

    val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT") ?: throw GradleException("SDK env var does not exist")
    
    outputs.file(getProvInBuildDir("libs/dex.zip"))

    doLast {
        val d8 = if(isWindows) "d8.bat" else "d8"
        val d8Path = if(sdkRoot.isNotEmpty()) sdkRoot + "/build-tools/33.0.3/" + d8 else d8
        val androidJar = if(sdkRoot.isNotEmpty()) sdkRoot + "/platforms/android-33/android.jar" else "android.jar"

        if(!File(androidJar).exists()) print("android.jar doesnt exist")
        if(!File(d8Path).exists()) print("d8 doesnt exist")

        if(File(androidJar).exists() && File(d8Path).exists()) {
            getFileInBuildDir("libs/dex.zip").parentFile.mkdirs()

            val classpaths = configurations.compileClasspath.get().files + configurations.runtimeClasspath.get().files + File(androidJar)

            val commands = mutableListOf(
                d8Path, "--min-api", "14", "--output", buildDir("libs/dex.zip").absolutePath, jar.get().archiveFile.get().asFile.absolutePath
            )

            classpaths.forEach { file ->
                if(file.exists()) {
                    commands.add("--classpath")
                    commands.add(file.absolutePath)
                }
            }

            val process = ProcessBuilder(commands).directory(layout.buildDirectory.asFile.get()).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT).start()

            val result = process.waitFor()

            if(!getProvInBuildDir("libs/dex.zip").get().getAsFile().exists()) print("libs/dex.zip does not exist!")
            print("d8 returned " + result)
        }
    }
}

tasks.register<Jar>("deploy") { // include jar and dex -> jar
    dependsOn(dex)
    archiveFileName.set(project.name + ".jar")

    from(zipTree(buildDirProv("libs/jar.jar")))

    if(buildDir("libs/dex.zip").exists()) {
        from(zipTree(buildDirProv("libs/dex.zip")))
    }

    from(dirs.coreDir) {
        include("assets/**")
    }

    from(dirs.coreDir + "/assets-root")

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