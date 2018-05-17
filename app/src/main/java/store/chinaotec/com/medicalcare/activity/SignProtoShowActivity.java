package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;

/**
 * 医生签约协议展示页面加载相应的h5
 */
public class SignProtoShowActivity extends BaseActivity {
    @Bind(R.id.sign_protoc)
    WebView signProtoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_sign_proto_show);
        ButterKnife.bind(this);
//        signProtoc.loadUrl(MyUrl.sign_protocol);
        signProtoc.loadUrl(url);
    }
}
