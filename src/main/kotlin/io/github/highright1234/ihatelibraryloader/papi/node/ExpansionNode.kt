package io.github.highright1234.ihatelibraryloader.papi.node

import io.github.highright1234.ihatelibraryloader.papi.ExpansionRequestContext

open class ExpansionNode(
    private val parent: ExpansionNode?,
    internal val name: String
) {

    init {
        parent?.let { delimiters = it.delimiters }
    }

    var delimiters = listOf("-")
    internal val thenNodes = mutableListOf<ExpansionNode>()
    internal var argumentNode: ArgumentNode? = null
    internal var executes: ((ExpansionRequestContext) -> String?)? = null

    fun then(name: String, block: ExpansionNode.() -> Unit) {
        thenNodes.find { it.name == name }?.let { error("No nodes should have same name") }
        thenNodes += ExpansionNode(this, name).apply(block)
    }

    fun argument(name: String, block: ArgumentNode.() -> Unit) {
        argumentNodes.find { it.name == name }?.let { error("No argument nodes should have same name") }
        argumentNode = ArgumentNode(this, name).apply(block)
    }

    fun executes(block: (ExpansionRequestContext) -> String?) {
        executes = block
    }





    private val argumentNodes get() = argumentNodes(this)
    private tailrec fun argumentNodes(node: ExpansionNode, nodes: List<ArgumentNode> = listOf()): List<ArgumentNode> {
        val outNodes = if (node is ArgumentNode) nodes + node else nodes
        if (node.parent == null) return outNodes
        return argumentNodes(node.parent, outNodes)
    }

    private tailrec fun rootNode(node: ExpansionNode): ExpansionNode {
        if (node.parent == null) return node
        return rootNode(node.parent)
    }
}

