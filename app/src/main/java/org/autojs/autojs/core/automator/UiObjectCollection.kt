package org.autojs.autojs.core.automator

import org.autojs.autojs.core.accessibility.UiSelector
import org.autojs.autojs.extension.ArrayExtensions.toHashCode
import org.autojs.autojs.tool.Consumer
import org.autojs.autojs.util.RhinoUtils
import org.mozilla.javascript.Scriptable

/**
 * Created by Stardust on Mar 9, 2017.
 * Modified by SuperMonster003 as of Jul 1, 2022.
 */
class UiObjectCollection private constructor(val nodes: List<UiObject?>) : UiObjectActions {

    operator fun get(i: Int): UiObject? = nodes[i]

    operator fun contains(o: UiObject?): Boolean = nodes.contains(o)

    operator fun iterator(): Iterator<UiObject?> = nodes.iterator()

    fun isEmpty() = nodes.isEmpty()

    fun isNotEmpty() = nodes.isNotEmpty()

    @Deprecated("Use isEmpty instead.", ReplaceWith("this.isEmpty()"))
    fun empty() = isEmpty()

    @Deprecated("Use isNotEmpty instead.", ReplaceWith("this.isNotEmpty()"))
    fun nonEmpty() = isNotEmpty()

    fun toArray(): Array<UiObject?> = nodes.toTypedArray()

    fun toList(): List<UiObject?> = nodes

    fun size(): Int = nodes.size

    fun each(consumer: Consumer<UiObject>) = also { nodes.forEach(consumer::accept) }

    fun find(selector: UiSelector): UiObjectCollection {
        return ArrayList<UiObject?>()
            .also { list ->
                nodes.filterNotNull().forEach { node ->
                    list.addAll(selector.findOf(node).nodes)
                }
            }.let(::of)
    }

    fun findOne(selector: UiSelector): UiObject? {
        return nodes.firstNotNullOfOrNull { node ->
            node?.let { selector.findOneOf(it) }
        }
    }

    fun performAction(action: Int) = performAction(action, *emptyArray())

    override fun performAction(action: Int, vararg arguments: ActionArgument): Boolean {
        var success = true
        nodes.filterNotNull().forEach { node ->
            when (arguments.isEmpty()) {
                true -> node.performAction(action)
                else -> node.performAction(action, *arguments)
            }.also { success = success and it }
        }
        return success
    }

    override fun toString(): String {
        return "${UiObjectCollection::class.java.name}@${super.hashCode()}"
    }

    override fun hashCode(): Int = when {
        nodes.isEmpty() -> super.hashCode()
        else -> nodes.map { it.hashCode() }.toHashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UiObjectCollection) return false
        return nodes == other.nodes
    }

    fun isSimilar(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        when (other) {
            is Scriptable -> {
                val thisHashCode = hashCode()
                val otherHashCode = RhinoUtils.hashCodeOfScriptable(other) ?: return false
                return thisHashCode == otherHashCode
            }
            else -> {
                return when {
                    other.javaClass != UiObjectCollection::class.java -> false
                    else -> hashCode() == other.hashCode()
                }
            }
        }
    }

    companion object {

        val EMPTY = of(emptyList())

        @JvmStatic
        fun of(list: List<UiObject?>) = UiObjectCollection(list)

        @JvmStatic
        fun of(list: Array<UiObject?>) = of(list.toList())

        internal fun transform(collection: UiObjectCollection, mapper: (UiObject?) -> UiObject?) = UiObjectCollection(mapNotNull(collection, mapper))

        internal fun <T> mapNotNull(collection: UiObjectCollection, mapper: (UiObject?) -> T): List<T> = collection.nodes.mapNotNull(mapper)

    }

}
