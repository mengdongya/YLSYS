package store.chinaotec.com.medicalcare.fragment.health;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.activity.HealthAddPersonActivity;
import store.chinaotec.com.medicalcare.activity.HealthAddRecordActivity;
import store.chinaotec.com.medicalcare.activity.HealthInfoAddActivity;
import store.chinaotec.com.medicalcare.activity.HealthInfoDetailActivity;
import store.chinaotec.com.medicalcare.activity.HealthyRecordActivity;
import store.chinaotec.com.medicalcare.activity.TakeExerciseActivity;
import store.chinaotec.com.medicalcare.dialog.PickDayDialog;
import store.chinaotec.com.medicalcare.dialog.PickMonthDialog;
import store.chinaotec.com.medicalcare.dialog.PickYearDialog;
import store.chinaotec.com.medicalcare.javabean.HealthControlBean;
import store.chinaotec.com.medicalcare.javabean.HealthDataBean;
import store.chinaotec.com.medicalcare.shopmarket.common.utils.systemutil.StringUtils;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.view.GlideRoundTransform;

/**
 * Created by seven on 2017/12/26 0026.
 * 健康管理
 */
public class HealthControlFragmentV2 extends Fragment implements View.OnClickListener {
    @Bind(R.id.entry_name)
    TextView entryName;
    @Bind(R.id.entry_gender)
    TextView entryGender;
    @Bind(R.id.entry_age)
    TextView entryAge;
    @Bind(R.id.entry_health_height)
    TextView entryHeight;
    @Bind(R.id.entry_health_weight)
    TextView entryWeight;
    @Bind(R.id.linear_person_info)
    LinearLayout linearPersonInfo;
    @Bind(R.id.blood_press_slowdise)
    RadioButton bloodPressSlowdise;
    @Bind(R.id.heart_rate_slowdise)
    RadioButton heartRateSlowdise;
    @Bind(R.id.blood_suger_slowdise)
    RadioButton bloodSugerSlowdise;
    @Bind(R.id.blood_slipid_slowdise)
    RadioButton bloodSlipidSlowdise;
    @Bind(R.id.health_info_group)
    RadioGroup healthInfoGroup;
    @Bind(R.id.check_start_date)
    TextView checkStartDate;
    @Bind(R.id.check_end_date)
    TextView checkEndDate;
    @Bind(R.id.slow_dise_data)
    LineChartView lineChart;
    @Bind(R.id.top_kind)
    TextView topKind;
    @Bind(R.id.top_line)
    View topLine;
    @Bind(R.id.linear_top)
    LinearLayout linearTop;
    @Bind(R.id.linear_down)
    LinearLayout linearDown;
    @Bind(R.id.by_year)
    RadioButton byYear;
    @Bind(R.id.by_month)
    RadioButton byMonth;
    @Bind(R.id.by_day)
    RadioButton byDay;
    @Bind(R.id.buy_date)
    RadioGroup buyDate;
    @Bind(R.id.rl_health_ad)
    RelativeLayout rl_health_ad;
    @Bind(R.id.iv_health_ad)
    ImageView iv_health_ad;
    @Bind(R.id.tv_health_ad_close)
    TextView ad_close;
    @Bind(R.id.tv_health_intro)
    TextView tv_health_intro;

    private HealthControlBean.DataBean.PatientDtosBean patientDtosBean;
    private HealthControlBean.DataBean.MedicalADV medicalADV;
    public static final String TAG = "HealthControlFragment";
    //日期查询日期集合
    private List<String> dateList = new ArrayList<String>();
    //收缩压数据集合
    private List<Float> sysPresList = new ArrayList<Float>();
    //舒张压数据集合
    private List<Float> diasPresList = new ArrayList<Float>();
    //心率数据集合
    private List<Float> heartRateList = new ArrayList<Float>();
    //血糖数据集合
    private List<Float> bloodSuagerList = new ArrayList<Float>();
    //血脂数据集合
    private List<Float> bloodFatList = new ArrayList<Float>();
    private LineChartData data;

