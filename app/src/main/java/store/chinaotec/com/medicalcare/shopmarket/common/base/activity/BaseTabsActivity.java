package store.chinaotec.com.medicalcare.shopmarket.common.base.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TabHost;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;


@SuppressWarnings("deprecation")
public abstract class BaseTabsActivity extends TabActivity implements
        OnCheckedChangeListener, View.OnClickListener {
    public static TabHost mTabHost;

    protected ArrayList<Intent> list;
    protected String[] arrayTitle;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initLayout();
        initIntent();
        setupIntent();
        initView();
        initListener();
        processLogic();

    }

    private void setupIntent() {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;
        final int listSize = list.size();

        for (int i = 0; i < listSize; i++) {
            localTabHost.addTab(buildTabSpec(arrayTitle[i], list.get(i)));
        }

    }

    private TabHost.TabSpec buildTabSpec(String tag, final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(tag, null)
                .setContent(content);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    mTabHost.setCurrentTabByTag(arrayTitle[0]);
                    break;
//                case R.id.radio_button1:
//                    mTabHost.setCurrentTabByTag(arrayTitle[1]);
//                    break;
                case R.id.radio_button2:
                    mTabHost.setCurrentTabByTag(arrayTitle[1]);
                    break;
                case R.id.radio_button3:
                    mTabHost.setCurrentTabByTag(arrayTitle[2]);
                    break;
                case R.id.radio_button4:
                    mTabHost.setCurrentTabByTag(arrayTitle[3]);
                    break;

            }
        }
    }

    @Override
    public void onClick(View paramView) {
        if (!onClickBottmBarEvent(paramView))
            onClickEvent(paramView);
    }

    private boolean onClickBottmBarEvent(View paramView) {
        boolean isBar = true;
        switch (paramView.getId()) {

            default:
                isBar = false;
                Log.e("BaseTabsActivity", "onClick : DEFAULT");
                break;
        }
        return isBar;
    }

    protected abstract void onClickEvent(View paramView);

    protected abstract void initLayout();

    protected abstract void initIntent();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void processLogic();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
