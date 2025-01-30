package org.autojs.autojs.timing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.autojs.autojs.external.ScriptIntents;

/**
 * Created by Stardust on Nov 27, 2017.
 */
public class TaskReceiver extends BroadcastReceiver {

    public static final String ACTION_TASK = "org.autojs.autojs.action.task";
    public static final String EXTRA_TASK_ID = "task_id";

    @Override
    public void onReceive(Context context, Intent intent) {
        ScriptIntents.handleIntent(context, intent);
        long id = intent.getLongExtra(EXTRA_TASK_ID, -1);
        if (id >= 0) {
            TimedTaskManager.notifyTaskFinished(id);
        }
    }
}
