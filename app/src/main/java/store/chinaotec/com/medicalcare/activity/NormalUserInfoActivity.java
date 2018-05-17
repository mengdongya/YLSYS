package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.DoctUserInfo;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.javabean.NormalUserInfo;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.TimeUtil;
import store.chinaotec.com.medicalcare.view.GlideCircleTransform;

/**
 * 普通用户个人信息展示页面
 */
public class NormalUserInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.user_logo)
    ImageView userLogo;
    @Bind(R.id.tv_nick_name)
    TextView tvNickName;
    @Bind(R.id.user_ID)
    TextView userID;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_sex)
    TextView userSex;
    @Bind(R.id.user_worn_date)
    TextView userWornDate;
    @Bind(R.id.user_age)
    TextView userAge;
    @Bind(R.id.user_gender)
    TextView userGender;
    @Bind(R.id.user_zhiye)
    TextView userZhiye;
    @Bind(R.id.chang_zhudi)
    TextView changZhudi;
    @Bind(R.id.user_height)
    TextView userHeight;
    @Bind(R.id.user_weight)
    TextView userWeight;
    @Bind(R.id.user_contact_info)
    TextView userContactInfo;
    @Bind(R.id.user_ID_card)
    TextView userIDCard;
    @Bind(R.id.user_shebao)
    TextView userShebao;
    @Bind(R.id.linear_user_logo)
    LinearLayout linearUserLogo;
    @Bind(R.id.linear_user_bind_phone)
    LinearLayout linearUserBindPhone;
    @Bind(R.id.user_bind_phone)
    TextView userBindPhone;
    ArrayList<ImageItem> images = null;
    private AlertDialog alertDialog;
    private final int USER_BIND = 100;
    private final int IMAGE_PICKER = 101;
    private String userSid;
    private NormalUserInfo.DataBean data;
    private SharedPreferences sharedPreferences;
    private DatePickerDialog dialog;
    private HashMap<String, String> mParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initListener();
        initData();
    }

    private void initListener() {
        userSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        mParam = new HashMap<>();
        linearUserLogo.setOnClickListener(this);
        linearUserBindPhone.setOnClickListener(this);
        tvNickName.setOnClickListener(this);
        userName.setOnClickListener(this);
        userSex.setOnClickListener(this);
        userWornDate.setOnClickListener(this);
        userAge.setOnClickListener(this);
        userGender.setOnClickListener(this);
        userZhiye.setOnClickListener(this);
        changZhudi.setOnClickListener(this);
        userHeight.setOnClickListener(this);
        userWeight.setOnClickListener(this);
        userContactInfo.setOnClickListener(this);
        userIDCard.setOnClickListener(this);
        userShebao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.linear_user_logo:
                showUserDialog();
                break;
            case R.id.linear_user_bind_phone:
                startActivityForResult(new Intent(this, BindPhoneActivity.class), USER_BIND);
                break;
            case R.id.tv_nick_name:
                intent.putExtra("title","用户名");
                break;
            case R.id.user_name:
                intent.putExtra("title","姓名");
                break;
            case R.id.user_sex:
                intent.putExtra("title","性别");
                break;
            case R.id.user_worn_date:
                changeBirthdayRequest();
                break;
            case R.id.user_age:
                intent.putExtra("title","年龄");
                break;
            case R.id.user_gender:
                intent.putExtra("title","民族");
                break;
            case R.id.user_zhiye:
                intent.putExtra("title","职业");
                break;
            case R.id.chang_zhudi:
                intent.putExtra("title","常住地");
                break;
            case R.id.user_height:
                intent.putExtra("title","身高");
                break;
            case R.id.user_weight:
                intent.putExtra("title","体重");
                break;
            case R.id.user_contact_info:
//                intent.putExtra("title","联系方式");
                startActivityForResult(new Intent(this, ContactChoseActivity.class), 10);
                break;
            case R.id.user_ID_card:
                intent.putExtra("title","身份证");
                break;
            case R.id.user_shebao:
                intent.putExtra("title","社保卡");
                break;
        }

        if (v.getId() != R.id.linear_user_logo && v.getId() != R.id.linear_user_bind_phone && v.getId() != R.id.user_worn_date
                && v.getId() != R.id.user_contact_info){
            intent.setClass(this,UpdatePatientInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("patientInfo",data);
            intent.putExtras(bundle);
            startActivityForResult(intent,ResourseSum.DATAREQUEST);
        }
    }

    private void initData() {
        OkHttpUtils.post().url(MyUrl.USER_INFO).addParams("sid",userSid).build().execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                NormalUserInfo patientUserInfo = JSONObject.parseObject(response, NormalUserInfo.class);
                if ("0".equals(patientUserInfo.getResponseCode())){
                    data = patientUserInfo.getData();
                    setData();
                }
            }
            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setData() {
        String imgPath = data.getImgPath();
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(this).load(imgPath).transform(new GlideCircleTransform(NormalUserInfoActivity.this)).into(userLogo);
            SpUtill.putString(this,ResourseSum.Medica_SP, "logo",imgPath);
        }
        //绑定手机号
        userBindPhone.setText(data.getTelephone());
        //用户名
        tvNickName.setText(data.getNickName());
        //用户姓名
        userName.setText(data.getName());
        //用户性别
        int sex = data.getSex();
        if (sex == 0) { //男
            userSex.setText("男");
            //保存用户的性别信息
            sharedPreferences.edit().putInt("sex", sex).apply();
        } else if (sex == 1) {
            userSex.setText("女");
            //保存用户的性别信息
            sharedPreferences.edit().putInt("sex", sex).apply();
        }
        //用户id
        userID.setText(data.getIdentityId());
        //出生日期
        userWornDate.setText(data.getBirthday());
        //年龄
        String age = data.getAge();
        userAge.setText(age);
        //保存年龄信息
        sharedPreferences.edit().putString("age", age).apply();
        //民族
        userGender.setText(data.getNation());
        //职业
        userZhiye.setText(data.getOccupation());
        //常住地
        changZhudi.setText(data.getDomicile());
        //身高
        userHeight.setText(data.getHeight());
        //体重
        userWeight.setText(data.getWeight());
        //联系方式
        userContactInfo.setText(data.getContactInfo());
        //身份证号
        userIDCard.setText(data.getIdentityCard());
        //社保卡
        userShebao.setText(data.getSocialInsuranceCard());
    }

    private void showUserDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_change_logo, null);
        TextView takePhoto = (TextView) inflate.findViewById(R.id.take_photo);
        TextView choseGalary = (TextView) inflate.findViewById(R.id.chose_galary);
        TextView canle = (TextView) inflate.findViewById(R.id.canle);
        //打开照相机拍照上传
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NormalUserInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });
        //从相册选择照片上传
        choseGalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(NormalUserInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(camera, IMAGE_PICKER);
            }
        });
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(inflate);
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    //返回的照片Uri
                    Uri uri = Uri.fromFile(new File(images.get(0).path));
                    try {
                        Bitmap bitmap = null;
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        upLoadLogo(bitmap);
                        //显示用户logo
                        userLogo.setImageBitmap(bitmap);
                        alertDialog.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else if(requestCode == ResourseSum.DATAREQUEST && resultCode == RESULT_OK){
            initData();
        }else if (requestCode == 10 && resultCode == RESULT_OK){
            String contact = data.getStringExtra("connect");
            int connectType = data.getIntExtra("type", 0);
            updateContact(contact,connectType);
        }else if(requestCode == USER_BIND && resultCode == RESULT_OK){
            //显示用户的电话号码
            String changePhone = data.getStringExtra("changePhone");
            userBindPhone.setText(changePhone);
        }
    }

    private void updateContact(String contact, int connectType) {
        mParam.clear();
        String url = MyUrl.UPDATE_USER_INFO;
        mParam.put("sid",userSid);
        mParam.put("contactType", connectType+"");
        mParam.put("contactInfo", contact);

        OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                if (result.responseCode.equals("0")) {
                    initData();
                } else {
                    ToastUtil.MyToast(NormalUserInfoActivity.this, result.msg);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.MyToast(NormalUserInfoActivity.this, "请检查网络");
            }

        });
    }

    private void upLoadLogo(Bitmap bitmap) {
        //获取Base64字符串
        String base64ByBitmap = BaseUtill.getBase64ByBitmap(bitmap);
        NetWorkUtill.upLoadUserLogo(userSid, base64ByBitmap, new NetWorkUtill.UpLoadListener() {
            @Override
            public void getLogo(String logo) {
                sharedPreferences.edit().putString("logo", logo).apply();
                //logo变更的开关保存
                sharedPreferences.edit().putBoolean("changeLogo", true).apply();
            }
        });
    }


    private void changeBirthdayRequest() {
        final Calendar calendar = Calendar.getInstance();
        if (!StringUtils.isEmpty(data.getBirthday())) {
            calendar.setTimeInMillis(TimeUtil.getDateFromString(data.getBirthday()).getTime());
        } else {
            calendar.setTimeInMillis(System.currentTimeMillis());
        }
        dialog = new DatePickerDialog(NormalUserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == monthOfYear && calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                    return;
                }
                if (calendar.getTime().getTime() > System.currentTimeMillis()) {
                    ToastUtil.MyToast(NormalUserInfoActivity.this, "还未出生");
                    return;
                }
                calendar.set(year, monthOfYear, dayOfMonth);
                mParam.clear();
                String url = MyUrl.UPDATE_USER_INFO;
                mParam.put("sid",userSid);
                mParam.put("birthday", TimeUtil.getDate(calendar.getTimeInMillis()));

                OkHttpUtils.post().url(url).params(mParam).build().execute(new StringCallback() {

                    @Override
                    public void onResponse(String response, int id) {
                        ResultBean result = JSONObject.parseObject(response, ResultBean.class);
                        if (result.responseCode.equals("0")) {
                            initData();
                        } else {
                            ToastUtil.MyToast(NormalUserInfoActivity.this, result.msg);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.MyToast(NormalUserInfoActivity.this, "请检查网络");
                    }

                });

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

}
