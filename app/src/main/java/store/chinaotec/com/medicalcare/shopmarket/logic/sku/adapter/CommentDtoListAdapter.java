package store.chinaotec.com.medicalcare.shopmarket.logic.sku.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import store.chinaotec.com.medicalcare.R;
import store.chinaotec.com.medicalcare.shopmarket.logic.sku.model.CommentDtoList;

public class CommentDtoListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentDtoList> dataList;

    public CommentDtoListAdapter(Context context, ArrayList<CommentDtoList> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_shop_market_sku_comment, null);
            holder.tv_member_account = (TextView) convertView.findViewById(R.id.tv_member_account);
            holder.tv_comment_creat_time = (TextView) convertView.findViewById(R.id.tv_comment_creat_time);
            holder.tv_commment_content = (TextView) convertView.findViewById(R.id.tv_commment_content);
            holder.sku_comment_star = (RatingBar) convertView.findViewById(R.id.sku_comment_star);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        CommentDtoList commentDtoList = dataList.get(position);
        String memberName = commentDtoList.getMemberName();
        if (memberName.length() == 11) {
            String memberNamee = memberName.substring(0, 3) + "****" + memberName.substring(memberName.length() - 4, memberName.length());
            holder.tv_member_account.setText(memberNamee);
        } else if (memberName.length() != 0) {
            holder.tv_member_account.setText(memberName);
        }
        holder.tv_comment_creat_time.setText(commentDtoList.getCommentTime());
        holder.tv_commment_content.setText("评论:" + commentDtoList.getCommentContent());

        holder.sku_comment_star.setRating(commentDtoList.getScore());
        return convertView;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        TextView tv_member_account;
        TextView tv_comment_creat_time;
        TextView tv_commment_content;
        RatingBar sku_comment_star;
    }

}
