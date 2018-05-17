package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.OneKeyPersBean;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.shopmarket.common.http.Result;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 添加一键呼叫联系人并保存页面
 */
public class ContaAddActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ContactAddActivity";
    @Bind(R.id.edit_contact_title)
    TextView tvTitle;
    @Bind(R.id.tv_add_type)
    TextView tv_add_type;
    @Bind(R.id.save_contact_switch)
    Button saveContactSwitch;
    @Bind(R.id.add_from_contacts)
    Button addFromContacts;
    @Bind(R.id.delete_contacts)
    Button deleteContacts;
    @Bind(R.id.contact_name)
    EditText contactName;
    @Bind(R.id.contact_phone)
    EditText contactPhone;
    private OneKeyPersBean.DataBean.MemberCalloutListBean contactBean;
    private String sid;
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == 100) {
            } else if (requestCode == 101) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people")), 0);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        sid = SpUtill.getString(this, ResourseSum.Medica_SP, "saveSid");
        if (ResourseSum.TURN_TO_ADD_CONTACT == 1){
            tvTitle.setText("添加");
            tv_add_type.setText("手动添加");
            addFromContacts.setVisibility(View.VISIBLE);
        }else {
            tvTitle.setText("编辑");
            tv_add_type.setText("编辑信息");
            addFromContacts.setVisibility(View.GONE);
            Bundle bundle = getIntent().getExtras();
            contactBean = (OneKeyPersBean.DataBean.MemberCalloutListBean)bundle.getSerializable("contact");
            contactName.setText(contactBean.getCalloutName());
            contactPhone.setText(contactBean.getTelephone());
        }
    }

    private void initListener() {
        saveContactSwitch.setOnClickListener(this);
        addFromContacts.setOnClickListener(this);
//        deleteContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //打开通讯录展示页面
            case R.id.add_from_contacts:
                checkPermissionContact();

                break;
            case R.id.save_contact_switch:
                saveContactResponse();
                break;
//            case R.id.delete_contacts:
//                Toast.makeText(this, "一键呼叫联系人删除成功", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

    private void checkPermissionContact() {
        AndPermission.with(this)
                .requestCode(101)
                .permission(Manifest.permission.READ_CONTACTS)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    private void saveContactResponse() {
        String name = contactName.getText().toString().trim();
        String phone = contactPhone.getText().toString().trim();
        if ("".equals(name)){
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(phone)){
            Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ResourseSum.TURN_TO_ADD_CONTACT == 1){
            OkHttpUtils.post().url(MyUrl.CONTACTS_ADD).addParams("sid",sid).addParams("calloutName",name).
                    addParams("telephone",phone).addParams("type","1").build().execute(new StringCallback() {

                @Override
                public void onResponse(String response, int id) {
                    ResultBean bean = JSONObject.parseObject(response, ResultBean.class);
                    if("0".equals(bean.responseCode)){
                        Toast.makeText(ContaAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }

                @Override
                public void onError(Call call, Exception e, int id) {

                }
            });
        }else {
            OkHttpUtils.post().url(MyUrl.CONTACTS_UPDATE).addParams("sid",sid).addParams("calloutName",name).addParams("telephone",phone).
                    addParams("memberCalloutId",contactBean.getMemberCalloutId()+"").build().execute(new StringCallback() {

                @Override
                public void onResponse(String response, int id) {
                    ResultBean bean = JSONObject.parseObject(response, ResultBean.class);
                    if("0".equals(bean.responseCode)){
                        Toast.makeText(ContaAddActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
                @Override
                public void onError(Call call, Exception e, int id) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            String username = cursor.
                    getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            MyLog.d(TAG, "选中联系人为..." + username);
            //因为电话号码可能为多个
            Cursor phone = reContentResolverol.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);
            String usernumber = null;
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                MyLog.d(TAG, "选中联系人电话为..." + usernumber);
            }
            //手动添加页面显示联系人电话和姓名
            contactName.setText(username);
            contactPhone.setText(usernumber);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ResourseSum.TURN_TO_ADD_CONTACT = 5;
    }
}
