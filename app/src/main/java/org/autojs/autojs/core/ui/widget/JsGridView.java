package org.autojs.autojs.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.autojs.autojs.AutoJs;

/**
 * Created by Stardust on Mar 30, 2018.
 */
public class JsGridView extends JsListView {

    public JsGridView(Context context) {
        super(context);
        init();
    }

    public JsGridView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JsGridView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void init() {
        super.init();
        setLayoutManager(new GridLayoutManager(getContext(), 1){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    AutoJs.getInstance().createGlobalConsole().error(e);
                }
            }
        });
    }
}
