package store.chinaotec.com.medicalcare.shopmarket.common.utils.httputil;

/**
 * 解析服务器返回数据
 */
public interface DataCallback<T> {

    /**
     * 解析服务器返回数据
     *
     * @param reqVo  请求对象
     * @param status 访问状态 <br>
     *               false :无网络,请求失败,请求成功(但无数据)<br>
     *               true:请求成功,并且有数据
     **/
    public abstract void processData(T reqVo, boolean status);
}
