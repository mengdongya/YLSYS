package store.chinaotec.com.medicalcare.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.R;

/**
 * Created by wjc on 2017/10/20 0020.
 * 图书详情
 */
public class MedicalBookDetailActivity extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener, OnLoadCompleteListener {
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_medical_title_name)
    TextView tvMedicalTitleName;
    @Bind(R.id.tv_medical_title_download)
    TextView tv_file_download;
    @Bind(R.id.pdfView)
    PDFView pdfView;
    private String title;
    private String url;

    Integer pageNumber = 0;
    String pdfFileName = "yyy.pdf";
    private Handler handler;
    private String fileAbsolutePath;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("medical_book_name");
        url = getIntent().getStringExtra("medical_book_url");
        setContentView(R.layout.activity_medical_book_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivTitleBack.setOnClickListener(this);
        tv_file_download.setOnClickListener(this);
        tvMedicalTitleName.setText(title);
        handler = new Handler();
        progressDialog = new ProgressDialog(this);
        loadPDF();
    }

    private void loadPDF() {

        String[] exts = url.split("/");
        pdfFileName = exts[exts.length - 1];
        Log.e("pdf文件名：", pdfFileName);

        String pdfName = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(pdfName, pdfFileName);
        fileAbsolutePath = file.getAbsolutePath();
        if (file.exists()) {
            Log.e("Tip：", "报告已经存在！");
            //文件已经存在，则直接显示
            displayFromFile(file);
        } else {
            getFile();
        }
    }

    private void getFile() {
        progressDialog.show();
        OkGo.get(url).tag(this).execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath(), pdfFileName) {  //文件下载时，可以指定下载的文件目录和文件名
            @Override
            public void onSuccess(File file, Call call, Response response) {
                progressDialog.dismiss();
                // file 即为文件数据，文件保存在指定目录
                fileAbsolutePath = file.getAbsolutePath();
                Log.e("Tip：", "下载完成！" + fileAbsolutePath);
                displayFromFile(file);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                //这里回调下载进度(该回调在主线程,可以直接更新ui)
                //currentSize totalSize以字节byte为单位
            }
        });
    }

    private void displayFromFile(File file) {
        pdfView.fromFile(file).defaultPage(pageNumber).onPageChange(this).enableAnnotationRendering(true).onLoad(this).scrollHandle(new DefaultScrollHandle(this)).load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_medical_title_download:

                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MedicalBookDetailActivity.this);
                        builder.setTitle("下载文件");
                        builder.setMessage(fileAbsolutePath);
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
//                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
                        builder.create().show();
                    }
                }, 2000);
                break;
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            // Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
