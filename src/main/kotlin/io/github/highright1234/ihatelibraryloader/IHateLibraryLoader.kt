package io.github.highright1234.ihatelibraryloader

import org.bukkit.plugin.java.JavaPlugin

object IHateLibraryLoader {

    private lateinit var _plugin: JavaPlugin
    val plugin: JavaPlugin get() = _plugin

    fun register(plugin: JavaPlugin) {
        _plugin = plugin
    }
}