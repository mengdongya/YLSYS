package store.chinaotec.com.medicalcare.shopmarket.logic.address.model;

/**
 * 省,市,区列表 -- item
 */
public class Area {
    // 省,市,区 id
    public String code;
    // 省,市,区 名字
    public String name;

    @Override
    public String toString() {
        return "Area [code=" + code + ", name=" + name + "]";
    }

}
