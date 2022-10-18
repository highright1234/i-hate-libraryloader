plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.dokka") version Versions.KOTLIN
    `maven-publish`
    signing
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}

group = "io.github.highright1234"
version = "0.0.1"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

dependencies {
    compileOnly("me.clip:placeholderapi:${Versions.PLACEHOLDER_API}")
    compileOnly("io.github.monun:tap-api:${Versions.TAP}")
    compileOnly("io.github.monun:invfx-api:${Versions.INVFX}")
    compileOnly("io.github.monun:kommand-api:${Versions.KOMMAND}")
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(kotlin("reflect"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.register<Jar>("libraryUpdate") {
    archiveBaseName.set(project.name)
    from(sourceSets["main"].output)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    val serverDir = File(rootDir, ".server")
    val plugins = File(serverDir, "plugins")
    val libraries = File(serverDir, "libraries")

    var directoryFolder = libraries

    "io.github.highright1234.i-hate-libraryloader".split(".").plus("${project.version}").forEach {
        directoryFolder = File(directoryFolder, it)
    }

    doLast {
        // 내 마크 서버 환경 불러오기
        val serverFolder = File("E:\\.server\\")
        if (!serverDir.exists() && serverFolder.exists()) {
            serverDir.mkdir()
            copy {
                from(serverFolder)
                include("**/**")
                into(serverDir)
            }
        }
        // 플러그인 적용
        if (!directoryFolder.exists()) libraries.mkdirs()

        copy {
            from(archiveFile)
            if (File(directoryFolder, archiveFileName.get()).exists()) {
                File(directoryFolder, archiveFileName.get()).delete()
            }
            into(directoryFolder)
        }
        // auto-reloader
        val updateFolder = File(plugins, "update")
        if (!updateFolder.exists()) return@doLast
        File(updateFolder, "RELOAD").delete()
    }
}

tasks.named("build") { finalizedBy("libraryUpdate") }

allprojects {
    if (hasProperty("buildScan")) {
        extensions.findByName("buildScan")?.withGroovyBuilder {
            setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
            setProperty("termsOfServiceAgree", "yes")
        }
    }
}