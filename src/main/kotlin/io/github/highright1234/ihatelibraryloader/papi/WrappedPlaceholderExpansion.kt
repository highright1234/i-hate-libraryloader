package io.github.highright1234.ihatelibraryloader.papi

import io.github.highright1234.ihatelibraryloader.IHateLibraryLoader
import io.github.highright1234.ihatelibraryloader.papi.node.ExpansionNode
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer

internal class WrappedPlaceholderExpansion(private val rootNode: ExpansionNode): PlaceholderExpansion() {
    override fun getIdentifier(): String = rootNode.name
    override fun getAuthor(): String = IHateLibraryLoader.plugin.description.authors.joinToString()
    override fun getVersion(): String = IHateLibraryLoader.plugin.description.version

    private var indexes = 0
    private var nodeOfIndex = rootNode
    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        indexes = 0
        nodeOfIndex = rootNode
        val parameters = params.split(*rootNode.delimiters.toTypedArray())
        println("ㅁㄴㅇㄹㄴㅇㄹ")
        val context = ExpansionRequestContext(player).apply {
            arguments = parameters.associateWith { ownerOf(it).name }
        }
        nodeOfIndex.executes ?: error("The end node does not have an execute code.")
        return nodeOfIndex.executes!!(context)
    }

    private fun ownerOf(value: String): ExpansionNode {
        indexes++
        nodeOfIndex.thenNodes.find { value == it.name }?.let {
            nodeOfIndex = it
            return it
        }
        nodeOfIndex.argumentNode?.let {
            nodeOfIndex = it
            return it
        }
        throw RuntimeException("Not found owner node of value")
    }
}