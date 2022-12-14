import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly

fun DependencyHandlerScope.monunLibrary(name: String, version: String) {
    compileOnly("io.github.monun:$name-api:$version")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
}

dependencies {
    monunLibrary("tap", Versions.TAP)
    monunLibrary("invfx", Versions.INVFX)
    monunLibrary("kommand", Versions.KOMMAND)
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(kotlin("reflect"))
    compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:${Versions.MC_COROUTINE}")
    compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:${Versions.MC_COROUTINE}")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}")
    compileOnly(rootProject)
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

val directoryName = rootProject.name.replace("-", "") +"debug"
val pluginName = rootProject.name.split("-").joinToString(separator = "") { it.capitalizeAsciiOnly() }
val thisPluginName = pluginName + "Debug"

tasks.register<Jar>("pluginsUpdate") {
    archiveBaseName.set(thisPluginName)
    from(sourceSets["main"].output)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    val serverDir = File(rootDir, ".server")
    val plugins = File(serverDir, "plugins")
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
        if (!plugins.exists()) return@doLast
        copy {
            from(archiveFile)
            if (File(plugins, archiveFileName.get()).exists()) {
                File(plugins, archiveFileName.get()).delete()
            }
            into(plugins)
        }
        // auto-reloader
        val updateFolder = File(plugins, "update")
        if (!updateFolder.exists()) return@doLast
        File(updateFolder, "RELOAD").delete()
    }
}

tasks.named("build") { finalizedBy("pluginsUpdate") }