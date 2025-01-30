package org.autojs.autojs.theme.preference;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.CheckBoxPreference;

import org.autojs.autojs6.R;

/**
 * Created by Stardust on Mar 5, 2017.
 */
public class ThemeColorCheckBoxPreference extends CheckBoxPreference {

    public ThemeColorCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ThemeColorCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ThemeColorCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorCheckBoxPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWidgetLayoutResource(R.layout.mt_theme_color_check_box_preference);
    }

}
