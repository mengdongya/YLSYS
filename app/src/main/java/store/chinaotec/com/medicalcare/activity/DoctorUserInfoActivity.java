package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.javabean.DoctUserInfo;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.view.GlideCircleTransform;

/**
 * 医生用户登陆后详细信息展示页面
 */
public class DoctorUserInfoActivity extends BaseActivity implements View.OnClickListener {
    private final int DCOTOR_CODE = 200;
    private final int IMAGE_PICKER = 101;
    @Bind(R.id.doct_logo)
    ImageView doctLogo;
    @Bind(R.id.doct_nick_name)
    TextView doctNickName;
    @Bind(R.id.doct_ID)
    TextView doctID;
    @Bind(R.id.doct_bind_phone)
    TextView doctBindPhone;
    @Bind(R.id.doct_name)
    TextView doctName;
    @Bind(R.id.doct_ID_card)
    TextView doctIDCard;
    @Bind(R.id.zhuanye_zhicheng)
    TextView zhuanyeZhicheng;
    @Bind(R.id.fuwu_jigou)
    TextView fuwuJigou;
    @Bind(R.id.congye_expere)
    TextView congyeExpere;
    @Bind(R.id.zhuanke_techang)
    TextView zhuankeTechang;
    @Bind(R.id.shoukuan_acunt)
    TextView shoukuanAcunt;
    @Bind(R.id.linear_doct_logo)
    RelativeLayout linearDoctLogo;
    @Bind(R.id.linear_doct_bind_phone)
    RelativeLayout linearDoctBindPhone;
    ArrayList<ImageItem> images = null;
    private SharedPreferences sharedPreferences;
    private String userSid;
    private AlertDialog alertDialog;
    private String logo;
    private DoctUserInfo.DataBean userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_user_info);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        userSid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        //获取登录成功后后台返回的sid
        sharedPreferences = getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
        //用户的sid值
//        userSid = sharedPreferences.getString("sid", "");
        //获取本地保存的图标的加载路径
//        logo = sharedPreferences.getString("logo", "");
    }

    private void initListener() {
        linearDoctLogo.setOnClickListener(this);
        linearDoctBindPhone.setOnClickListener(this);
        doctNickName.setOnClickListener(this);
        doctName.setOnClickListener(this);
        doctIDCard.setOnClickListener(this);
        zhuanyeZhicheng.setOnClickListener(this);
        fuwuJigou.setOnClickListener(this);
        congyeExpere.setOnClickListener(this);
        zhuankeTechang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //修改医生用户logo
            case R.id.linear_doct_logo:
                showTakePhoto();
                break;
            //修改医生用户绑定手机号
            case R.id.linear_doct_bind_phone:
                startActivityForResult(new Intent(this, BindPhoneActivity.class), DCOTOR_CODE);
                break;
            case R.id.doct_nick_name:
                intent.putExtra("title","用户名");
                break;
            case R.id.doct_name:
                intent.putExtra("title","姓名");
                break;
            case R.id.doct_ID_card:
                intent.putExtra("title","身份证");
                break;
            case R.id.zhuanye_zhicheng:
                intent.putExtra("title","专业职称");
                break;
            case R.id.fuwu_jigou:
                intent.putExtra("title","服务机构");
                break;
            case R.id.congye_expere:
                intent.putExtra("title","从业经历");
                break;
            case R.id.zhuanke_techang:
                intent.putExtra("title","专科特长");
                break;
        }
        if (v.getId() != R.id.linear_doct_logo && v.getId() != R.id.linear_doct_bind_phone){
            intent.setClass(this,UpDateUserInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userInfo",userInfo);
            intent.putExtras(bundle);
            startActivityForResult(intent,ResourseSum.DATAREQUEST);
        }
    }

    private void initData() {

        OkHttpUtils.post().url(MyUrl.DOCTOR_INFO).addParams("sid",userSid).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                DoctUserInfo doctUserInfo = JSONObject.parseObject(response, DoctUserInfo.class);
                if ("0".equals(doctUserInfo.getResponseCode())){
                    userInfo = doctUserInfo.getData();
                    setData();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private void setData() {
        String imgPath = userInfo.getImgPath();
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(MyApp.getContext()).load(imgPath).transform(new GlideCircleTransform(DoctorUserInfoActivity.this)).into(doctLogo);
            sharedPreferences.edit().putString("logo", imgPath).apply();
        }

        //用户名
        doctNickName.setText(userInfo.getNickName());
        //id
        doctID.setText(userInfo.getIdentityId());
        //绑定手机号
        doctBindPhone.setText(userInfo.getTelephone());
        //医生姓名
        doctName.setText(userInfo.getDoctorName());
        //身份证
        doctIDCard.setText(userInfo.getIdentityCard());
        //专业职称
        zhuanyeZhicheng.setText(userInfo.getProfessionalTitle());
        //服务机构
        fuwuJigou.setText(userInfo.getFacilitatingAgency());
        //从业经历
        congyeExpere.setText(userInfo.getWorkingExperience());
        //专科特长
        zhuankeTechang.setText(userInfo.getJuniorStrong());
        //收款账户
        shoukuanAcunt.setText(userInfo.getBankInfo());
    }

    private void showTakePhoto() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_change_logo, null);
        TextView takePhoto = (TextView) inflate.findViewById(R.id.take_photo);
        TextView choseGalary = (TextView) inflate.findViewById(R.id.chose_galary);
        TextView canle = (TextView) inflate.findViewById(R.id.canle);
        //打开照相机拍照上传
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorUserInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });
        //从相册选择照片上传
        choseGalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(DoctorUserInfoActivity.this, ImageGridActivity.class);
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
    }

    //拍照  相册中选中照片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        doctLogo.setImageBitmap(bitmap);
                        alertDialog.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if(requestCode == ResourseSum.DATAREQUEST && resultCode == RESULT_OK){
            initData();
        }else if(requestCode == DCOTOR_CODE && resultCode == RESULT_OK){
            //显示用户的电话号码
            String changePhone = data.getStringExtra("changePhone");
            doctBindPhone.setText(changePhone);
        }

    }

    /**
     * @param bitmap 从相册选中的照片,或者用相机拍照得到的照片
     *               上传图片到服务器
     */
    private void upLoadLogo(Bitmap bitmap) {
        //获取Base64字符串
        String base64ByBitmap = BaseUtill.getBase64ByBitmap(bitmap);
        NetWorkUtill.upLoadUserLogo(userSid, base64ByBitmap, new NetWorkUtill.UpLoadListener() {
            @Override
            public void getLogo(String logo) {
                sharedPreferences.edit().putString("logo", logo).apply();
                //用户logo变更的标志tag
                sharedPreferences.edit().putBoolean("changeLogo", true).apply();
            }
        });
    }
}
