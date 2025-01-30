package org.autojs.autojs.core.ui.attribute

import android.view.View
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.widget.JsVideoView
import org.autojs.autojs.runtime.ScriptRuntime

class JsVideoViewAttributes(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser, view: View) : VideoViewAttributes(scriptRuntime, resourceParser, view) {

    override val view = super.view as JsVideoView

    override fun onRegisterAttrs(scriptRuntime: ScriptRuntime) {
        super.onRegisterAttrs(scriptRuntime)

        registerAttrs(arrayOf("controller", "mediaController", "isControllerEnabled", "controllerEnabled", "enableController", "isMediaControllerEnabled", "mediaControllerEnabled", "enableMediaController")) {
            when (it) {
                "null", "false" -> view.clearMediaController()
                "true" -> view.resetMediaController()
            }
        }
    }

}