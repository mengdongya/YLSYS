package store.chinaotec.com.medicalcare.utill;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.MyUrl;
import store.chinaotec.com.medicalcare.javabean.CheckRegBean;
import store.chinaotec.com.medicalcare.javabean.ChronicBean;
import store.chinaotec.com.medicalcare.javabean.CodeKeyBean;
import store.chinaotec.com.medicalcare.javabean.DeleteDiseBean;
import store.chinaotec.com.medicalcare.javabean.DoctUserInfo;
import store.chinaotec.com.medicalcare.javabean.EditDiseBean;
import store.chinaotec.com.medicalcare.javabean.FindPasw;
import store.chinaotec.com.medicalcare.javabean.HealthDataBean;
import store.chinaotec.com.medicalcare.javabean.HealthInfoBean;
import store.chinaotec.com.medicalcare.javabean.HealthyRecordBean;
import store.chinaotec.com.medicalcare.javabean.HelpDoctorBeean;
import store.chinaotec.com.medicalcare.javabean.LoginMess;
import store.chinaotec.com.medicalcare.javabean.NormalUserInfo;
import store.chinaotec.com.medicalcare.javabean.OneKeyPersBean;
import store.chinaotec.com.medicalcare.javabean.PatientBean;
import store.chinaotec.com.medicalcare.javabean.SlowDiseKind;
import store.chinaotec.com.medicalcare.javabean.SlowDisesBean;
import store.chinaotec.com.medicalcare.javabean.UpLogoBean;

/**
 * Created by hxk on 2017/7/12 0012 11:10
 */

public class NetWorkUtill {
    private static final String TAG = "NetWorkUtill";

