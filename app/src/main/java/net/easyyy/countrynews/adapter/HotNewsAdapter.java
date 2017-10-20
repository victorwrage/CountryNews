package net.easyyy.countrynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.bean.HotNewsBean;
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
public class HotNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<HotNewsBean> items;
    Utils util;

    public HotNewsAdapter(ArrayList<HotNewsBean> items, Context context) {
        this.items = items;
        this.context = context;
        util = Utils.getInstance();

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int VIEW_TYPE) {

        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_hot_news, viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder_, int i) {
        MyViewHolder holder = (MyViewHolder) holder_;
        HotNewsBean item = items.get(i);


        holder.hot_author_name.setText(item.getAuthor_name());
        holder.hot_essay_extra.setText(item.getEssay_extra());

        String create_time = "";
        Long dec = System.currentTimeMillis() - item.getEssay_create_time();
        if (dec % 1000 * 60 < 3) {
            create_time = "刚刚";
        } else if (dec % 1000 * 60 < 60) {
            create_time = dec % 1000 * 60 + "分钟 ";
        } else if (dec % 1000 * 60 * 60 < 24) {
            create_time = dec % 1000 * 60 * 60 + "小时 ";
        } else if (dec % 1000 * 60 * 60 * 24 < 30) {
            create_time = dec % 1000 * 60 * 24 + "天 ";
        } else if (dec % 1000 * 60 * 60 * 24 * 7 < 4) {
            create_time = dec % 1000 * 60 * 60 * 24 * 7 + "周 ";
        } else if (dec % 1000 * 60 * 60 * 24 * 30 < 1) {
            create_time = dec % 1000 * 60 * 60 * 24 * 30 + "月 ";
        } else {
            create_time = dec % 1000 * 60 * 60 * 24 * 30 * 12 + "年 ";
        }
        holder.hot_essay_time.setText(create_time);

        holder.hot_essay_content.setText(item.getEssay_content());
        holder.hot_essay_emotion_count.setText(item.getEmotion_count() + "人");

        holder.hot_essay_comment_count.setText(item.getComment_count() + "条评论");
        holder.hot_essay_share_count.setText(item.getShare_count() + "条分享");
        holder.hot_essay_look_count.setText(item.getLook_count() + "次浏览");

        Picasso.with(context).load(item.getAuthor_avatar())
                .placeholder(R.drawable.white_radius)
                .error(R.drawable.error)
                .into(holder.hot_author_avatar);

        switch (item.getEssay_type_extra()) {
            case 0:
                holder.hot_essay_btn_extra.setImageResource(R.drawable.good);
                RxView.clicks(holder.hot_essay_btn_extra).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Good());
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                holder.hot_essay_btn_extra.setImageResource(R.drawable.more);
                RxView.clicks(holder.hot_essay_btn_extra).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> More());

                break;
        }
        Picasso.with(context).load(item.getEssay_source())
                .placeholder(R.drawable.white_radius)
                .error(R.drawable.error)
                .into(holder.hot_essay_source);

        holder.hot_essay_img.setHorizontalSpacing(2);
        holder.hot_essay_img.setVerticalSpacing(2);
        holder.hot_essay_img.setSingleImageSize(640, 400);
        holder.hot_essay_img.loadImage(item.getEssay_images().size(), images -> {
            for (int i1 = 0; i1 < images.size(); i1++) {
                Picasso.with(context).load(item.getEssay_images().get(i1))
                        .placeholder(R.drawable.white_radius)
                        .error(R.drawable.error)
                        .into(images.get(i1));
            }
        });

        RxView.clicks(holder.hot_essay_good).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Good());
        RxView.clicks(holder.hot_essay_comment).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Comment());
        RxView.clicks(holder.hot_essay_share).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Share());

    }

    private void Share() {

    }

    private void Comment() {

    }

    private void Good() {

    }

    private void More() {

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hot_author_name, hot_essay_extra, hot_essay_time, hot_essay_content, hot_essay_emotion_count,
                hot_essay_comment_count, hot_essay_share_count, hot_essay_look_count;
        CircleImageView hot_author_avatar;
        ImageView hot_essay_btn_extra, hot_essay_source;
        FlowImageLayout hot_essay_img;
        LinearLayout hot_essay_good, hot_essay_comment, hot_essay_share;

        public MyViewHolder(View view) {
            super(view);
            hot_author_name = (TextView) view.findViewById(R.id.hot_author_name);
            hot_essay_extra = (TextView) view.findViewById(R.id.hot_essay_extra);
            hot_essay_time = (TextView) view.findViewById(R.id.hot_essay_time);
            hot_essay_content = (TextView) view.findViewById(R.id.hot_essay_content);
            hot_essay_emotion_count = (TextView) view.findViewById(R.id.hot_essay_emotion_count);
            hot_essay_comment_count = (TextView) view.findViewById(R.id.hot_essay_comment_count);
            hot_essay_share_count = (TextView) view.findViewById(R.id.hot_essay_share_count);
            hot_essay_look_count = (TextView) view.findViewById(R.id.hot_essay_look_count);
            hot_author_avatar = (CircleImageView) view.findViewById(R.id.hot_author_avatar);

            hot_essay_btn_extra = (ImageView) view.findViewById(R.id.hot_essay_btn_extra);
            hot_essay_source = (ImageView) view.findViewById(R.id.hot_essay_source);

            hot_essay_img = (FlowImageLayout) view.findViewById(R.id.hot_essay_img);

            hot_essay_good = (LinearLayout) view.findViewById(R.id.hot_essay_good);
            hot_essay_comment = (LinearLayout) view.findViewById(R.id.hot_essay_comment);
            hot_essay_share = (LinearLayout) view.findViewById(R.id.hot_essay_share);
        }
    }

    private void setRead(MyViewHolder holder, int position) {
        HotNewsBean item = items.get(position);
        // item.setIs_read(true);
    }

}
