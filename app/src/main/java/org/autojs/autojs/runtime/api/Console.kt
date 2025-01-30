package org.autojs.autojs.runtime.api

import org.autojs.autojs.annotation.ScriptInterface

/**
 * Created by Stardust on Apr 2, 2017.
 * Modified by SuperMonster003 as of Oct 19, 2022.
 */
interface Console {

    @ScriptInterface
    fun verbose(data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun verbose(data: Any?) = verbose(data, *emptyArray())

    @ScriptInterface
    fun log(data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun log(data: Any?) = log(data, *emptyArray())

    @ScriptInterface
    fun print(level: Int, data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun print(level: Int, data: Any?) = print(level, data, *emptyArray())

    @ScriptInterface
    fun info(data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun info(data: Any?) = info(data, *emptyArray())

    @ScriptInterface
    fun warn(data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun warn(data: Any?) = warn(data, *emptyArray())

    @ScriptInterface
    fun error(data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun error(data: Any?) = error(data, *emptyArray())

    @ScriptInterface
    fun assertTrue(value: Boolean, data: Any?, vararg formatArgs: Any?)

    @ScriptInterface
    fun assertTrue(value: Boolean, data: Any?) = assertTrue(value, data, *emptyArray())

    @ScriptInterface
    fun clear()

    @ScriptInterface
    fun show()

    @ScriptInterface
    fun hide()

    @ScriptInterface
    fun println(level: Int, charSequence: CharSequence): String?

    @ScriptInterface
    fun setTitle(title: CharSequence?)

    @ScriptInterface
    fun setSize(w: Double, h: Double)

    @ScriptInterface
    fun setPosition(x: Double, y: Double)

    @ScriptInterface
    fun setTouchable(touchable: Boolean)

    @ScriptInterface
    fun rawInput(): String?

}