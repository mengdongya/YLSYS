package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

import java.util.ArrayList;

/**
 * 此类描述的是: 商品评论的列表返回对象
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月14日 上午11:52:42
 */
public class SkuCommentList {
    private Integer allCount;
    private Integer goodCount;
    private Integer secondaryCount;
    private Integer lowestCount;

    private ArrayList<CommentDtoList> commentDtoList;

    public SkuCommentList(Integer allCount, Integer goodCount, Integer secondaryCount, Integer lowestCount,
                          ArrayList<CommentDtoList> commentDtoList) {
        super();
        this.allCount = allCount;
        this.goodCount = goodCount;
        this.secondaryCount = secondaryCount;
        this.lowestCount = lowestCount;
        this.commentDtoList = commentDtoList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getSecondaryCount() {
        return secondaryCount;
    }

    public void setSecondaryCount(Integer secondaryCount) {
        this.secondaryCount = secondaryCount;
    }

    public Integer getLowestCount() {
        return lowestCount;
    }

    public void setLowestCount(Integer lowestCount) {
        this.lowestCount = lowestCount;
    }

    public ArrayList<CommentDtoList> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(ArrayList<CommentDtoList> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }

}
