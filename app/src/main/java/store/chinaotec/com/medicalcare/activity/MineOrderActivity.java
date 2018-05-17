package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MineOrderAddapter;

/**
 * 我的订单展示页面
 */
public class MineOrderActivity extends BaseActivity{
    @Bind(R.id.mineOrderRecycleview)
    RecyclerView mineOrderRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        mineOrderRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mineOrderRecycleview.setAdapter(new MineOrderAddapter(getApplicationContext()));
    }
}
