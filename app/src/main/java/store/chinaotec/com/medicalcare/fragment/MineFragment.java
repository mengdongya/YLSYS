package store.chinaotec.com.medicalcare.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.DoctorUserInfoActivity;
import store.chinaotec.com.medicalcare.activity.HomeActivity;
import store.chinaotec.com.medicalcare.activity.LoginActivity;
import store.chinaotec.com.medicalcare.activity.MedicalBookDetailActivity;
import store.chinaotec.com.medicalcare.activity.MineCollectionActivity;
import store.chinaotec.com.medicalcare.activity.MineSettingActivity;
import store.chinaotec.com.medicalcare.activity.NormalUserInfoActivity;
import store.chinaotec.com.medicalcare.activity.ShipAddManaActivity;
import store.chinaotec.com.medicalcare.activity.UserRegistActivity;
import store.chinaotec.com.medicalcare.javabean.NickPhone;
import store.chinaotec.com.medicalcare.shopmarket.logic.orders.activity.AllOrderActivity;
import store.chinaotec.com.medicalcare.utill.EventTag;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.RegexUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.ToastUtil;
import store.chinaotec.com.medicalcare.view.GlideCircleTransform;
import store.chinaotec.com.medicalcare.view.SetItemView;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_phone)
    TextView userPhone;
    @Bind(R.id.relative_login_suceed)
    RelativeLayout relativeLoginSuceed;
    @Bind(R.id.click_login)
    TextView clickLogin;
    @Bind(R.id.user_logo)
    ImageView userLogo;
    @Bind(R.id.mine_collection)
    SetItemView mineCollection;
    @Bind(R.id.mine_order)
    SetItemView mineOrder;
    @Bind(R.id.mine_my_device)
    SetItemView mineMyDevice;
    @Bind(R.id.mine_ship_address)
    SetItemView mineShipAddress;
    @Bind(R.id.mine_use_guide)
    SetItemView mineGuide;
    @Bind(R.id.mine_setting)
    SetItemView mineSetting;
    @Bind(R.id.regist)
    TextView regist;
    private Context mContext = null;
    private HomeActivity activity;
    private boolean login;
    private String sid;
    private SharedPreferences sharedPreferences;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment instance() {
        Bundle bundle = new Bundle();
        MineFragment mineFragment = new MineFragment();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initListener();
        return view;
    }

    private void initListener() {
        mineCollection.setOnClickListener(this);
        mineOrder.setOnClickListener(this);
        mineMyDevice.setOnClickListener(this);
        mineShipAddress.setOnClickListener(this);
        mineGuide.setOnClickListener(this);
        mineSetting.setOnClickListener(this);
        clickLogin.setOnClickListener(this);
        relativeLoginSuceed.setOnClickListener(this);
        userLogo.setOnClickListener(this);
        regist.setOnClickListener(this);
        mContext = MyApp.getContext();
        activity = (HomeActivity) getActivity();
        initBseData();
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        reshFragment(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_logo:
                break;
            case R.id.mine_collection:
                if (!"".equals(sid)) {
                    startActivity(new Intent(mContext, MineCollectionActivity.class));
                } else {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                }
                break;
            case R.id.mine_order:
                if (!"".equals(sid)) {
                    startActivity(new Intent(mContext, AllOrderActivity.class));
                } else {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                }

                break;
            //我的设备模块
            case R.id.mine_my_device:
//                startActivity(new Intent(mContext, DeviceNoAddActivity.class));
                break;
            case R.id.mine_ship_address:
                if (!"".equals(sid)) {
                    startActivity(new Intent(mContext, ShipAddManaActivity.class));
                } else {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                }

                break;
            //进入登录用户信息详情页面
            case R.id.relative_login_suceed:
                //根据用户种类的不同进入不同的个人信息详情页面
                NickPhone nickPhone = activity.getLoginInfo();
                if (nickPhone != null) {
                    int type = nickPhone.getType();
                    if (type == 1) {  //普通用户
                        startActivityForResult(new Intent(mContext, NormalUserInfoActivity.class),10);
                    } else if (type == 2) { //医生用户
                        startActivityForResult(new Intent(mContext, DoctorUserInfoActivity.class),10);
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "账户异常请重新登录");
                    exitLoginManage();
                    startActivityForResult(new Intent(mContext, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                }
                break;
            //系统设置页面
            case R.id.mine_setting:
                Intent intent = new Intent(mContext, MineSettingActivity.class);
                intent.putExtra("login", login);
                startActivityForResult(intent, ResourseSum.LOGIN_OUT_RESPONSE);
                break;
            //点击登录按钮
            case R.id.click_login:
                startActivityForResult(new Intent(mContext, LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
                break;
            case R.id.regist:
                startActivity(new Intent(mContext, UserRegistActivity.class));
                break;
            case R.id.mine_use_guide:
                Intent intent1 = new Intent(mContext, MedicalBookDetailActivity.class);
                intent1.putExtra("medical_book_name","使用指南");
                intent1.putExtra("medical_book_url","http://219.144.68.15:9000/img/userfiles/appInfo/adzy_help.pdf");
                startActivity(intent1);
                break;
        }
    }

    /**
     * 帐号异常和退出登录处理
     */
    private void exitLoginManage() {
        regist.setVisibility(View.VISIBLE);
        relativeLoginSuceed.setVisibility(View.GONE);
        clickLogin.setVisibility(View.VISIBLE);
        userLogo.setImageResource(R.mipmap.user_local);
        sharedPreferences.edit().putBoolean("login", false).apply();
        sharedPreferences.edit().putBoolean("signOut", true).apply();
        sharedPreferences.edit().putString("saveSid", "").apply();
        EventBus.getDefault().post(0, EventTag.LOGIN);
    }

    public void reshFragment(Context context) {
        //用户的登录状态
        login = SpUtill.getBoolen(context, ResourseSum.Medica_SP, "login");
        MyLog.d("reshFragment...." + login);
        //根据用户的登录状态进行相应的刷新碎片操作
        if (login) {  //当前用户已经登录
            relativeLoginSuceed.setVisibility(View.VISIBLE);
            clickLogin.setVisibility(View.GONE);
            //用户的在线logo路径
            String logo = SpUtill.getString(context, ResourseSum.Medica_SP, "logo");
            //登陆成功后加载在线用户logo
            if (!(TextUtils.isEmpty(logo))) {
                Glide.with(MyApp.getContext()).load(logo).transform(new GlideCircleTransform(getActivity())).into(userLogo);
            }
            //登录页面点击登录后获取用户名  电话
            NickPhone nickPhone = activity.getLoginInfo();
            if (nickPhone != null) {
                userName.setText(nickPhone.getNickName());
                String phone = nickPhone.getPhone();
                if (RegexUtils.isMobileExact(phone)) {
                    phone = phone.substring(0, 3) + "*****" + phone.substring(8, 11);
                }
                userPhone.setText(phone);
                regist.setVisibility(View.GONE);
            } else {
                //这是出现用户帐号为空异常问题，清空所有重新登录
                exitLoginManage();
            }
        } else { //当前用户未登录
            regist.setVisibility(View.VISIBLE);
            relativeLoginSuceed.setVisibility(View.GONE);
            clickLogin.setVisibility(View.VISIBLE);
            userLogo.setImageResource(R.mipmap.user_local);
        }
    }

    private void initBseData() {
        sharedPreferences = getActivity().getSharedPreferences(ResourseSum.Medica_SP, MODE_PRIVATE);
    }

    @Subscriber(tag = EventTag.LOGIN)
    private void loginEvent(int i) {
        reshFragment(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK){
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
            reshFragment(activity);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
