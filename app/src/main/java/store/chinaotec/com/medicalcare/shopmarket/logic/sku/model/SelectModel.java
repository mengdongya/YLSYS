package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

public class SelectModel {
    public String id;
    public String content;
    // 1:选中 , 2:未选中 , 3: 不能选择
    public String flag = "2";

    public SelectModel(String id, String content, String flag) {
        this.id = id;
        this.content = content;
        this.flag = flag;
    }

    public boolean equals(Object other) { // 重写equals方法，后面最好重写hashCode方法

        if (this == other) // 先检查是否其自反性，后比较other是否为空。这样效率高
            return true;
        if (other == null)
            return false;
        if (!(other instanceof SelectModel))
            return false;

        final SelectModel cat = (SelectModel) other;

        if (!content.equals(cat.content))
            return false;

        return true;
    }
}
