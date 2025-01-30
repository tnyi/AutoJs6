package org.autojs.autojs.runtime.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.autojs.autojs.engine.RootAutomatorEngine;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stardust on Apr 24, 2017.
 */
public abstract class AbstractShell {

    private static final String TAG = AbstractShell.class.getSimpleName();

    public static class Result {

        private static final Gson sGson = new GsonBuilder().setPrettyPrinting().create();

        @SerializedName("code")
        public int code;

        @SerializedName("result")
        public String result;

        @SerializedName("error")
        public String error;

        public Result() {
            this(-1);
        }

        public Result(int code) {
            this(code, "");
        }

        public Result(int code, String result) {
            this(code, result, "");
        }

        public Result(int code, String result, String error) {
            this.code = code;
            this.result = result;
            this.error = error;

            if (!error.isEmpty()) {
                Matcher matcher = Pattern.compile("^Error type (\\d+)").matcher(error);
                if (matcher.find()) {
                    this.code = Integer.parseInt(matcher.group(1));
                }
                if (this.code == 0) this.code = 1;
            }
        }

        public Result(int code, @NotNull Exception e) {
            this(code, "", e.getMessage() != null ? e.getMessage() : "");
        }

        public Result(int code, ProcessShell shell) {
            this(code, shell.getSuccessOutput().toString(), shell.getErrorOutput().toString());
        }

        @NonNull
        @Override
        public String toString() {
            return "ShellResult{" +
                    "code=" + code +
                    ", error=\"" + error + '"' +
                    ", result=\"" + result + '"' +
                    '}';
        }

        public String toJson() {
            return sGson.toJson(this);
        }

        public void throwIfError() throws ShellException {
            if (code != 0) {
                throw new ShellException(this);
            }
        }

        public static Result fromJson(String json) {
            return sGson.fromJson(json, Result.class);
        }

        public static class ShellException extends Exception {

            public ShellException(Result result) {
                super("code: " + result.code + ", error: " + result.error);
            }

        }

    }

    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_LINE_END = "\n";
    public static final String COMMAND_EXIT = "exit" + COMMAND_LINE_END;

    private int mTouchDevice = -1;
    private ScreenMetrics mScreenMetrics;

    private final boolean mIsExecWithRoot;
    protected Context mContext;

    public AbstractShell() {
        this(false);
    }

    public AbstractShell(boolean isExecWithRoot) {
        this(null, isExecWithRoot);
    }

    public AbstractShell(Context context, boolean isExecWithRoot) {
        mContext = context;
        mIsExecWithRoot = isExecWithRoot;
        if (context != null) {
            mTouchDevice = RootAutomatorEngine.getTouchDeviceId(context);
        }
        init(isExecWithRoot ? COMMAND_SU : COMMAND_SH);
    }

    public boolean isExecWithRoot() {
        return mIsExecWithRoot;
    }

    protected abstract void init(String initialCommand);

    public abstract void exec(String command);

    public abstract void exit();

    public void SetTouchDevice(int touchDevice) {
        if (mTouchDevice <= 0) {
            mTouchDevice = touchDevice;
        }
    }

    public void SendEvent(int type, int code, int value) {
        SendEvent(mTouchDevice, type, code, value);
    }

    public void SendEvent(int device, int type, int code, int value) {
        String eventCommand = TextUtils.join("", new Object[]{"sendevent /dev/input/event", device, " ", type, " ", code, " ", value});
        Log.d(TAG, eventCommand);
        exec(eventCommand);
    }

    public void SetScreenMetrics(int width, int height) {
        if (mScreenMetrics == null) {
            mScreenMetrics = new ScreenMetrics();
        }
        mScreenMetrics.setScreenMetrics(width, height);
    }

    public void SetScreenMetrics(ScreenMetrics screenMetrics) {
        mScreenMetrics = screenMetrics;
    }

    public void Touch(int x, int y) {
        TouchX(x);
        TouchY(y);
    }

    public void TouchX(int x) {
        SendEvent(mTouchDevice, 3, 53, scaleX(x));
    }

    private int scaleX(int x) {
        if (mScreenMetrics == null)
            return x;
        return mScreenMetrics.scaleX(x);
    }

    public void TouchY(int y) {
        SendEvent(mTouchDevice, 3, 54, scaleY(y));
    }

    private int scaleY(int y) {
        if (mScreenMetrics == null)
            return y;
        return mScreenMetrics.scaleY(y);
    }

    public void Tap(int x, int y) {
        exec("input tap " + scaleX(x) + " " + scaleY(y));
    }

    public void Swipe(int x1, int y1, int x2, int y2) {
        exec(org.autojs.autojs.util.StringUtils.join(" ", "input", "swipe", scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2)));
    }

    public void Swipe(int x1, int y1, int x2, int y2, int time) {
        exec(org.autojs.autojs.util.StringUtils.join(" ", "input", "swipe", scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2), time));
    }

    public void KeyCode(int keyCode) {
        exec("input keyevent " + keyCode);
    }

    public void KeyCode(String keyCode) {
        exec("input keyevent " + keyCode);
    }

    public void Home() {
        KeyCode(3);
    }

    public void Back() {
        KeyCode(4);
    }

    public void Power() {
        KeyCode(26);
    }

    public void Up() {
        KeyCode(19);
    }

    public void Down() {
        KeyCode(20);
    }

    public void Left() {
        KeyCode(21);
    }

    public void Right() {
        KeyCode(22);
    }

    public void OK() {
        KeyCode(23);
    }

    public void VolumeUp() {
        KeyCode(24);
    }

    public void VolumeDown() {
        KeyCode(25);
    }

    public void Menu() {
        KeyCode(1);
    }

    public void Camera() {
        KeyCode(27);
    }

    public void Input(String text) {
        exec("input text " + text);
    }

    public void Screencap(String path) {
        exec("screencap -p " + path);
    }

    public void Text(String text) {
        Input(text);
    }

    public abstract void exitAndWaitFor();

    public void sleep(long i) {
        exec("sleep " + i);
    }

    public void usleep(long l) {
        exec("usleep " + l);
    }
}
