package store.chinaotec.com.medicalcare;


/**
 * Created by hxk on 2017/7/12 0012 11:10
 */
public class MyUrl {
    //医生签约协议域名
    public static String BASE_URL_TEN = "http://10.10.0.95:8887";
    //公司外网服务器域名
//    public static String BASE_URL_FIVE = "http://219.144.68.15:8084";
    //公司外网服测试务器域名
    public static String BASE_URL_FIVE ="http://219.144.68.15:8084";
    //测试暂用域名
    public static String BASE_URL_TEXT = "http://10.10.0.95:8080";
    public static String BASE_URL_TWO = "http://106.75.119.205:8081";
    //首页检查更新接口域名
    public static String BASE_URL_SIX = "http://219.144.68.15:8083";
    //自主诊疗预解析接口域名
    public static String PRE_ASYC = "http://219.144.68.146:8000";
    //自主诊疗模块创建病历,问诊,提交问诊,获取问题,解析问题接口主域名
    public static String VOICE_URL = "http://219.144.95.185:8000";
    //注册账户页面,获取证码接口
    public static String REGIS_VERF_CODE = BASE_URL_FIVE + "/fushionbaby-app/register/getMedicalRegisterSmsCode";
    //注册账户页面,验证KEY和验证码接口
    public static String CHECK_REGIS = BASE_URL_FIVE + "/fushionbaby-app/register/checkRegisterVerfiyCode";
    //普通用户最终确认注册接口
    public static String USER_END_REGIST = BASE_URL_FIVE + "/fushionbaby-app/register/medicalPatientCommit";
    //医生用户最终确认注册接口
    public static String DOCT_END_REGIST = BASE_URL_FIVE + "/fushionbaby-app/register/medicalDoctorCommit";
    //获取银行汇总信息接口
    public static String GET_BANKS = BASE_URL_FIVE + "/fushionbaby-app/bank/bankList";
    //登录接口
    public static String USER_LOGIN = BASE_URL_FIVE + "/fushionbaby-app/login/medicalEnter";
    //绑定手机号接口
    public static String BIND_PHONE = BASE_URL_FIVE + "/fushionbaby-app/member/changeTelephone";
    //普通用户个人信息接口
    public static String USER_INFO = BASE_URL_FIVE + "/fushionbaby-app/member/medicalPatientInfo";
    //修改普通用户个人信息接口
    public static String UPDATE_USER_INFO = BASE_URL_FIVE + "/fushionbaby-app/member/changePatientExtra";
    //医生用户个人信息接口
    public static String DOCTOR_INFO = BASE_URL_FIVE + "/fushionbaby-app/member/medicalDoctorInfo";
    //修改医生用户个人信息接口
    public static String UPDATE_DOCTOR_INFO = BASE_URL_FIVE + "/fushionbaby-app/member/changeDoctorExtra";
    //忘记密码页面,确认验证接口
    public static String FINDPAS_SURE = BASE_URL_FIVE + "/fushionbaby-app/forgetpwd/setMedicalNewPwd";
    //忘记密码页面,获取证码接口
    public static String FORGOT_VERF_CODE = BASE_URL_FIVE + "/fushionbaby-app/forgetpwd/getMedicalForgetPwdSmsCode";
    //上传用户图像接口
    public static String UPLOAD_LOGO = BASE_URL_FIVE + "/fushionbaby-app/member/changeHeadImg";
    //更改手机号获取验证码
    public static String CHANGE_PHONE_CODE = BASE_URL_FIVE + "/fushionbaby-app/member/getBindingPhoneSmsCode";
    //更改手机号
    public static String CHANGE_PHONE = BASE_URL_FIVE + "/fushionbaby-app/member/changeTelephone";
    //自动更新接口
    public static String AUTO_UPDATE = BASE_URL_SIX + "/aorun-medical-wap/checkUpdate/updateApp";
    public static String BASE_URL_SEVEN = "http://219.144.95.185:8000";
    //客户端交互接口
    public static String CONNECT_INTERAC = BASE_URL_SEVEN + "/cy/ck/LaunchCommunication.php";
    //创建病例接口
    public static String CREATE_CASE = VOICE_URL + "/cy/ck/AddCase.php";
    //请求预解析接口
    public static String PRE_ANYS = PRE_ASYC + "/preanalysis";
    //开始问诊接口
    public static String WEN_ZHEN = VOICE_URL + "/cy/ck/LaunchCommunication.php";
    //提交问诊
    public static String SUBMIT_ZHEN = VOICE_URL + "/cy/ck/SubmitUFeedback.php";
    //获取问题
    public static String GET_QUSTION = VOICE_URL + "/cy/ck/GetQuestion.php";
    //合成问题
    public static String SYSNC_QUSTION = PRE_ASYC + "/sentence";
    //突发伤病检查接口接续分类接口
    public static String SUDEN_DISE_RESULT = BASE_URL_FIVE + "/fushionbaby-app/memberSickDeal/memberSickDealList";
    //突发疾病&检查结果详情
    public static String CONTENT = BASE_URL_FIVE + "/fushionbaby-app/memberSickDeal/memberSickDealDetail";
    //医院列表接口
    public static String HOSPITAL_LIST_URL = BASE_URL_FIVE + "/fushionbaby-app/hospital/hospitalList?";
    //医院搜索列表接口
    public static String HOSPITAL_SEARCH_LIST = BASE_URL_FIVE + "/fushionbaby-app/hospital/searchHospitalByName?";
    //医院详情
    public static String HOSPITAL_DETAIL = BASE_URL_FIVE + "/fushionbaby-app/hospital/hospitalDetail?";
    //优生优育详情
    public static String KNOWLEDGE_GESTATION_LIST = BASE_URL_FIVE + "/fushionbaby-app/knowledge/gestationList?";
    //首页健康咨询接口
    public static String home_health_info = BASE_URL_FIVE + "/fushionbaby-app/knowledge/healthList";
    //添加急救医生联系方式
    public static String add_help_doctor = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/addMemberCallout";
    //紧急医生列表接口
    public static String help_doctor_list = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/memberCalloutList";
    //获取慢性病的种类接口
    public static String slow_dise_kind = BASE_URL_FIVE + "/fushionbaby-app/chronic/findChronicType";
    //获取分类慢性病信息接口
    public static String slow_dise_list = BASE_URL_FIVE + "/fushionbaby-app/chronic/findChronicListByType";
    //添加慢性病种类接口
    public static String add_dise_kind = BASE_URL_FIVE + "/fushionbaby-app/chronic/addMyChronic";
    //慢性病模块添加病人健康信息接口
    public static String add_health_info = BASE_URL_FIVE + "/fushionbaby-app/chronic/addPatientDate";
    //签约医生列表接口
    public static String sign_doctors = BASE_URL_FIVE + "/fushionbaby-app/doctor/findDoctorList";
    //签约医生详情接口
    public static String sign_doctor_detail = BASE_URL_FIVE + "/fushionbaby-app/doctor/findDoctorDetail";
    //一键呼叫联系人列表接口
    public static String CONTACTS_LIST = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/memberCalloutList";
    //一键呼叫联系人添加接口
    public static String CONTACTS_ADD = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/addMemberCallout";
    //一键呼叫联系人修改接口
    public static String CONTACTS_UPDATE = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/updateMemberCallout";
    //一键呼叫联系人删除接口
    public static String CONTACTS_DELETE = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/deleteMemberCallout";


