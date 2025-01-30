package org.autojs.autojs.core.ui.inflater.inflaters

import android.content.Context
import android.view.ViewGroup
import org.autojs.autojs.core.ui.widget.JsConsoleView
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.inflater.ViewCreator
import org.autojs.autojs.runtime.ScriptRuntime

/**
 * Created by SuperMonster003 on May 20, 2023.
 */
class JsConsoleViewInflater(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser) : ConsoleViewInflater<JsConsoleView>(scriptRuntime, resourceParser) {

    override fun getCreator(): ViewCreator<in JsConsoleView> = object : ViewCreator<JsConsoleView> {
        override fun create(context: Context, attrs: HashMap<String, String>, parent: ViewGroup?): JsConsoleView {
            return JsConsoleView(context)
        }
    }

}