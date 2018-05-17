package store.chinaotec.com.medicalcare.shopmarket.logic.address.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.RegExp;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.VerifyUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.AddressInfo;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.CityModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.DistrictModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.ProvinceModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.weight.OptionsPopupWindow;
import store.chinaotec.com.medicalcare.shopmarket.logic.user.model.User;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.UserKeeper;

/**
 * 新建地址
 */
public class AddressInfoActivity extends BaseAoActivity {

    OptionsPopupWindow pwOptions;
    private TextView mTvTitle;
    private ImageView mBtnBack;
    private Button mBtnSave;
    private EditText mEdName;
    private EditText mEdPhone;
    private TextView tv_city;
    private EditText mEdAddress;
    private Button mBtnCheckDefault;
    // true : 添加地址
    // false : 修改地址
    private boolean mFlag = true;
    private AddressInfo addressInfo;
    private int position;
    /**
     * 是否设置为默认地址
     */
    private boolean addressIsDefault;
    private SharedPreferences fileNameAli;
    private String sId;
    private ArrayList<DistrictModel> options1Items = new ArrayList<DistrictModel>();
    private ArrayList<ArrayList<DistrictModel>> options2Items = new ArrayList<ArrayList<DistrictModel>>();
    private ArrayList<ArrayList<ArrayList<DistrictModel>>> options3Items = new ArrayList<ArrayList<ArrayList<DistrictModel>>>();
    private JsonParser jsonParser;
    private JsonElement json;
    private List<ProvinceModel> cityList;
    private List<CityModel> mCityModel;
    private DistrictModel districtModel;
    private String TAG = "addressChange";
    private String data;

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.btn_save:

                // 保存新添加的地址
                verify();
                // finish();
                break;

            case R.id.tv_city:
                pwOptions.showAtLocation(tv_city, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.btn_adddefault_address:
                /** 改变图片 */
                if (addressIsDefault) {
                    mBtnCheckDefault.setCompoundDrawablesWithIntrinsicBounds(null,null,
                            getResources().getDrawable(R.drawable.btn_default_ok_up),null);
                } else {
                    mBtnCheckDefault.setCompoundDrawablesWithIntrinsicBounds(null,null,
                            getResources().getDrawable(R.drawable.btn_default_ok_down), null);
                }
                addressIsDefault = !addressIsDefault;
                break;
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_address_info);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        mBtnSave = (Button) findViewById(R.id.btn_save);

        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdPhone = (EditText) findViewById(R.id.ed_phone);
        tv_city = (TextView) findViewById(R.id.tv_city);
        mEdAddress = (EditText) findViewById(R.id.ed_address);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        mBtnCheckDefault = (Button) findViewById(R.id.btn_adddefault_address);
        // 选项选择器
        pwOptions = new OptionsPopupWindow(this);

