package org.autojs.autojs.core.ui.attribute

import android.view.View
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.widget.JsListView
import org.autojs.autojs.groundwork.WrapContentLinearLayoutManager
import org.autojs.autojs.runtime.ScriptRuntime

open class JsListViewAttributes(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser, view: View) : RecyclerViewAttributes(scriptRuntime, resourceParser, view) {

    override val view = super.view as JsListView

    override fun onRegisterAttrs(scriptRuntime: ScriptRuntime) {
        super.onRegisterAttrs(scriptRuntime)

        registerAttr("orientation") { view.layoutManager = WrapContentLinearLayoutManager(view.context, LinearLayoutAttributes.ORIENTATIONS[it], false) }
    }

}