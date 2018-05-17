package store.chinaotec.com.medicalcare.shopmarket.logic.sku.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.view.XListView;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter.CommentDtoListAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.CommentDtoList;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.SkuCommentList;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 此类描述的是:商品评价列表
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月13日 下午1:36:58
 */
public class SkuEvaluateListActivity extends BaseAoActivity implements XListView.IXListViewListener {

    int[] arrMenuTv = {R.id.tv_menu_name, R.id.tv_menu_name01, R.id.tv_menu_name02, R.id.tv_menu_name03};
    int[] arrNumberTv = {R.id.tv_menu_number, R.id.tv_menu_number01, R.id.tv_menu_number02, R.id.tv_menu_number03};
    private ImageView btnBack;
    private TextView tvTitle;
    private SharedPreferences fileNameAli;
    /**
     * 用户登录标识
     */
    private String sid;
    /**
     * 评论内容
     */
    private XListView lv_comment_content;
    private LinearLayout ll_comment_all;
    private LinearLayout ll_comment_good;
    private LinearLayout ll_comment_middle;
    private LinearLayout ll_comment_bad;
    private LinearLayout ll_no_comment;
    private int initialPosition;
    private View view_bow;
    private LinearLayout ll_comment;
    private TextView tv_menu_name;
    private TextView tv_menu_name01;
    private TextView tv_menu_name02;
    private TextView tv_menu_name03;
    private TextView tv_menu_number;
    private TextView tv_menu_number01;
    private TextView tv_menu_number02;
    private TextView tv_menu_number03;
    private int position;
    private String TAG = "SkuEvaluateListActivity==";
    private int page_index = 1;
    private int page_size = 50;
    private String skuCode;
    private SkuCommentList formatSkuCommentList;
    private ArrayList<CommentDtoList> commentDtoList;
    private User user;
    private boolean flagClear = false;
    private CommentDtoListAdapter dtoListAdapter;
    private Handler handler;
    private String status;
    private ArrayList<CommentDtoList> commentDtoLists;
    private boolean isRefresh = true;
    private boolean isFirstComeIn = true;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.ll_comment_all:
                position = 0;
                status = "";
                page_index = 1;
                flagClear = false;
                isFirstComeIn = true;
                getData(status);
//			getData(true,status,page_index);
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name, R.id.tv_menu_number);
                break;
            case R.id.ll_comment_good:
                position = 1;
                status = "1";
                page_index = 1;
                flagClear = false;
                isFirstComeIn = true;
                getData(status);
//			getData(status,page_index);
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name01, R.id.tv_menu_number01);
//			getDataGood();
                break;
            case R.id.ll_comment_middle:
                position = 2;
                status = "2";
                page_index = 1;
                flagClear = false;
                isFirstComeIn = true;
                getData(status);
//			getData(status,page_index);
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name02, R.id.tv_menu_number02);
//			getDataMiddle();
                break;
            case R.id.ll_comment_bad:
                position = 3;
                status = "3";
                page_index = 1;
                flagClear = false;
                isFirstComeIn = true;
                getData(status);
//			getData(status,page_index);
                startTranslateAnimation(position);
                setTextViewForColor(R.id.tv_menu_name03, R.id.tv_menu_number03);
