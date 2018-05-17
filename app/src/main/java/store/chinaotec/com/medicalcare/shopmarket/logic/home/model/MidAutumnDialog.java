package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 */
public class MidAutumnDialog extends AlertDialog {
    private int layoutId;

    public MidAutumnDialog(Context context, int theme, int layoutId) {
        super(context, theme);
        this.layoutId = layoutId;
    }

    public MidAutumnDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
    }

    @Override
    public void show() {
        super.show();

    }
}