        sId =  SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");

    }

    @Override
    protected void initListener() {
        if ("".equals(sId)) {
            this.finish();
//            startActivity(new Intent(this, LoginActivity.class));
        }
        mBtnBack.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        mBtnCheckDefault.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        addressInfo = new AddressInfo();
        Bundle bundle = getIntent().getExtras();
        mFlag = bundle.getBoolean("flag");
        if (mFlag) {
            // 添加地址
            mFlag = true;

        } else {
            // 修改地址
            mFlag = false;
            addressInfo = bundle.getParcelable("addressInfo");
            this.position = bundle.getInt("position");
            mEdName.setText(addressInfo.name);
            mEdPhone.setText(addressInfo.phone);
            tv_city.setText(addressInfo.provinceName + " " + addressInfo.cityName + " "
                    + addressInfo.districtName);
            // mTvCity.setText(addressInfo.provinceName + addressInfo.cityName);
            LogUtil.e(TAG, addressInfo.toString());
            addressIsDefault = "y".equals(addressInfo.isDefault);
            if (addressIsDefault) {
                mBtnCheckDefault.setCompoundDrawablesWithIntrinsicBounds(null,null,
                        getResources().getDrawable(R.drawable.btn_default_ok_down), null);
            } else {
                mBtnCheckDefault.setCompoundDrawablesWithIntrinsicBounds(null,null,
                        getResources().getDrawable(R.drawable.btn_default_ok_up),null);
            }
            mEdAddress.setText(addressInfo.addressInfo);

        }

        mTvTitle.setText(mFlag ? "新建地址" : "修改地址");
        data = fileNameAli.getString(SourceConstant.ADDRESS_LIST_JSON, "");
        if ("".equals(data)) {
            LogUtil.e(TAG, "data是空的===================");
            // 请求网络
            getData();
        } else {
            LogUtil.e(TAG, "data有数据 没有请求网络===================");
            setProvince(data);
        }

    }

    private void getData() {
        // 请求省级列表
        AorunApi.getAreaList("", mDataCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void verify() {
        String name = mEdName.getText().toString().trim();
        String phone = mEdPhone.getText().toString().trim();
        String city = tv_city.getText().toString().trim();
        String address = mEdAddress.getText().toString().trim();

        if (name.equals("")) {
            ToastUtil.MyToast(this, "请填写收货人");
            return;
        }
        addressInfo.name = name;
        if (phone.equals("")) {
            ToastUtil.MyToast(this, "请填写联系电话");
            return;
        }
        if (!VerifyUtil.isVerify(this, phone, RegExp.PHONE_NUM,
                R.string.register_hint_username,
                R.string.register_tip_username_error)) {
            return;
        }

        addressInfo.phone = phone;
        if (city.equals("")) {
            ToastUtil.MyToast(this, "请选择城市");
            return;
        }
        if (address.equals("")) {
            ToastUtil.MyToast(this, "请填写详细地址");
            return;
        }
        addressInfo.addressInfo = address;
        submit(name, phone, addressInfo.provinceId, addressInfo.cityId,
                addressInfo.districtId, address, addressIsDefault ? "y" : "n");
    }

    private void submit(String name, String phone, String provinceId,
                        String cityId, String districtId, String address, String isDefault) {
        if (mFlag) {
            // 添加地址
            AorunApi.getAddressAdd(sId, name, phone, provinceId, cityId, districtId, address, isDefault, mDataCallback);

        } else {
            // 修改地址
            AorunApi.getAddressChange(sId, addressInfo.addressId, name, phone, provinceId, cityId, districtId, address, isDefault, mDataCallback);
        }

    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.ADDRESS_ADD:
                setResult(RESULT_OK);
                finish();
                break;
            case RequestUrl.ADDRESS_CHANGE:

                Intent intent = new Intent();
                intent.putExtra("position", this.position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("addressInfo", addressInfo);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case RequestUrl.ADDRESS_AREA_LIST:
                // 保存数据
                LogUtil.e(TAG, "保存数据===================");
                Editor editor = fileNameAli.edit();
                editor.putString(SourceConstant.ADDRESS_LIST_JSON, data);
                editor.commit();
                LogUtil.e(TAG, "保存 了之后的数据===" + fileNameAli.getString(SourceConstant.ADDRESS_LIST_JSON, ""));
                setProvince(data);
                break;
        }
    }

    /**
     * 设置省列表
     */
    private void setProvince(String obj) {
        LogUtil.e(TAG, "=========进了设置数据的方法");
        jsonParser = new JsonParser();
        json = jsonParser.parse(obj);
        JsonArray jsonp = json.getAsJsonArray();
        cityList = new ArrayList<ProvinceModel>();
        for (int i = 0; i < jsonp.size(); i++) {// 遍历JSONArray
            JsonElement oj = jsonp.get(i);
            String name = oj.getAsJsonObject().get("name").getAsString();
            String code = oj.getAsJsonObject().get("code").getAsString();
            mCityModel = new ArrayList<CityModel>();
            JsonArray asJsonArray = oj.getAsJsonObject().get("cityList")
                    .getAsJsonArray();
            for (int j = 0; j < asJsonArray.size(); j++) {
                JsonObject asJson = asJsonArray.get(j).getAsJsonObject();
                String code1 = asJson.getAsJsonObject().get("code")
                        .getAsString();
                String name1 = asJson.getAsJsonObject().get("name")
                        .getAsString();
                JsonArray asJsonArray1 = asJson.getAsJsonObject()
                        .get("areaList").getAsJsonArray();
                List<DistrictModel> cityArray = new ArrayList<DistrictModel>();
                for (int k = 0; k < asJsonArray1.size(); k++) {
                    JsonObject asJsonA = asJsonArray1.get(k).getAsJsonObject();
                    String code2 = asJsonA.get("code").getAsString();
                    String name2 = asJsonA.get("name").getAsString();
                    districtModel = new DistrictModel(name2, code2);
                    cityArray.add(districtModel);
                }
                mCityModel.add(new CityModel(name1, code1, cityArray));
            }
            cityList.add(new ProvinceModel(name, code, mCityModel));
        }
        LogUtil.e(TAG, "=========进了设置数据的方法cityList：" + cityList.size());
        // 设置数据
        initProvinceDatas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CODE_ADDRESS_PROVINCE:
                    Bundle bundle = data.getExtras();

                    addressInfo.provinceId = bundle.getString("province_id");
                    addressInfo.provinceName = bundle.getString("province_name");
                    addressInfo.cityId = bundle.getString("city_id");
                    addressInfo.cityName = bundle.getString("city_name");
                    addressInfo.districtId = bundle.getString("district_id");
                    addressInfo.districtName = bundle.getString("district_name");
                    tv_city.setText(addressInfo.provinceName + " "  + addressInfo.cityName
                            + " " + addressInfo.districtName);
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 解析省市区的数据
     */

    protected void initProvinceDatas() {
        LogUtil.e(TAG, "进了解析地址的方法===========");
        // 选项1 循环添加省集合
        for (int i = 0; i < cityList.size(); i++) {
            options1Items.add(new DistrictModel(cityList.get(i).getName(),
                    cityList.get(i).getCode()));
        }

        for (int i = 0; i < cityList.size(); i++) {
            ArrayList<DistrictModel> options2Items_01 = new ArrayList<DistrictModel>();
            for (int j = 0; j < cityList.get(i).getCityList().size(); j++) {
                options2Items_01.add(new DistrictModel(cityList.get(i)
                        .getCityList().get(j).getName(), cityList.get(i)
                        .getCityList().get(j).getCode()));
            }
            options2Items.add(options2Items_01);
        }

        for (int i = 0; i < cityList.size(); i++) {
            ArrayList<ArrayList<DistrictModel>> options3Items_01 = new ArrayList<ArrayList<DistrictModel>>();
            for (int j = 0; j < cityList.get(i).getCityList().size(); j++) {
                ArrayList<DistrictModel> options3Items_01_01 = new ArrayList<DistrictModel>();
                for (int k = 0; k < cityList.get(i).getCityList().get(j)
                        .getDistrictList().size(); k++) {
                    options3Items_01_01.add(new DistrictModel(cityList.get(i)
                            .getCityList().get(j).getDistrictList().get(k)
                            .getName(), cityList.get(i).getCityList().get(j)
                            .getDistrictList().get(k).getCode()));
                }
                options3Items_01.add(options3Items_01_01);
            }
            options3Items.add(options3Items_01);
        }

        LogUtil.e("options3Items=====", options3Items.toString());


        // 三级联动效果
        pwOptions.setPicker(options1Items, options2Items, options3Items, true);
        // 设置选择的三级单位
        pwOptions.setLabels("", "", "");
        // 设置默认选中的三级项目
        pwOptions.setSelectOptions(0, 0, 0);
        // 监听确定选择按钮
        pwOptions
                .setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2,
                                                int options3) {
                        // 返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getName()+" "
                                + options2Items.get(options1).get(option2).getName() +" "
                                + options3Items.get(options1).get(option2).get(options3).getName();

                        addressInfo.provinceId = options1Items.get(options1)
                                .getCode();
                        addressInfo.cityId = options2Items.get(options1)
                                .get(option2).getCode();
                        addressInfo.districtId = options3Items.get(options1)
                                .get(option2).get(options3).getCode();
                        tv_city.setText(tx);
                    }
                });
    }
}
