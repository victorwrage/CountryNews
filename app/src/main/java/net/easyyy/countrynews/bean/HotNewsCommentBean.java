package net.easyyy.countrynews.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/5/13 14:47
 */

public class HotNewsCommentBean extends BmobObject{

    BmobPointer comment_author;
    String comment_author_name;

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    String comment_author_avatar;
    String comment_content;
    BmobPointer comment_essay;
    BmobRelation comment_goods;
    BmobDate comment_date;

    public BmobPointer getComment_author() {
        return comment_author;
    }

    public void setComment_author(BmobPointer comment_author) {
        this.comment_author = comment_author;
    }

    public BmobPointer getComment_essay() {
        return comment_essay;
    }

    public void setComment_essay(BmobPointer comment_essay) {
        this.comment_essay = comment_essay;
    }

    public BmobRelation getComment_goods() {
        return comment_goods;
    }

    public void setComment_goods(BmobRelation comment_goods) {
        this.comment_goods = comment_goods;
    }

    public BmobDate getComment_date() {
        return comment_date;
    }

    public void setComment_date(BmobDate comment_date) {
        this.comment_date = comment_date;
    }

    public Boolean getEssay_comment() {
        return essay_comment;
    }

    public void setEssay_comment(Boolean essay_comment) {
        this.essay_comment = essay_comment;
    }

    public BmobPointer getComment_comment() {
        return comment_comment;
    }

    public void setComment_comment(BmobPointer comment_comment) {
        this.comment_comment = comment_comment;
    }

    Boolean essay_comment;
    BmobPointer comment_comment;

    public String getComment_author_name() {
        return comment_author_name;
    }

    public void setComment_author_name(String comment_author_name) {
        this.comment_author_name = comment_author_name;
    }

    public String getComment_author_avatar() {
        return comment_author_avatar;
    }

    public void setComment_author_avatar(String comment_author_avatar) {
        this.comment_author_avatar = comment_author_avatar;
    }
}