//			getDataBad();
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_sku_evaluate_list);
    }

    @Override
    protected void initView() {
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        user = UserKeeper.readUser(this);
        sid = user.sid;
        btnBack = (ImageView) findViewById(R.id.title_btn_left);
        tvTitle = ((TextView) findViewById(R.id.title_textview));
        lv_comment_content = (XListView) findViewById(R.id.lv_comment_content);
        ll_comment_all = (LinearLayout) findViewById(R.id.ll_comment_all);
        ll_comment_good = (LinearLayout) findViewById(R.id.ll_comment_good);
        ll_comment_middle = (LinearLayout) findViewById(R.id.ll_comment_middle);
        ll_comment_bad = (LinearLayout) findViewById(R.id.ll_comment_bad);
        ll_no_comment = (LinearLayout) findViewById(R.id.ll_no_comment);
        view_bow = (View) findViewById(R.id.view_bow);
        ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
        tv_menu_name = (TextView) findViewById(R.id.tv_menu_name);
        tv_menu_name01 = (TextView) findViewById(R.id.tv_menu_name01);
        tv_menu_name02 = (TextView) findViewById(R.id.tv_menu_name02);
        tv_menu_name03 = (TextView) findViewById(R.id.tv_menu_name03);
        tv_menu_number = (TextView) findViewById(R.id.tv_menu_number);
        tv_menu_number01 = (TextView) findViewById(R.id.tv_menu_number01);
        tv_menu_number02 = (TextView) findViewById(R.id.tv_menu_number02);
        tv_menu_number03 = (TextView) findViewById(R.id.tv_menu_number03);
    }

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(this);
        ll_comment_all.setOnClickListener(this);
        ll_comment_good.setOnClickListener(this);
        ll_comment_middle.setOnClickListener(this);
        ll_comment_bad.setOnClickListener(this);

        lv_comment_content.setPullLoadEnable(true);
        lv_comment_content.setXListViewListener(this);
        handler = new Handler();
    }

    @Override
    protected void processLogic() {
        isFirstComeIn = true;
        tvTitle.setText("评论列表");

        Intent intent = getIntent();
        skuCode = intent.getStringExtra(SourceConstant.SKU_CODE);

        initData();
        int indx = 0;
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_bow.getLayoutParams(); // 取控件textView当前的布局参数
        linearParams.width = SourceConstant.screenWidth / 4;
        linearParams.leftMargin = indx * linearParams.width;
        linearParams.rightMargin = (4 - indx) * linearParams.width;
        initialPosition = indx * linearParams.width;
        getCommentList(indx);
    }

    private void initData() {
        commentDtoList = new ArrayList<CommentDtoList>();
        dtoListAdapter = new CommentDtoListAdapter(this, commentDtoList);
        lv_comment_content.setAdapter(dtoListAdapter);

    }

    /**
     * 获取全部评论的列表
     */
    private void getCommentList(int index) {
        switch (index) {
            case 0:
                position = 0;
                status = "0";
                getData("");
                setTextViewForColor(R.id.tv_menu_name, R.id.tv_menu_number);
                break;
            case 1:
                position = 1;
//			getDataGood();
                status = "1";
                getData(status);
                setTextViewForColor(R.id.tv_menu_name01, R.id.tv_menu_number01);
                break;
            case 2:
                position = 2;
//			getDataMiddle();
                status = "2";
                getData(status);
                setTextViewForColor(R.id.tv_menu_name02, R.id.tv_menu_number02);
                break;
            case 3:
                position = 3;
                status = "3";
                getData(status);
                LogUtil.e(TAG, "position:" + position);
//			getDataBad();
                setTextViewForColor(R.id.tv_menu_name03, R.id.tv_menu_number03);
                break;
        }
    }

    /**
     * 获取全部的评价列表
     */
    private void getData(String status) {
        AorunApi.getSkuCommentList(skuCode, status, page_index, page_size, mDataCallback);
    }

    /**
     * 获取好评的评价列表
     */
    private void getDataGood() {
    }

    /**
     * 获取中评的评价列表
     */
    private void getDataMiddle() {

    }

    /**
     * 获取差评的评价列表
     */
    private void getDataBad() {
    }

    /**
     * 动画移动选中状态
     */
    private void startTranslateAnimation(int id) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view_bow.getLayoutParams(); // 取控件textView当前的布局参数
        int bow_width = SourceConstant.screenWidth / 4;
        linearParams.leftMargin = 0;
        linearParams.rightMargin = 0;
        Animation translateIn = null;
        // 平移动画
        translateIn = new TranslateAnimation(initialPosition, bow_width * id, 0, 0);
        translateIn.setDuration(300);
        translateIn.setFillAfter(true);
        view_bow.startAnimation(translateIn);
        // 存储上一次的位置
        initialPosition = bow_width * id;
        // LogUtil.e(TAG, "移动前的======初始位置：" + initialPosition);
    }

    /**
     * 设置文字颜色
     */
    private void setTextViewForColor(int menuId, int numberId) {

        for (int i = 0; i < arrMenuTv.length; i++) {
            TextView view = (TextView) findViewById(arrMenuTv[i]);
            if (arrMenuTv[i] != menuId) {
                view.setTextColor(this.getResources().getColor(R.color.text_color_sku_name));
            } else {
                view.setTextColor(this.getResources().getColor(R.color.text_color_orange));
            }
        }
        for (int i = 0; i < arrNumberTv.length; i++) {
            TextView view = (TextView) findViewById(arrNumberTv[i]);
            if (arrNumberTv[i] != numberId) {
                view.setTextColor(this.getResources().getColor(R.color.text_color_sku_name1));
            } else {
                view.setTextColor(this.getResources().getColor(R.color.text_color_orange));
            }
        }

    }

    @Override
    protected void processData(String data, RequestVo requestVo) {
        switch (requestVo.requestUrl) {
            case RequestUrl.SKU_EVALUATE_LIST:
                if (!flagClear) {
                    flagClear = true;
                    commentDtoList.clear();
                    LogUtil.e(TAG, "清空了集合");
                    dtoListAdapter.notifyDataSetChanged();
                }
                formatSkuCommentList = JSON.parseObject(data, SkuCommentList.class);
                setcommentNum(formatSkuCommentList);
                commentDtoLists = formatSkuCommentList.getCommentDtoList();
                if (commentDtoLists.size() != 0) {
                    isRefresh = true;
                    isFirstComeIn = false;
                    commentDtoList.addAll(commentDtoLists);
                    dtoListAdapter.notifyDataSetChanged();
                    LogUtil.e(TAG, "当前第几页: " + page_index);
                    page_index++;
                    ll_no_comment.setVisibility(View.GONE);
                    ll_comment.setVisibility(View.VISIBLE);
                } else {

                    if (isFirstComeIn) {
                        ll_comment.setVisibility(View.GONE);
                        ll_no_comment.setVisibility(View.VISIBLE);
                    } else {
                        isRefresh = false;
                        isFirstComeIn = false;
                    }

                }
                break;
        }
    }

    private void setcommentNum(SkuCommentList formatSkuCommentList) {
        Integer allCount = 0;
        Integer goodCount = 0;
        Integer secondaryCount = 0;
        Integer lowestCount = 0;
        if ((formatSkuCommentList.getAllCount() != null)) {
            allCount = formatSkuCommentList.getAllCount();
        }
        if ((formatSkuCommentList.getGoodCount() != null)) {
            goodCount = formatSkuCommentList.getGoodCount();
        }
        if ((formatSkuCommentList.getSecondaryCount() != null)) {
            secondaryCount = formatSkuCommentList.getSecondaryCount();
        }
        if ((formatSkuCommentList.getLowestCount() != null)) {
            lowestCount = formatSkuCommentList.getLowestCount();
        }

        // 全部
        tv_menu_number.setText(Html.fromHtml(String.format(this.getResources().getString(R.string.order_tv_order_menu),
                allCount + "")));
        // 好评
        tv_menu_number01.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), goodCount + "")));
        // 中评
        tv_menu_number02.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), secondaryCount + "")));
        // 差评
        tv_menu_number03.setText(Html.fromHtml(String.format(this.getResources()
                .getString(R.string.order_tv_order_menu), lowestCount + "")));
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentDtoList.clear();
                page_index = 1;
                getData(status);
                load();
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    getData(status);
                    dtoListAdapter.notifyDataSetChanged();
                    load();
                } else {
                    lv_comment_content.stopLoadMore();
                    ToastUtil.MyToast(getApplicationContext(), "没有更多数据了");
                }
            }
        }, 1000);
    }

    private void load() {
        lv_comment_content.stopRefresh();
        lv_comment_content.stopLoadMore();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        lv_comment_content.setRefreshTime(str);
    }

}
