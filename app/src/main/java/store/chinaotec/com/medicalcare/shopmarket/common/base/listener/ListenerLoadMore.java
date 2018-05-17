package store.chinaotec.com.medicalcare.shopmarket.common.base.listener;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * ListView自动加载监听
 */
public abstract class ListenerLoadMore implements OnScrollListener {

    int lastItem, mtotalItemCount;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        lastItem = firstVisibleItem + visibleItemCount;
        mtotalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

        System.out.println("onScrollStateChanged");
        if (lastItem == mtotalItemCount
                && (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING)) {
            nextPage();
            // mLoadMoreListener.onLoadMore();
        }
    }

    protected abstract void nextPage();

    // private onLoadMoreListener mLoadMoreListener;

    // public interface onLoadMoreListener {
    //
    // public abstract void onLoadMore();
    // }
}
