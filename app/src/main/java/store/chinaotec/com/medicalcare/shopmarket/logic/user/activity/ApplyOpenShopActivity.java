package store.chinaotec.com.medicalcare.shopmarket.logic.user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.common.base.activity.BaseAoActivity;
import store.chinaotec.com.medicalcare.shopmarket.common.base.model.RequestVo;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.SourceConstant;
import store.chinaotec.com.medicalcare.shopmarket.common.http.AorunApi;
import store.chinaotec.com.medicalcare.shopmarket.common.request.model.Result;
import store.chinaotec.com.medicalcare.shopmarket.common.request.requestUrl.RequestUrl;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.BitmapUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.FileUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.GetPathFromUri4kitkat;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.MyImageLoader;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.CityModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.DistrictModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.model.ProvinceModel;
import store.chinaotec.com.medicalcare.shopmarket.logic.address.weight.OptionsPopupWindow;
import store.chinaotec.com.medicalcare.utill.FileUtil;

/**
 * Created by wjc on 2016/8/19 0019.
 */
public class ApplyOpenShopActivity extends BaseAoActivity {
    private static final int TAKE_PICTURE = 1;
    private static final int PHOTO_PICTURE = 0;
    OptionsPopupWindow pwOptions;
    private TextView mTvTitle;
    private EditText etName;
    private EditText etPhone;
    private TextView etCity;
    private EditText etAddress;
    private Button btnsubmit;
    private ImageView mBtnBack;
    private String storeName;
    private String storePhone;
    private String storeCity;
    private String storeAddress;
    private SharedPreferences fileNameAli;
    private ArrayList<DistrictModel> options1Items = new ArrayList<DistrictModel>();
    private ArrayList<ArrayList<DistrictModel>> options2Items = new ArrayList<ArrayList<DistrictModel>>();
    private ArrayList<ArrayList<ArrayList<DistrictModel>>> options3Items = new ArrayList<ArrayList<ArrayList<DistrictModel>>>();
    private JsonParser jsonParser;
    private JsonElement json;
    private List<ProvinceModel> cityList;
    private List<CityModel> mCityModel;
    private DistrictModel districtModel;
    private String data;
    private String TAG = "ApplyOpenShopActivity====";
    private ImageView ivBusinessLicense;
    private LinearLayout ll_popup;
    private PopupWindow pop;
    private File imgUrl;
    private ImageView ivIdCard;
    private File imgUrlIdCard;
    // 保存全尺寸照片
    private String mCurrentPhotoPath;
    private HashMap<String, String> mParams;
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 100) {
            } else if (requestCode == 101) {
                callCamera();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            if (requestCode == 100) {
            } else if (requestCode == 101) {
            }
        }
    };

    @Override
    protected void onClickEvent(View paramView) {
        switch (paramView.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.btn_apply_open_shop_submit:
                verifyInfo();
                break;
            case R.id.apply_open_shop_city:
                pwOptions.showAtLocation(etCity, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.apply_open_shop_id_card:
                SourceConstant.IS_XXX = SourceConstant.ONE;
                showPopwindow();
                break;
            case R.id.apply_open_shop_business_license:
                SourceConstant.IS_XXX = SourceConstant.THREE;
                showPopwindow();
                break;
            case R.id.item_popupwindows_cancel:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_camera:
                //跳转至相机
                checkCamera();
                break;
            case R.id.item_popupwindows_Photo:
                // 跳转至相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PHOTO_PICTURE);
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.parent:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
        }
    }

    /**
     * 检查是否有相机权限
     */
    private void checkCamera() {

        AndPermission.with(this)
                .requestCode(101)
                .permission(Manifest.permission.CAMERA)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    /**
     * 调用相机
     */
    private void callCamera() {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);
        File appDir = new File(Environment.getExternalStorageDirectory(),"/zhyl/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String fileName = timeStamp + ".jpg";
        File outputImage = new File(appDir, fileName);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCurrentPhotoPath = outputImage.getAbsolutePath();
        Uri imgUri = FileUtil.getUriForFile(this,outputImage);
        // 意图 相机
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        // 如果有相机
        if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
        pop.dismiss();
        ll_popup.clearAnimation();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_shop_market_apply_open_shop);
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.title_textview);
        mBtnBack = (ImageView) findViewById(R.id.title_btn_left);
        etName = (EditText) findViewById(R.id.et_apply_open_shop_name);
        etPhone = (EditText) findViewById(R.id.apply_open_shop_phone);
        etCity = (TextView) findViewById(R.id.apply_open_shop_city);
        etAddress = (EditText) findViewById(R.id.apply_open_shop_address);
        ivBusinessLicense = (ImageView) findViewById(R.id.apply_open_shop_business_license);
        ivIdCard = (ImageView) findViewById(R.id.apply_open_shop_id_card);
        btnsubmit = (Button) findViewById(R.id.btn_apply_open_shop_submit);
        fileNameAli = this.getSharedPreferences(SourceConstant.fileNameAli, 0);
        data = fileNameAli.getString(SourceConstant.ADDRESS_LIST_JSON, "");
        mParams = new HashMap<>();
        // 选项选择器
        pwOptions = new OptionsPopupWindow(this);
    }

    @Override
    protected void initListener() {
        mBtnBack.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        etCity.setOnClickListener(this);
        ivIdCard.setOnClickListener(this);
        ivBusinessLicense.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mTvTitle.setText("申请开店");

        if ("".equals(data)) {
            LogUtil.e(TAG, "data是空的===================");
            // 请求网络
            getData();
        } else {
            LogUtil.e(TAG, "data有数据 没有请求网络===================");
            setProvince(data);
        }
    }

    private void verifyInfo() {
        storeName = etName.getText().toString().trim();
        storePhone = etPhone.getText().toString().trim();
        storeCity = etCity.getText().toString().trim();
        storeAddress = etAddress.getText().toString().trim();

        if ("".equals(storeName)) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_et_name);
            return;
        }
        if ("".equals(storePhone)) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_et_phone);
            return;
        }
        if ("".equals(storeCity)) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_et_area);
            return;
        }
        if ("".equals(storeAddress)) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_et_address);
            return;
        }
        if ("".equals(imgUrlIdCard) || null == imgUrlIdCard) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_id_card);
            return;
        }
        if ("".equals(imgUrl) || null == imgUrl) {
            ToastUtil.MyToast(this, R.string.shop_market_apply_open_shop_business_license);
            return;
        }

        applyOpenStore(storeName, storePhone, storeCity, storeAddress, imgUrl, imgUrlIdCard);

    }

    //申请开店的请求
    private void applyOpenStore(String storeName, String storePhone, String storeCity, String storeAddress, File imgUrl, File imgUrlIdCard) {
        mParams.clear();
        String url = RequestUrl.APP_HOME + RequestUrl.STORE_COMMIT_STORE_APPIY;
        mParams.put("name", storeName);
        mParams.put("phone", storePhone);
        mParams.put("area", storeCity);
        mParams.put("addressDetail", storeAddress);
        mParams.put("storeType", "1");
        OkHttpUtils.post().addFile("identityCardFile", imgUrlIdCard.getName(), imgUrlIdCard).addFile("businessLicenseFile", imgUrl.getName(),
                imgUrl).addFile("foodAndBeverageLicenseFile", "", imgUrlIdCard)
                .url(url).params(mParams).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Result result = JSON.parseObject(response, Result.class);
                if (result.responseCode == 0) {
                    ToastUtil.MyToast(ApplyOpenShopActivity.this, "您已提交申请，请耐心等待审核");
                    finish();
                }
            }
        });
