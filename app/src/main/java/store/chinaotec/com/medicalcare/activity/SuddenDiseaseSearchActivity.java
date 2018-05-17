package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.DiseaseSearchBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;

/**
 * Created by wjc on 2018/3/2 0002.
 * 突发伤病搜索
 */

public class SuddenDiseaseSearchActivity extends AppCompatActivity {
    @Bind(R.id.tv_title_back)
    ImageView tvTitleBack;
    @Bind(R.id.ed_disease_search)
    EditText edDiseaseSearch;
    @Bind(R.id.iv_delete_text)
    ImageView ivDeleteText;
    @Bind(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    private List<DiseaseSearchBean.DataBean.MemberSickDeals> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edDiseaseSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            SuddenDiseaseSearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 跳转到搜索结果界面
                    String search_key = edDiseaseSearch.getText().toString().trim();
                    if (!"".equals(search_key)) {
                        getSearchResult(search_key);
                    } else {
                        ToastUtil.MyToast(SuddenDiseaseSearchActivity.this, "请输入要搜索的内容");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void getSearchResult(String key) {
        OkHttpUtils.post().url(MyUrl.SUDDEN_DISEASE_SEARCH).addParams("keyWord",key).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                DiseaseSearchBean bean = JSON.parseObject(response, DiseaseSearchBean.class);
                dataList = bean.getData().getMemberSickDeals();
                rvSearchResult.setLayoutManager(new GridLayoutManager(SuddenDiseaseSearchActivity.this,4));
                rvSearchResult.setAdapter(new MySearchAdapter());
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(SuddenDiseaseSearchActivity.this).inflate(R.layout.item_sudden_diease_kind, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.suddenDiaeaseName.setText(dataList.get(position).getName());
            Glide.with(SuddenDiseaseSearchActivity.this).load(dataList.get(position).getImage()).into(holder.suddenDiaeaseLogo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.sudden_diaease_logo)
            ImageView suddenDiaeaseLogo;
            @Bind(R.id.sudden_diaease_name)
            TextView suddenDiaeaseName;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