    //慢性病模块,编辑病人信息接口
    public static String edit_patient_info = BASE_URL_FIVE + "/fushionbaby-app/chronic/upPatient";
    //医生签约协议接口
    public static String sign_protocol = BASE_URL_TEN + "/aorun-medical-wap/signAgreement/at";
    //慢性病模块查询病人健康信息接口
    public static String get_health_info = BASE_URL_FIVE + "/fushionbaby-app/chronic/findPatientDate";
    //医疗电子图书
    public static String medical_book_list = BASE_URL_FIVE + "/fushionbaby-app/medicalBook/medicalBookList";
    //删除求救医生信息
    public static String delete_help_doctor = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/deleteMemberCallout";
    //修改求救医生信息
    public static String edit_help_doctor = BASE_URL_FIVE + "/fushionbaby-app/memberCallout/updateMemberCallout";
    //查询医患论坛详情接口
    public static String DOCTOR_FORUM_LIST = BASE_URL_FIVE + "/fushionbaby-app/bbsTopicMedical/findTopic";
    //发表医患论坛详情接口
    public static String FORUM_ADD = BASE_URL_FIVE + "/fushionbaby-app/bbsTopicMedical/addTopic";
    //评论列表查询接口
    public static String FORUM_FIND_REPLY = BASE_URL_FIVE + "/fushionbaby-app/bbsReplyMedical/findReply";
    //评论增加接口
    public static String FORUM_ADD_REPLY = BASE_URL_FIVE + "/fushionbaby-app/bbsReplyMedical/addReply";
    //点赞取消点赞
    public static String FORUM_THUMB_UP = BASE_URL_FIVE + "/fushionbaby-app/bbsMemberLikes/thumbUp";
    //专科厅
    public static String FIND_TOPIC_CLASS = BASE_URL_FIVE + "/fushionbaby-app/bbsTopicMedical/findTopicClass";
    //提交错误日志
    public static String submit_error_log = BASE_URL_FIVE + "/fushionbaby-app/medicalLog/addMedicalLog";
    //慢性病模块首页获取用户的慢性病信息 已经生成的病人id信息  /根据慢性病id信息获取对应的病人信息
    public static String dise_patient_info = BASE_URL_FIVE + "/fushionbaby-app/chronic/findMyChronic";
    //删除用户的疾病种类
    public static String delete_user_dise = BASE_URL_FIVE + "/fushionbaby-app/chronic/delMyChronic";
    //修改用户的疾病种类
    public static String edit_user_dise = BASE_URL_FIVE + "/fushionbaby-app/chronic/updateMyChronic";
    //病情记录数据添加修改
    public static String add_dise_record = BASE_URL_FIVE + "/fushionbaby-app/chronic/oprDiseaseRecord";

