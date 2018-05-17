package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyBlankAdapter;
import store.chinaotec.com.medicalcare.javabean.BankBean;

public class BankChoseActivity extends BaseActivity {

    @Bind(R.id.blank_recycleview)
    RecyclerView blankRecycleview;
    private MyBlankAdapter myBlankAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_choose);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        //获取银行数据源
        OkGo.get(MyUrl.GET_BANKS).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                BankBean bankBean = gson.fromJson(s, BankBean.class);
                List<BankBean.DataBean> data = bankBean.getData();

                blankRecycleview.setLayoutManager(new LinearLayoutManager(MyApp.getContext(), LinearLayoutManager.VERTICAL, false));
                myBlankAdapter = new MyBlankAdapter(data, MyApp.getContext());
                blankRecycleview.setAdapter(myBlankAdapter);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myBlankAdapter.setOnClickItemListener(new MyBlankAdapter.ItemClikListener() {
                            @Override
                            public void clickItem(BankBean.DataBean dataBean) {
                                BankBean.DataBean mDataBean = dataBean;
                                Intent intent = new Intent();
                                intent.putExtra("blankName", mDataBean.getBankName());
                                intent.putExtra("bankCode", mDataBean.getBankCode());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });


    }
}
