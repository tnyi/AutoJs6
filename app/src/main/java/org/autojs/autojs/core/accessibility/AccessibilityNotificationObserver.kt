package org.autojs.autojs.core.accessibility

import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent

import org.autojs.autojs.core.notification.Notification

import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Stardust on Nov 3, 2017.
 */
class AccessibilityNotificationObserver(private val mContext: Context) : NotificationListener, AccessibilityDelegate {

    private val mNotificationListeners = CopyOnWriteArrayList<NotificationListener>()
    private val mToastListeners = CopyOnWriteArrayList<ToastListener>()

    override val eventTypes: Set<Int>
        get() = EVENT_TYPES

    inner class Toast(val packageName: String, texts: List<CharSequence>) {

        private val texts: MutableList<String> = ArrayList(texts.size)

        val text: String?
            get() = if (texts.isEmpty()) null else texts[0]

        init {
            for (t in texts) {
                this.texts.add(t.toString())
            }
        }

        override fun toString(): String {
            return "Toast{texts=$texts, packageName='$packageName'}"
        }
    }

    interface ToastListener {
        fun onToast(toast: Toast)
    }

    fun addNotificationListener(listener: NotificationListener) {
        mNotificationListeners.add(listener)
    }

    fun removeNotificationListener(listener: NotificationListener): Boolean {
        return mNotificationListeners.remove(listener)
    }

    fun addToastListener(listener: ToastListener) {
        mToastListeners.add(listener)
    }

    fun removeToastListener(listener: ToastListener): Boolean {
        return mToastListeners.remove(listener)
    }

    override fun onAccessibilityEvent(service: android.accessibilityservice.AccessibilityService, event: AccessibilityEvent): Boolean {
        if (event.parcelableData is Notification) {
            val notification = event.parcelableData as android.app.Notification
            Log.d(TAG, "onNotification: $notification; $event")
            onNotification(Notification.create(notification, event.packageName.toString()))
        } else {
            val list = event.text
            Log.d(TAG, "onNotification: $list; $event")
            if (event.packageName == mContext.packageName) {
                return false
            }
            onToast(Toast(event.packageName.toString(), list))
        }
        return false
    }

    private fun onToast(toast: Toast) {
        for (listener in mToastListeners) {
            try {
                listener.onToast(toast)
            } catch (e: Exception) {
                Log.e(TAG, "Error onNotification: $toast Listener: $listener", e)
            }
        }
    }

    override fun onNotification(notification: Notification) {
        for (listener in mNotificationListeners) {
            try {
                listener.onNotification(notification)
            } catch (e: Exception) {
                Log.e(TAG, "Error onNotification: $notification Listener: $listener", e)
            }

        }
    }

    companion object {

        private val EVENT_TYPES = setOf(AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
        private val EMPTY = arrayOfNulls<String>(0)
        private const val TAG = "NotificationObserver"

    }

}
