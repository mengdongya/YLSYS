package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.HealthInfoBean;

/**
 * 优生优育模块
 */
public class PretentPostCareActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.sign_back)
    ImageView signBack;
    @Bind(R.id.linear_jingqi)
    LinearLayout linearJingqi;
    @Bind(R.id.linear_huaiyun)
    LinearLayout linearHuaiyun;
    @Bind(R.id.linear_fenyi)
    LinearLayout linearFenyi;
    @Bind(R.id.linear_weiyang)
    LinearLayout linearWeiyang;
    @Bind(R.id.linear_jiezhong)
    LinearLayout linearJiezhong;
    @Bind(R.id.linear_biyun)
    LinearLayout linearBiyun;
    @Bind(R.id.month_center_recycleview)
    RecyclerView monthCenterRecycleview;
    private List<HealthInfoBean.DataBean> list;
    private TextView tv_include_title_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretent_post_care);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("优生优育");
        ButterKnife.bind(this);
        initListener();
        initData();

    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.RECOMMEND_KNOWLEDGE_LIST).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                HealthInfoBean bean = JSONObject.parseObject(response, HealthInfoBean.class);
                if ("0".equals(bean.getResponseCode())){
                    if (bean.getData().size() > 0){
                        list = bean.getData();
                        monthCenterRecycleview.setLayoutManager(new LinearLayoutManager(PretentPostCareActivity.this, LinearLayoutManager.VERTICAL, false));
                        monthCenterRecycleview.setAdapter(new MyAdapter());
                    }
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void initListener() {
        signBack.setOnClickListener(this);
        linearJingqi.setOnClickListener(this);
        linearHuaiyun.setOnClickListener(this);
        linearFenyi.setOnClickListener(this);
        linearWeiyang.setOnClickListener(this);
        linearJiezhong.setOnClickListener(this);
        linearBiyun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PreCareDetailActivity.class);
        switch (v.getId()) {
            case R.id.sign_back:
                finish();
                break;
            case R.id.linear_jingqi:
                intent.putExtra("gestationId","1");
                intent.putExtra("title","经期");
                intent.putExtra("summary","经期有困难  这里一站式解决");
                break;
            case R.id.linear_huaiyun:
                intent.putExtra("gestationId","2");
                intent.putExtra("title","怀孕");
                intent.putExtra("summary","怀孕讲技巧  这里告诉你");
                break;
            case R.id.linear_fenyi:
                intent.putExtra("gestationId","3");
                intent.putExtra("title","分娩");
                intent.putExtra("summary","还在扒分娩教学? 这里堪称教材基地");
                break;
            case R.id.linear_weiyang:
                intent.putExtra("gestationId","4");
                intent.putExtra("title","喂养");
                intent.putExtra("summary","营养,身心,时刻关注成长");
                break;
            case R.id.linear_jiezhong:
                intent.putExtra("gestationId","5");
                intent.putExtra("title","接种");
                intent.putExtra("summary","想当妈妈？我们有办法");
                break;
            case R.id.linear_biyun:
                intent.putExtra("gestationId","6");
                intent.putExtra("title","避孕");
                intent.putExtra("summary","最新最全的的便捷避孕方法");
                break;
        }
        if (v.getId() != R.id.sign_back){
            startActivity(intent);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(PretentPostCareActivity.this).inflate(R.layout.item_month_center, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.monthCenterNo.setText((position + 1) + ".");
            holder.monthCenterAddres.setText(list.get(position).getTitleName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PretentPostCareActivity.this,HealthInfoDetailActivity.class);
                    intent.putExtra("title", list.get(position).getTitleName());
                    intent.putExtra("url",list.get(position).getContentUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.month_center_no)
            TextView monthCenterNo;
            @Bind(R.id.month_center_addres)
            TextView monthCenterAddres;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