    //天气
    public static final String WeatherLink = "http://jisutianqi.market.alicloudapi.com/weather/query";
    //健康管理
    public static String HEALTH_CONTROL = BASE_URL_FIVE + "/fushionbaby-app/chronic/findMyFitness";
    //添加健康人员
    public static String HEALTH_ADD_PERSON = BASE_URL_FIVE + "/fushionbaby-app/chronic/addPatient";
    //修改健康人员
    public static String HEALTH_UPDATE_PERSON = BASE_URL_FIVE + "/fushionbaby-app/chronic/upPatient";
    //删除用户的疾病种类
    public static String HEALTH_DELETE_PERSON = BASE_URL_FIVE + "/fushionbaby-app/chronic/delPatient";


    //健康记录列表
    public static String FIND_DISEASEBY = BASE_URL_FIVE + "/fushionbaby-app/chronic/findDiseaseByPatient";

    //签约医生列表
    public static final String SIGN_DOCTOR_LIST = BASE_URL_FIVE + "/fushionbaby-app/doctorV2/findDoctorList?";
    //签约医生详情
    public static final String SIGN_DOCTOR_DETAIL = BASE_URL_FIVE + "/fushionbaby-app/doctorV2/findDoctorDetail?";
    //查询医生签约
    public static final String SIGN_DOCTOR_FIND_APPOINT = BASE_URL_FIVE + "/fushionbaby-app/doctorV2/findAppoint?";
    //增加医生签约
    public static final String SIGN_DOCTOR_ADD_APPOINT = BASE_URL_FIVE + "/fushionbaby-app/doctorV2/addAppoint?";
    //签约护士列表
    public static final String SIGN_NURSE_LIST = BASE_URL_FIVE + "/fushionbaby-app/nurse/findNurseList?";
    //医院是否收藏
    public static final String COLLECTION_QUERY = BASE_URL_FIVE + "/fushionbaby-app/memberHospital/getIsCollect?";
    //医院收藏
    public static final String COLLECTION_TYPE = BASE_URL_FIVE + "/fushionbaby-app/memberHospital/updateCollect?";
    //医生是否收藏
    public static final String COLLECTION_DOCTOR_QUERY = BASE_URL_FIVE + "/fushionbaby-app/memberDoctor/getIsCollect?";
    //医生收藏
    public static final String COLLECTION_DOCTOR_TYPE = BASE_URL_FIVE + "/fushionbaby-app/memberDoctor/updateCollect?";
    //收藏医生列表
    public static final String COLLECTION_DOCTOR_LIST = BASE_URL_FIVE + "/fushionbaby-app/memberDoctor/listDoctor?";
    //收藏医院列表
    public static final String COLLECTION_HOSPITAL_LIST = BASE_URL_FIVE + "/fushionbaby-app/memberHospital/listHospital?";
    //大众医学知识列表
    public static final String MEDICAL_KNOWLEDGE_LIST = BASE_URL_FIVE + "/fushionbaby-app/medicalBook/medicalBookPageList?";

    //优生优育推荐
    public static final String RECOMMEND_KNOWLEDGE_LIST = BASE_URL_FIVE + "/fushionbaby-app/knowledge/homeRecommend?";
    //电子图书详情
    public static final String MEDICAL_BOOK_INTRO = BASE_URL_FIVE + "/fushionbaby-app/medicalBookDetail/detailPage?";
    //突发伤病搜索
    public static final String SUDDEN_DISEASE_SEARCH = BASE_URL_FIVE + "/fushionbaby-app/memberSickDeal/memberSickSearch?";

    //去看病
    //创建病人信息
    public static final String INQUIRY_ADD_CASE = BASE_URL_FIVE + "/fushionbaby-app/inquiry/addCase?";
    //问诊
    public static final String INQUIRY_PREANALYSIS = BASE_URL_FIVE + "/fushionbaby-app/inquiry/preanalysis?";
    //绑定设备号
    public static final String INQUIRY_BIND_DEVICE = BASE_URL_FIVE + "/fushionbaby-app/inquiry/bindDevice?";

    //添加联系人
    public static final String ADD_CHRONIC_MEMBER = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/addChronicMember?";
    //获取联系人
    public static final String GET_REF_MEMBER = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/getRefMember?";
    //获取消息
    public static final String GET_REF_MESSAGE = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/getMessage?";
    //同意或拒绝添加联系人
    public static final String OPR_CHECK = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/oprCheck?";
    // 提醒联系人
    public static final String REMIND_MEMBER = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/remindMember?";
    // 获取未读数量
    public static final String GET_NOREADE_MESSAGE_NUM = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/getNoReadeMessageNum?";
    // 消息已读
    public static final String GET_MESSAGE_READ = BASE_URL_FIVE + "/fushionbaby-app/chronicMember/reade?";

}
