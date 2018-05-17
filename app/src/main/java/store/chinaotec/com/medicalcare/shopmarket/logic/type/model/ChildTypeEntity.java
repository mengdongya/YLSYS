package store.chinaotec.com.medicalcare.shopmarket.logic.type.model;

public class ChildTypeEntity {
    private String name;
    private String logoUrl;
    private String code;

    public ChildTypeEntity(String name, String logoUrl, String code) {
        super();
        this.name = name;
        this.logoUrl = logoUrl;
        this.code = code;
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
