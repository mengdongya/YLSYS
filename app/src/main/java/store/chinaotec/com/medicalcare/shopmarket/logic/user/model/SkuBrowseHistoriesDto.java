package store.chinaotec.com.medicalcare.shopmarket.logic.user.model;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

/***
 * 此类描述的是:商品浏览历史返回的对象
 *
 * @author: wjc
 * @version:1.0
 * @date:2015年11月6日 下午2:04:28
 */
public class SkuBrowseHistoriesDto {
    /**
     * 用户ID
     */
    public long memberId;
    /**
     * 商品对象
     */
    public Sku skuDto;
    /**
     * 浏览次数
     */
    public String browseCounts;
    /**
     * 浏览时间
     */
    public String browseTime;

    public ArrayList<SkuBrowseHistoriesDto> skuBrowseHistoriesDto;

    public SkuBrowseHistoriesDto() {
        super();
    }

    public SkuBrowseHistoriesDto(long memberId, Sku skuDto, String browseCounts, String browseTime) {
        super();
        this.memberId = memberId;
        this.skuDto = skuDto;
        this.browseCounts = browseCounts;
        this.browseTime = browseTime;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Sku getSkuDto() {
        return skuDto;
    }

    public void setSkuDto(Sku skuDto) {
        this.skuDto = skuDto;
    }

    public String getBrowseCounts() {
        return browseCounts;
    }

    public void setBrowseCounts(String browseCounts) {
        this.browseCounts = browseCounts;
    }

    public String getBrowsTime() {
        return browseTime;
    }

    public void setBrowsTime(String browseTime) {
        this.browseTime = browseTime;
    }

    @Override
    public String toString() {
        return "SkuBrowseHistoriesDto [memberId=" + memberId + ", skuDto=" + skuDto + ", browseCounts=" + browseCounts
                + ", browsTime=" + browseTime + "]";
    }

}
