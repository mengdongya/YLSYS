package store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json解析
 *
 * @author Desmond
 * @version [版本号, 2015-7-22]
 */
public class JsonUtil {
    /**
     * 对象转json
     *
     * @param entity
     * @return String
     * @author Desmond 2014-10-15 上午10:40:06
     */
    public static String entityToJson(Object entity) {
        String json = null;
        try {
            json = new Gson().toJson(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * json转单个对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @param <T>
     * @return Object
     * @author Desmond 2014-10-15 上午10:40:16
     */
    public static <T> T jsonToEntity(String json, Class<T> clazz) {
        T t = null;
        try {
            t = new Gson().fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json转对象集合
     *
     * @param json
     * @param typeToken 例：new TypeToken&lt;List&lt;Person&gt;&gt;(){}
     * @param <T>
     * @return Object
     * @author Desmond 2014-10-15 上午10:40:31
     */
    public static <T> T jsonToEntity(String json, TypeToken<T> typeToken) {
        T t = null;
        try {
            t = new Gson().fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
