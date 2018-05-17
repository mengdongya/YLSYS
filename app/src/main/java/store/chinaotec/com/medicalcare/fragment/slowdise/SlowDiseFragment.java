package store.chinaotec.com.medicalcare.fragment.slowdise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import store.chinaotec.com.medicalcare.activity.AddRecourseCordActivity;
import store.chinaotec.com.medicalcare.activity.EditDiseActivity;
import store.chinaotec.com.medicalcare.activity.HealthInfoAddActivity;
import store.chinaotec.com.medicalcare.activity.PatientInfoAddActivity;
import store.chinaotec.com.medicalcare.adapter.MyTimeLineAdapter;
import store.chinaotec.com.medicalcare.dialog.PickDayDialog;
import store.chinaotec.com.medicalcare.dialog.PickMonthDialog;
import store.chinaotec.com.medicalcare.dialog.PickYearDialog;
import store.chinaotec.com.medicalcare.javabean.HealthDataBean;
import store.chinaotec.com.medicalcare.javabean.PatientBean;
import store.chinaotec.com.medicalcare.utill.BaseUtill;
import store.chinaotec.com.medicalcare.utill.ConvertUtils;
import store.chinaotec.com.medicalcare.utill.ModueUtill;
import store.chinaotec.com.medicalcare.utill.MyLog;
import store.chinaotec.com.medicalcare.utill.NetWorkUtill;
import store.chinaotec.com.medicalcare.utill.ResourseSum;
import store.chinaotec.com.medicalcare.utill.SpUtill;

/**
 * A simple {@link Fragment} subclass.
 * 慢性病管理首页碎片
 */
