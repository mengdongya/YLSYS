package store.chinaotec.com.medicalcare.shopmarket.common.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;

import java.util.ArrayList;
import java.util.List;

import store.chinaotec.com.medicalcare.R;

/**
 * Created by seven on 2017/10/10 0010.
 */
public class ViewPoiOverlay extends PoiOverlay {
    private Context mContext;
    //缓存View用
    private ArrayList<View> views = new ArrayList<View>();

    public ViewPoiOverlay(AMap aMap, List<PoiItem> list, Context mContext) {
        super(aMap, list);
        this.mContext = mContext;
    }

    protected BitmapDescriptor getBitmapDescriptor(int index) {

        ImageView imageView = null;

        //缓存View，减少系统消耗

        if (views.size() <= index || views.get(index) == null) {

            imageView = new ImageView(mContext);

            imageView.setBackgroundResource(R.drawable.icon_hospital_map);

            views.add(imageView);

        } else {

            imageView = (ImageView) views.get(index);

        }
        return BitmapDescriptorFactory.fromView(imageView);
    }
}
