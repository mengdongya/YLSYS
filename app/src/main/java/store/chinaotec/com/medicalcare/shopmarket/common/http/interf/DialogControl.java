package store.chinaotec.com.medicalcare.shopmarket.common.http.interf;

import android.app.ProgressDialog;

public interface DialogControl {

    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog(String text);
}
