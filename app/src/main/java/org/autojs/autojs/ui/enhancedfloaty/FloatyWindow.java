package org.autojs.autojs.ui.enhancedfloaty;

import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import org.opencv.core.Point;
import org.opencv.core.Size;

/**
 * Created by Stardust on May 1, 2017.
 */
public abstract class FloatyWindow {
    private WindowManager mWindowManager;
    private FloatyService mFloatyService;

    private WindowBridge mWindowBridge;
    private WindowManager.LayoutParams mWindowLayoutParams;

    @Nullable
    private View mWindowView;

    public Size initialSize;

    public Point initialPosition;

    @CallSuper
    public void onCreate(FloatyService service, WindowManager manager) {
        mFloatyService = service;
        mWindowManager = manager;
        onCreateWindow(service, manager);
    }

    @CallSuper
    protected void onCreateWindow(FloatyService service, WindowManager manager) {
        setWindowLayoutParams(onCreateWindowLayoutParams());
        setWindowView(onCreateView(service));
        setWindowBridge(onCreateWindowBridge(getWindowLayoutParams()));

        onViewCreated(mWindowView);

        // attach to window
        attachToWindow(mWindowView, manager);
    }

    protected void onViewCreated(View view) {
        /* Empty body. */
    }

    protected void attachToWindow(View view, WindowManager manager) {
        if (view != null) {
            mWindowManager.addView(view, getWindowLayoutParams());
            onAttachToWindow(view, manager);
        }
    }

    protected void onAttachToWindow(View view, WindowManager manager) {
        /* Empty body. */
    }

    protected abstract View onCreateView(FloatyService service);

    protected WindowBridge onCreateWindowBridge(WindowManager.LayoutParams params) {
        return new WindowBridge.DefaultImpl(params, mWindowManager, mWindowView);
    }

    protected abstract WindowManager.LayoutParams onCreateWindowLayoutParams();

    public void updateWindowLayoutParams(WindowManager.LayoutParams params) {
        setWindowLayoutParams(params);
        mWindowManager.updateViewLayout(mWindowView, getWindowLayoutParams());
    }

    protected void setWindowManager(WindowManager windowManager) {
        mWindowManager = windowManager;
    }

    public WindowManager.LayoutParams getWindowLayoutParams() {
        return mWindowLayoutParams != null ? mWindowLayoutParams : getDefaultWindowLayoutParams();
    }

    public void setWindowLayoutParams(@Nullable WindowManager.LayoutParams windowLayoutParams) {
        mWindowLayoutParams = windowLayoutParams;
    }

    @Nullable
    public View getWindowView() {
        return mWindowView;
    }

    protected void setWindowView(View windowView) {
        mWindowView = windowView;
    }

    public FloatyService getFloatyService() {
        return mFloatyService;
    }

    public WindowManager getWindowManager() {
        return mWindowManager;
    }

    @Nullable
    public WindowBridge getWindowBridge() {
        return mWindowBridge;
    }

    protected void setWindowBridge(WindowBridge windowBridge) {
        mWindowBridge = windowBridge;
    }

    public void onServiceDestroy(FloatyService service) {
        close();
    }

    public void close() {
        try {
            mWindowManager.removeView(mWindowView);
            FloatyService.removeWindow(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WindowManager.LayoutParams getDefaultWindowLayoutParams() {
        return new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                getDefaultWindowType(),
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
    }

    /**
     * @noinspection deprecation
     */
    private int getDefaultWindowType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            return WindowManager.LayoutParams.TYPE_PHONE;
        }
    }

}
