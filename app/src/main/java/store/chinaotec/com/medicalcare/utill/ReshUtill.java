package store.chinaotec.com.medicalcare.utill;

import android.content.Context;
import android.support.v4.app.Fragment;
import java.util.List;
import store.chinaotec.com.medicalcare.fragment.MineFragment;

/**
 * Created by hxk on 2017/10/27 0027 15:07
 * 刷新fragment碎片汇总
 */

public class ReshUtill {
    /**
     * @param currentItem 当前页面编号
     * @param fragments   fragment碎片集合
     * @param context     上下文对象
     *                    根据页面展示数据变化刷新MineFragment
     */
    public static void reshMineFragment(int currentItem, List<Fragment> fragments, Context context) {
        Fragment fragment = fragments.get(currentItem);
        if (fragment instanceof MineFragment) {
            MineFragment mineFragment = (MineFragment) fragment;
            mineFragment.reshFragment(context);
        }
    }
}
