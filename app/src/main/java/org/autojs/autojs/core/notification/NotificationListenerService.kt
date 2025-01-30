package org.autojs.autojs.core.notification

import android.service.notification.StatusBarNotification
import org.autojs.autojs.core.accessibility.NotificationListener
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by Stardust on Oct 30, 2017.
 */
class NotificationListenerService : android.service.notification.NotificationListenerService() {

    private val mNotificationListeners = CopyOnWriteArrayList<NotificationListener>()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onNotificationPosted(sbn: StatusBarNotification, rankingMap: RankingMap) {
        onNotificationPosted(sbn)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        for (listener in mNotificationListeners) {
            listener.onNotification(Notification.create(sbn.notification, sbn.packageName))
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {}

    override fun onNotificationRemoved(sbn: StatusBarNotification, rankingMap: RankingMap) {}

    fun addListener(listener: NotificationListener) {
        mNotificationListeners.add(listener)
    }

    fun removeListener(listener: NotificationListener): Boolean {
        return mNotificationListeners.remove(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    companion object {
        @JvmStatic
        var instance: NotificationListenerService? = null
            private set
    }
}
