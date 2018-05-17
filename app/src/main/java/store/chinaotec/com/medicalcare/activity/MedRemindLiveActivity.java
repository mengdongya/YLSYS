package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * 有体检提醒的展示页面
 */
public class MedRemindLiveActivity extends BaseActivity{
    @Bind(R.id.medical_remind_info)
    TextView medicalRemindInfo;
    @Bind(R.id.showMedicaHospRecycleview)
    RecyclerView showMedicaHospRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_remind);
        ButterKnife.bind(this);
    }
}
