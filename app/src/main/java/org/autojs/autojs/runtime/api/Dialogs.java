package org.autojs.autojs.runtime.api;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.autojs.autojs.annotation.ScriptInterface;
import org.autojs.autojs.annotation.ScriptVariable;
import org.autojs.autojs.core.ui.dialog.BlockedMaterialDialog;
import org.autojs.autojs.core.ui.dialog.JsDialogBuilder;
import org.autojs.autojs.runtime.ScriptRuntime;
import org.autojs.autojs.util.ArrayUtils;
import org.autojs.autojs6.R;
import org.mozilla.javascript.BaseFunction;

/**
 * Created by Stardust on May 8, 2017.
 */
public class Dialogs {

    private ContextThemeWrapper mThemeWrapper;
    private final ScriptRuntime mRuntime;

    @ScriptVariable
    public final NonUiDialogs nonUiDialogs = new NonUiDialogs();

    public Dialogs(ScriptRuntime runtime) {
        mRuntime = runtime;
    }

    @ScriptInterface
    public Object rawInput(String title, String prefill, BaseFunction callback) {
        return ((BlockedMaterialDialog.Builder) dialogBuilder(callback)
                .input(null, prefill, true)
                .title(title))
                .showAndGet();
    }

    @ScriptInterface
    public Object alert(String title, String content, BaseFunction callback) {
        MaterialDialog.Builder builder = dialogBuilder(callback)
                .alert()
                .title(title)
                .positiveText(R.string.text_ok);
        if (!TextUtils.isEmpty(content)) {
            builder.content(content);
        }
        return ((BlockedMaterialDialog.Builder) builder).showAndGet();
    }

    @ScriptInterface
    public Object confirm(String title, String content, BaseFunction callback) {
        MaterialDialog.Builder builder = dialogBuilder(callback)
                .confirm()
                .title(title)
                .positiveText(R.string.text_ok)
                .negativeText(R.string.text_cancel);
        if (!TextUtils.isEmpty(content)) {
            builder.content(content);
        }
        return ((BlockedMaterialDialog.Builder) builder).showAndGet();
    }

    private Context getContext() {
        if (mThemeWrapper != null)
            return mThemeWrapper;
        mThemeWrapper = new ContextThemeWrapper(mRuntime.getUiHandler().getApplicationContext().getApplicationContext(), androidx.appcompat.R.style.Theme_AppCompat_Light);
        return mThemeWrapper;
    }

    @ScriptInterface
    public Object select(String title, String[] items, BaseFunction callback) {
        return ((BlockedMaterialDialog.Builder) dialogBuilder(callback)
                .itemsCallback()
                .title(title)
                .items(items))
                .showAndGet();
    }

    @ScriptInterface
    public Object singleChoice(String title, int selectedIndex, String[] items, BaseFunction callback) {
        return ((BlockedMaterialDialog.Builder) dialogBuilder(callback)
                .itemsCallbackSingleChoice(selectedIndex)
                .title(title)
                .positiveText(R.string.text_ok)
                .items(items))
                .showAndGet();
    }

    @ScriptInterface
    public Object multiChoice(String title, int[] indices, String[] items, BaseFunction callback) {
        return ((BlockedMaterialDialog.Builder) dialogBuilder(callback)
                .itemsCallbackMultiChoice(ArrayUtils.box(indices))
                .title(title)
                .positiveText(R.string.text_ok)
                .items(items))
                .showAndGet();
    }

    @ScriptInterface
    public Object selectFile(String title, String prefill, BaseFunction callback) {
        return ((BlockedMaterialDialog.Builder) dialogBuilder(callback)
                .input(null, prefill, true)
                .title(title))
                .showAndGet();
    }

    private BlockedMaterialDialog.Builder dialogBuilder(BaseFunction callback) {
        Context context = mRuntime.app.getCurrentActivity();
        if (context == null || ((Activity) context).isFinishing()) {
            context = getContext();
        }
        return (BlockedMaterialDialog.Builder) new BlockedMaterialDialog.Builder(context, mRuntime, callback)
                .theme(Theme.LIGHT);
    }

    @ScriptInterface
    public JsDialogBuilder newBuilder() {
        Context context = mRuntime.app.getCurrentActivity();
        if (context == null || ((Activity) context).isFinishing()) {
            context = getContext();
        }
        return new JsDialogBuilder(context, mRuntime)
                .theme(Theme.LIGHT);
    }

    public class NonUiDialogs {

        public String rawInput(String title, String prefill, BaseFunction callback) {
            return (String) Dialogs.this.rawInput(title, prefill, callback);
        }

        @ScriptInterface
        public boolean confirm(String title, String content, BaseFunction callback) {
            return (boolean) Dialogs.this.confirm(title, content, callback);
        }

        @ScriptInterface
        public int select(String title, String[] items, BaseFunction callback) {
            return (Integer) Dialogs.this.select(title, items, callback);
        }

        @ScriptInterface
        public int singleChoice(String title, int selectedIndex, String[] items, BaseFunction callback) {
            return (int) Dialogs.this.singleChoice(title, selectedIndex, items, callback);
        }

        @ScriptInterface
        public int[] multiChoice(String title, int[] indices, String[] items, BaseFunction callback) {
            return (int[]) Dialogs.this.multiChoice(title, indices, items, callback);
        }

        @ScriptInterface
        public Object alert(String title, String content, BaseFunction callback) {
            return Dialogs.this.alert(title, content, callback);
        }

    }
}
