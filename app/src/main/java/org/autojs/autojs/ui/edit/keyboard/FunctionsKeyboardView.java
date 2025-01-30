package org.autojs.autojs.ui.edit.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.autojs.autojs.groundwork.WrapContentGridLayoutManger;
import org.autojs.autojs.model.indices.Module;
import org.autojs.autojs.model.indices.Modules;
import org.autojs.autojs.model.indices.Property;
import org.autojs.autojs.ui.widget.GridDividerDecoration;
import org.autojs.autojs6.R;
import org.autojs.autojs6.databinding.FunctionsKeyboardViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Stardust on Dec 9, 2017.
 * Modified by SuperMonster003 as of May 26, 2022.
 */
public class FunctionsKeyboardView extends FrameLayout {

    private static final int SPAN_COUNT = 4;

    private RecyclerView mModulesView;
    private RecyclerView mPropertiesView;
    private Drawable mGridDividerView;

    private List<Module> mModules;
    private final Map<Module, List<Integer>> mSpanSizes = new HashMap<>();
    private Module mSelectedModule;
    private View mSelectedModuleView;
    private Paint mPaint;
    private ClickCallback mClickCallback;

    public FunctionsKeyboardView(@NonNull Context context) {
        super(context);
        init();
    }

    public FunctionsKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FunctionsKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public FunctionsKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public interface ClickCallback {
        void onModuleLongClick(Module module);

        void onPropertyClick(Module m, Property property);

        void onPropertyLongClick(Module m, Property property);
    }

    public void setClickCallback(ClickCallback clickCallback) {
        mClickCallback = clickCallback;
    }

    private void init() {
        FunctionsKeyboardViewBinding binding = FunctionsKeyboardViewBinding.inflate(LayoutInflater.from(getContext()), this, true);

        initModulesView(binding);
        initPropertiesView(binding);
    }

