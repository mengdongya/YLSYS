package store.chinaotec.com.medicalcare.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by hxk on 2017/9/30 0030 14:14
 * 提示用户上传单据等资料
 */

public class PropUploadDialog extends DialogFragment implements View.OnClickListener {
    @Bind(R.id.prop_title)
    TextView propTitle;
    @Bind(R.id.cancle)
    Button cancle;
    @Bind(R.id.upload)
    Button upload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_prop_upload, container);
        ButterKnife.bind(this, inflate);
        initListener();
        return inflate;
    }

    private void initListener() {
        cancle.setOnClickListener(this);
        upload.setOnClickListener(this);
        propTitle.setText(title);
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private CancleListener cancleListener = null;

    public interface CancleListener {
        void cancle();
    }

    public void setOnClickCancleListener(CancleListener cancleListener) {
        this.cancleListener = cancleListener;
    }

    private UploadListener uploadListener = null;

    public interface UploadListener {
        void upload();
    }

    public void setOnClickUploadListener(UploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    private String title = null;

    public void setPropTitle(String title) {
        this.title = title;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                if (cancleListener != null) {
                    cancleListener.cancle();
                }
                dismiss();
                break;
            case R.id.upload:
                if (uploadListener != null) {
                    uploadListener.upload();
                }
                break;
        }
    }
}
