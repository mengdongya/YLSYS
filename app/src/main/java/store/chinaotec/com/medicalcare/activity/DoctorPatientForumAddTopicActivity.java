package store.chinaotec.com.medicalcare.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.GridPictureAdapter;
import store.chinaotec.com.medicalcare.javabean.ResultBean;
import store.chinaotec.com.medicalcare.localalbum.LocalAlbum;
import store.chinaotec.com.medicalcare.localalbum.LocalImageHelper;
import store.chinaotec.com.medicalcare.shopmarket.common.constant.Constant;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.BitmapUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.ToastUtil;
import store.chinaotec.com.medicalcare.utill.IntentUtils;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * Created by wjc on 2017/10/27 0027.
 * 医患论坛-发表论坛
 */
public class DoctorPatientForumAddTopicActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_doctor_add_finish)
    TextView tvDoctorAddFinish;
    @Bind(R.id.et_send_content)
    EditText edit_content;
    @Bind(R.id.gv_addsku_icon)
    GridView gvAddskuIcon;

    private static final int TAKE_PICTURE = 1;
    // 保存全尺寸照片
    String mCurrentPhotoPath;
    private List<ImageView> picList;
    private GridPictureAdapter adapter;
    private TextView tv_photo;
    private TextView tv_album;
    private TextView tv_cancel;
    private PopupWindow mImageMenuWnd = null;
    private View imageMenu = null;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String bitmapStrings = (String) msg.obj;
                    commit(bitmapStrings);
                    break;
            }
            super.handleMessage(msg);
        }

    };

    private Uri imgUri;
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
    private Bitmap bitmap;
    private HashMap<String, String> mParams;
    private ProgressDialog proDialog;
    private String sid;
    private int classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classId = getIntent().getIntExtra("classId", 0);
        setContentView(R.layout.activity_doctor_forum_add);
        ButterKnife.bind(this);
        initView();
        initImage();
    }

    private void initView() {
        sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        mParams = new HashMap<>();
        picList = new ArrayList<>();
        ivTitleBack.setOnClickListener(this);
        tvDoctorAddFinish.setOnClickListener(this);
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("发送中...");
        proDialog.setCancelable(false);
        initPopWindow();
    }

    private void initImage() {
        LocalImageHelper.init(this);
        LocalImageHelper.getInstance().getCheckedItems().clear();
        gvAddskuIcon.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridPictureAdapter(this);
        gvAddskuIcon.setAdapter(adapter);
        gvAddskuIcon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == LocalImageHelper.getInstance().getCheckedItems().size()) {
                    InputMethodManager inputManager = (InputMethodManager) edit_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(edit_content.getWindowToken(), 0);
                    showPopMenu();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_doctor_add_finish:
                setUpRequest();
                break;
        }
    }

    private void setUpRequest() {
        if (StringUtils.isEmpty(edit_content.getText().toString()) && picList.size() == 0) {
            ToastUtil.MyToast(this, "请输入内容");
            return;
        }
        if ("".equals(sid)) {
            startActivityForResult(new Intent(MyApp.getContext(), LoginActivity.class), ResourseSum.LOGIN_RESPONSE);
            return;
        }
        mParams.clear();
        proDialog.show();

        mParams.put("sid", sid);
        mParams.put("source", 1 + "");
        mParams.put("classId", classId + "");
        mParams.put("title", "");
        mParams.put("source", "1");
        mParams.put("body", edit_content.getText().toString());
        mParams.put("file", "");

        /**在子线程将bitmap转化为base64字符串*/
        new Thread() {
            public void run() {
                String[] strings = new String[LocalImageHelper.getInstance().getCheckedItems().size()];
                for (int i = 0; i < LocalImageHelper.getInstance().getCheckedItems().size(); i++) {
                    String string = BitmapUtils.bitmapToBase64(LocalImageHelper.getInstance().getCheckedItems().get(i).getBitmap());
                    strings[i] = string;
                }
                String bitmapString = StringUtils.format(strings);
                Message message = new Message();
                message.obj = bitmapString;
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();

    }

    private void commit(String bitmapStrings) {
        if (!StringUtils.isEmpty(bitmapStrings)) {
            mParams.put("body_imgs", bitmapStrings);
        }
        OkHttpUtils.post().url(MyUrl.FORUM_ADD).params(mParams).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                proDialog.cancel();
                ResultBean result = JSON.parseObject(response, ResultBean.class);
                if ("0".equals(result.responseCode)) {
                    ToastUtil.MyToast(DoctorPatientForumAddTopicActivity.this, "发表成功");
                    setResult(RESULT_OK);
                } else {
                    ToastUtil.MyToast(DoctorPatientForumAddTopicActivity.this, result.msg);
                }
                Intent intent = new Intent();
                intent.setAction(Constant.REFRESH_FORUM);
                sendBroadcast(intent);
                finish();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                proDialog.cancel();
                ToastUtil.MyToast(DoctorPatientForumAddTopicActivity.this, "请求失败！");
            }

        });
    }


    public void initPopWindow() {
        imageMenu = LayoutInflater.from(this).inflate(R.layout.popwin_take_photo, null);
        mImageMenuWnd = new PopupWindow(imageMenu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_photo = (TextView) imageMenu.findViewById(R.id.pop_photo);
        tv_album = (TextView) imageMenu.findViewById(R.id.pop_album);
        tv_cancel = (TextView) imageMenu.findViewById(R.id.pop_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageMenuWnd.dismiss();
            }
        });

        mImageMenuWnd.setFocusable(true);
        mImageMenuWnd.setOutsideTouchable(true);
        mImageMenuWnd.setAnimationStyle(R.style.ImageSelectMenuStyle);
        mImageMenuWnd.setBackgroundDrawable(new ColorDrawable(0));
        mImageMenuWnd.update();
        mImageMenuWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showPopMenu() {
        View contentView = findViewById(R.id.post_root);
        backgroundAlpha(0.5f);
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageMenuWnd.dismiss();
                checkCamera();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mImageMenuWnd.dismiss();
                checkRead();
            }
        });
        mImageMenuWnd.showAtLocation(contentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onRestart() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        super.onRestart();
    }

    @Override
    protected void onResume() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        super.onResume();
    }

    /**
     * 检查是否有相机权限
     */
    private void checkCamera() {
        AndPermission.with(this).requestCode(101).permission(Manifest.permission.CAMERA).send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，剩下的AndPermission自动完成。
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults, listener);
    }

    /**
     * 检查是否有相册权限
     */
    private void checkRead() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(DoctorPatientForumAddTopicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(DoctorPatientForumAddTopicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ToastUtil.showMessageOKCancel("你需要获取读取的权限\n设置方法:权限管理-相册-允许", DoctorPatientForumAddTopicActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse("package:" + "store.chinaotec.com.medicalcare"));
                            startActivity(intent);
                        }
                    });
                    return;
                }
            } else {
                callPicture();
            }
        } else {
            callPicture();
        }
    }

    private void callPicture() {
        Intent intent = new Intent(this, LocalAlbum.class);
        startActivity(intent);
    }

    private void callCamera() {

        File appDir = new File(Environment.getExternalStorageDirectory(), "/zhyl/");
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
        imgUri = Uri.fromFile(outputImage);
        // 意图 相机
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        // 如果有相机
        if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ResourseSum.LOGIN_RESPONSE && resultCode == RESULT_OK){
            sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        }
        switch (requestCode) {
            case TAKE_PICTURE:
                if (LocalImageHelper.getInstance().getCheckedItems().size() < 11 && resultCode == RESULT_OK) {
                    String fileName = String.valueOf(System.currentTimeMillis());
//                    bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = BitmapUtils.getimage(mCurrentPhotoPath);
//                    FileUtils.saveBitmap(bitmap, fileName);
//                    File file = new File(FileUtils.f.getAbsolutePath());
                    File file = new File(mCurrentPhotoPath);
                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        LocalImageHelper.LocalFile localFile = new LocalImageHelper.LocalFile();
                        localFile.setBitmap(bitmap);
                        localFile.setOriginalUri(uri.toString());
                        LogUtil.e("照片的数据为——=", localFile.toString());
                        LocalImageHelper.getInstance().getCheckedItems().add(localFile);
                    } else {
                        Toast.makeText(this, "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
