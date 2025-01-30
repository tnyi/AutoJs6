package org.autojs.autojs.ui.floating.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

import org.autojs.autojs.ui.enhancedfloaty.WindowBridge;

/**
 * Created by Stardust on Apr 18, 2017.
 */
public class DragGesture extends GestureDetector.SimpleOnGestureListener {

    protected WindowBridge mWindowBridge;
    protected View mView;

    private float mKeepToSideHiddenWidthRadio = 0.5f;
    private int mInitialX;
    private int mInitialY;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private View.OnClickListener mOnClickListener;
    private boolean mAutoKeepToEdge;
    private float mPressedAlpha = 1.0f;
    private float mUnpressedAlpha = 0.4f;
    private boolean mEnabled = true;

    public DragGesture(WindowBridge windowBridge, View view) {
        mWindowBridge = windowBridge;
        mView = view;
        setupView();
    }
    
    public void setAlpha(float alpha) {
        mView.setAlpha(alpha);
    }

    public void setAlphaPressed() {
        setAlpha(mPressedAlpha);
    }

    public void setAlphaUnpressed() {
        setAlpha(mUnpressedAlpha);
    }

    private void setupView() {
        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(mView.getContext(), this);
        mView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setAlphaUnpressed();
                if (!onTheEdge() && mAutoKeepToEdge) {
                    keepToEdge();
                }
            }
            return true;
        });
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    protected boolean onTheEdge() {
        int dX1 = Math.abs(mWindowBridge.getX());
        int dX2 = Math.abs(mWindowBridge.getX() - mWindowBridge.getScreenWidth());
        return Math.min(dX1, dX2) < 5;
    }

    public float getPressedAlpha() {
        return mPressedAlpha;
    }

    public void setPressedAlpha(float pressedAlpha) {
        mPressedAlpha = pressedAlpha;
    }

    public float getUnpressedAlpha() {
        return mUnpressedAlpha;
    }

    public void setUnpressedAlpha(float unpressedAlpha) {
        mUnpressedAlpha = unpressedAlpha;
    }

    public void setAutoKeepToEdge(boolean autoKeepToEdge) {
        mAutoKeepToEdge = autoKeepToEdge;
    }

    public boolean isAutoKeepToEdge() {
        return mAutoKeepToEdge;
    }

    public void setKeepToSideHiddenWidthRadio(float keepToSideHiddenWidthRadio) {
        mKeepToSideHiddenWidthRadio = keepToSideHiddenWidthRadio;
    }

    public float getKeepToSideHiddenWidthRadio() {
        return mKeepToSideHiddenWidthRadio;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        mInitialX = mWindowBridge.getX();
        mInitialY = mWindowBridge.getY();
        mInitialTouchX = event.getRawX();
        mInitialTouchY = event.getRawY();
        return false;
    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(mView);
        }
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mEnabled) {
            mWindowBridge.updatePosition(mInitialX + (int) ((e2.getRawX() - mInitialTouchX)),
                    mInitialY + (int) ((e2.getRawY() - mInitialTouchY)));
            setAlphaPressed();
            Log.d("DragGesture", "onScroll");
        }
        return false;
    }

    public void keepToEdge() {
        int x = mWindowBridge.getX();
        int hiddenWidth = (int) (mKeepToSideHiddenWidthRadio * mView.getWidth());
        if (x > mWindowBridge.getScreenWidth() / 2)
            mWindowBridge.updatePosition(mWindowBridge.getScreenWidth() - mView.getWidth() + hiddenWidth, mWindowBridge.getY());
        else
            mWindowBridge.updatePosition(-hiddenWidth, mWindowBridge.getY());
    }

    public void setOnDraggedViewClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

}
