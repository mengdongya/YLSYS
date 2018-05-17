package store.chinaotec.com.medicalcare.fragment.medicalcare;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.MedicalFoodGuideActivity;
import store.chinaotec.com.medicalcare.activity.MedicalKnowledgeActivity;
import store.chinaotec.com.medicalcare.activity.ScienceInformationActivity;

/**
 * A simple {@link Fragment} subclass.
 * 医养知识充电页面
 */
public class KnoleChargeFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.iv_knowledge_charge_food)
    ImageView ivFood;
    @Bind(R.id.iv_medical_knowledge)
    ImageView ivMedicalKnowledge;
    @Bind(R.id.iv_scientific_knowledge)
    ImageView ivScientificKnowledge;
  /*  @Bind(R.id.konowledge_charge_recycleview)
    RecyclerView konowledgeChargeRecycleview;*/

    public KnoleChargeFragment() {
        // Required empty public constructor
    }

    public static KnoleChargeFragment instance() {
        Bundle bundle = new Bundle();
        KnoleChargeFragment knoleChargeFragment = new KnoleChargeFragment();
        knoleChargeFragment.setArguments(bundle);
        return knoleChargeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_knowle_charge, container, false);
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    private void initListener() {
        ivFood.setOnClickListener(this);
        ivMedicalKnowledge.setOnClickListener(this);
        ivScientificKnowledge.setOnClickListener(this);
     /*   konowledgeChargeRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        konowledgeChargeRecycleview.setAdapter(new MyKnowlChargeAdapter(getContext()));*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.iv_scientific_knowledge:
                intent.setClass(getActivity(), ScienceInformationActivity.class);//科普资料
                break;
            case R.id.iv_knowledge_charge_food:
                intent.setClass(getActivity(),MedicalFoodGuideActivity.class);
                intent.putExtra("title","膳食指南");
                break;
            case R.id.iv_medical_knowledge:
                intent.setClass(getActivity(),MedicalKnowledgeActivity.class);
                intent.putExtra("title","大众医学知识");
                break;
        }
        startActivity(intent);
    }
}
