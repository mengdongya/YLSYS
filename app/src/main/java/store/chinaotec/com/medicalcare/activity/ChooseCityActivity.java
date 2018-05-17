package store.chinaotec.com.medicalcare.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.adapter.MyHotCityAdapter;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * 选择导航城市页面
 */
public class ChooseCityActivity extends BaseActivity {
    @Bind(R.id.now_location_city)
    TextView nowLocationCity;
    @Bind(R.id.hot_city_recycleview)
    RecyclerView hotCityRecycleview;
    @Bind(R.id.choose_city_input)
    EditText chooseCityInput;
    @Bind(R.id.choose_city_scroolview)
    ScrollView chooseCityScroolview;
    private TextView tv_include_title_view;
    private String[] hotCityArray = {"北京市", "天津市", "沈阳市", "上海市", "南京市", "杭州市", "武汉市", "长沙市",
            "广州市", "深圳市", "重庆市", "成都市","银川市"};
    private Context mContext = this;
    private MyHotCityAdapter myHotCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        tv_include_title_view = (TextView) findViewById(R.id.tv_include_title_view);
        tv_include_title_view.setText("城市列表");
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        final String city = SpUtill.getString(this, ResourseSum.Medica_SP, "saveLocationCity");
        nowLocationCity.setText(city);
        nowLocationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "saveHotCity", city);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void initListener() {
        hotCityRecycleview.setLayoutManager(new GridLayoutManager(this, 3));
        myHotCityAdapter = new MyHotCityAdapter(hotCityArray);
        hotCityRecycleview.setAdapter(myHotCityAdapter);

        myHotCityAdapter.setOnItemClickListener(new MyHotCityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(String data) {
//                HomeFragment instance = HomeFragment.instance();
//                Bundle arguments = instance.getArguments();
//                arguments.putString("hot_city", data);
                SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, "saveHotCity", data);
                setResult(RESULT_OK);
                finish();
            }
        });
        //添加输入城市框的内容改变监听
        chooseCityInput.addTextChangedListener(new MyCityTextWatch());
    }

    class MyCityTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //输入定位城市信息发生变化时进行查询操作
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
