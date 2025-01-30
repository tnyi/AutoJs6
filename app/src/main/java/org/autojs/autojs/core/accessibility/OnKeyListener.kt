package org.autojs.autojs.core.accessibility

import android.util.Log
import android.view.KeyEvent
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Stardust on Jul 18, 2017.
 */
interface OnKeyListener {

    fun onKeyEvent(keyCode: Int, event: KeyEvent)

    class Observer : OnKeyListener {

        private val mOnKeyListeners = CopyOnWriteArrayList<OnKeyListener>()

        override fun onKeyEvent(keyCode: Int, event: KeyEvent) {
            for (listener in mOnKeyListeners) {
                try {
                    listener.onKeyEvent(keyCode, event)
                } catch (e: Exception) {
                    Log.e(TAG, "Error OnKeyEvent: $event Listener: $listener", e)
                }

            }
        }

        fun addListener(listener: OnKeyListener) {
            mOnKeyListeners.add(listener)
        }

        fun removeListener(listener: OnKeyListener): Boolean {
            return mOnKeyListeners.remove(listener)
        }

        companion object {

            private const val TAG = "OnKeyListenerObserver"

        }
    }
}
