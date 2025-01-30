package org.autojs.autojs.core.ui.inflater.inflaters

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageSwitcher
import org.autojs.autojs.core.ui.inflater.ResourceParser
import org.autojs.autojs.core.ui.inflater.ViewCreator
import org.autojs.autojs.runtime.ScriptRuntime

open class ImageSwitcherInflater<V : ImageSwitcher>(scriptRuntime: ScriptRuntime, resourceParser: ResourceParser) : ViewSwitcherInflater<V>(scriptRuntime, resourceParser) {

    override fun getCreator(): ViewCreator<in V> = object : ViewCreator<ImageSwitcher> {
        override fun create(context: Context, attrs: HashMap<String, String>, parent: ViewGroup?): ImageSwitcher {
            return ImageSwitcher(context)
        }
    }

}
