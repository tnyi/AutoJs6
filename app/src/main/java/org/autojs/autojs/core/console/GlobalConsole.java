package org.autojs.autojs.core.console;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;
import static android.util.Log.d;

import androidx.annotation.NonNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.autojs.autojs.tool.UiHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stardust on Oct 22, 2017.
 */
public class GlobalConsole extends ConsoleImpl {
    private static final String TAG = GlobalConsole.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(GlobalConsole.class);
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());

    public GlobalConsole(UiHandler uiHandler) {
        super(uiHandler);
    }

    @Override
    @NonNull
    public String println(int level, @NonNull CharSequence charSequence) {
        String log = String.format(Locale.getDefault(), "%s/%s: %s",
                mDateFormat.format(new Date()), getLevelChar(level), charSequence);
        LOGGER.log(toLog4jLevel(level), log);
        d(TAG, log);
        super.println(level, log);
        return log;
    }

    protected Priority toLog4jLevel(int level) {
        return switch (level) {
            case VERBOSE, DEBUG -> Level.DEBUG;
            case INFO -> Level.INFO;
            case WARN -> Level.WARN;
            case ERROR -> Level.ERROR;
            case ASSERT -> Level.FATAL;
            default -> throw new IllegalArgumentException(GlobalConsole.class.getSimpleName() + ".toLog4jLevel");
        };
    }

    protected String getLevelChar(int level) {
        return switch (level) {
            case VERBOSE -> "V";
            case DEBUG -> "D";
            case INFO -> "I";
            case WARN -> "W";
            case ERROR -> "E";
            case ASSERT -> "A";
            default -> "";
        };
    }

}
