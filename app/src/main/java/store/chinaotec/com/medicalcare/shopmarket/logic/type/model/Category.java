package store.chinaotec.com.medicalcare.shopmarket.logic.type.model;

import java.util.List;

/**
 * @ClassName: Category
 * @Description: 商品分类
 * @author: wyk
 * @date:2015年7月13日 下午7:32:25
 */
public class Category {

    private List<ChildCategoryEntity> childCategory;

    private String name;
    private String logoUrl;
    private String code;

    public List<ChildCategoryEntity> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<ChildCategoryEntity> childCategory) {
        this.childCategory = childCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class ChildCategoryEntity {
        private List<ChildCategoryEntity> childCategory;
        private String name;
        private String logoUrl;
        private String code;

        public List<ChildCategoryEntity> getChildCategory() {
            return childCategory;
        }

        public void setChildCategory(List<ChildCategoryEntity> childCategory) {
            this.childCategory = childCategory;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

}
