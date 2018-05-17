package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;


import java.util.ArrayList;

import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.Sku;

public class Home {

    /**
     * banner列表
     */
    public ArrayList<ViewPagerImage> banner = new ArrayList<ViewPagerImage>();
    /**
     * 精品推荐列表
     */
    public ArrayList<Sku> JPTJ = new ArrayList<Sku>();
    /**
     * 每日专场列表
     */
    public ArrayList<Sku> MRZC = new ArrayList<Sku>();
    /**
     * 特惠街列表
     */
    public ArrayList<Sku> THJ = new ArrayList<Sku>();
    /**
     * 专题列表
     */
    public ArrayList<FunctionalBlock> block = new ArrayList<FunctionalBlock>();

    /**
     * 6个lable
     */
    public ArrayList<CategoryAndPre> category = new ArrayList<CategoryAndPre>();

    public ArrayList<CategoryAndPre> pre_category = new ArrayList<CategoryAndPre>();
    /**
     * 3个lable
     */
    public ArrayList<LableNum> label = new ArrayList<LableNum>();

    /**
     * 通知消息
     */
    public ArrayList<NotifyNews> news = new ArrayList<NotifyNews>();
    /**
     * 特别专区
     */
    public ArrayList<SpecialArea> special_area = new ArrayList<SpecialArea>();


}
