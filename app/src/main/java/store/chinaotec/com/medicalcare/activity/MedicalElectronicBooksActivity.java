package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeContentAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeContentLevelAdapter;
import store.chinaotec.com.medicalcare.adapter.MedicalTypeNameAdapter;
import store.chinaotec.com.medicalcare.javabean.MedicalBookBean;


/**
 * Created by wjc on 2017/10/20 0020.
 * 医学电子图书
 */
public class MedicalElectronicBooksActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_bar)
    TextView tvTitle;
    @Bind(R.id.rv_medical_kinds)
    RecyclerView rvMedicalKinds;
    @Bind(R.id.rv_medical_content)
    RecyclerView rvMedicalContent;
    private MedicalBookBean medicalBookBean;
    private String title;
    private String type;
    private HashMap<String, String> mParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_book);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText(title);
        mParams = new HashMap<>();
    }

    private void initData() {
        mParams.clear();
        if (!"0".equals(type)) {
            mParams.put("type", type);
        }

        OkHttpUtils.post().url(MyUrl.medical_book_list).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                medicalBookBean = JSON.parseObject(response, MedicalBookBean.class);
                setData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        rvMedicalKinds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final MedicalTypeNameAdapter medicalTypeNameAdapter = new MedicalTypeNameAdapter(this, medicalBookBean.getData().getMedicalTypeList());
        rvMedicalKinds.setAdapter(medicalTypeNameAdapter);
        medicalTypeNameAdapter.notifyDataSetChanged();

        rvMedicalContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MedicalTypeContentAdapter medicalTypeContentAdapter = new MedicalTypeContentAdapter(this, medicalBookBean.getData().getMedicalTypeList());
        rvMedicalContent.setAdapter(medicalTypeContentAdapter);

        medicalTypeNameAdapter.setOnItemClick(new MedicalTypeNameAdapter.MedicalTypeClickListener() {
            @Override
            public void click(int position) {
                medicalTypeNameAdapter.setIndex(position);
                medicalTypeNameAdapter.notifyDataSetChanged();
                if (position == 0) {
                    rvMedicalContent.setLayoutManager(new LinearLayoutManager(MedicalElectronicBooksActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvMedicalContent.setAdapter(new MedicalTypeContentAdapter(MedicalElectronicBooksActivity.this, medicalBookBean.getData().getMedicalTypeList()));
                } else {
                    rvMedicalContent.setLayoutManager(new GridLayoutManager(MedicalElectronicBooksActivity.this, 2));
                    MedicalTypeContentLevelAdapter contentLevelAdapter = new MedicalTypeContentLevelAdapter(MedicalElectronicBooksActivity.this, medicalBookBean.getData().getMedicalTypeList().get(position).getDataList().get(0));
                    rvMedicalContent.setAdapter(contentLevelAdapter);
                }
            }
        });
    }
}
