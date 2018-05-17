package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import java.util.List;

import store.chinaotec.com.medicalcare.MyApp;
import store.chinaotec.com.medicalcare.fragment.slowdise.SlowDiseFragment;
import store.chinaotec.com.medicalcare.javabean.AddDiseBean;
import store.chinaotec.com.medicalcare.javabean.ChronicBean;
import store.chinaotec.com.medicalcare.javabean.LoginMess;
import store.chinaotec.com.medicalcare.javabean.PatientBean;

/**
 * Created by hxk on 2017/10/12 0012 13:36
 * 模块功能抽取
 */

public class ModueUtill {
    /**
     * @param idList   病人ID集合
     * @param diseList 慢性病信息集合
     *                 保存获取到的等慢性病信息名字和id,以及病人id到SP
     */
    public static void saveDiseInfo(List<String> idList, List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList, List<String> memberIdList) {
        for (int i = 0; i < idList.size(); i++) {
            //保存疾病的chronicMemberId
            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, i + "chronicMemberId", memberIdList.get(i));
            //慢性病名字
            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, i + "chronicName", diseList.get(i).getName());
            //病人ID
            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, i + "patientId", idList.get(i));
            //慢性病id
            SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, diseList.get(i).getName() + "chronicId", diseList.get(i).getChronicId());
            MyLog.d("保存首页获取的.疾病名字.." + diseList.get(i).getName() + "..病人id.." + idList.get(i) + "..慢性病id.." + diseList.get(i).getChronicId());
        }
    }

    /**
     * @param diseList      碎片集合慢性病信息集合
     * @param slowTablayout 展示慢性病类别的标题头控件
     *                      根据获取到的慢性病种类,添加fragment
     */
    public static void addSlowDisePage(List<Fragment> fragmentList, List<ChronicBean.DataBean.ChronicPatientDtosBean.ChronicDtoBean> diseList, TabLayout slowTablayout, List<String> titleDiseList) {
        MyLog.d("addSlowDisePage...当前页面数.." + fragmentList.size() + "..当前慢性病种类.." + titleDiseList.size());
        if (fragmentList.size() != 0 && titleDiseList.size() != 0) {
            fragmentList.clear();
            titleDiseList.clear();
        }
        slowTablayout.removeAllTabs();
        for (int i = 0; i < diseList.size(); i++) {
            //添加TabLayout头标签
            slowTablayout.addTab(slowTablayout.newTab().setText(diseList.get(i).getName()));
            //添加疾病首页标题
            titleDiseList.add(diseList.get(i).getName());
            //添加fragment页面
            fragmentList.add(SlowDiseFragment.newInstance(i));
        }
        MyLog.d("addSlowDisePage.,,,,,,for......当前页面数.." + fragmentList.size() + "..当前慢性病种类.." + titleDiseList.size());
    }

    /**
     * @param context  上下文对象
     * @param dataBean 用户登陆后返回的包含用户注册信息的java对象
     *                 登录页面登陆后保存用户的一些个人信息   普通用户注册,填写注册信息详情页面点击"立即注册"按钮后直接登录,并且跳转到首页显示登录状态
     */
    public static void saveLoginUserInfo(Context context, LoginMess.DataBean dataBean) {
        //用户返回的sid  和用户id一次保存
        SpUtill.putString(context, ResourseSum.Medica_SP, "sid", dataBean.getSid());
        SpUtill.putString(context, ResourseSum.Medica_SP, "memberId", dataBean.getMemberId());
        //用户返回的sid  和用户id二次保存
        SpUtill.putString(context, ResourseSum.Medica_SP, "saveSid", dataBean.getSid());
        SpUtill.putString(context, ResourseSum.Medica_SP, "saveMemberId", dataBean.getMemberId());
        //用户的种类
        SpUtill.putInt(context, ResourseSum.Medica_SP, "usertype", dataBean.getMemberType());
        //用户的用户名
        SpUtill.putString(context, ResourseSum.Medica_SP, "username", dataBean.getNickName());
        //用户的电话
        SpUtill.putString(context, ResourseSum.Medica_SP, "userphone", dataBean.getTelephone());
        //用户的头像
        SpUtill.putString(context,ResourseSum.Medica_SP, "logo",dataBean.getImgPath());
    }

    /**
     * @param context 上下文对象
     *                退出该应用时清除一些登录保存的信息
     */
    public static void loginOutClean(Context context) {
        //清空病历号
//        SpUtill.putString(context, ResourseSum.Medica_SP, "caseCode", "");
        //清空sessionid
//        SpUtill.putString(context, ResourseSum.Medica_SP, "sid", "");
        //清空保存的用户年龄
        SpUtill.putString(context, ResourseSum.Medica_SP, "age", "");
        //用户的id
        SpUtill.putString(context, ResourseSum.Medica_SP, "memberId", "");
    }

    /**
     * @param patientDtosBean 当前种类疾病对应的病人信息
     * @param position        当前页面编号
     * @param context         上下文对象
     *                        获取疾病对应的病人信息后保存
     */
    public static void savePatientInfo(PatientBean.DataBean.ChronicPatientDtosBean.PatientDtosBean patientDtosBean, int position, Context context) {
        SpUtill.putString(context, ResourseSum.Medica_SP, position + "patientName", patientDtosBean.getName());
        SpUtill.putString(context, ResourseSum.Medica_SP, position + "patientSex", patientDtosBean.getSex());
        SpUtill.putString(context, ResourseSum.Medica_SP, position + "startTime", patientDtosBean.getStarTime());
        SpUtill.putString(context, ResourseSum.Medica_SP, position + "treatMedicine", patientDtosBean.getMedicine1());
        SpUtill.putString(context, ResourseSum.Medica_SP, position + "patientAge", patientDtosBean.getAge());
    }

    /**
     * @param json         用户添加完疾病种类后返回的json
     * @param addPosition  添加疾病种类首页的显示编号
     * @param slowDiseName 添加疾病的名字
     * @param slowDiseId   添加疾病的id
     *                     添加用户的疾病种类后保存 疾病名字  疾病id 对应的病人id 用户慢性病添加页面
     */
    public static void addDiseKindSaveData(String json, int addPosition, String slowDiseName, String slowDiseId) {
        //添加当前慢性病的id根据此id可以获取病人信息
        int chronicId = MyApp.getGson().fromJson(json, AddDiseBean.class).getData().getChronicId();
        //该慢性病对应的病人id
        int patientId = MyApp.getGson().fromJson(json, AddDiseBean.class).getData().getPatientId();
        //保存添加疾病对应的病人id 慢性病id 疾病名字
        SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, addPosition + "patientId", patientId + "");
        SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, slowDiseName + "chronicId", chronicId + "");
        SpUtill.putString(MyApp.getContext(), ResourseSum.Medica_SP, addPosition + "chronicName", slowDiseName);
        MyLog.d("clickItem...慢性病添加页面..添加的慢性病名字.." + slowDiseName + "..添加的慢性病id.." + slowDiseId);
    }
}