public class SlowDiseFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.entry_name)
    TextView entryName;
    @Bind(R.id.entry_gender)
    TextView entryGender;
    @Bind(R.id.entry_age)
    TextView entryAge;
    @Bind(R.id.entry_onset_time)
    TextView entryOnsetTime;
    @Bind(R.id.entry_treat_medic)
    TextView entryTreatMedic;
    @Bind(R.id.linear_person_info)
    LinearLayout linearPersonInfo;
    @Bind(R.id.health_info_group)
    RadioGroup healthInfoGroup;
    @Bind(R.id.buy_date)
    RadioGroup buyDate;
    @Bind(R.id.blood_press_slowdise)
    RadioButton bloodPressSlowdise;
    @Bind(R.id.by_day)
    RadioButton byDay;
    @Bind(R.id.top_kind)
    TextView topKind;
    @Bind(R.id.linear_top)
    LinearLayout linearTop;
    @Bind(R.id.linear_down)
    LinearLayout linearDown;
    @Bind(R.id.top_line)
    View topLine;
    @Bind(R.id.heart_rate_slowdise)
    RadioButton heartRateSlowdise;
    @Bind(R.id.blood_suger_slowdise)
    RadioButton bloodSugerSlowdise;
    @Bind(R.id.blood_slipid_slowdise)
    RadioButton bloodSlipidSlowdise;
    @Bind(R.id.time_line)
    RecyclerView timeLine;
    @Bind(R.id.add_course_record)
    LinearLayout addCourseRecord;
    @Bind(R.id.check_start_date)
    TextView checkStartDate;
    @Bind(R.id.check_end_date)
    TextView checkEndDate;
    @Bind(R.id.treat_advise)
    TextView treatAdvise;
    @Bind(R.id.check_advise)
    TextView checkAdvise;

    private TextView diseName;
    private LineChartView lineChart;
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
    //当前疾病对应病人的id
    private String patientId;
    //当前显示页面的编号
    private int pagePosition;
    public static final String TAG = "SlowDiseFragment";
    private Integer patientIds = 0;//病程记录病人的id

    public static SlowDiseFragment newInstance(int position) {
        SlowDiseFragment slowDiseFragment = new SlowDiseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        slowDiseFragment.setArguments(bundle);
        return slowDiseFragment;
    }

    public SlowDiseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slow_dise, container, false);
        ButterKnife.bind(this, view);
        initBasicView(view);
        initBasicData();
        initListener();
        return view;
    }

    private void initBasicView(View view) {
        lineChart = (LineChartView) view.findViewById(R.id.slow_dise_data);
        diseName = (TextView) view.findViewById(R.id.dise_name);
        //默认选中"血压"  "按日"按钮
        bloodPressSlowdise.setChecked(true);
        byDay.setChecked(true);
    }

    private void initBasicData() {
        //判断当前该页面是否可见并加载数据展示
        if (getUserVisibleHint()) {
            //获取当前页面的的编码
            Bundle arguments = getArguments();
            if (arguments != null) {
                int position = arguments.getInt("position");
                //当前页面有数据并刷新展示
                if (position != ResourseSum.default_value) {
                    //保存当前页面的编号
                    SpUtill.putInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition", position);
                    //获取当前展示页面对应的慢性病id,病人id,并获取病人信息展示
                    reshSlodiseData(getActivity(), position);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //修改病人信息通过病人的id
            case R.id.linear_person_info:
                //当前页面的编号
                pagePosition = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition");
                if (pagePosition != ResourseSum.default_value) {
                    patientId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, pagePosition + "patientId");
                    MyLog.d("..修改病人信息时病人id..." + patientId);
                    Intent patient = new Intent(MyApp.getContext(), PatientInfoAddActivity.class);
                    patient.putExtra("patientid", patientId);
                    patient.putExtra("pageNumber", pagePosition);
                    startActivity(patient);
                } else {
                    BaseUtill.toastUtil("请先添加病人慢性病的种类");
                }
                break;
            //添加病人血压 血糖等数据通过病人的id
            case R.id.slow_dise_data:
                //病人的id
                String patientIdTwo = getPatientId();
                MyLog.d("...slow_dise_data..当前病人id..." + patientIdTwo);
                if (!(TextUtils.isEmpty(patientIdTwo))) {
                    Intent disedata = new Intent(getActivity(), HealthInfoAddActivity.class);
                    disedata.putExtra("title","慢性病管理");
                    disedata.putExtra("patientid", patientIdTwo);
                    startActivity(disedata);
                } else {
                    BaseUtill.toastUtil("请先添加病人慢性病的种类");
                }
                break;
            //进入用户疾病修改页面
            case R.id.dise_name:
                //当前页面编号
                pagePosition = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition");
                if (pagePosition != ResourseSum.default_value) {
                    Intent intent = new Intent(getActivity(), EditDiseActivity.class);
                    intent.putExtra("disePosition", pagePosition);
                    startActivity(intent);
                }
                break;
            //添加病程记录
            case R.id.add_course_record:
                pagePosition = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition");
                if (pagePosition != ResourseSum.default_value) {
                    //获取病人的id
                    patientId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, pagePosition + "patientId");
                    Intent intent = new Intent(getActivity(), AddRecourseCordActivity.class);
                    intent.putExtra("patientId", patientId);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * @param context  上下文对象
     * @param position 当前慢性病展示页面的在viewpager中的编号
     *                 获取当前展示页面对应的慢性病id,病人id,并获取病人信息展示
     */
    public void reshSlodiseData(final Context context, final int position) {
        //获取当前登录用户的sid
        final String sid = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, "saveSid");
        //获取疾病种类的名字
        final String name = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, position + "chronicName");
        //慢性病id获取
        final String chronicId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, name + "chronicId");
        MyLog.d("..当前疾病名字..." + name + "..疾病id.." + chronicId + "..用户sid.." + sid);
        //第一次设置显示疾病名字
     /*   if (diseName != null) {
            diseName.setText(name);
        }*/
      /*  if (!(TextUtils.isEmpty(name))) {
            //慢性病id获取
            chronicId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, name + "chronicId");
            MyLog.d("...................当前疾病名字..." + name + "....疾病id......" + chronicId);
        }*/
        //根据用户登录后返回的sid,疾病的id获取病人的信息,病程记录信息并展示
        NetWorkUtill.getPatientInfo(sid, chronicId, new NetWorkUtill.GetPatientInfoListener() {
            @Override
            public void getPatientInfo(String slowDiseName, final PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean patientDtosBean) {
                MyLog.d("..疾病种类名字.." + slowDiseName + "..疾病人名字.." + patientDtosBean.getName() +
                        "..病人性别.." + patientDtosBean.getSex() + "..病人年龄.." + patientDtosBean.getAge() + "..病人起病时间.." + patientDtosBean.getStarTime() + "病人治疗药品.." + patientDtosBean.getMedicine1() + "..疾病的id.." + chronicId);
                //首页病人信息名字展示
                if (entryName != null) {
                    entryName.setText(patientDtosBean.getName());
                }
                //首页疾病名字展示
                if (!(TextUtils.isEmpty(slowDiseName))) {
                    if (!(slowDiseName.equals(name))) {
                        if (diseName != null) {
                            String dise = diseName.getText().toString();
                            if (!(TextUtils.isEmpty(dise))) {
                                diseName.setText("");
                                diseName.setText(slowDiseName);
                                MyLog.d("..anme..slowDiseName..不一样...值分别为..." + name + "..." + slowDiseName);
                            } else {
                                diseName.setText(slowDiseName);
                            }
                        }
                    } else {
                        if (diseName != null) {
                            String dise = diseName.getText().toString();
                            if (!(TextUtils.isEmpty(dise))) {
                                diseName.setText("");
                                diseName.setText(name);
                                MyLog.d("..anme..slowDiseName..一样...值分别为..." + name + "..." + slowDiseName);
                            } else {
                                diseName.setText(name);
                            }
                        }
                    }
                } else {
                    if (diseName != null) {
                        String dise = diseName.getText().toString();
                        if (!(TextUtils.isEmpty(dise))) {
                            diseName.setText("");
                            diseName.setText(name);
                            MyLog.d(".....slowDiseName...为空...name值为..." + name);
                        } else {
                            diseName.setText(name);
                        }
                    }
                }
                //病人性别信息展示
                String patientSex = patientDtosBean.getSex();
                if (!(TextUtils.isEmpty(patientSex)) && entryGender != null) {
                    if (patientDtosBean.getSex().equals("1")) {
                        entryGender.setText("男");
                    } else if (patientDtosBean.getSex().equals("2")) {
                        entryGender.setText("女");
                    }
                }
                //起病时间展示
                if (entryOnsetTime != null) {
                    entryOnsetTime.setText(patientDtosBean.getStarTime());
                }
                //病人年龄展示
                if (entryAge != null) {
                    entryAge.setText(patientDtosBean.getAge());
                }
                //服用药物展示
                if (entryTreatMedic != null) {
                    entryTreatMedic.setText(patientDtosBean.getMedicine1());
                }
                //病人信息名字 性别 起病时间  服用药物信息保存
                ModueUtill.savePatientInfo(patientDtosBean, position, MyApp.getContext());
                patientIds = patientDtosBean.getPatientId();
                //病情记录数据获取展示展示
                if (timeLine != null) {
                    timeLine.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    MyTimeLineAdapter timeLineAdapter = new MyTimeLineAdapter(getActivity(), patientDtosBean.getDiseaseRecordDtos());
                    timeLineAdapter.setOnItemClickListener(new MyTimeLineAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //获取病人的id
                            Intent intent = new Intent(getActivity(), AddRecourseCordActivity.class);
                            intent.putExtra("patientId", patientIds + "");
                            intent.putExtra("diseaseRecordDtos", patientDtosBean.getDiseaseRecordDtos().get(position));
                            startActivity(intent);
                        }
                    });
                    timeLine.setAdapter(timeLineAdapter);
                    if (patientDtosBean.getDiseaseRecordDtos() != null && patientDtosBean.getDiseaseRecordDtos().size() > 1) {
                        countRecyclerViewHeight(patientDtosBean.getDiseaseRecordDtos());
                    }
                }
                //治疗康复建议   复诊检察建议
                if ((!TextUtils.isEmpty(patientDtosBean.getSuggestion()))) {
                    treatAdvise.setText(Html.fromHtml(patientDtosBean.getSuggestion()));
                }
                if (!(TextUtils.isEmpty(patientDtosBean.getTherapeuticRehabilitaitn()))) {
                    checkAdvise.setText(Html.fromHtml(patientDtosBean.getTherapeuticRehabilitaitn()));
                }
            }
        });

        //默认查询前几天的数据并展示没种疾病
        getHealthDataSeven();
    }

    /**
     * 计算病程记录高度
     */
    private Integer timeHeight = 0;

    private void countRecyclerViewHeight(final List<PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean> diseaseRecordDtos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (diseaseRecordDtos != null) {
                            if (timeHeight == 0) {
                                timeHeight = timeLine.getHeight();
                            }
                            int i = timeHeight * diseaseRecordDtos.size();
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, i);
                            layoutParams.setMargins(ConvertUtils.dp2px(getActivity(), 15), 0, 0, ConvertUtils.dp2px(getActivity(), 10));
                            timeLine.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        }).start();
    }

    private void initListener() {
        lineChart.setOnClickListener(this);
        diseName.setOnClickListener(this);
        linearPersonInfo.setOnClickListener(this);
        addCourseRecord.setOnClickListener(this);
        healthInfoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.blood_press_slowdise:
                        drawBloodPresLine(dateList, sysPresList, diasPresList);
                        MyLog.object("日期集合" + dateList);
                        MyLog.object("舒张压数据源..." + sysPresList + "..收缩压数据源.." + diasPresList);
                        drawCoodinate(0, 250);
                        break;
                    case R.id.heart_rate_slowdise:
                        drawHealthLine(dateList, heartRateList);
                        MyLog.object("心率数据集合" + heartRateList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 180);
                        topKind.setText("心率");
                        break;
                    case R.id.blood_suger_slowdise:
                        drawHealthLine(dateList, bloodSuagerList);
                        MyLog.object("血糖数据集合" + bloodSuagerList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 15);
                        topKind.setText("血糖");
                        break;
                    case R.id.blood_slipid_slowdise:
                        drawHealthLine(dateList, bloodFatList);
                        MyLog.object("体重数据集合" + bloodFatList);
                        MyLog.object("日期集合" + dateList);
                        drawCoodinate(0, 150);
                        topKind.setText("体重");
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

    /**
     * @return 病人的id
     * 获取病人的id
     */
    private String getPatientId() {
        String patientId = null;
        //获取当前展示页面的编号
        int pagePosition = SpUtill.getInt(MyApp.getContext(), ResourseSum.Medica_SP, "pagePosition");
        if (pagePosition != ResourseSum.default_value) {
            //获取病人id
            patientId = SpUtill.getString(MyApp.getContext(), ResourseSum.Medica_SP, pagePosition + "patientId");
        }
        return patientId;
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
                String patientId = getPatientId();
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
                String patientId = getPatientId();
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
        String patientId = getPatientId();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
        topKind.setTextColor(getResources().getColor(R.color.userPhone));
        topLine.setBackgroundColor(getResources().getColor(R.color.userPhone));
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
        line.setColor(getResources().getColor(R.color.userPhone));
        line.setHasLines(true);
        line.setHasPoints(true);
        line.setStrokeWidth(1);
        line.setPointRadius(3);
        line.setPointColor(getResources().getColor(R.color.userPhone));
        lines.add(line);
        data = new LineChartData(lines);
        //XY坐标轴初始化并添加数据
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setValues(Xvalues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
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
        topKind.setText("收缩压");
        //设置字体颜色,以及提示线颜色
        topKind.setTextColor(getResources().getColor(R.color.text_color_sku_price));
        topLine.setBackgroundColor(getResources().getColor(R.color.text_color_sku_price));
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
        Line lineTwo = new Line(valuesTwo);

        lineOne.setColor(getResources().getColor(R.color.text_color_sku_price));
        lineOne.setPointColor(getResources().getColor(R.color.text_color_sku_price));
        setLineProty(lineOne);

        lineTwo.setColor(getResources().getColor(R.color.userPhone));
        lineTwo.setPointColor(getResources().getColor(R.color.userPhone));
        setLineProty(lineTwo);

        lines.add(lineOne);
        lines.add(lineTwo);
        data = new LineChartData(lines);
        //XY坐标轴初始化并添加数据
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setValues(Xvalues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
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
        line.setPointRadius(3);
    }
}