//        AorunApi.getApplyOpenShop(storeName, storePhone, storeCity, storeAddress, imgUrl, imgUrlIdCard,"" ,"1",mDataCallback);
    }

    private void getData() {
        // 请求省级列表
        AorunApi.getAreaList("", mDataCallback);
    }

    /**
     * 弹出弹窗
     */
    private void showPopwindow() {
        View view = getLayoutInflater().inflate(R.layout.item_shop_market_apply_popupwindows,
                null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        view.findViewById(R.id.parent).setOnClickListener(this);
        view.findViewById(R.id.item_popupwindows_camera).setOnClickListener(
                this);
        view.findViewById(R.id.item_popupwindows_Photo)
                .setOnClickListener(this);
        view.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(
                this);
        // 8.设置气泡相对于view的位置
        pop.showAtLocation(ivBusinessLicense, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void processData(String data, RequestVo reqVo) {
        switch (reqVo.requestUrl) {
            case RequestUrl.STORE_COMMIT_STORE_APPIY:
                this.finish();
                ToastUtil.MyToast(this, "您已提交申请，请耐心等待审核");
                break;
            case RequestUrl.ADDRESS_AREA_LIST:
                // 保存数据
                LogUtil.e(TAG, "保存数据===================");
                SharedPreferences.Editor editor = fileNameAli.edit();
                editor.putString(SourceConstant.ADDRESS_LIST_JSON, data);
                editor.commit();
                LogUtil.e(TAG, "保存 了之后的数据===" + fileNameAli.getString(SourceConstant.ADDRESS_LIST_JSON, ""));
                setProvince(data);
                break;
        }
    }

    /**
     * 解析省列表
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

    protected void initProvinceDatas() {
        LogUtil.e(TAG, "进了设置地址的方法===========");
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
                        String tx = options1Items.get(options1).getName()
                                + options2Items.get(options1).get(option2)
                                .getName()
                                + options3Items.get(options1).get(option2)
                                .get(options3).getName();

                        etCity.setText(tx);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_PICTURE:
                    Uri uri = data.getData();
                    String localPath = GetPathFromUri4kitkat.getPath(this, uri);
                    if (SourceConstant.IS_XXX == SourceConstant.ONE) {
                        MyImageLoader.displayImage(localPath, ivIdCard);
                        imgUrlIdCard = new File(getRealPathFromURI(uri));
                    } else {
                        MyImageLoader.displayImage(localPath, ivBusinessLicense);
                        imgUrl = new File(getRealPathFromURI(uri));
                    }
                    SourceConstant.IS_XXX = SourceConstant.ZERO;
                    break;
                case TAKE_PICTURE:
                    Bitmap bitmap = BitmapUtils.getimage(mCurrentPhotoPath);
                    String fileName = String.valueOf(System.currentTimeMillis());
                    FileUtils.saveBitmap(bitmap, fileName);
                    if (SourceConstant.IS_XXX == SourceConstant.ONE) {
                        ivIdCard.setImageBitmap(bitmap);
                        imgUrlIdCard = new File(FileUtils.f.getAbsolutePath());
                    } else {
                        ivBusinessLicense.setImageBitmap(bitmap);
                        imgUrl = new File(FileUtils.f.getAbsolutePath());
                    }
                    SourceConstant.IS_XXX = SourceConstant.ZERO;
                    break;
            }
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private byte[] getBitmap(String localPath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap tBitmap = BitmapFactory.decodeFile(localPath);

        final int width = tBitmap.getWidth();
        final int height = tBitmap.getHeight();
        options.inSampleSize = 1;
        if (height > 1280 || width > 720) {
            final int heightRatio = Math.round((float) height / (float) 720);
            final int widthRatio = Math.round((float) width / (float) 1280);
            options.inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inJustDecodeBounds = false;
        tBitmap.recycle();
        tBitmap = BitmapFactory.decodeFile(localPath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tBitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        tBitmap.recycle();
        return baos.toByteArray();
    }
}
