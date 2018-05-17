package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.dialog.DeleteUserDiseDialog;
import store.chinaotec.com.medicalcare.javabean.ChronicDiseaseBean;
import store.chinaotec.com.medicalcare.javabean.HealthControlBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2017/12/27 0027.
 * 删除健康管理人员
 */
public class DeleteChronicDiseaseActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.rv_health_person)
    RecyclerView recyclerView;
    private String sid;
    private List<ChronicDiseaseBean.DataBean.ChronicPatientDto> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_delete_person);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        //获取用户的sid
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.dise_patient_info)
                .addParams("sid", sid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(DeleteChronicDiseaseActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                ChronicDiseaseBean chronicDiseaseBean = JSON.parseObject(response, ChronicDiseaseBean.class);
                if ("0".equals(chronicDiseaseBean.getResponseCode())) {
                    data = chronicDiseaseBean.getData().getChronicPatientDtos();
                    setData();
                } else {
                    Toast.makeText(DeleteChronicDiseaseActivity.this, chronicDiseaseBean.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        HealthPersonAdapter adapter = new HealthPersonAdapter();
        recyclerView.setAdapter(adapter);
    }

    class HealthPersonAdapter extends RecyclerView.Adapter<HealthPersonAdapter.ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(DeleteChronicDiseaseActivity.this).inflate(R.layout.item_delete_dise, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.personName.setText(data.get(position).getChronicDto().getName());
            holder.deleteNumber.setText(position + 1 + ".");
            holder.deleteSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DeleteUserDiseDialog deleteUserDiseDialog = new DeleteUserDiseDialog();
                    deleteUserDiseDialog.show(getSupportFragmentManager(), "deleteDiease");

                    deleteUserDiseDialog.setDeleteDiseSure(new DeleteUserDiseDialog.SureDeleteDiseListener() {
                        @Override
                        public void sureDelete() {
                            OkHttpUtils.post().url(MyUrl.delete_user_dise).addParams("sid",sid).
                                    addParams("chronicMemberId",data.get(position).getChronicMemberId()+"").build().execute(new StringCallback() {
                                @Override
                                public void onResponse(String response, int id) {
                                    ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                                    if ("0".equals(result.responseCode)){
                                        BaseUtill.toastUtil("删除成功");
                                        setResult(RESULT_OK);
                                        finish();
                                    }else {
                                        ToastUtil.MyToast(DeleteChronicDiseaseActivity.this,result.msg);
                                    }
                                }
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }
                            });
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.delete_dise_number)
            TextView deleteNumber;
            @Bind(R.id.dise_name)
            TextView personName;
            @Bind(R.id.delete_switch)
            ImageView deleteSwitch;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }
}
