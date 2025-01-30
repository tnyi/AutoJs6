package org.autojs.autojs.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import org.autojs.autojs.AutoJs;
import org.autojs.autojs.core.ui.ViewExtras;
import org.autojs.autojs.core.ui.inflater.DynamicLayoutInflater;
import org.autojs.autojs.core.ui.nativeview.NativeView;
import org.autojs.autojs.core.ui.nativeview.ViewPrototype;
import org.autojs.autojs.groundwork.WrapContentLinearLayoutManager;
import org.autojs.autojs.runtime.ScriptRuntime;
import org.autojs.autojs.util.ViewUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Stardust on Mar 28, 2018.
 */
public class JsListView extends RecyclerView {

    private Node mItemTemplate;
    private DynamicLayoutInflater mDynamicLayoutInflater;
    private Object mDataSource;
    private DataSourceAdapter mDataSourceAdapter;
    private OnItemTouchListener mOnItemTouchListener;
    private ScriptRuntime mScriptRuntime;

    public JsListView(Context context) {
        super(context);
        init();
    }

    public JsListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JsListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setAdapter(new Adapter());
        setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
    }

    public void initWithScriptRuntime(ScriptRuntime scriptRuntime) {
        mScriptRuntime = scriptRuntime;
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        mOnItemTouchListener = onItemTouchListener;
    }

    public void setDataSourceAdapter(DataSourceAdapter dataSourceAdapter) {
        mDataSourceAdapter = dataSourceAdapter;
        getAdapter().notifyDataSetChanged();
    }

    public Object getDataSource() {
        return mDataSource;
    }

    public void setDataSource(Object dataSource) {
        mDataSource = dataSource;
        if (mDataSourceAdapter != null)
            mDataSourceAdapter.setDataSource(dataSource);
        getAdapter().notifyDataSetChanged();
    }

    public void setItemTemplate(DynamicLayoutInflater inflater, Node itemTemplate) {
        mDynamicLayoutInflater = inflater;
        mItemTemplate = itemTemplate;
    }

    public static class ItemHolder {
        private final ViewHolder mViewHolder;

        ItemHolder(ViewHolder viewHolder) {
            mViewHolder = viewHolder;
        }

        public int getPosition() {
            return mViewHolder.getAdapterPosition();
        }

        public Object getItem() {
            return mViewHolder.item;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Object item = null;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (mOnItemTouchListener != null) {
                    int pos = getAdapterPosition();
                    mOnItemTouchListener.onItemClick(JsListView.this, itemView, mDataSourceAdapter.getItem(mDataSource, pos), pos);
                }
            });
            itemView.setOnLongClickListener(v -> {
                if (mOnItemTouchListener == null)
                    return false;
                int pos = getAdapterPosition();
                return mOnItemTouchListener.onItemLongClick(JsListView.this, itemView, mDataSourceAdapter.getItem(mDataSource, pos), pos);
            });
            NativeView nativeView = ViewExtras.getNativeView(JsListView.this);
            if (nativeView != null) {
                ViewPrototype prototype = nativeView.getViewPrototype();
                prototype.emit("item_bind", itemView, new ItemHolder(this));
            }
        }

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            try {
                mDynamicLayoutInflater.setInflateFlags(DynamicLayoutInflater.FLAG_IGNORES_DYNAMIC_ATTRS);
                return new ViewHolder(mDynamicLayoutInflater.inflate(mDynamicLayoutInflater.newInflateContext(), mItemTemplate, parent, false));
            } catch (Exception e) {
                ViewUtils.showToast(getContext(), e.getMessage());
                AutoJs.getInstance().getGlobalConsole().printAllStackTrace(e);
                if (mScriptRuntime != null) mScriptRuntime.exit(e);
                return new ViewHolder(new View(parent.getContext()));
            } finally {
                mDynamicLayoutInflater.setInflateFlags(DynamicLayoutInflater.FLAG_DEFAULT);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            try {
                Object oldCtx = mScriptRuntime.ui.getBindingContext();
                Object item = mDataSourceAdapter.getItem(mDataSource, position);
                holder.item = item;
                mScriptRuntime.ui.setBindingContext(item);
                mDynamicLayoutInflater.setInflateFlags(DynamicLayoutInflater.FLAG_JUST_DYNAMIC_ATTRS);
                applyDynamicAttrs(mItemTemplate, holder.itemView, JsListView.this);
                mScriptRuntime.ui.setBindingContext(oldCtx);
            } catch (Exception e) {
                ViewUtils.showToast(getContext(), e.getMessage());
                AutoJs.getInstance().getGlobalConsole().printAllStackTrace(e);
                if (mScriptRuntime != null) mScriptRuntime.exit(e);
            } finally {
                mDynamicLayoutInflater.setInflateFlags(DynamicLayoutInflater.FLAG_DEFAULT);
            }
        }

        private void applyDynamicAttrs(Node node, View itemView, ViewGroup parent) {
            mDynamicLayoutInflater.applyAttributes(mDynamicLayoutInflater.newInflateContext(), itemView, mDynamicLayoutInflater.getAttributesMap(node), parent);
            if (!(itemView instanceof ViewGroup viewGroup))
                return;
            NodeList nodeList = node.getChildNodes();
            int j = 0;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                if (child.getNodeType() != Node.ELEMENT_NODE) continue;
                applyDynamicAttrs(child, viewGroup.getChildAt(j), viewGroup);
                j++;
            }
        }

        @Override
        public int getItemCount() {
            return mDataSource == null ? 0
                    : mDataSourceAdapter == null ? 0
                    : mDataSourceAdapter.getItemCount(mDataSource);
        }
    }

    public interface DataSourceAdapter {

        int getItemCount(Object dataSource);

        Object getItem(Object dataSource, int i);

        void setDataSource(Object dataSource);
    }

    public interface OnItemTouchListener {

        void onItemClick(JsListView listView, View itemView, Object item, int pos);

        boolean onItemLongClick(JsListView listView, View itemView, Object item, int pos);

    }

}
