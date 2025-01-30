package org.autojs.autojs.core.ui.attribute

import android.view.View
import android.widget.Spinner
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.inflater.util.Dimensions
import org.autojs.autojs.core.ui.inflater.util.Gravities
import org.autojs.autojs.core.ui.inflater.util.Strings
import org.autojs.autojs.runtime.ScriptRuntime

open class SpinnerAttributes(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser, view: View) : AbsSpinnerAttributes(scriptRuntime, resourceParser, view) {

    override val view = super.view as Spinner

    override fun onRegisterAttrs(scriptRuntime: ScriptRuntime) {
        super.onRegisterAttrs(scriptRuntime)

        registerAttr("dropDownHorizontalOffset") { view.dropDownHorizontalOffset = Dimensions.parseToIntPixel(it, view) }
        registerAttr("dropDownVerticalOffset") { view.dropDownVerticalOffset = Dimensions.parseToIntPixel(it, view) }
        registerAttr("dropDownWidth") { view.dropDownWidth = Dimensions.parseToIntPixel(it, view) }
        registerAttr("gravity") { view.gravity = Gravities.parse(it) }
        registerAttrs(arrayOf("popupBackgroundDrawable", "popupBgDrawable", "popupBackground", "popupBg")) { view.setPopupBackgroundDrawable(drawables.parse(view, it)) }
        registerAttr("prompt") { view.prompt = Strings.parse(view, it) }
    }

}
