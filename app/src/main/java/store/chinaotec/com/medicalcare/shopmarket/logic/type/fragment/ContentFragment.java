package store.chinaotec.com.medicalcare.shopmarket.logic.type.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.JsonUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.activity.TypeActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter.ProductMenuAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter.ProductMenuStoreAdapter;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.Category;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.model.ChildTypeEntity;

public class ContentFragment extends Fragment {
    ImageView iv;
    /**
     * 右侧二级菜单的listview
     */
    ListView typelist;
    private ProductMenuAdapter productMenuAdapter;
    private String TAG = "ContentFragment";
    private SharedPreferences fileNameAli;
    private String storeCode;
    private View view;
    private GridView typeStroeList;

    private ArrayList<ChildTypeEntity> options1Items;
    private ProductMenuStoreAdapter productMenuStoreAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fileNameAli = getActivity().getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        view = LayoutInflater.from(getActivity()).inflate(
                R.layout.shop_market_content_type_list, null);
        typelist = (ListView) view.findViewById(R.id.lv_con_type_list);
        typeStroeList = (GridView) view.findViewById(R.id.gv_store);

        return view;
    }

    @Override
    public void onResume() {
        fileNameAli = getActivity().getSharedPreferences(SourceConstant.fileNameAli, 0);
        storeCode = fileNameAli.getString(SourceConstant.STORE_CODE, "");
        super.onResume();
    }

    // 创建一个方法来更改控件内容
    public void updateUI(int position) {
        if (TypeActivity.TypeList != null) {
//			if ("".equals(storeCode)) {
//				LogUtil.e(TAG, "商城---设置了右边的二级分类三级分类的数据");
//				typelist.setVisibility(View.VISIBLE);
//				typeStroeList.setVisibility(View.GONE);
//				productMenuAdapter = new ProductMenuAdapter(getActivity(),
//						TypeActivity.TypeList.get(position).getChildCategory());
//				typelist.setAdapter(productMenuAdapter);
//				productMenuAdapter.notifyDataSetChanged();
//			}else {
            LogUtil.e(TAG, "门店---设置了右边的二级分类三级分类的数据");
            getTypeList(position);
            typelist.setVisibility(View.GONE);
            typeStroeList.setVisibility(View.VISIBLE);
            productMenuStoreAdapter = new ProductMenuStoreAdapter(getActivity(), options1Items);
            typeStroeList.setAdapter(productMenuStoreAdapter);
            productMenuStoreAdapter.notifyDataSetChanged();
//			}
        } else {
//			typelist.removeAllViews();
            LogUtil.e(TAG, "typelist是空的呀，怎么设置数据呢？？？？？？？？？？？");
        }
    }

    /**
     * 添加所有的分类到一个集合中
     */
    private void getTypeList(int position) {
        options1Items = new ArrayList<ChildTypeEntity>();
        List<Category.ChildCategoryEntity> twoType = TypeActivity.TypeList.get(position).getChildCategory();

        if (twoType.size() > 0) {

            for (int i = 0; i < twoType.size(); i++) {

                List<Category.ChildCategoryEntity> threeType = twoType.get(i).getChildCategory();

                if (threeType.size() > 0) {

                    for (int j = 0; j < threeType.size(); j++) {

                        options1Items.add(new ChildTypeEntity(threeType.get(j).getName(), threeType.get(j).getLogoUrl(),
                                threeType.get(j).getCode()));

                    }
                } else {
                    options1Items.add(new ChildTypeEntity(twoType.get(i).getName(), twoType.get(i).getLogoUrl(),
                            twoType.get(i).getCode()));
                }
            }
        } else {
            options1Items.add(new ChildTypeEntity(TypeActivity.TypeList.get(position).getName(),
                    TypeActivity.TypeList.get(position).getLogoUrl(), TypeActivity.TypeList.get(position).getCode()));
        }
        LogUtil.e("把分类转换成一个统一的集合===", "data==" + JsonUtil.entityToJson(options1Items));
    }

}
