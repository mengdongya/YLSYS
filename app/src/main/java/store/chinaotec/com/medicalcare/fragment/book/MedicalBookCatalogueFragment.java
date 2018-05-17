package store.chinaotec.com.medicalcare.fragment.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.MedicalBookDetailActivity;
import store.chinaotec.com.medicalcare.javabean.MedicalBookIntro;

/**
 * Created by wjc on 2018/3/1 0001.
 * 医疗图书目录
 */

public class MedicalBookCatalogueFragment extends Fragment {
    List<MedicalBookIntro.Data.MedicalBookDetailDto.MedicalBookLists> medicalBookLists;
    @Bind(R.id.rv_book_catalogue)
    RecyclerView rvBookCatalogue;

    public static MedicalBookCatalogueFragment newInstance(List<MedicalBookIntro.Data.MedicalBookDetailDto.MedicalBookLists> medicalBookLists) {
        MedicalBookCatalogueFragment fragment = new MedicalBookCatalogueFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) medicalBookLists);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        medicalBookLists = (List<MedicalBookIntro.Data.MedicalBookDetailDto.MedicalBookLists>) getArguments().getSerializable("list");
        View view = inflater.inflate(R.layout.fragment_catalogue_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        rvBookCatalogue.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBookCatalogue.setAdapter(new MyBookCatalogueAdapter());
    }


    class MyBookCatalogueAdapter extends RecyclerView.Adapter<MyBookCatalogueAdapter.MyViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_disease_name, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final MedicalBookIntro.Data.MedicalBookDetailDto.MedicalBookLists dataBean = medicalBookLists.get(position);
            holder.tvMedicalName.setText(dataBean.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),MedicalBookDetailActivity.class);
                    intent.putExtra("medical_book_name",dataBean.getTitle());
                    intent.putExtra("medical_book_url",dataBean.getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return medicalBookLists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_disease_name)
            TextView tvMedicalName;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
