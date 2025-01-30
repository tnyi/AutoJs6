package org.autojs.autojs.external.shortcut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import org.autojs.autojs.util.BitmapUtils;
import org.autojs.autojs6.R;

/**
 * Created by Stardust on Jan 20, 2017.
 */
public class Shortcut {

    private final Context mContext;
    private String mName;
    private String mTargetClass;
    private String mTargetPackage;
    private Intent.ShortcutIconResource mIconRes;
    private boolean mDuplicate = false;
    private final Intent mLaunchIntent = new Intent();
    private Bitmap mIcon;

    public Shortcut(Context context) {
        mContext = context;
        mTargetPackage = mContext.getPackageName();
    }

    public Shortcut(String name, Context context) {
        this(context);
        mName = name;
    }

    public Shortcut name(String name) {
        mName = name;
        return this;
    }

    public Shortcut targetPackage(String targetPackage) {
        mTargetPackage = targetPackage;
        return this;
    }

    public Shortcut targetClass(String targetClass) {
        mTargetClass = targetClass;
        return this;
    }

    public Shortcut targetClass(Class<?> targetClass) {
        mTargetClass = targetClass.getName();
        return this;
    }

    public Shortcut iconRes(Intent.ShortcutIconResource icon) {
        if (mIcon != null) {
            throw new IllegalStateException(mContext.getString(R.string.error_set_both_icon_res_and_icon));
        }
        mIconRes = icon;
        return this;
    }

    public Shortcut iconRes(int resId) {
        return iconRes(Intent.ShortcutIconResource.fromContext(mContext, resId));
    }

    public Shortcut icon(Bitmap icon) {
        if (mIconRes != null) {
            throw new IllegalStateException(mContext.getString(R.string.error_set_both_icon_res_and_icon));
        }
        if (icon.getByteCount() > 1024 * 500) {
            mIcon = BitmapUtils.scaleBitmap(icon, 200, 200);
        }else {
            mIcon = icon;
        }
        return this;
    }

    public Intent.ShortcutIconResource getIconRes() {
        return mIconRes;
    }

    public Shortcut duplicate(boolean duplicate) {
        mDuplicate = duplicate;
        return this;
    }

    public String getName() {
        return mName;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public boolean isDuplicate() {
        return mDuplicate;
    }

    private String getClassName() {
        return mTargetClass;
    }

    private String getPackageName() {
        return mTargetPackage;
    }

    public Intent getCreateIntent() {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT)
                .putExtra(Intent.EXTRA_SHORTCUT_NAME, getName())
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, getLaunchIntent())
                .putExtra("duplicate", isDuplicate())
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        Bitmap icon = getIcon();
        if (icon == null) {
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, getIconRes());
        } else {
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        }
        return intent;
    }

    public Intent getLaunchIntent() {
        mLaunchIntent.setClassName(getPackageName(), getClassName());
        return mLaunchIntent;
    }

    public void send() {
        mContext.sendBroadcast(getCreateIntent());
    }

    public Shortcut extras(Bundle bundle) {
        mLaunchIntent.putExtras(bundle);
        return this;
    }

    public Shortcut extras(Intent intent) {
        mLaunchIntent.putExtras(intent);
        return this;
    }
}