    /**
     * @param userPhone      注册账户手机号
     * @param localMacIP     本机的mac地址
     * @param url            获取验证码的路径
     * @param signa          注册用户的种类
     * @param getKeyListener 忘记密码时,获取验证码,并获取一个key用于接下来的新密码验证.
     *                       注册账户时(普通用户,医生用户),获取验证码,同时获取一个key用于接下来的注册验证
     */
    public static void allGetvercode(String userPhone, String localMacIP, String url, final String signa, final GetKeyListener getKeyListener) {
        OkGo.post(url).params("phone", userPhone).params("sourceCode", 1).params("macAddr", localMacIP).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        CodeKeyBean codeKeyBean = MyApp.getGson().fromJson(s, CodeKeyBean.class);
                        //获取后台传递的绑定的key
                        String data = codeKeyBean.getData();
                        //获取后台相应码
                        int responseCode = codeKeyBean.getResponseCode();
                        if (getKeyListener != null) {
                            getKeyListener.getBindKey(data, responseCode);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        BaseUtill.toastUtil("请求失败");
                    }
                });
    }

    /**
     * @param userPhone 注册账户手机号
     * @param smsKey    获取验证码时获取到的key
     * @param newPas    设置的新密码
     * @param smsCode   第一步获取并手工输入的验证码
     *                  忘记密码页面,设置新密码,并最终校验.
     */
    public static void findPasWordCheck(String userPhone, String smsKey, String newPas, String smsCode, final String mess, final SetPassListener setPassListener) {
        OkGo.post(MyUrl.FINDPAS_SURE).params("phone", userPhone).params("smsCodeBindingKey", smsKey).params("sourceCode", 1).params("password", newPas).
                params("smsCode", smsCode).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                FindPasw findPasw = MyApp.getGson().fromJson(s, FindPasw.class);
                //获取相应码和信息
                int responseCode = findPasw.getResponseCode();
                String msg = findPasw.getMsg();
                //输入验证码有误
                if (responseCode == 500) {
                    BaseUtill.toastUtil(msg);
                } else if (responseCode == 0) {
                    BaseUtill.toastUtil("设置密码成功");
                    if (setPassListener != null) {
                        setPassListener.setPass(responseCode);
                    }
                }
            }
        });
    }

    /**
     * @param userPhone          注册手机号
     * @param url                注册获取验证码的url
     * @param smsKey             获取验证码时得到的key
     * @param code               收到的短信验证码
     * @param nickName           注册填写的用户名
     * @param checkRegisListener 普通用户注册第一步校验验证码等注册信息   医生用户注册第一步校验验证码等注册信息
     */

    public static void checkRegistCode(final String userPhone, String url, final String smsKey, final String code, final String nickName, final CheckRegisListener checkRegisListener, final String userKinid) {
        OkGo.post(url).params("phone", userPhone).params("sourceCode", 1).params("smsCode", code).
                params("smsCodeBindingKey", smsKey).params("nickName", nickName).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d(userKinid + "..验证成功.." + s);
                CheckRegBean checkRegBean = MyApp.getGson().fromJson(s, CheckRegBean.class);
                int responseCode = checkRegBean.getResponseCode();
                if (checkRegisListener != null) {
                    checkRegisListener.getMsgCode(responseCode, checkRegBean.getMsg());
                }
            }
        });
    }

    /**
     * @param phone               注册手机号
     * @param smsCode             手机注册时收到的验证码
     * @param smsKey              手机获取验证码时同时获取到的key
     * @param nickName            注册时填写的用户名
     * @param password            注册时设置的密码
     * @param name                注册人姓名
     * @param sex                 注册人性别
     * @param age                 注册人年龄
     * @param birthDay            注册人出生日期
     * @param nation              注册人民族
     * @param occupation          注册人职业
     * @param domicile            注册人常住地
     * @param height              注册人身高
     * @param weight              注册人体重
     * @param contactType         注册人联系方式  1-电话、2-微信、3-邮件
     * @param socialInsuranceCard 社保卡号
     * @param identityCard        注册人身份证号
     *                            普通用户注册最终提交注册信息验证
     */
    public static void userRegistEndSure(String phone, String smsCode, String smsKey, String nickName, String password, String name, int sex, String age, String birthDay, String nation, String occupation, String domicile, String height, String weight, int contactType, String socialInsuranceCard, String identityCard, String contactInfo, final UserSureListener userSureListener) {
        OkGo.post(MyUrl.USER_END_REGIST).
                params("phone", phone).params("sourceCode", 1).params("smsCode", smsCode).params("smsCodeBindingKey", smsKey).params("nickName", nickName).
                params("password", password).params("name", name).params("sex", sex).params("age", age).params("birthday", birthDay).params("nation", nation).
                params("occupation", occupation).params("domicile", domicile).params("height", height).params("weight", weight).params("contactType", contactType).
                params("socialInsuranceCard", socialInsuranceCard).params("identityCard", identityCard).params("contactInfo", contactInfo).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (userSureListener != null) {
                            userSureListener.userSureEnd();
                            BaseUtill.toastUtil("普通用户注册成功");
                        }
                    }
                });
    }

    /**
     * @param phone              注册手机号码
     * @param smsCode            收到的短信验证码
     * @param smsKey             收到短信验证码时获取的key
     * @param nickName           注册时设置的用户名
     * @param password           设置的密码
     * @param macAddr            mac地址
     * @param doctorName         注册人姓名
     * @param identityCard       注册人身份证号码
     * @param professionalTitle  专业职称
     * @param facilitatingAgency 服务机构
     * @param workingExperience  从业经历
     * @param juniorStrong       专科特长
     * @param bankCode           银行编码
     * @param cardNo             银行卡号
     * @param bankName           银行名字
     * @param bankBranchName     支行名字
     * @param alipayAccount      支付宝账号
     * @param wechatAccount      微信帐号
     * @param cardHolder         收款账户拥有人
     * @param payType            收款账户种类
     *                           医生用户注册最终提交注册信息验证
     */
    public static void doctorRegistEndSure(String phone, String smsCode, String smsKey, String nickName, String password, String macAddr, String doctorName, String identityCard, String professionalTitle, String facilitatingAgency, String workingExperience, String juniorStrong, String bankCode, String cardNo, String bankName, String bankBranchName, String alipayAccount, String wechatAccount, String cardHolder, int payType) {
        OkGo.post(MyUrl.DOCT_END_REGIST).params("phone", phone).params("sourceCode", 1).params("smsCode", smsCode).params("smsCodeBindingKey", smsKey).params("nickName", nickName).
                params("password", password).params("nickName", nickName).params("macAddr", macAddr).params("doctorName", doctorName).params("identityCard", identityCard).
                params("professionalTitle", professionalTitle).params("facilitatingAgency", facilitatingAgency).params("workingExperience", workingExperience).
                params("juniorStrong", juniorStrong).params("bankCode", bankCode).params("cardNo", cardNo).params("bankName", bankName).params("bankBranchName", bankBranchName).
                params("alipayAccount", alipayAccount).params("wechatAccount", wechatAccount).params("cardHolder", cardHolder).params("payType", payType).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BaseUtill.toastUtil("资料已提交，审核通过后可登录");
                    }
                });
    }

    /**
     * @param nickName  用户名
     * @param password  密码
     * @param macAddres mac地址
     * @param pushSing  推送的唯一标识(没有接推送可以先传空)
     *                  医生用户,普通用户登录接口
     */
    public static void userLogin(final String nickName, String password, String macAddres, String pushSing, final LoginListener loginListener) {
        OkGo.post(MyUrl.USER_LOGIN).params("userName", nickName).params("password", password).params("sourceCode", 1).
                params("macAddr", macAddres).params("pushSignAddr", pushSing).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("userLogin...用户登录成功" + s);
                LoginMess loginMess = MyApp.getGson().fromJson(s, LoginMess.class);
                //获取登录后后台返回的相应码
                int responseCode = loginMess.getResponseCode();
                //获取登录后返回的信息对象
                LoginMess.DataBean data = loginMess.getData();
                if (loginListener != null) {
                    loginListener.login(data, responseCode);
                }
            }
        });
    }

    /**
     * @param userSid        用户的sid值
     * @param base64ByBitmap 上传图片的字节流
     * @param upLoadListener
     */
    public static void upLoadUserLogo(String userSid, String base64ByBitmap, final UpLoadListener upLoadListener) {
        OkGo.post(MyUrl.UPLOAD_LOGO).params("sid", userSid).params("base64DataHeadImg", base64ByBitmap).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                UpLogoBean upLogoBean = MyApp.getGson().fromJson(s, UpLogoBean.class);
                //logo的加载路径
                String data = upLogoBean.getData();
                //操作的相应码
                int responseCode = upLogoBean.getResponseCode();
                if (responseCode == 0) {
                    if (upLoadListener != null) {
                        upLoadListener.getLogo(data);
                    }
                }
            }
        });
    }

    /**
     * @param url 获取用户详细信息的接口路径
     * @param sid 用户登录成功后从后台获取的sid值
     *            医生用户登陆后获取个人全部信息的接口
     */
    public static void getDoctInfo(String url, String sid, final DoctInfoListener doctInfoListener) {
        OkGo.post(url).params("sid", sid).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                DoctUserInfo doctUserInfo = MyApp.getGson().fromJson(s, DoctUserInfo.class);
                if (doctInfoListener != null) {
                    doctInfoListener.getDoctorInfo(doctUserInfo.getData());
                }
            }
        });
    }

    /**
     * 医生用户登陆后获取个人全部信息的监听回调
     */
    public interface DoctInfoListener {
        /**
         * @param dataBean 医生用户全部信息的java对象
         */
        void getDoctorInfo(DoctUserInfo.DataBean dataBean);
    }

    /**
     * @param url 获取用户详细信息的接口路径
     * @param sid 用户登录成功后从后台获取的sid值
     *            普通用户登陆后获取个人全部信息 接口
     */
    public static void getUserInfo(String url, String sid, final UserInfoListener userInfoListener) {
        OkGo.post(url).params("sid", sid).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..当前用户的详细信息.." + s);
                NormalUserInfo.DataBean data = MyApp.getGson().fromJson(s, NormalUserInfo.class).getData();
                if (userInfoListener != null) {
                    if (data != null) {
                        userInfoListener.getUserInfo(data);
                    }
                }
            }
        });
    }

    /**
     * 普通用户登陆后获取个人全部信息监听回调
     */
    public interface UserInfoListener {
        /**
         * @param data 包含用户全部个人信息的java对象
         */
        void getUserInfo(NormalUserInfo.DataBean data);
    }

    /**
     * @param url       获取短信验证码接口
     * @param sid       当前登录用户的sid
     * @param telephone 获取验证码的手机号
     * @param macAddr   当前mac地址
     *                  改变手机号时,获取短信验证码(普通用户   医生用户)
     */
    public static void changePhone(String url, String sid, String telephone, String macAddr, final ChangePhoneListenr changePhoneListenr) {
        OkGo.post(url).params("sid", sid).params("telephone", telephone).params("sourceCode", 1).
                params("macAddr", macAddr).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                CodeKeyBean codeKeyBean = MyApp.getGson().fromJson(s, CodeKeyBean.class);
                //获取后台传递的绑定的key
                String data = codeKeyBean.getData();
                //获取后台相应码
                int responseCode = codeKeyBean.getResponseCode();
                //获取相应信息
                String msg = codeKeyBean.getMsg();
                if (responseCode == 0) {
                    if (changePhoneListenr != null) {
                        changePhoneListenr.changePhone(data);
                    }
                }
            }
        });
    }

    /**
     * @param url       确认接口
     * @param sid       用户sid值
     * @param telephone 手机号
     * @param smscode   短信验证码
     * @param key       返回的key
     *                  更改手机号最终
     */
    public static void sureChangePhone(String url, String sid, String telephone, String smscode, String key) {
        OkGo.post(url).params("sid", sid).params("telephone", telephone).params("smsCode", smscode).
                params("smsCodeBindingKey", key).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                BaseUtill.toastUtil("修改电话成功");
            }
        });
    }


    public static void getGetHalthyRecord(String patientId, Integer pageIndex, final GetHalthyRecordListener getHalthyRecordListener) {
        OkGo.post(MyUrl.FIND_DISEASEBY).params("patientId", patientId).params("pageIndex", pageIndex).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                HealthyRecordBean fromJson = MyApp.getGson().fromJson(s, HealthyRecordBean.class);
                getHalthyRecordListener.getHalthyRecord(fromJson);
            }
        });
    }

    public interface GetHalthyRecordListener {
        void getHalthyRecord(HealthyRecordBean fromJson);
    }

    /**
     * 监听并获取得到换手机验证码的同时得到的key
     */
    public interface GetKeyListener {
        void getBindKey(String key, int code);
    }

    /**
     * 获取设置密码返回的code值
     */
    public interface SetPassListener {
        void setPass(int code);
    }

    /**
     * 获取校验后得到的返回码
     */
    public interface CheckRegisListener {
        void getMsgCode(int code, String msg);
    }

    public interface UserSureListener {
        void userSureEnd();
    }

    /**
     * 登陆成功后获取用户详细信息对象,相应码,返回json字符串信息
     */
    public interface LoginListener {
        void login(LoginMess.DataBean dataBean, int code);
    }

    public interface UpLoadListener {
        void getLogo(String logo);
    }


    public interface ChangePhoneListenr {
        void changePhone(String data);
    }

    /**
     * @param getHealthInfoListener 用户首页获取健康咨询信息
     */
    public static void getHealthInfo(String url, final GetHealthInfoListener getHealthInfoListener) {
        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                HealthInfoBean healthInfoBean = MyApp.getGson().fromJson(s, HealthInfoBean.class);
                List<HealthInfoBean.DataBean> data = healthInfoBean.getData();
                if (getHealthInfoListener != null) {
                    getHealthInfoListener.getHealthInfo(data);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                MyLog.object("首页获取信息失败联网失败.." + e);
                MyLog.d("首页获取信息失败联网失败.." + e);
            }
        });
    }

    /**
     * 用户首页获取健康咨询信息
     */
    public interface GetHealthInfoListener {
        void getHealthInfo(List<HealthInfoBean.DataBean> data);
    }

    /**
     * @param sid   登录用户sid
     * @param name  添加紧急医生名字
     * @param phone 添加紧急医生电话
     *              添加紧急医生接口
     */
    public static void addHelpDoctor(String sid, String name, String phone) {
        OkGo.post(MyUrl.add_help_doctor).params("sid", sid).params("calloutName", name).params("telephone", phone).
                params("type", 2).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess.添加紧急医生成功." + s);
            }
        });
    }

    /**
     * @param sid                    用户登陆后返回的sid
     * @param pageIndex              求救医生列表页码
     * @param getHelpDoctorsListener 获取紧急求救医生列表接口
     */
    public static void getHelpDoctors(String sid, int pageIndex, final GetHelpDoctorsListener getHelpDoctorsListener) {
        OkGo.post(MyUrl.help_doctor_list).params("sid", sid).params("pageIndex", pageIndex).params("type", 2).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("..获取晋级求救医生列表数据成功" + s);
                        String responCode = BaseUtill.getResponCode(s);
                        //获取数据成功
                        if (responCode.equals("0")) {
                            HelpDoctorBeean helpDoctorBeean = MyApp.getGson().fromJson(s, HelpDoctorBeean.class);
                            List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberCalloutList = helpDoctorBeean.getData().getMemberCalloutList();
                            if (getHelpDoctorsListener != null) {
                                if (memberCalloutList.size() != 0) {
                                    getHelpDoctorsListener.getHelpDoctorList(memberCalloutList, true);
                                } else {
                                    getHelpDoctorsListener.getHelpDoctorList(memberCalloutList, false);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 获取紧急求救医生列表接口
     */
    public interface GetHelpDoctorsListener {
        void getHelpDoctorList(List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberCalloutList, boolean getData);
    }

    /**
     * 慢性病模块首页获取添加的慢性病种类名字,和生成的病人id
     */
    public static void getUserChronicData(String sid, final GetChronicDataListener getChronicDataListener) {
        OkGo.post(MyUrl.dise_patient_info).params("sid", sid).execute(new StringCallback() {
            //慢性病信息集合
            private List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList = new ArrayList();
            //是否获取到疾病病人的数据的标志
            private boolean dataEmpty;
            //病人id集合
            private List<String> patientIdList = new ArrayList();
            //疾病memberid集合
            private List<String> diseMemberIdList = new ArrayList();

            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("..当前用户慢性病信息病人信息..." + s);
                //获取数据对象并判断
                ChronicBean.DataBean data = MyApp.getGson().fromJson(s, ChronicBean.class).getData();
                List<ChronicBean.DataBean.ChronicPatientDtosBean> chronicPatientDtos = data.getChronicPatientDtos();
                if (chronicPatientDtos != null) {
                    for (int i = 0; i < chronicPatientDtos.size(); i++) {
                        //添加慢性病信息
                        diseList.add(chronicPatientDtos.get(i).getChronicDto());
                        //初始化chronicMemberId数据集合
                        diseMemberIdList.add(chronicPatientDtos.get(i).getChronicMemberId());
                        //添加病人id
                        patientIdList.add(chronicPatientDtos.get(i).getPatientDtos().get(0).getPatientId());
                    }
                    dataEmpty = true;
                } else {
                    dataEmpty = false;
                }
                if (getChronicDataListener != null) {
                    getChronicDataListener.getChronicData(patientIdList, diseList, diseMemberIdList, dataEmpty);
                    /******************************新增病程记录******************************/
                    getChronicDataListener.getCourseOfDiseaseContent(chronicPatientDtos);
                }


            }
        });
    }


    /**
     * 慢性病模块首页获取添加的慢性病种类名字,和生成的病人id  是否获取到病人 疾病数据的标志获取到数据为true不然为false
     */
    public interface GetChronicDataListener {
        void getChronicData(List<String> patientIdList, List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList, List<String> diseMemberIdList, boolean dataEmpty);

        void getCourseOfDiseaseContent(List<ChronicBean.DataBean.ChronicPatientDtosBean> chronicPatientDtos);
    }

    /**
     * @param diseId 慢性病的id
     * @param sid    用户登陆后返回的sid
     *               用户慢性病种类添加页面,添加慢性病种类
     */
    public static void addSlowDiseData(String diseId, String sid, final AddChoronicDiseaseListener addChoronicDiseaseListener) {
        OkGo.post(MyUrl.add_dise_kind).params("chronicId", diseId).params("sid", sid).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..慢性病种类添加成功.." + s);
                String responCode = BaseUtill.getResponCode(s);
                //添加该种类慢性病是否重复添加
                boolean addChronic;
                if (responCode.equals("0")) {  //不重复添加
                    addChronic = false;
                } else {//重复添加
                    addChronic = true;
                }
                if (addChoronicDiseaseListener != null) {
                    addChoronicDiseaseListener.addChronicDisease(addChronic, s);
                }
            }
        });
    }

    /**
     * 用户慢性病种类添加页面,添加慢性病种类
     */
    public interface AddChoronicDiseaseListener {
        /**
         * @param add  添加该类慢性病是否重复添加   true表示重复添加   false表示没有重复添加
         * @param json 返回的json字符串
         */
        void addChronicDisease(boolean add, String json);
    }

    /**
     * @param patientid    病人id
     * @param patientName  病人名字
     * @param sex          病人性别
     * @param takeMedicine 病人服用药物
     *                     慢性病模块修改病人信息页面修改病人的信息
     */
    public static void editPatientInfo(String patientid, String patientName, int sex, String age, String takeMedicine, String startTime) {
        OkGo.post(MyUrl.edit_patient_info).params("patientId", patientid).params("name", patientName).
                params("sex", sex).params("age", age).params("medicine1", takeMedicine).params("starTime", startTime).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("onSuccess病人信息修改成功..." + s);
                    }
                });
    }

    /**
     * @param sid       用户的sid
     * @param chronicId 慢性病的id
     *                  慢性病模块获取病人信息数据
     */
    public static void getPatientInfo(String sid, String chronicId, final GetPatientInfoListener getPatientInfoListener) {
        OkGo.post(MyUrl.dise_patient_info).params("sid", sid).params("chronicId", chronicId).execute(new StringCallback() {
            //病程信息数据
            private List<PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean.DiseaseRecordDtosBean> diseaseRecordDtos;
            //病人信息数据
            private PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean patientDtosBean;
            //疾病名字
            private String diseName;

            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..当前病人疾病信息为.." + s);
                List<PatientBean.DataBean.ChronicPatientDtosBean> chronicPatientDtos = MyApp.getGson().fromJson(s, PatientBean.class).getData().getChronicPatientDtos();
                if (chronicPatientDtos != null) {
                    for (int i = 0; i < chronicPatientDtos.size(); i++) {
                        //慢性病名字
                        diseName = chronicPatientDtos.get(i).getChronicDto().getName();
                        //病人信息数据  病程记录   治疗康复建议  复诊检察建议
                        patientDtosBean = chronicPatientDtos.get(i).getPatientDtos().get(0);
                        if (patientDtosBean != null) {
                            if (getPatientInfoListener != null) {
                                getPatientInfoListener.getPatientInfo(diseName, patientDtosBean);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 慢性病模块获取病人信息数据
     */
    public interface GetPatientInfoListener {
        /**
         * @param slowDiseName    慢性病名字
         * @param patientDtosBean 病人信息数据  病程记录   治疗康复建议  复诊检察建议
         */
        void getPatientInfo(String slowDiseName, PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean patientDtosBean);
    }

    /**
     * @param patientId
     * @param systolicPressure  收缩压
     * @param heartRate         心率
     * @param bloodSugar        血糖
     * @param bloodFat          血脂
     * @param diastolicPressure 舒张压
     *                          慢性病模块,添加慢性病数据
     */
    public static void addHealthInfo(String patientId, String systolicPressure, String heartRate, String bloodSugar, String bloodFat, String diastolicPressure) {
        OkGo.post(MyUrl.add_health_info).params("patientId", patientId).params("systolicPressure", systolicPressure).params("heartRate", heartRate).
                params("bloodSugar", bloodSugar).params("bloodFat", bloodFat).params("diastolicPressure", diastolicPressure).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..健康信息添加成功.." + s);
            }
        });
    }

    /**
     * @param patientId 病人id
     * @param starDate  按日查询的开始日期
     * @param endDate   按日查询的结束日期
     * @param dateType  查询的种类按年   按月  按天   1按天  2按月   3按年
     *                  获取病人的健康数据血脂 血糖等信息数据
     */
    public static void getHelthData(String patientId, String starDate, String endDate, int dateType, final HealthDataListener healthDataListener) {
        OkGo.post(MyUrl.get_health_info).params("patientId", patientId).params("dateType", dateType).params("starDate", starDate).
                params("endDate", endDate).execute(new StringCallback() {
            private List<HealthDataBean.DataBean.PatientDateViewDtosBean> patientDateViewDtos;

            @Override
            public void onSuccess(String s, Call call, Response response) {
                HealthDataBean.DataBean data = MyApp.getGson().fromJson(s, HealthDataBean.class).getData();
                if (data != null) {
                    patientDateViewDtos = data.getPatientDateViewDtos();
                    if (healthDataListener != null) {
                        healthDataListener.getHealthData(patientDateViewDtos);
                    }
                }
            }
        });
    }

    /**
     * 获取病人的健康信息数据血脂 血糖等信息数据
     */
    public interface HealthDataListener {
        /**
         * @param patientDateViewDtos 包含血脂  血糖等信息数据的集合
         */
        void getHealthData(List<HealthDataBean.DataBean.PatientDateViewDtosBean> patientDateViewDtos);
    }

    /**
     * @param slowDiseKinidListener 慢性病种类获取数据监听回调
     *                              慢性病添加页面获取慢性病种类数据
     */
    public static void getSlowDiseKnidData(final SlowDiseKinidListener slowDiseKinidListener) {
        OkGo.get(MyUrl.slow_dise_kind).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes = MyApp.getGson().fromJson(s, SlowDiseKind.class).getData().getMedicalTypes();
                if (slowDiseKinidListener != null) {
                    slowDiseKinidListener.getSlowDiseKnidData(medicalTypes);
                }
            }
        });
    }

    /**
     * 慢性病添加页面获取慢性病种类数据
     */
    public interface SlowDiseKinidListener {
        /**
         * @param medicalTypes 慢性病种类数据源集合
         */
        void getSlowDiseKnidData(List<SlowDiseKind.DataBean.MedicalTypesBean> medicalTypes);
    }

    /**
     * @param typeId 慢性病种类的id
     *               根据慢性病种类的id获取该类慢性病数据
     */
    public static void getSlowDiseDataByKinid(int typeId, final GetSlowDiseDataListener getSlowDiseDataListener) {
        OkGo.post(MyUrl.slow_dise_list).params("typeId", typeId).execute(new StringCallback() {
            @Override
            public void onSuccess(final String s, Call call, Response response) {
                List<SlowDisesBean.DataBean.ChronicListBean> chronicDataList = MyApp.getGson().fromJson(s, SlowDisesBean.class).getData().getChronicList();
                if (getSlowDiseDataListener != null) {
                    getSlowDiseDataListener.getSlowDiseData(chronicDataList);
                }
            }
        });
    }

    /**
     * 根据慢性病种类的id获取该类慢性病数据
     */
    public interface GetSlowDiseDataListener {
        /**
         * @param chronicDataList 该类慢性病数据集合
         */
        void getSlowDiseData(List<SlowDisesBean.DataBean.ChronicListBean> chronicDataList);
    }

    /**
     * @param sid             当前登录用户的sid
     * @param memberCalloutId 删除当前求救医生的id
     *                        添加求救医生页面删除求医生信息
     */
    public static void deleteDoctor(String sid, String memberCalloutId, final AbsCallback absCallback) {
        OkGo.post(MyUrl.delete_help_doctor).params("sid", sid).params("memberCalloutId", memberCalloutId).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..求救医生删除成功");
                absCallback.onSuccess(s, call, response);
            }
        });
    }

    /**
     * @param saveSid 用户登录后返回的sid
     *                一键呼叫页面获取一键呼叫联系人信息
     */
    public static void getOneKeyContact(String saveSid, final GetOneKeyContactListener getOneKeyContactListener) {
        OkGo.get(MyUrl.CONTACTS_LIST).params("sid", saveSid).params("pageIndex", 1).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..一键呼叫联系人列表.." + s);
                OneKeyPersBean oneKeyPersBean = MyApp.getGson().fromJson(s, OneKeyPersBean.class);
                List<OneKeyPersBean.DataBean.MemberCalloutListBean> list = oneKeyPersBean.getData().getMemberCalloutList();
                if (getOneKeyContactListener != null) {
                    if (list != null && list.size() > 0) {
                        getOneKeyContactListener.getContact(list);
                    }
                }
            }
        });
    }

    /**
     * 一键呼叫页面获取一键呼叫联系人信息的监听回调
     */
    public interface GetOneKeyContactListener {
        /**
         * @param memberCalloutList 一键呼叫联系人的数据集合
         */
        void getContact(List<OneKeyPersBean.DataBean.MemberCalloutListBean> memberCalloutList);
    }

    /**
     * @param saveSid         用户登陆后返回的sid
     * @param memberCalloutId 求救医生信息的id
     * @param editName        求救医生名字
     * @param editPhone       求救医生的电话
     */
    public static void editHelpDoctor(String saveSid, String memberCalloutId, String editName, String editPhone) {
        OkGo.post(MyUrl.edit_help_doctor).params("sid", saveSid).params("memberCalloutId", memberCalloutId).
                params("calloutName", editName).params("telephone", editPhone).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("onSuccess..求救医生信息修改成功.." + s);
            }
        });
    }

    /**
     * @param content   报错异常信息
     * @param version   版本
     * @param phoneName 手机型号
     * @param sdkNum    系统版本
     *                  提交应用报错的异常信息
     */
    public static void submitErrorLog(String saveSid, String content, String version, String phoneName, String sdkNum) {
        OkGo.post(MyUrl.submit_error_log).params("content", content).params("sid", saveSid).params("version", version).params("phoneName", phoneName).
                params("sdkNum", sdkNum).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("报错日志提交成功" + s);
            }
        });
    }

    /**
     * 删除用户的疾病种类的监听回调
     */
    public interface DeleteUserDiseListener {
        /**
         * 删除用户的疾病种类监听回调
         */
        void deleteUserDise();
    }

    /**
     * @param userSid         用户登陆后得到的sid
     * @param chronicMemberId 当前删除疾病的chronicMemberId
     *                        删除用户的疾病种类
     */
    public static void deleteUserDise(String userSid, String chronicMemberId, final DeleteUserDiseListener deleteUserDiseListener) {
        OkGo.post(MyUrl.delete_user_dise).params("sid", userSid).params("chronicMemberId", chronicMemberId).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MyLog.d("删除用户的慢性病成功..." + s);
                        String responseCode = MyApp.getGson().fromJson(s, DeleteDiseBean.class).getResponseCode();
                        if (responseCode.equals("0")) {
                            if (deleteUserDiseListener != null) {
                                deleteUserDiseListener.deleteUserDise();
                            }
                        }
                    }
                });
    }

    /**
     * 修改用户的慢性病种类的监听回调
     */
    public interface EditUserDiseListener {
        /**
         * @param data 修改完用户的慢性病种类后返回的数据
         */
        void editDise(EditDiseBean.DataBean data);
    }

    /**
     * @param sid             用户登陆后得到的sid
     * @param chronicMemberId 当前修改疾病的chronicMemberId
     * @param newChronicId    修改后最终的id
     *                        修改用户的慢性病种类
     */
    public static void editUserDise(String sid, String chronicMemberId, String newChronicId, final EditUserDiseListener editUserDiseListener) {
        OkGo.post(MyUrl.edit_user_dise).params("newChronicId", newChronicId).params("sid", sid).
                params("chronicMemberId", chronicMemberId).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                EditDiseBean.DataBean data = MyApp.getGson().fromJson(s, EditDiseBean.class).getData();
                MyLog.d("修改用户的慢性病成功..." + s);
                if (editUserDiseListener != null) {
                    editUserDiseListener.editDise(data);
                }
            }
        });
    }

    /**
     * 病情记录添加页面添加病情记录
     */
    public interface RecourseRecordListener {
        /**
         * @param s 添加病情记录后返回的json字符串
         */
        void getContent(String s);
    }

    /**
     * @param patientId   病人ID
     * @param bloodFatTg  甘油三酯
     * @param bloodFatLdl 低密度脂蛋白
     * @param bloodFatHdl 高密度脂蛋白
     * @param fundoscopy  眼底检查期数
     * @param diseaseDesc 病情描述
     *                    病情记录添加页面添加病情记录
     */
    public static void addRecourseRecord(Integer oprType, String patientId, String bloodFatTg, String bloodFatLdl, String bloodFatHdl, String fundoscopy, String diseaseDesc, Integer diseaseRecordId, final RecourseRecordListener mRecordListener) {
        OkGo.post(MyUrl.add_dise_record).params("oprType", oprType).params("patientId", patientId).params("bloodFatTg", bloodFatTg).params("bloodFatLdl", bloodFatLdl).
                params("bloodFatHdl", bloodFatHdl).params("fundoscopy", fundoscopy).params("diseaseDesc", diseaseDesc).params("diseaseRecordId", diseaseRecordId).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                MyLog.d("添加病情记录成功" + s);
                mRecordListener.getContent(s);
            }
        });
    }
}
