package net.easyyy.countrynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.bean.HotNewsBean;
import net.easyyy.countrynews.bean.HotNewsCommentBean;
import net.easyyy.countrynews.customView.FlowImageLayout;
import net.easyyy.countrynews.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Info: 消息
 * Created by xiaoyl
 * 创建时间:2017/8/07 10:15
 */
public class HotNewsCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<HotNewsCommentBean> items;
    Utils util;

    public HotNewsCommentAdapter(ArrayList<HotNewsCommentBean> items, Context context) {
        this.items = items;
        this.context = context;
        util = Utils.getInstance();

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int VIEW_TYPE) {

        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_hot_news_comment, viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder_, int i) {
        MyViewHolder holder = (MyViewHolder) holder_;
        HotNewsCommentBean item = items.get(i);


        holder.hot_comment_author_name.setText(item.getComment_author_name());
        holder.hot_essay_content.setText(item.getComment_content());
        holder.hot_essay_comment_count.setText(item.getComment_goods().getObjects().size());
        holder.hot_comment_time.setText(item.getComment_date().getDate());

        RxView.clicks(holder.hot_comment_good).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Good());
        RxView.clicks(holder.hot_comment_comment).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Comment());
        RxView.clicks(holder.hot_whole_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Comment());

    }

    private void Comment() {

    }

    private void Good() {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hot_comment_author_name, hot_essay_content,hot_comment_time,hot_comment_good,hot_comment_comment,hot_essay_comment_count;
        CircleImageView hot_comment_author_avatar;

        FlowImageLayout hot_comment_img;
        RelativeLayout hot_whole_lay;
        public MyViewHolder(View view) {
            super(view);
            hot_comment_author_name = (TextView) view.findViewById(R.id.hot_comment_author_name);
            hot_comment_time = (TextView) view.findViewById(R.id.hot_comment_time);

            hot_essay_content = (TextView) view.findViewById(R.id.hot_essay_content);
            hot_comment_good = (TextView) view.findViewById(R.id.hot_comment_good);
            hot_essay_comment_count = (TextView) view.findViewById(R.id.hot_essay_comment_count);
            hot_comment_comment = (TextView) view.findViewById(R.id.hot_comment_comment);

            hot_comment_author_avatar = (CircleImageView) view.findViewById(R.id.hot_comment_author_avatar);

            hot_comment_img = (FlowImageLayout) view.findViewById(R.id.hot_comment_img);

            hot_whole_lay = (RelativeLayout) view.findViewById(R.id.hot_whole_lay);
        }
    }
}
