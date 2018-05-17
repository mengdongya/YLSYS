package store.chinaotec.com.medicalcare.javabean;

import java.util.List;

/**
 * Created by wjc on 2017/12/21 0021.
 */
public class Weather {
    public WeatherResult result;
    public String status;
    public String msg;

    public static class WeatherResult{
        public String city;
        public String cityid;
        public String citycode;
        public String date;
        public String week;
        public String weather;
        public String temp;
        public String temphigh;
        public String templow;
        public String img;
        public String humidity;
        public String pressure;
        public String windspeed;
        public String winddirect;
        public String windpower;
        public String updatetime;
//        public List<WeatherIndex> index;
        public List<WeatherDaily> daily;

        public class WeatherDaily{
            public String date;
            public String week;
            public String sunrise;
            public String sunset;
            public WeatherDailyDayOrNight night;
            public WeatherDailyDayOrNight day;

            public class WeatherDailyDayOrNight{
                public String weather;
                public String templow;
                public String temphigh;
                public String img;
                public String winddirect;
                public String windpower;
            }
        }
    }
}
