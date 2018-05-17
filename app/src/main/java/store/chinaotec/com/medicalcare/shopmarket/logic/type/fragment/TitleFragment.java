package store.chinaotec.com.medicalcare.shopmarket.logic.type.fragment;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.activity.ProductTypeOnItemClickListener;
import store.chinaotec.com.medicalcare.shopmarket.logic.type.adapter.LeftMenuAdapter;


public class TitleFragment extends Fragment {
    public static String[] data;
    private ListView lv_title;
    private LeftMenuAdapter adapter;
    // = { "分组0", "分组1", "分组2", "分组3", "分组4", "分组5", "分组6"}
    private SharedPreferences fileNameAli;
    private String typeData;

    private ProductTypeOnItemClickListener listener;
    private String TAG = "TitleFragment========";

    public void setListener(ProductTypeOnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LogUtil.e(TAG, "执行了 onCreateView ======= ");

        fileNameAli = getActivity().getSharedPreferences(SourceConstant.fileNameAli, 0);
        typeData = fileNameAli.getString(SourceConstant.LEFT_MENU_LIST_JSON, "");

        if ("".equals(typeData)) {
            data = Constant.data1;
        } else {
            LogUtil.e(typeData, "左侧菜单栏用的是保存的数据");
            data = typeData.split(",");
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.shop_market_menu_type_list, null);
        lv_title = (ListView) view.findViewById(R.id.lv_type_list);
        if (data != null && !"".equals(data)) {

            adapter = new LeftMenuAdapter(getActivity(), data);
            lv_title.setAdapter(adapter);
            lv_title.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // 友盟 一级标题的点击次数统计
                    adapter.setIndx(position);
                    LogUtil.e("TitleFragment", "onItemClick点击的是：" + position);

                    listener.getPostion(position);// 拿到值
                    adapter.notifyDataSetChanged();
                }

            });
        }
        return view;

    }

    // 如果是继承已有的Fragment,重写onActivityCreated
    // @Override
    // public void onActivityCreated(Bundle savedInstanceState) {
    // getListView().setVerticalScrollBarEnabled(true);
    // // 操作
    // lv_title = getListView();
    // data = Constant.data;
    // View view = LayoutInflater.from(getActivity()).inflate(
    // R.layout.menu_type_list, null);
    // lv_title = (ListView) view.findViewById(R.id.lv_type_list);
    // 设置adapter
    // adapter = new LeftMenuAdapter(getActivity(), data);
    // Drawable drawable =
    // getResources().getDrawable(R.drawable.type_list_itemselecter);
    // lv_title.setSelector(drawable);
    // lv_title.setLis
    // setListAdapter(adapter);
    // lv_title.setVerticalScrollBarEnabled(true);
    // lv_title.setScrollbarFadingEnabled(true);
    // super.onActivityCreated(savedInstanceState);
    // }
    public void setData(String[] data) {
        LogUtil.e(TAG, "先设置了数据data=====");
        this.data = data;
        Editor leftmenu = fileNameAli.edit();
        if (data != null && data.length > 0) {
            leftmenu.putString(SourceConstant.LEFT_MENU_LIST_JSON, getSpit(data));
            LogUtil.e(typeData, "左侧一级分类：" + getSpit(data));
        } else {
            leftmenu.putString(SourceConstant.LEFT_MENU_LIST_JSON, "");
            data = null;
        }
        leftmenu.commit();

        adapter = new LeftMenuAdapter(getActivity(), data);
        lv_title.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // @Override
    // public void onListItemClick(ListView l, View v, int position, long id) {
    // // 做操作
    // // 1.将position通过Activity传给ContentFragment
    // // 2.用接口
    // listener.getPostion(position);// 拿到值
    // // ((View)
    // TextView tv = (TextView) l.getChildAt(position).findViewById(
    // R.id.tv_name);
    // tv.setBackgroundColor(R.color.red);
    // adapter.notifyDataSetChanged();
    // super.onListItemClick(l, v, position, id);
    // }
    @Override
    public void onResume() {
        super.onResume();
//		MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
//		MobclickAgent.onPause(getActivity());
    }

    public String getSpit(String[] data) {
        String str = data[0];
        for (int i = 1; i < data.length; i++) {
            str += "," + data[i];
        }
        return str;
    }
}
