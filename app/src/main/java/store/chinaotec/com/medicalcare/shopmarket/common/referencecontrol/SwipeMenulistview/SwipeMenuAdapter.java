package store.chinaotec.com.medicalcare.shopmarket.common.referencecontrol.SwipeMenulistview;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

public class SwipeMenuAdapter implements WrapperListAdapter, SwipeMenuView.OnSwipeItemClickListener {
    private ListAdapter mAdapter;
    private Context mContext;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;

    public SwipeMenuAdapter(Context context, ListAdapter adapter) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    public int getCount() {
        return this.mAdapter.getCount();
    }

    public Object getItem(int position) {
        return this.mAdapter.getItem(position);
    }

    public long getItemId(int position) {
        return this.mAdapter.getItemId(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SwipeMenuLayout layout = null;
        if (convertView == null) {
            View contentView = this.mAdapter.getView(position, convertView, parent);
            SwipeMenu menu = new SwipeMenu(this.mContext);
            menu.setViewType(this.mAdapter.getItemViewType(position));
            this.createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu, (SwipeMenuListView) parent);
            menuView.setOnSwipeItemClickListener(this);
            SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(), listView.getOpenInterpolator());
            layout.setPosition(position);
        } else {
            layout = (SwipeMenuLayout) convertView;
            layout.closeMenu();
            layout.setPosition(position);
            this.mAdapter.getView(position, layout.getContentView(), parent);
        }

        return layout;
    }

    public void createMenu(SwipeMenu menu) {
        SwipeMenuItem item = new SwipeMenuItem(this.mContext);
        item.setTitle("Item 1");
        item.setBackground(new ColorDrawable(-7829368));
        item.setWidth(300);
        menu.addMenuItem(item);
        item = new SwipeMenuItem(this.mContext);
        item.setTitle("Item 2");
        item.setBackground(new ColorDrawable(-65536));
        item.setWidth(300);
        menu.addMenuItem(item);
    }

    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (this.onMenuItemClickListener != null) {
            this.onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
        }

    }

    public void setOnMenuItemClickListener(SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.mAdapter.registerDataSetObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mAdapter.unregisterDataSetObserver(observer);
    }

    public boolean areAllItemsEnabled() {
        return this.mAdapter.areAllItemsEnabled();
    }

    public boolean isEnabled(int position) {
        return this.mAdapter.isEnabled(position);
    }

    public boolean hasStableIds() {
        return this.mAdapter.hasStableIds();
    }

    public int getItemViewType(int position) {
        return this.mAdapter.getItemViewType(position);
    }

    public int getViewTypeCount() {
        return this.mAdapter.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.mAdapter.isEmpty();
    }

    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }
}

