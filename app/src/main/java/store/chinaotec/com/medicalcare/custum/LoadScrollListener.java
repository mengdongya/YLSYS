package store.chinaotec.com.medicalcare.custum;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hxk on 2017/11/1 0001 16:08
 */

public abstract class LoadScrollListener extends RecyclerView.OnScrollListener {
    //是否正在上拉加载数据
    public boolean loadMore = true;
    //传入一个布局管理器
    public LinearLayoutManager mLinearLayoutManager;
    //当前列表显示的条目数
    public int visibleItemCount;
    //当前列表已加载的条目数
    public int loadItemCount;
    //当前列表上一次加载的条目数
    public int lastLoadItemCount = 0;
    //当前列表第一个可见条目的编号
    private int firstVisibleItem;
    //分页加载的编号
    public int indexPage = 1;

    public LoadScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        loadItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (loadMore) {
            if (loadItemCount > lastLoadItemCount) {
                //数据已经加载结束
                loadMore = false;
                lastLoadItemCount = loadItemCount;
            }
        }
        //这里需要好好理解
        if (!loadMore && loadItemCount - visibleItemCount <= firstVisibleItem) {
            indexPage++;
            onLoadMore(indexPage);
            loadMore = true;
        }
    }

    public abstract void onLoadMore(int indexPage);
}
