package io.github.highright1234.ihatelibraryloader.papi.node

import io.github.highright1234.ihatelibraryloader.papi.WrappedPlaceholderExpansion
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.OfflinePlayer

object PapiNode {
    fun expansion(identifier: String, block: ExpansionNode.() -> Unit) {
        val node = ExpansionNode(null, identifier).apply(block)
        WrappedPlaceholderExpansion(node).register() // 여기서 PlaceholderExpansion 없다고 오류남
    }
}

fun String.placeholderPlaced(player: OfflinePlayer? = null) = PlaceholderAPI.setPlaceholders(player, this)
