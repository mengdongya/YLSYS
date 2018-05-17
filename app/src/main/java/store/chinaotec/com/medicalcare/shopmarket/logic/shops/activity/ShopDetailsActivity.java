package store.chinaotec.com.medicalcare.shopmarket.logic.shops.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;


/**
 * Created by wjc  on 2016/8/11 0011.
 */
public class ShopDetailsActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {

    public static boolean ffinsh = true;
    private ShopDetailsActivity context;
    private RadioGroup mRG;
    private RadioButton mRBtnHome;
    private RadioButton mRBtnType;
    private RadioButton mRBtnIntroduce;
    private Intent mIntentStoHome;
    private Intent mIntentStoType;
    private Intent mIntentStoIntroduc;
    private TabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ShopDetailsActivity.this;
        initLayout();
        initView();
        initListener();
        processLogic();
    }

    protected void initLayout() {
        if (!SourceConstant.GOTO_SHOP_HOME_OR_TYPE.equals("TYPE")) {
            setContentView(R.layout.activity_shop_market_store_details);
        } else {
            setContentView(R.layout.activity_shop_market_store_details_type);
        }
    }

    private void initView() {
        mRG = (RadioGroup) findViewById(R.id.main_radio);
        mRBtnHome = (RadioButton) findViewById(R.id.radio_button0);
        mRBtnType = (RadioButton) findViewById(R.id.radio_button1);
        mRBtnIntroduce = (RadioButton) findViewById(R.id.radio_button2);

    }

    private void initListener() {
        mRBtnHome.setOnCheckedChangeListener(this);
        mRBtnType.setOnCheckedChangeListener(this);
        mRBtnIntroduce.setOnCheckedChangeListener(this);
    }

    private void processLogic() {
        initIntent();
    }

    /**
     * @Title: initIntent
     * @Description: 指定tab页面内容
     */
    private void initIntent() {
        this.mIntentStoHome = new Intent(this, ShopStoreMainActivity.class).putExtra("store_code", "");
        this.mIntentStoType = new Intent(this, ShopStoreTypeActivity.class).putExtra("", "");
        this.mIntentStoIntroduc = new Intent(this, ShopStoreIntrActivity.class).putExtra("", "");
        setupIntent();
    }

    /**
     * @Title: setupIntent
     * @Description: 设置Intent
     */
    private void setupIntent() {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;
        if (!SourceConstant.GOTO_SHOP_HOME_OR_TYPE.equals("TYPE")) {
            localTabHost.addTab(buildTabSpec("HOME_TAB", R.string.shop_market_store_home, R.drawable.icon_share, mIntentStoHome));
            localTabHost.addTab(buildTabSpec("TYPE_TAB", R.string.shop_market_store_type, R.drawable.icon_sdmbao, mIntentStoType));
            localTabHost.addTab(buildTabSpec("INTRO_TAB", R.string.shop_market_store_introduce, R.drawable.no_wif_icon, mIntentStoIntroduc));
        } else {
            localTabHost.addTab(buildTabSpec("HOME_TAB", R.string.shop_market_store_home, R.drawable.icon_share, mIntentStoType));
            localTabHost.addTab(buildTabSpec("TYPE_TAB", R.string.shop_market_store_type, R.drawable.icon_sdmbao, mIntentStoHome));
            localTabHost.addTab(buildTabSpec("INTRO_TAB", R.string.shop_market_store_introduce, R.drawable.no_wif_icon, mIntentStoIntroduc));

        }

    }

    /**
     * @Title: buildTabSpec
     * @Description: 建立标签
     */
    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon, final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel), getResources().getDrawable(resIcon))
                .setContent(content);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            return;
        }
        switch (buttonView.getId()) {
            case R.id.radio_button0:
//                mRBtnHome.setChecked(true);
                if (!SourceConstant.GOTO_SHOP_HOME_OR_TYPE.equals("TYPE")) {
                    setRadioButtonAction(buttonView, isChecked, "HOME_TAB");
                } else {
                    setRadioButtonAction(buttonView, isChecked, "TYPE_TAB");
                }

                break;
            case R.id.radio_button1:
//              mRBtnType.setChecked(true);
                if (!SourceConstant.GOTO_SHOP_HOME_OR_TYPE.equals("TYPE")) {
                    setRadioButtonAction(buttonView, isChecked, "TYPE_TAB");
                } else {
                    setRadioButtonAction(buttonView, isChecked, "HOME_TAB");
                }
                break;
            case R.id.radio_button2:
//                mRBtnIntroduce.setChecked(true);
                setRadioButtonAction(buttonView, isChecked, "INTRO_TAB");
                break;
        }
    }

    private void setRadioButtonAction(CompoundButton buttonView, boolean isChecked, String tabTag) {
        if (isChecked) {
            this.mTabHost.setCurrentTabByTag(tabTag);
        }
    }

    @Override
    protected void onResume() {
        if (!ffinsh) {
            this.finish();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SourceConstant.GOTO_SHOP_HOME_OR_TYPE = "";
    }
}