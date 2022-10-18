package io.github.highright1234.ihatelibraryloader.papi

import io.github.highright1234.ihatelibraryloader.IHateLibraryLoader.plugin
import io.github.highright1234.ihatelibraryloader.papi.node.PapiNode

fun papi(block: PapiNode.() -> Unit) {
    if (existPlaceholderAPI) {
        PapiNode.block()
    }
}

private val existPlaceholderAPI get() = plugin.server.pluginManager.getPlugin("PlaceholderAPI") != null