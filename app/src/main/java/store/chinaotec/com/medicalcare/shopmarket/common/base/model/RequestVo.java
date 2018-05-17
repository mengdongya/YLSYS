package store.chinaotec.com.medicalcare.shopmarket.common.base.model;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 此类描述的是:请求信息
 *
 * @version:1.5
 * @date:2015年3月11日上午10:53:05
 */
public class RequestVo {

    public Context context;
    public String apphoust = "";
    /**
     * 请求方式 type : 0-get , 1-post , 2-webservice
     */
    public int type = 0;

    /**
     * requestUrl :接口名称
     */
    public String requestUrl = "";

    /**
     * requestUrl :http get ,post ,webservice 方式请求参数集合
     *
     * @param参数 k为参数名 v为参数值
     */
    public HashMap<String, String> requestDataMap = new HashMap<String, String>();
    public Map<String, String> params = new LinkedHashMap<>();

    /**
     * 服务端返回内容
     */
    public String resultStr = "";

    /**
     * 是否出现等待对话框
     */
    public boolean hasDialog = true;

}

