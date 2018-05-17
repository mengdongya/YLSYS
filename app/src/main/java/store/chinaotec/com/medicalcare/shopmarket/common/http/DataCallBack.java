package store.chinaotec.com.medicalcare.shopmarket.common.http;

import android.widget.Toast;

import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;

/**
 * Created by wjc on 2016/10/9 0009.
 */
public abstract class DataCallBack extends Callback<Result> {

    private RequestVo requestVo = new RequestVo();

    public RequestVo getRequestVo() {
        return requestVo;
    }

    public void setRequestVo(RequestVo requestVo) {
        this.requestVo = requestVo;
    }

    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     */
    @Override
    public Result parseNetworkResponse(Response response, int id) throws Exception {
        Result result = new Result();

        try {
            JSONObject jo = new JSONObject(response.body().string());
            result.responseCode = jo.getInt("responseCode");
            result.msg = jo.getString("msg");
            result.data = jo.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
            result.responseCode = -1;
            result.msg = "数据解析失败";
            result.data = "";
        }
        LogUtil.e("", result.toString());
        return result;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        MyApp.getHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApp.getContext(), "网络繁忙...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResponse(final Result response, int id) {
        MyApp.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (response.responseCode == 0) {
                    parseData(response.data.toString(), getRequestVo());
                } else if (response.responseCode == 602) {
                    parseData("", getRequestVo());
                } else {
                    if (!StringUtils.isEmpty(response.msg))
                        Toast.makeText(MyApp.getContext(), response.msg, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MyApp.getContext(), "网络繁忙...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected abstract void parseData(String s, RequestVo requestVo);
}
