package org.autojs.autojs.external.open;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import org.autojs.autojs.pio.PFiles;
import org.autojs.autojs.ui.BaseActivity;
import org.autojs.autojs.ui.edit.EditActivity;
import org.autojs.autojs.util.ViewUtils;
import org.autojs.autojs6.R;

import java.io.File;

/**
 * Created by Stardust on Feb 2, 2017.
 */
public class EditIntentActivity extends BaseActivity {

    private static final String EXTERNAL_FILES = "external_files";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            handleIntent();
        } catch (Exception e) {
            e.printStackTrace();
            ViewUtils.showToast(this, R.string.edit_and_run_handle_intent_error, true);
        }
        finish();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String path = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            path = uri.getPath();
        } else {
            int i = uri.getPath().indexOf(EXTERNAL_FILES);
            if (i >= 0) {
                path = uri.getPath().substring(i + EXTERNAL_FILES.length());
                if (!PFiles.exists(path)) {
                    path = new File(Environment.getExternalStorageDirectory(), path).getPath();
                    if (!PFiles.exists(path)) {
                        path = null;
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(path)) {
            EditActivity.editFile(this, path, false);
        } else {
            EditActivity.editFile(this, uri, false);
        }
    }
}
