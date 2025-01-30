package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

import androidx.annotation.Nullable;
import androidx.core.widget.EdgeEffectCompat;

import org.autojs.autojs.theme.ThemeColor;
import org.autojs.autojs.theme.ThemeColorManager;
import org.autojs.autojs.theme.ThemeColorMutable;

import java.lang.reflect.Field;

/**
 * Created by Stardust on Aug 14, 2016.
 */
public class ThemeColorRecyclerView extends RecyclerView implements ThemeColorMutable {

    private Field mLeftGlowField, mTopGlowField, mRightGlowField, mBottomGlowField;
    private Field mEdgeEffectField;
    private int mColorPrimary;
    private boolean hasAppliedThemeColorLeft;
    private boolean hasAppliedThemeColorTop;
    private boolean hasAppliedThemeColorRight;
    private boolean hasAppliedThemeColorBottom;

    public ThemeColorRecyclerView(Context context) {
        super(context);
        init();
    }

    public ThemeColorRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private boolean applyThemeColor(Field edgeEffectCompatField) {
        try {
            EdgeEffectCompat edgeEffectCompat = (EdgeEffectCompat) edgeEffectCompatField.get(this);
            if (edgeEffectCompat != null)
                return setEdgeEffectColor(edgeEffectCompat, mColorPrimary);
        } catch (Exception ignored) {
            /* Ignored. */
        }
        return false;
    }

    private void init() {
        try {
            mEdgeEffectField = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
            mEdgeEffectField.setAccessible(true);
            mLeftGlowField = RecyclerView.class.getDeclaredField("mLeftGlow");
            mLeftGlowField.setAccessible(true);
            mRightGlowField = RecyclerView.class.getDeclaredField("mRightGlow");
            mRightGlowField.setAccessible(true);
            mTopGlowField = RecyclerView.class.getDeclaredField("mTopGlow");
            mTopGlowField.setAccessible(true);
            mBottomGlowField = RecyclerView.class.getDeclaredField("mBottomGlow");
            mBottomGlowField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ThemeColorManager.add(this);
    }

    private boolean setEdgeEffectColor(EdgeEffectCompat compat, int color) {
        if (compat == null)
            return false;
        try {
            EdgeEffect edgeEffect = (EdgeEffect) mEdgeEffectField.get(compat);
            if (edgeEffect != null) {
                edgeEffect.setColor(color);
            }
        } catch (Exception ignored) {
            /* Ignored. */
        }
        return true;
    }

    @Override
    public void setThemeColor(ThemeColor color) {
        if (color.colorPrimary == mColorPrimary)
            return;
        mColorPrimary = color.colorPrimary;
        invalidateGlows();
    }

    void invalidateGlows() {
        super.invalidateGlows();
        hasAppliedThemeColorBottom = hasAppliedThemeColorLeft = hasAppliedThemeColorRight = hasAppliedThemeColorTop = false;
    }

    void ensureLeftGlow() {
        super.ensureLeftGlow();
        if (!hasAppliedThemeColorLeft)
            hasAppliedThemeColorLeft = applyThemeColor(mLeftGlowField);
    }

    void ensureRightGlow() {
        super.ensureLeftGlow();
        if (!hasAppliedThemeColorRight)
            hasAppliedThemeColorRight = applyThemeColor(mRightGlowField);
    }

    void ensureTopGlow() {
        super.ensureTopGlow();
        if (!hasAppliedThemeColorTop)
            hasAppliedThemeColorTop = applyThemeColor(mTopGlowField);

    }

    void ensureBottomGlow() {
        super.ensureBottomGlow();
        if (!hasAppliedThemeColorBottom)
            hasAppliedThemeColorBottom = applyThemeColor(mBottomGlowField);
    }

}
