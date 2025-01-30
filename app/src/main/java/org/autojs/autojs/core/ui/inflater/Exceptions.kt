package org.autojs.autojs.core.ui.inflater

import android.view.View

/**
 * Created by Stardust on Nov 4, 2017.
 * Transformed by SuperMonster003 on May 21, 2023.
 */
object Exceptions {

    val NO_EXCEPTION = RuntimeException()

    fun unsupported(v: View, name: String?, value: String?) {
        throw UnsupportedOperationException(
            "${v.javaClass.simpleName}: { name: $name, value: $value } is not supported"
        )
    }
}