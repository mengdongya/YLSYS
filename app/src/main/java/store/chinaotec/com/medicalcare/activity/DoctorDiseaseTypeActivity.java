package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.fragment.medicalcare.DoctorSpecializedHallFragment;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;

/**
 * Created by wjc on 2017/11/14 0014.
 * 疾病圈具体详情
 */
public class DoctorDiseaseTypeActivity extends AppCompatActivity implements DoctorSpecializedHallFragment.BackHandlerInterface{
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_medical_title_name)
    TextView tvMedicalTitleName;
    @Bind(R.id.tv_doctor_setup)
    TextView tvDoctorSetup;
    @Bind(R.id.content_fragment)
    FrameLayout contentFragment;
    private String title;
    private int classId;
    private DoctorSpecializedHallFragment selectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        classId = getIntent().getIntExtra("classId", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_disease_type);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDoctorSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(DoctorDiseaseTypeActivity.this, DoctorPatientForumAddTopicActivity.class));
                intent.putExtra("classId", classId);
                startActivityForResult(intent, SourceConstant.GO_TO_SHARE);
            }
        });
        tvMedicalTitleName.setText(title);

        DoctorSpecializedHallFragment doctorSpecializedHallFragment = DoctorSpecializedHallFragment.newInstance(classId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment,doctorSpecializedHallFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(selectedFragment != null && !selectedFragment.onBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(DoctorSpecializedHallFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }
}
