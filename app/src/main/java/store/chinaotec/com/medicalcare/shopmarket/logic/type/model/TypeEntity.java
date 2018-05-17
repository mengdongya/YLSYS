package store.chinaotec.com.medicalcare.shopmarket.logic.type.model;

/**
 * @ClassName: TypeEntity
 * @Description: 商品分类
 * @author: wjc
 * @date:2015年7月10日 下午1:18:17
 */
public class TypeEntity {
    /**
     * 分类名称
     */
    private String nickName;

    private String code;

    public TypeEntity(String nickName, String code) {
        this.nickName = nickName;
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
