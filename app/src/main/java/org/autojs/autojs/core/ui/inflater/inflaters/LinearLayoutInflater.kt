package org.autojs.autojs.core.ui.inflater.inflaters

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.inflater.ViewCreator
import org.autojs.autojs.runtime.ScriptRuntime

/**
 * Created by Stardust on Nov 4, 2017.
 * Transformed by SuperMonster003 on May 20, 2023.
 */
open class LinearLayoutInflater<V : LinearLayout>(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser) : ViewGroupInflater<V>(scriptRuntime, resourceParser) {

    override fun getCreator(): ViewCreator<in V> = object : ViewCreator<LinearLayout> {
        override fun create(context: Context, attrs: HashMap<String, String>, parent: ViewGroup?): LinearLayout {
            return LinearLayout(context)
        }
    }

}