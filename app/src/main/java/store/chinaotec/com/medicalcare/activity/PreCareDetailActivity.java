package store.chinaotec.com.medicalcare.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.KnowledgeResponse;
import store.chinaotec.com.medicalcare.view.FlowLayout;

/**
 * 优生优育条目详情页面
 */
public class PreCareDetailActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.detail_back)
    ImageView detailBack;
    @Bind(R.id.tv_detail_title)
    TextView tvTitle;
    @Bind(R.id.tv_detail_summary)
    TextView tvSummary;
    @Bind(R.id.gv_detail_lexicon)
    GridView gv_detail_lexicon;
    @Bind(R.id.wv_lexicon_detail)
    WebView wv_lexicon_detail;
    @Bind(R.id.tv_lexicon_name)
    TextView tv_lexicon_name;
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;

    private String gestationId;
    private String title;
    private String summary;
    private List<KnowledgeResponse.Knowledge> knowledgeList;
    private LexiconAdapter lexiconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_care_detail);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        Intent intent = getIntent();
        gestationId = intent.getStringExtra("gestationId");
        title = intent.getStringExtra("title");
        summary = intent.getStringExtra("summary");

        tvTitle.setText(title);
        tvSummary.setText(summary);

        initWebView();

        getData();
    }

    private void initWebView() {
        // 设置允许使用
        wv_lexicon_detail.getSettings().setJavaScriptEnabled(true);
        wv_lexicon_detail.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_lexicon_detail.getSettings().setAppCacheEnabled(false);
        // 适应屏幕
        wv_lexicon_detail.getSettings().setUseWideViewPort(true);
        wv_lexicon_detail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_lexicon_detail.getSettings().setDomStorageEnabled(true);
        wv_lexicon_detail.getSettings().setSupportZoom(false);// 支持缩放
        wv_lexicon_detail.getSettings().setBuiltInZoomControls(false);// 设置显示缩放按钮
        wv_lexicon_detail.getSettings().setAllowFileAccess(true); // 允许访问文件
        wv_lexicon_detail.getSettings().setLoadWithOverviewMode(true);
    }

    private void getData() {
        String url = MyUrl.KNOWLEDGE_GESTATION_LIST;
        OkHttpUtils.post().url(url).addParams("gestationId",gestationId).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                KnowledgeResponse knowledgeResponse = JSON.parseObject(response, KnowledgeResponse.class);
                if ("0".equals(knowledgeResponse.responseCode)){
                    knowledgeList = knowledgeResponse.data;
                    setData();
//                    setTestData();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });
    }

    private void setTestData() {

        if (knowledgeList != null && knowledgeList.size() > 0){
            tv_lexicon_name.setText(knowledgeList.get(0).getLexiconName());
            wv_lexicon_detail.loadUrl(knowledgeList.get(0).getContentUrl());
        }

        if (knowledgeList.size() > 0){
            for (int i = 0;i < knowledgeList.size();i++){
                final TextView view = new TextView(this);
                view.setText(knowledgeList.get(i).getLexiconName());
//                view.setTextColor(Color.GRAY);
                view.setBackgroundResource(R.drawable.bg_choose_white);
                view.setTextColor(Color.parseColor("#3F51B5"));
                view.setPadding(5, 5, 5, 5);
                view.setGravity(Gravity.CENTER);
                view.setTextSize(14);

                // 设置点击事件
                final int finalI = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upDateWv(finalI);
                    }
                });

                // 设置按下的灰色背景
                GradientDrawable pressedDrawable = new GradientDrawable();
                pressedDrawable.setShape(GradientDrawable.RECTANGLE);
                pressedDrawable.setColor(Color.GRAY);

                // 背景选择器
                StateListDrawable stateDrawable = new StateListDrawable();
                stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);

                // 设置背景选择器到TextView上
                view.setBackground(stateDrawable);

                flowLayout.addView(view);
            }
        }

    }

    private void upDateWv(int position) {
        tv_lexicon_name.setText("1."+knowledgeList.get(position).getLexiconName());
        wv_lexicon_detail.clearHistory();
        wv_lexicon_detail.loadUrl(knowledgeList.get(position).getContentUrl());
    }

    private void setData() {
        lexiconAdapter = new LexiconAdapter();
        gv_detail_lexicon.setAdapter(lexiconAdapter);
        if (knowledgeList != null && knowledgeList.size() > 0){
//            tv_lexicon_name.setText(knowledgeList.get(0).getLexiconName());
            wv_lexicon_detail.loadUrl(knowledgeList.get(0).getContentUrl());
        }
        gv_detail_lexicon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lexiconAdapter.changeState(position);
                upDateWv(position);

            }
        });
    }

    private void initListener() {
        detailBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                finish();
                break;
        }
    }

    class LexiconAdapter extends BaseAdapter{

        private int selectorPosition;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(PreCareDetailActivity.this, R.layout.item_screen_contion_content, null);
            RelativeLayout mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.rl_content);
            TextView textView = (TextView) convertView.findViewById(R.id.contion_content);
            textView.setText(knowledgeList.get(position).getLexiconName());
            //如果当前的position等于传过来点击的position,就去改变他的状态
            if (selectorPosition == position) {
                mRelativeLayout.setBackgroundResource(R.drawable.bg_choose_green);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                //其他的恢复原来的状态
                mRelativeLayout.setBackgroundResource(R.drawable.bg_choose_white);
                textView.setTextColor(Color.parseColor("#6f6f6f"));
            }
            return convertView;
        }


        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }
        @Override
        public int getCount() {
            return knowledgeList.size();
        }

        @Override
        public Object getItem(int position) {
            return knowledgeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

}
