package store.chinaotec.com.medicalcare.shopmarket.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyScrollWebView extends WebView {
    public MyScrollWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyScrollWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void scrollTo(int x, int y) {
        if (x == 0) {
            return;
        }
        getScrollY();
        super.scrollTo(x, y);
    }
}