    private void initPropertiesView(FunctionsKeyboardViewBinding binding) {
        mPropertiesView = binding.properties;
        WrapContentGridLayoutManger manager = new WrapContentGridLayoutManger(getContext(), SPAN_COUNT);
        manager.setDebugInfo("FunctionsKeyboardView");
        mPropertiesView.setLayoutManager(manager);
        mPropertiesView.setAdapter(new PropertiesAdapter());
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSpanSizes.get(mSelectedModule).get(position);
            }
        });
        mGridDividerView = ContextCompat.getDrawable(getContext(), R.drawable.divider_functions_view);
        GridDividerDecoration dividerItemDecoration = new GridDividerDecoration(getContext(), mGridDividerView);
        mPropertiesView.addItemDecoration(dividerItemDecoration);
    }

    private void initSpanSizes(Module module) {
        if (mSpanSizes.containsKey(module)) {
            return;
        }
        if (getMeasuredWidth() == 0) {
            throw new IllegalStateException();
        }
        List<Integer> spanSizes = new ArrayList<>();
        // @Hint by Stardust (https://github.com/hyb1996) on Dec 10, 2017.
        //  ! 初始化 spanSizes 列表.
        //  ! en-US (translated by SuperMonster003 on Jul 28, 2024):
        //  ! Initialize spanSizes.
        for (Property property : mSelectedModule.getProperties()) {
            int width = Math.max(getTextWidth(property.getKey()), getTextWidth(property.getSummary()));
            if (mGridDividerView != null) {
                // @Hint by SuperMonster003 on Nov 30, 2023.
                //  ! Increase the width as much as possible to prevent multi-line module names in TextView.
                //  ! Like:
                //  ! [ stopAllAndToa ]
                //  ! [      st       ]
                //  ! zh-CN:
                //  ! 尽可能增加宽度以避免 TextView 控件中的模块名称出现多行现象.
                //  ! 像是这样:
                //  ! [ stopAllAndToa ]
                //  ! [      st       ]
                width += mGridDividerView.getIntrinsicWidth() * 2;
            }
            int spanSize = (int) Math.ceil(width / ((double) getMeasuredWidth() / SPAN_COUNT));
            spanSizes.add(Math.min(spanSize, 2));
        }
        // 遍历这个列表, 调整 spanSize.
        // 例如以下这种情况:
        // [] [] []
        // [   ] [] []
        // [] [] [] []
        // 把第一行的第三个元素的 spanSize 设置为 2.
        int column = 0;
        for (int i = 0; i < spanSizes.size(); i++) {
            int spanSize = spanSizes.get(i);
            if (spanSize + column > SPAN_COUNT) {
                spanSizes.set(i - 1, 2);
                column = spanSize;
            } else {
                column += spanSize;
            }
            if (column == SPAN_COUNT) {
                column = 0;
            }
        }
        mSpanSizes.put(module, spanSizes);
    }

    private String getDisplayText(Property property) {
        if (TextUtils.isEmpty(property.getSummary())) {
            return property.getKey();
        }
        return property.getKey() + "\n" + property.getSummary();
    }

    private int getTextWidth(String text) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textSize_item_property));
        }
        Rect r = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), r);
        return r.width();
    }

    private void initModulesView(FunctionsKeyboardViewBinding binding) {
        mModulesView = binding.moduleList;
        mModulesView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mModulesView.setAdapter(new ModulesAdapter());
    }

    @SuppressLint("CheckResult")
    private void loadModules() {
        Modules.getInstance().getModules(getContext())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(modules -> {
                    mModules = modules;
                    if (modules.size() > 0) {
                        setSelectedModule(modules.get(0), null);
                    }
                    mModulesView.getAdapter().notifyDataSetChanged();
                    mPropertiesView.getAdapter().notifyDataSetChanged();
                });
    }

    private void setSelectedModule(Module module, @Nullable View moduleView) {
        mSelectedModule = module;
        if (mSelectedModuleView != null) {
            mSelectedModuleView.setSelected(false);
        }
        mSelectedModuleView = moduleView;
        if (mSelectedModuleView != null) {
            mSelectedModuleView.setSelected(true);
        }
        initSpanSizes(mSelectedModule);
        mPropertiesView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mModules == null) {
            loadModules();
        }
    }

    private class ModuleViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private Module mModule;

        ModuleViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnClickListener(v -> {
                if (mModule == null) {
                    return;
                }
                setSelectedModule(mModule, mTextView);
            });
            mTextView.setOnLongClickListener(v -> {
                if (mClickCallback != null) {
                    mClickCallback.onModuleLongClick(mModule);
                    return true;
                }
                return false;
            });
        }

        void bind(Module module) {
            mModule = module;
            mTextView.setText(module.getSummary());
            mTextView.setSelected(module == mSelectedModule);
            if (module == mSelectedModule) {
                mSelectedModuleView = mTextView;
            }
        }
    }

    private class PropertyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private Property mProperty;

        PropertyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnLongClickListener(v -> {
                if (mClickCallback != null) {
                    mClickCallback.onPropertyLongClick(mSelectedModule, mProperty);
                    return true;
                }
                return false;
            });
            mTextView.setOnClickListener(v -> {
                if (mClickCallback != null) {
                    mClickCallback.onPropertyClick(mSelectedModule, mProperty);
                }
            });
        }

        void bind(Property property) {
            mProperty = property;
            mTextView.setText(getDisplayText(property));
        }
    }

    private class ModulesAdapter extends RecyclerView.Adapter<ModuleViewHolder> {

        @NonNull
        @Override
        public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ModuleViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_module, parent, false));
        }

        @Override
        public void onBindViewHolder(ModuleViewHolder holder, int position) {
            holder.bind(mModules.get(position));
        }

        @Override
        public int getItemCount() {
            return mModules == null ? 0 : mModules.size();
        }

    }

    private class PropertiesAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

        @NonNull
        @Override
        public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PropertyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_property, parent, false));
        }

        @Override
        public void onBindViewHolder(PropertyViewHolder holder, int position) {
            holder.bind(mSelectedModule.getProperties().get(position));
        }

        @Override
        public int getItemCount() {
            return mSelectedModule == null ? 0 : mSelectedModule.getProperties().size();
        }

    }

}
