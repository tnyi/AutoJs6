package org.autojs.autojs.external.open;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import org.autojs.autojs.AutoJs;
import org.autojs.autojs.execution.ExecutionConfig;
import org.autojs.autojs.execution.ScriptExecutionGlobalListener;
import org.autojs.autojs.external.ScriptIntents;
import org.autojs.autojs.model.script.Scripts;
import org.autojs.autojs.pio.PFiles;
import org.autojs.autojs.script.StringScriptSource;
import org.autojs.autojs.util.ViewUtils;
import org.autojs.autojs6.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

/**
 * Created by Stardust on Feb 22, 2017.
 */
public class RunIntentActivity extends Activity {

    private static final String EXTRA_LOG_CALLBACK_URL = "log_callback_url";
    private static final String TAG = "RunIntentActivity";
    private WebSocket webSocket;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            handleIntent(getIntent());
        } catch (Exception e) {
            e.printStackTrace();
            ViewUtils.showToast(this, R.string.edit_and_run_handle_intent_error, true);
        }
        finish();
    }

    private void handleIntent(Intent intent) throws FileNotFoundException {
        String logCallbackUrl = intent.getStringExtra(EXTRA_LOG_CALLBACK_URL);
        android.util.Log.d(TAG, "handleIntent: logCallbackUrl=" + logCallbackUrl);
        
        Uri uri = intent.getData();
        if (uri != null && "content".equals(uri.getScheme())) {
            android.util.Log.d(TAG, "handleIntent: handling content uri: " + uri);
            InputStream stream = getContentResolver().openInputStream(uri);
            String script = PFiles.read(stream);
            StringScriptSource source = new StringScriptSource(script);
            
            if (!TextUtils.isEmpty(logCallbackUrl)) {
                ExecutionConfig config = new ExecutionConfig();
                config.putTag(ScriptExecutionGlobalListener.ENGINE_TAG_WEBSOCKET_URL, logCallbackUrl);
                AutoJs.getInstance().getScriptEngineService().execute(source, config);
            } else {
                Scripts.run(this, source);
            }
        } else {
            if (!TextUtils.isEmpty(logCallbackUrl)) {
                intent.putExtra(ScriptIntents.EXTRA_KEY_CONFIG_TAG_WEBSOCKET_URL, logCallbackUrl);
            }
            ScriptIntents.handleIntent(this, intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroy RunIntentActivity");
    }
}
