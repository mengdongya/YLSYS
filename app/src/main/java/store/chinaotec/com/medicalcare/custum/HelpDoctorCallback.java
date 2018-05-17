package store.chinaotec.com.medicalcare.custum;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import store.chinaotec.com.medicalcare.adapter.MyHelpDoctorAdapter;
import store.chinaotec.com.medicalcare.javabean.HelpDoctorBeean;
import store.chinaotec.com.medicalcare.utill.MyLog;

/**
 * Created by hxk on 2017/10/30 0030 13:49
 * 求救医生列表自定义左右滑动删除条目
 */

public class HelpDoctorCallback extends ItemTouchHelper.SimpleCallback {
    private List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberCalloutList;
    private MyHelpDoctorAdapter myHelpDoctorAdapter;
    private DeleteDoctorListener deleteDoctorListener;

    public HelpDoctorCallback(int dragDirs, int swipeDirs,List<HelpDoctorBeean.DataBean.MemberCalloutListBean> memberCalloutList,MyHelpDoctorAdapter myHelpDoctorAdapter, DeleteDoctorListener deleteDoctorListener) {
        super(dragDirs, swipeDirs);
        this.memberCalloutList = memberCalloutList;
        this.myHelpDoctorAdapter = myHelpDoctorAdapter;
        this.deleteDoctorListener = deleteDoctorListener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int itemCount = myHelpDoctorAdapter.getItemCount();
        MyLog.d("当前列表条目数.删除前.." + itemCount + "...当前列表数据源个数.." + memberCalloutList.size());
        int adapterPosition = viewHolder.getAdapterPosition();
        //当前删除求医生的memberCalloutId
        String memberCalloutId = memberCalloutList.get(adapterPosition).getMemberCalloutId();
        //求救医生数据集合是否还有数据  true表示集合没有数据了空了  false表示还有数据
        boolean doctorDataEmpty;
        memberCalloutList.remove(memberCalloutList.get(adapterPosition));
        if (memberCalloutList.size() == 0) {
            doctorDataEmpty = true;
        } else {
            doctorDataEmpty = false;
        }
        if (deleteDoctorListener != null) {
            deleteDoctorListener.deleteDoctor(memberCalloutId, doctorDataEmpty);
        }
        myHelpDoctorAdapter.notifyDataSetChanged();
    }

    /**
     * 删除求救医生监听回调
     */
    public interface DeleteDoctorListener {
        /**
         * @param memberCalloutId 求救医生信息id
         */
        void deleteDoctor(String memberCalloutId, boolean doctorDataEmpty);
    }
}
