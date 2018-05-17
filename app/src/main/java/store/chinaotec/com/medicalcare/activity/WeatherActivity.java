package store.chinaotec.com.medicalcare.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.javabean.Weather;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.LogUtil;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;
import store.chinaotec.com.medicalcare.utill.WeatherUtil;

/**
 * Created by wjc on 2017/12/21.
 * 天气
 */
public class WeatherActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView ivBack;
    @Bind(R.id.weather_root)
    RelativeLayout lyt_weather;
    @Bind(R.id.weather_realtemp)
    TextView tv_realTemp;
    @Bind(R.id.weather_realsky)
    TextView tv_realsky;
    @Bind(R.id.weather_humidity)
    TextView tv_humidity;
    @Bind(R.id.weather_direct)
    TextView tv_direct;
    @Bind(R.id.weather_power)
    TextView tv_power;
    @Bind(R.id.weather_date)
    TextView tv_date;
    @Bind(R.id.weather_weekday)
    TextView tv_weekday;
    @Bind(R.id.weather_moon)
    TextView tv_moon;
    @Bind(R.id.weather_pushtime)
    TextView tv_pushtime;
    @Bind(R.id.weather_weekline)
    LinearLayout lyt_week;
    @Bind(R.id.tv_weather_city)
    TextView weatherCity;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initWidget();
        getWeatherRequest();
    }

    public void initWidget() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        city = SpUtill.getString(this, ResourseSum.Medica_SP, "saveLocationCity");
        weatherCity.setText(city);
    }

    private void getWeatherRequest() {
        String host = MyUrl.WeatherLink;
        String appCode = "3a3812dae5bc40cfad41176c824c286a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", city);

        OkHttpUtils.get().url(host).headers(headers).params(querys).build().execute(new StringCallback() {

            @Override
            public void onResponse(String response, int id) {
                Weather weather = JSONObject.parseObject(response, Weather.class);
                LogUtil.e("", "Weather==" + weather);
                if ("0".equals(weather.status) && weather.status != null) {

                    tv_realTemp.setText(weather.result.temp);
                    tv_realsky.setText(weather.result.weather);
                    tv_humidity.setText("湿度" + weather.result.humidity + "%");
                    tv_direct.setText(weather.result.winddirect);
                    tv_power.setText(weather.result.windpower);
                    tv_date.setText(getDate(weather.result.date));
                    tv_weekday.setText(weather.result.week);
//                    tv_moon.setText("农历" + weather.result.data.realtime.moon);
                    tv_pushtime.setText(weather.result.updatetime + "发布");

                    if (weather.result.weather.contains("晴")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_sun);
                    } else if (weather.result.weather.contains("多云")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_cloudy);
                    } else if (weather.result.weather.contains("阴")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_most_clody);
                    } else if (weather.result.weather.contains("电") || weather.result.weather.contains("雷")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_elect);
                    } else if (weather.result.weather.contains("小雨")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_small_rain);
                    } else if (weather.result.weather.contains("雨")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_big_rain);
                    } else if (weather.result.weather.contains("雪")) {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_snow);
                    }else {
                        lyt_weather.setBackgroundResource(R.mipmap.weatherbg_most_clody);
                    }

                    for (int i = 0; i < weather.result.daily.size(); i++) {
                        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.item_weekweather, null);
//                        view.setLayoutParams(params);
                        ((TextView) view.findViewById(R.id.week_day)).setText(i == 0 ? "今天" : weather.result.daily.get(i).week);
                        ((TextView) view.findViewById(R.id.week_maxtemp)).setText(weather.result.daily.get(i).day.temphigh + "°");
                        ((TextView) view.findViewById(R.id.week_mintemp)).setText(weather.result.daily.get(i).night.templow + "°");
                        ((ImageView) view.findViewById(R.id.week_icon)).setImageResource(WeatherUtil.getSkyPic(weather.result.daily.get(i).day.img));
                        ((TextView) view.findViewById(R.id.week_sky)).setText(weather.result.daily.get(i).day.weather);
                        lyt_week.addView(view);
                    }
//                    SharedPreferences.Editor editor = getSharedPreferences("weather", Context.MODE_PRIVATE).edit();
//                    editor.putString("temperature", weather.result.temp);
//                    editor.putString("img", weather.result.img);
//                    editor.commit();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

        });

    }

    private String getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = format.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day);
        } catch (ParseException e) {
            return null;
        }
    }

}
