package org.autojs.autojs.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.view.Window;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;

/**
 * Created by Stardust on Aug 22, 2017.
 */
public class AppWithStatusBarLayout extends AppBarLayout {

    public AppWithStatusBarLayout(Context context) {
        super(context);
    }

    public AppWithStatusBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            setPadding();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setPadding();
    }

    private void setPadding() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setPadding(getPaddingLeft(), getStatusBarHeight(), getPaddingRight(), getPaddingBottom());
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private int getStatusBarHeight() {
        Rect rect = new Rect();
        Window window = getWindow();
        if (window == null) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        }
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    @Nullable
    private Window getWindow() {
        return getWindowForContext(getContext());
    }

    @Nullable
    private Window getWindowForContext(Context context) {
        if (context instanceof Activity) {
            return ((Activity) context).getWindow();
        }
        if (context instanceof ContextWrapper) {
            return getWindowForContext(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

}
