import org.gradle.jvm.tasks.Jar
import java.nio.file.*
import java.io.*
import java.util.zip.*
import java.util.*

plugins {
    java
}

val mindustryVersion = getBuildSetting("mindustryVersion")
val javaVersion = getBuildSetting("javaVersion")

fun getFileInBuildDir(fi: String): File {return getProvInBuildDir(fi).get().asFile}
fun getProvInBuildDir(fi: String): Provider<RegularFile> {return layout.buildDirectory.file(fi)}

val buildSettingString: Array<String> = Files.readString(getFileInBuildDir("buildSettings").toPath()).split("\\r\\n").filterNot {it.trim().isEmpty() || it.startsWith("#")}.toTypedArray()
fun getBuildSetting(name: String): String {
    for(curSetting in buildSettingString) {
        val trimmed: String = curSetting.trim()
        if(trimmed.startsWith(name)) {return curSetting.substringAfter(" ", missingDelimiterValue("")).trim()}
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
    
    outputs.file(getProvInBuildDir("libs/dex.zip"))

    doLast {
        val d8Path = if(sdkRoot.isNotEmpty()) sdkRoot + "/build-tools/" + getBuildSetting("androidBuildToolsVer") + "/" + (if(isWindows) "d8.bat" else "d8") else (if(isWindows) "d8.bat" else "d8")
        val androidJar = if(sdkRoot.isNotEmpty()) sdkRoot + "/platforms/" + getBuildSetting("androidSDKVer") + "/android.jar" else "android.jar"

        val hasD8: Boolean = File(d8Path).exists().also {if(!it) print("d8 is not intalled")}
        val hasAndroidSDK: Boolean = File(androidJar).exists().also {if(!it) print("android SDK is not installed")}

        if(hasD8 && hasAndroidSDK) {
            getFileInBuildDir("libs/dex.zip").parentFile.mkdirs()

            val classpaths = configurations.compileClasspath.get().files + configurations.runtimeClasspath.get().files + File(androidJar)
            val commands = mutableListOf(d8Path, "--min-api", "14", "--output", getFileInBuildDir("libs/dex.zip").absolutePath, jar.get().archiveFile.get().asFile.absolutePath)

            classpaths.forEach { file ->
                if(file.exists()) {
                    commands.add("--classpath")
                    commands.add(file.absolutePath)
                }
            }

            val process = ProcessBuilder(commands).directory(layout.buildDirectory.asFile.get()).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT).start()
            val result = process.waitFor()

            if(!getProvInBuildDir("libs/dex.zip").get().getAsFile().exists()) print("libs/dex.zip does not exist, an error occurred while dexing")
        }
    }
}

tasks.register<Jar>("deploy") { // include jar and dex -> jar
    dependsOn(dex)
    archiveFileName.set(project.name + ".jar")

    from(zipTree(getProvInBuildDir("libs/jar.jar")))

    if(getFileInBuildDir("libs/dex.zip").exists()) {from(zipTree(getProvInBuildDir("libs/dex.zip")))}

    from(dirs.coreDir) {include(getBuildSetting("assetDir"))}
    from(getBuildSetting("rootAssetDir"))
    from(projectDir) {include("mod.json")}
}

dependencies {
    compileOnly(if(mindustryVersion == "be") "Anuken:MindustryBuilds:latest" else "Anuken:Mindustry:" + mindustryVersion)
}
repositories {
    mavenCentral()

    //Downloads the dependencies JAR file from Mindustry releases; does not use any real repository. Surprisingly, this is the most reliable option.
    ivy {
        url = uri("https://github.com/")
        patternLayout {artifact("/[organisation]/[module]/releases/download/[revision]/dependencies.jar")}
        metadataSources {artifact()}
    }

    //If the version is set to "latest", downloads the latest Mindustry *release* as a dependency
    ivy {
        url = uri("https://github.com/")
        patternLayout {artifact("/[organisation]/[module]/releases/[revision]/download/dependencies.jar")}
        metadataSources{artifact()}
    }

    //For depending on the absolute newest commit for Mindustry
    ivy {
        url = uri("https://github.com/")
        patternLayout {artifact("/[organisation]/[module]/releases/download/master/[revision].jar")}
        metadataSources {artifact()}
    }
}

java {
    val ver = if(javaVersion == "latest") JavaVersion.entries.last() else if(javaVersion == "mindustryJavaVersion") JavaVersion.VERSION_17 else try {
        JavaVersion.toVersion(javaVersion)
    } catch(e: IllegalArgumentException) {JavaVersion.VERSION_17}

    targetCompatibility = ver
    sourceCompatibility = ver
}
sourceSets.main.get().java.srcDirs(getBuildSetting("sourceDir"))

val isWindows = System.getProperty("os.name").lowercase().contains("windows")
val sdkRoot = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")