    @Bind(R.id.tv_fragment_health_controlv2_01)
    TextView tvFragmentHealthControlv201;
    @Bind(R.id.tv_fragment_health_controlv2_02)
    TextView tvFragmentHealthControlv202;


    public static HealthControlFragmentV2 newInstance(HealthControlBean.DataBean.PatientDtosBean healthControlBean, HealthControlBean.DataBean.MedicalADV medicalADV) {
        Bundle args = new Bundle();
        args.putSerializable("healthControlBean", healthControlBean);
        args.putSerializable("medicalADV", medicalADV);
        HealthControlFragmentV2 fragment = new HealthControlFragmentV2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        patientDtosBean = (HealthControlBean.DataBean.PatientDtosBean) getArguments().getSerializable("healthControlBean");
        medicalADV = (HealthControlBean.DataBean.MedicalADV) getArguments().getSerializable("medicalADV");
        View view = inflater.inflate(R.layout.fragment_health_controlv2, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    public void setHealthData(HealthControlBean.DataBean.PatientDtosBean data, HealthControlBean.DataBean.MedicalADV medicalADV) {
        getArguments().putSerializable("healthControlBean", data);
        getArguments().putSerializable("medicalADV", medicalADV);
    }

    private void initView() {
        //默认选中"血压"  "按日"按钮
        bloodPressSlowdise.setChecked(true);
        byDay.setChecked(true);
//        timeLine.setNestedScrollingEnabled(false);
        entryName.setFocusableInTouchMode(true);
        entryName.requestFocus();
        tvFragmentHealthControlv201.setOnClickListener(this);
        tvFragmentHealthControlv202.setOnClickListener(this);
    }

    private void initData() {
        entryName.setText("姓名:" + patientDtosBean.getName());
        if (patientDtosBean.getSex() == 1) {
            entryGender.setText("性别: 男");
        } else {
            entryGender.setText("性别: 女");
        }
        entryAge.setText("年龄：" + patientDtosBean.getAge());
        if (!StringUtils.isEmpty(patientDtosBean.getHeight())){
            entryHeight.setText("身高:" + patientDtosBean.getHeight());
        }else {
            entryHeight.setText("身高:");
        }

        if (!StringUtils.isEmpty(patientDtosBean.getHeight())){
            entryWeight.setText("体重:" + patientDtosBean.getWeight() + "(公斤)");
        }else {
            entryWeight.setText("体重:");
        }
        tv_health_intro.setText("正常人血压60~89/90~139 mmHg");
        //默认查询前几天的数据并展示没种疾病
        getHealthDataSeven();


        if (medicalADV.getImg() != null && !"".equals(medicalADV.getImg())) {
            rl_health_ad.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(medicalADV.getImg()).transform(new GlideRoundTransform(getActivity(), 1)).into(iv_health_ad);
            iv_health_ad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!StringUtils.isEmpty(medicalADV.getUrl())){
                        Intent intent = new Intent(getActivity(), HealthInfoDetailActivity.class);
                        intent.putExtra("url", medicalADV.getUrl());
                        intent.putExtra("title", medicalADV.getName());
                        startActivity(intent);
                    }
                }
            });
        } else {
            rl_health_ad.setVisibility(View.GONE);
        }
    }


    private void initListener() {
        lineChart.setOnClickListener(this);
        linearPersonInfo.setOnClickListener(this);
        ad_close.setOnClickListener(this);
        healthInfoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.blood_press_slowdise:
                        drawBloodPresLine(dateList, sysPresList, diasPresList);
                        MyLog.object("日期集合" + dateList);
                        MyLog.object("舒张压数据源..." + sysPresList + "..收缩压数据源.." + diasPresList);
                        drawCoodinate(0, 250);
                        tv_health_intro.setText("正常人血压60~89/90~139 mmHg");
                        break;
                    case R.id.heart_rate_slowdise:
                        drawHealthLine(dateList, heartRateList);
                        MyLog.object("心率数据集合" + heartRateList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 180);
                        topKind.setText("次/m");
                        tv_health_intro.setText("心率正常值: 60～100次/分");
                        break;
                    case R.id.blood_suger_slowdise:
                        drawHealthLine(dateList, bloodSuagerList);
                        MyLog.object("血糖数据集合" + bloodSuagerList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 15);
                        topKind.setText("毫摩(mmol/L)");
                        tv_health_intro.setText("血糖正常值：空腹全血血糖:3.9～6.1毫摩/升、餐后1小时:6.7-9.4毫摩/升、餐后2小时:≤7.8毫摩/升");
                        break;
                    case R.id.blood_slipid_slowdise:
                        drawHealthLine(dateList, bloodFatList);
                        MyLog.object("体重数据集合" + bloodFatList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 150);
                        tv_health_intro.setText("世卫计算方法:男性：(身高cm－80)×70﹪=标准体重 女性：(身高cm－70)×60﹪=标准体重");
                        topKind.setText("kg");
                        break;
                }
            }
        });
        buyDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.by_year:
                        PickYearDialog pickYearDialog = new PickYearDialog();
                        pickYearDialog.show(getFragmentManager(), "choseYear");
                        getDiseDataByYear(pickYearDialog);
                        break;
                    case R.id.by_month:
                        PickMonthDialog pickMonthDialog = new PickMonthDialog();
                        pickMonthDialog.show(getFragmentManager(), "choseMonth");
                        getDiseDataByMonth(pickMonthDialog);
                        break;
                    case R.id.by_day:
                        PickDayDialog pickDayDialog = new PickDayDialog();
                        pickDayDialog.show(getFragmentManager(), "choseDay");
                        getDiseDataByDay(pickDayDialog);
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //修改病人信息通过病人的id
            case R.id.linear_person_info:
                ResourseSum.TURN_TO_UPDATE = 1;
                Intent patient = new Intent(MyApp.getContext(), HealthAddPersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("patientDtosBean", patientDtosBean);
                patient.putExtras(bundle);
                startActivity(patient);
                break;
            //添加病人血压 血糖等数据通过病人的id
            case R.id.slow_dise_data:
                Intent disedata = new Intent(getActivity(), HealthInfoAddActivity.class);
                disedata.putExtra("title", "健康管理");
                disedata.putExtra("patientid", patientDtosBean.getPatientId());
                startActivity(disedata);
                break;
            //添加病程记录
            case R.id.add_course_record:
                Intent intent = new Intent(getActivity(), HealthAddRecordActivity.class);
                intent.putExtra("patientId", patientDtosBean.getPatientId());
                startActivity(intent);
                break;
            case R.id.tv_health_ad_close:
                rl_health_ad.setVisibility(View.GONE);
                break;
            case R.id.tv_fragment_health_controlv2_01:
                Intent intent2 = new Intent(getActivity(), HealthyRecordActivity.class);
                intent2.putExtra("patientId", patientDtosBean.getPatientId());
                startActivity(intent2);
                break;
            case R.id.tv_fragment_health_controlv2_02:
                Intent intent1 = new Intent(getActivity(), TakeExerciseActivity.class);
                startActivity(intent1);
                break;
        }
    }

    /**
     * @param start 开始最小值
     * @param end   结束最大值
     *              设置LineChartView的纵坐标刻度
     */
    private void drawCoodinate(float start, float end) {
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = start;
        v.top = end;
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

    /**
     * @param pickYearDialog 选择年份的日期弹窗
     *                       按年份获取病人的健康信息数据
     */
    private void getDiseDataByYear(PickYearDialog pickYearDialog) {
        pickYearDialog.setChoseDate(new PickYearDialog.ChoseYearListener() {
            @Override
            public void choseDate(final String choseYear) {
                String startDate = choseYear + "-01" + "-01";
                String endDate = choseYear + "-12" + "-01";
                //按年查询查询开始结束时间显示
                checkStartDate.setText(choseYear);
                checkEndDate.setText("-" + choseYear);
                MyLog.d("按年查询选中年份.." + choseYear);
                //病人的id
                String patientId = patientDtosBean.getPatientId();
                if (!(TextUtils.isEmpty(patientId))) {
                    NetWorkUtill.getHelthData(patientId, startDate, endDate, 3, new NetWorkUtill.HealthDataListener() {
                        @Override
                        public void getHealthData(List<HealthDataBean.DataBean.PatientDateViewDtosBean> patientDateViewDtos) {
                            clearDataList();
                            for (int i = 0; i < patientDateViewDtos.size(); i++) {
                                //横轴日期
                                dateList.add(patientDateViewDtos.get(i).getFromX());
                                //收缩压
                                sysPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getSystolicPressure());
                                //舒张压
                                diasPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getDiastolicPressure());
                                //血脂
                                bloodFatList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodFat());
                                //血糖
                                bloodSuagerList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodSugar());
                                //心率
                                heartRateList.add(patientDateViewDtos.get(i).getPatientDateDto().getHeartRate());
                            }
                            drawHealthCruve();
                        }
                    });
                }
            }
        });
    }

    /**
     * 根据 血压  血糖 血脂 心率的选中情况画曲线图
     */
    private void drawHealthCruve() {
        if (bloodPressSlowdise != null) {
            if (bloodPressSlowdise.isChecked()) {
                drawBloodPresLine(dateList, sysPresList, diasPresList);
                drawCoodinate(0, 250);
            }
        } else if (heartRateSlowdise != null) {
            if (heartRateSlowdise.isChecked()) {
                MyLog.d("drawHealthCruve..默认线显示画心率曲线图..");
                drawHealthLine(dateList, heartRateList);
                drawCoodinate(0, 180);
                topKind.setText("心率");
            }
        } else if (bloodSugerSlowdise != null) {
            if (bloodSugerSlowdise.isChecked()) {
                MyLog.d("drawHealthCruve..默认线显示画血糖曲线图..");
                drawHealthLine(dateList, bloodSuagerList);
                drawCoodinate(0, 15);
                topKind.setText("血糖");
            }
        } else if (bloodSlipidSlowdise != null) {
            if (bloodSlipidSlowdise.isChecked()) {
                MyLog.d("drawHealthCruve..默认线显示画体重曲线图..");
                drawHealthLine(dateList, bloodFatList);
                drawCoodinate(0, 150);
                topKind.setText("体重");
            }
        }
    }

    /**
     * 按天,按年查询前清空原有的数据集合
     */
    private void clearDataList() {
        dateList.clear();
        sysPresList.clear();
        diasPresList.clear();
        bloodFatList.clear();
        bloodSuagerList.clear();
        heartRateList.clear();
    }

    /**
     * @param pickMonthDialog 选择月份弹窗
     *                        按月查询健康信息数据
     */
    private void getDiseDataByMonth(PickMonthDialog pickMonthDialog) {
        pickMonthDialog.setChoseDate(new PickMonthDialog.ChoseMonthListener() {
            @Override
            public void choseDate(String choseYearMonth, String showMonth) {
                String startDate = choseYearMonth + "-" + "01";
                String endDate = choseYearMonth + "-" + "31";
                MyLog.d("按月查询开始日期.." + startDate + "..结束日期.." + endDate);
                //按月查询查询开始结束时间显示
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
                checkStartDate.setText(simpleDateFormat.format(date) + "/" + showMonth);
                checkEndDate.setText("-" + simpleDateFormat.format(date) + "/" + showMonth);
                //病人的id
                String patientId = patientDtosBean.getPatientId();
                if (!(TextUtils.isEmpty(patientId))) {
                    NetWorkUtill.getHelthData(patientId, startDate, endDate, 2, new NetWorkUtill.HealthDataListener() {
                        @Override
                        public void getHealthData(List<HealthDataBean.DataBean.PatientDateViewDtosBean> patientDateViewDtos) {
                            clearDataList();
                            for (int i = 0; i < patientDateViewDtos.size(); i++) {
                                //横轴日期
                                dateList.add(patientDateViewDtos.get(i).getFromX());
                                //收缩压
                                sysPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getSystolicPressure());
                                //舒张压
                                diasPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getDiastolicPressure());
                                //血脂
                                bloodFatList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodFat());
                                //血糖
                                bloodSuagerList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodSugar());
                                //心率
                                heartRateList.add(patientDateViewDtos.get(i).getPatientDateDto().getHeartRate());
                            }
                            drawHealthCruve();
                        }
                    });
                }
            }
        });
    }

    /**
     * @param pickDayDialog 选择开始  结束日期弹窗对象
     *                      根据开始日期 结束日期查询病人的健康信息数据
     */
    private void getDiseDataByDay(PickDayDialog pickDayDialog) {
        pickDayDialog.setChoseDate(new PickDayDialog.ChoseDateListener() {
            @Override
            public void choseDate(String startDate, String endDate, String startShowDate, String endShowDate) {
                getHealthDataDay(startDate, endDate, startShowDate, endShowDate);
            }
        });
    }

    /**
     * @param startDate 查询开始日期
     * @param endDate   查询结束日期
     *                  按天数查询病人的健康信息
     */
    private void getHealthDataDay(String startDate, String endDate, String startShowDate, String endShowDate) {
        //默认开始结束查询日期显示
        if ((checkStartDate != null) && (checkEndDate != null)) {
            checkStartDate.setText(startShowDate);
            checkEndDate.setText("-" + endShowDate);
        }
        //病人的id
        String patientId = patientDtosBean.getPatientId();
        if (!(TextUtils.isEmpty(patientId))) {
            NetWorkUtill.getHelthData(patientId, startDate, endDate, 1, new NetWorkUtill.HealthDataListener() {
                @Override
                public void getHealthData(List<HealthDataBean.DataBean.PatientDateViewDtosBean> patientDateViewDtos) {
                    //清空数据集合
                    clearDataList();
                    if (patientDateViewDtos != null) {
                        for (int i = 0; i < patientDateViewDtos.size(); i++) {
                            //横轴日期
                            dateList.add(patientDateViewDtos.get(i).getFromX());
                            //收缩压
                            sysPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getSystolicPressure());
                            //舒张压
                            diasPresList.add(patientDateViewDtos.get(i).getPatientDateDto().getDiastolicPressure());
                            //血脂
                            bloodFatList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodFat());
                            //血糖
                            bloodSuagerList.add(patientDateViewDtos.get(i).getPatientDateDto().getBloodSugar());
                            //心率
                            heartRateList.add(patientDateViewDtos.get(i).getPatientDateDto().getHeartRate());
                        }
                        drawHealthCruve();
                    }
                }
            });
        }
    }

    /**
     * 默认查询获取包过当前日期在内前七天的数据并展示
     */
    public void getHealthDataSeven() {
        //获取默认七天的开始结束日期
        SimpleDateFormat checkDate = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        Date dateEnd = new Date();
        String endTime = yyyyMMdd.format(dateEnd);
        //获取标题显示的查询结束日期
        String endDate = checkDate.format(dateEnd);
        //往后推6天得出开始日期
        long timeEnd = dateEnd.getTime();
        long sixDay = 1000 * 3600 * 24 * 6;
        long timeStart = timeEnd - sixDay;
        Date dateStart = new Date(timeStart);
        String startTime = yyyyMMdd.format(dateStart);
        //获取标题显示的查询的开始日期
        String startDate = checkDate.format(dateStart);
        getHealthDataDay(startTime, endTime, startDate, endDate);
    }


    /**
     * 心率血糖体重折线图
     *
     * @param Xdata
     * @param Ydata
     */
    private void drawHealthLine(List<String> Xdata, List<Float> Ydata) {
        //"舒张压"提示标题
        linearDown.setVisibility(View.INVISIBLE);
        //设置线的颜色,标题字体颜色
        topKind.setTextColor(getResources().getColor(R.color.search_hint_text_color));
//        topLine.setBackgroundColor(getResources().getColor(R.color.userPhone));
        //X轴显示数据初始化
        List<AxisValue> Xvalues = new ArrayList<>();
        for (int i = 0; i < Ydata.size(); i++) {
            Xvalues.add(new AxisValue(i).setLabel(Xdata.get(i)));
        }
        //折线初始化并添加圆点数据
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int j = 0; j < Ydata.size(); ++j) {
            values.add(new PointValue(j, Ydata.get(j)));
        }
        Line line = new Line(values);
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setColor(getResources().getColor(R.color.colorTooBar));
//        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasPoints(true);
        line.setStrokeWidth(1);
        line.setPointRadius(2);
        line.setPointColor(getResources().getColor(R.color.colorTooBar));
        lines.add(line);
        data = new LineChartData(lines);
        //XY坐标轴初始化并添加数据
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setValues(Xvalues);
        axisX.setHasLines(true);//是否显示X轴网格线
        axisY.setHasLines(true);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setValueLabelBackgroundEnabled(false);//设置是否有数据背景
        data.setValueLabelsTextColor(Color.RED);//设置数据文字颜色
        data.setValueLabelTextSize(12);//设置数据文字大小
        data.setValueLabelTypeface(Typeface.MONOSPACE);//设置数据文字样式
        //LineChartView添加数据
        lineChart.setLineChartData(data);
    }

    /**
     * 血压折线图
     *
     * @param Xdata
     * @param YdataOne
     * @param YdataTwo
     */
    private void drawBloodPresLine(List<String> Xdata, List<Float> YdataOne, List<Float> YdataTwo) {
        //"收缩压"  "舒张压"标题显示
        linearTop.setVisibility(View.VISIBLE);
        linearDown.setVisibility(View.VISIBLE);
        topKind.setText("mmHg");
        //设置字体颜色,以及提示线颜色
        topKind.setTextColor(getResources().getColor(R.color.search_hint_text_color));
//        topLine.setBackgroundColor(getResources().getColor(R.color.text_color_sku_price));
        //X轴显示数据初始化
        List<AxisValue> Xvalues = new ArrayList<>();
        for (int i = 0; i < YdataOne.size(); i++) {
            Xvalues.add(new AxisValue(i).setLabel(Xdata.get(i)));
        }
        //折线初始化并添加圆点数据
        List<Line> lines = new ArrayList<Line>();
        List<PointValue> valuesOne = new ArrayList<PointValue>();
        List<PointValue> valuesTwo = new ArrayList<PointValue>();
        for (int j = 0; j < YdataOne.size(); ++j) {
            valuesOne.add(new PointValue(j, YdataOne.get(j)));
        }
        for (int j = 0; j < YdataTwo.size(); ++j) {
            valuesTwo.add(new PointValue(j, YdataTwo.get(j)));
        }
        Line lineOne = new Line(valuesOne);
        lineOne.setCubic(true);
        lineOne.setHasLabels(true);//曲线的数据坐标是否加上备注
        lineOne.setHasPoints(true);
        lineOne.setColor(getResources().getColor(R.color.text_color_sku_price));
        lineOne.setPointColor(getResources().getColor(R.color.text_color_sku_price));
        setLineProty(lineOne);
        Line lineTwo = new Line(valuesTwo);

        lineTwo.setHasLabels(true);//曲线的数据坐标是否加上备注
        lineTwo.setHasPoints(true);
        lineTwo.setCubic(true);
        lineTwo.setColor(getResources().getColor(R.color.colorTooBar));
        lineTwo.setPointColor(getResources().getColor(R.color.colorTooBar));
        setLineProty(lineTwo);

        lines.add(lineOne);
        lines.add(lineTwo);
        data = new LineChartData(lines);
        //XY坐标轴初始化并添加数据
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setValues(Xvalues);
        axisX.setHasLines(true);//是否显示X轴网格线
        axisY.setHasLines(true);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setValueLabelBackgroundEnabled(false);//设置是否有数据背景
        data.setValueLabelsTextColor(Color.RED);//设置数据文字颜色
        data.setValueLabelTextSize(12);//设置数据文字大小
        data.setValueLabelTypeface(Typeface.MONOSPACE);//设置数据文字样式
        //LineChartView添加数据
        lineChart.setLineChartData(data);
    }

    /**
     * @param line 折线线对象
     *             设置折线的属性大小 形状等
     */
    private void setLineProty(Line line) {
        line.setShape(ValueShape.CIRCLE);
        line.setStrokeWidth(1);
        line.setPointRadius(2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
