package org.autojs.autojs.util;

import static org.autojs.autojs.util.StringUtils.getStringByLocal;
import static org.autojs.autojs.util.StringUtils.key;
import static org.autojs.autojs.util.StringUtils.str;

import androidx.annotation.NonNull;

import com.stericson.RootShell.RootShell;

import org.autojs.autojs.core.pref.Pref;
import org.autojs.autojs6.R;

import java.text.MessageFormat;

/**
 * Created by Stardust on Jan 26, 2018.
 * Modified by SuperMonster003 as of March 10, 2022.
 */
public class RootUtils {

    private static String runtimeOverriddenRootMode = RootMode.AUTO_DETECT.key;

    public enum RootMode {
        FORCE_ROOT(key(R.string.key_root_mode_force_root), getStringByLocal(R.string.entry_root_mode_force_root, "en")),
        FORCE_NON_ROOT(key(R.string.key_root_mode_force_non_root), getStringByLocal(R.string.entry_root_mode_force_non_root, "en")),
        AUTO_DETECT(key(R.string.key_root_mode_auto_detect), getStringByLocal(R.string.entry_root_mode_auto_detect, "en"));

        public final String key;

        public final String description;

        RootMode(String key, String description) {
            this.key = key;
            this.description = description;
        }

        public static RootMode getRootMode(@NonNull String key) {
            if (key.equals(FORCE_ROOT.key)) return FORCE_ROOT;
            if (key.equals(FORCE_NON_ROOT.key)) return FORCE_NON_ROOT;
            if (key.equals(AUTO_DETECT.key)) return AUTO_DETECT;
            throw new IllegalArgumentException(str(R.string.error_illegal_argument, "RootMode", key));
        }

        @NonNull
        @Override
        public String toString() {
            return MessageFormat.format("RootMode'{'key={0}, description=''{1}'''}'", key, description);
        }

    }

    public static void setRootMode(RootMode mode) {
        setRootMode(mode, false);
    }

    public static void setRootMode(RootMode mode, boolean isWriteInfoPreference) {
        if (isWriteInfoPreference) {
            Pref.setRootMode(mode);
        } else {
            runtimeOverriddenRootMode = mode.key;
        }
    }

    public static void resetRuntimeOverriddenRootModeState() {
        runtimeOverriddenRootMode = RootMode.AUTO_DETECT.key;
    }

    public static RootMode getRootMode() {
        if (runtimeOverriddenRootMode.equals(RootMode.AUTO_DETECT.key)) {
            return Pref.getRootMode();
        }
        return RootMode.getRootMode(runtimeOverriddenRootMode);
    }

    public static boolean isRootAvailable() {
        RootMode rootMode = getRootMode();
        return rootMode == RootMode.AUTO_DETECT
                ? getRootStateWithRootShell()
                : rootMode == RootMode.FORCE_ROOT;
    }

    private static boolean getRootStateWithRootShell() {
        try {
            return RootShell.isRootAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
