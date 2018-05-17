package store.chinaotec.com.medicalcare.shopmarket.common.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zhy.http.okhttp.OkHttpUtils;

import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;


/**
 * 封装网络请求
 *
 * @author xhw
 */
public class AorunYuMenApiClient {

    /**
     * HttpGet
     *
     * @param vo
     */
    public static void post(RequestVo vo, DataCallBack dataCallback) {
        String url = vo.apphoust.concat(vo.requestUrl);
        LogUtil.e("Url--Http - " + "POST" + " :==>>", url);
        LogUtil.e("params====>>", vo.params + "");
        OkHttpUtils
                .post()
                .url(url)
                .params(vo.params)
                .build()
                .execute(dataCallback);
    }

    /**
     * HttpGet
     *
     * @param vo
     */
    public static void get(RequestVo vo, DataCallBack dataCallback) {
        String url = vo.apphoust.concat(vo.requestUrl);
        LogUtil.e("Url--Http - " + "GET" + " :==>>", url);
        LogUtil.d("params====>>", vo.params + "");
        OkHttpUtils
                .get()
                .url(url)
                .params(vo.params)
                .build()
                .execute(dataCallback);
    }

    /**
     * @param context
     * @return
     * @author sky
     * Email vipa1888@163.com
     * QQ:840950105
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap网络3：net网络
     */
    public static int getAPNType(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

}
