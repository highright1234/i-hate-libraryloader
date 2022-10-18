package io.github.highright1234.ihatelibraryloaderdebug

import io.github.highright1234.ihatelibraryloader.IHateLibraryLoader
import org.bukkit.plugin.java.JavaPlugin

class IHateLibraryLoaderDebug: JavaPlugin() {
    override fun onEnable() {
        IHateLibraryLoader.register(this)
        TestPAPI.register()
        logger.info("${server.pluginManager.getPlugin("PlaceholderAPI") != null}")
    }
}