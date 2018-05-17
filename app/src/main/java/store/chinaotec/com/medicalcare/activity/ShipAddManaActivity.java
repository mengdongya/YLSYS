package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.activity.AddressInfoActivity;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 收货地址管理页面
 */
public class ShipAddManaActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.add_ship_address)
    TextView addShipAddress;
    @Bind(R.id.rv_address_list)
    LRecyclerView recyclerView;
    private HashMap<String, String> mParams;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private int pageIndex = 1;
    private List<AddressInfo> data;
    private String sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_address_manage);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        addShipAddress.setOnClickListener(this);
        mParams = new HashMap<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lRecyclerViewAdapter = new LRecyclerViewAdapter(new AddressRecyclerAdapter());
        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLoadMoreEnabled(false);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getAddressData();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pageIndex++;
                getAddressData();
            }
        });

        getAddressData();
    }

    private void getAddressData() {
        mParams.clear();
        mParams.put("sid", sid);
        mParams.put("sourceCode", "1");
        mParams.put("pageIndex", pageIndex + "");

        OkHttpUtils.post().url(RequestUrl.APP_HOME+RequestUrl.ADDRESS_LIST).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean bean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(bean.responseCode)) {
                    List<AddressInfo> list = JSONArray.parseArray(bean.data, AddressInfo.class);
                    if (pageIndex == 1) {
                        data.clear();
                    }
                    data.addAll(list);
                    lRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                recyclerView.refreshComplete(pageIndex);
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_ship_address:
                startAddressInfo(true, -1);
                break;
        }
    }

    class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(ShipAddManaActivity.this).inflate(R.layout.item_ship_address, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            AddressInfo addressInfo = data.get(position);
            holder.tvAddressName.setText(addressInfo.name);
            if (addressInfo.phone.length() != 0){
                String phone = addressInfo.phone.substring(0, 3) + "****"+ addressInfo.phone.substring(addressInfo.phone.length() - 4,
                        addressInfo.phone.length());
                holder.tvAddressPhone.setText(phone);
            }else {
                holder.tvAddressPhone.setText("");
            }

            holder.tvAddressDetail.setText(addressInfo.provinceName +" "+addressInfo.cityName+" "+ addressInfo.districtName +" "+
                    addressInfo.addressInfo);
            holder.ivAddressDefault.setImageResource("y".equals(addressInfo.isDefault) ? R.drawable.default_selected : R.drawable.default_normal);
            holder.tvAddressEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startAddressInfo(false,position);
                }
            });
            holder.tvAddressDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAddress(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_address_name)
            TextView tvAddressName;
            @Bind(R.id.tv_address_phone)
            TextView tvAddressPhone;
            @Bind(R.id.tv_address_detail)
            TextView tvAddressDetail;
            @Bind(R.id.iv_address_default)
            ImageView ivAddressDefault;
            @Bind(R.id.tv_address_edit)
            TextView tvAddressEdit;
            @Bind(R.id.tv_address_delete)
            TextView tvAddressDelete;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    private void deleteAddress(int position) {
        mParams.clear();
        mParams.put("sid",sid);
        mParams.put("addressId",data.get(position).addressId);
        mParams.put("sourceCode","1");
        OkHttpUtils.post().url(RequestUrl.APP_HOME+RequestUrl.ADDRESS_DELETE).params(mParams).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                ResultBean bean = JSONObject.parseObject(response, ResultBean.class);
                if ("0".equals(bean.responseCode)){
                    getAddressData();
                }
            }
        });
    }

    /**
     * @param flag true: 添加地址<br>
     *             false : 修改地址
     */
    private void startAddressInfo(boolean flag, int position) {
        Intent intent = new Intent(this, AddressInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("flag", flag);
        if (flag) {
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_ADD);
        } else {
            AddressInfo addressInfo = data.get(position);
            bundle.putParcelable("addressInfo", addressInfo);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUEST_CODE_ADDRESS_CHANGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.REQUEST_CODE_ADDRESS_ADD:
                pageIndex = 1;
                getAddressData();
                break;
            case Constant.REQUEST_CODE_ADDRESS_CHANGE:
                pageIndex = 1;
                getAddressData();
                break;
        }
    }
}
