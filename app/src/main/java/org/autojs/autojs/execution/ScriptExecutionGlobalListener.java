package org.autojs.autojs.execution;

import static org.autojs.autojs.util.StringUtils.str;

import android.annotation.SuppressLint;
import android.util.Log;

import org.autojs.autojs.AutoJs;
import org.autojs.autojs.core.pref.Language;
import org.autojs.autojs.core.websocket.WebSocketManager;
import org.autojs.autojs.runtime.api.Console;
import org.autojs.autojs6.R;

/**
 * Created by Stardust on May 3, 2017.
 */
public class ScriptExecutionGlobalListener implements ScriptExecutionListener {
    private static final String TAG = ScriptExecutionGlobalListener.class.getSimpleName();
    public static final String ENGINE_TAG_START_TIME = "org.autojs.autojs.autojs.Goodbye, World";
    public static final String ENGINE_TAG_WEBSOCKET_URL = "websocket_url";

    @Override
    public void onStart(ScriptExecution execution) {
        execution.getEngine().setTag(ENGINE_TAG_START_TIME, System.currentTimeMillis());
        
        // 获取 WebSocket URL (如果有)
        String websocketUrl = (String) execution.getConfig().getTag(ENGINE_TAG_WEBSOCKET_URL);
        Log.i(TAG, "websocketUrl=" + websocketUrl);
        if (websocketUrl != null) {
            String scriptName = execution.getSource().getName();
            Log.i(TAG, "scriptName=" + scriptName);
            WebSocketManager.getInstance().connect(websocketUrl, scriptName);
        }
    }

    @Override
    public void onSuccess(ScriptExecution execution, Object result) {
        onFinish(execution);
    }

    @SuppressLint("DefaultLocale")
    private void onFinish(ScriptExecution execution) {
        Long millis = (Long) execution.getEngine().getTag(ENGINE_TAG_START_TIME);
        if (millis != null) {
            printSeconds(execution, millis);
        }
        
        // 断开 WebSocket 连接
        String websocketUrl = (String) execution.getConfig().getTag(ENGINE_TAG_WEBSOCKET_URL);
        if (websocketUrl != null) {
            WebSocketManager.getInstance().disconnect();
        }
    }

    private void printSeconds(ScriptExecution execution, Long millis) {
        double seconds = (System.currentTimeMillis() - millis) / 1000.0;
        String secondsString = String.format(Language.getPrefLanguage().getLocale(), "%.3f", seconds).stripTrailing();
        printSeconds(execution, secondsString);
    }

    private void printSeconds(ScriptExecution execution, String seconds) {
        Console console = AutoJs.getInstance().getScriptEngineService().getGlobalConsole();
        String path = execution.getSource().getElegantPath();
        console.verbose(str(R.string.text_execution_finished, path, seconds));
    }

    @Override
    public void onException(ScriptExecution execution, Throwable e) {
        onFinish(execution);
    }

}
