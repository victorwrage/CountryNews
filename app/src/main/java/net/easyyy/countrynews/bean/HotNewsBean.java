package net.easyyy.countrynews.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/5/13 14:47
 */

public class HotNewsBean  extends BmobObject{

    String author_avatar;
    String author_name;
    String essay_content;
    Long essay_create_time;
    String essay_source;
    String essay_title;
    String essay_extra;

    int essay_type_extra;

    Long emotion_count;
    Long comment_count;
    Long look_count;

    public String getEssay_extra() {
        return essay_extra;
    }

    public void setEssay_extra(String essay_extra) {
        this.essay_extra = essay_extra;
    }

    public int getEssay_type_extra() {
        return essay_type_extra;
    }

    public void setEssay_type_extra(int essay_type_extra) {
        this.essay_type_extra = essay_type_extra;
    }

    public Long getEmotion_count() {
        return emotion_count;
    }

    public void setEmotion_count(Long emotion_count) {
        this.emotion_count = emotion_count;
    }

    public Long getComment_count() {
        return comment_count;
    }

    public void setComment_count(Long comment_count) {
        this.comment_count = comment_count;
    }

    public Long getLook_count() {
        return look_count;
    }

    public void setLook_count(Long look_count) {
        this.look_count = look_count;
    }

    public Long getShare_count() {
        return share_count;
    }

    public void setShare_count(Long share_count) {
        this.share_count = share_count;
    }

    public ArrayList<String> getEssay_tags() {
        return essay_tags;
    }

    public void setEssay_tags(ArrayList<String> essay_tags) {
        this.essay_tags = essay_tags;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public Boolean getIs_good() {
        return is_good;
    }

    public void setIs_good(Boolean is_good) {
        this.is_good = is_good;
    }

    Long share_count;

    ArrayList<String> essay_images;
    ArrayList<String> essay_tags;
    Boolean is_read;
    Boolean is_good;

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getEssay_content() {
        return essay_content;
    }

    public void setEssay_content(String essay_content) {
        this.essay_content = essay_content;
    }

    public Long getEssay_create_time() {
        return essay_create_time;
    }

    public void setEssay_create_time(Long essay_create_time) {
        this.essay_create_time = essay_create_time;
    }

    public String getEssay_source() {
        return essay_source;
    }

    public void setEssay_source(String essay_source) {
        this.essay_source = essay_source;
    }

    public String getEssay_title() {
        return essay_title;
    }

    public void setEssay_title(String essay_title) {
        this.essay_title = essay_title;
    }

    public ArrayList<String> getEssay_images() {
        return essay_images;
    }

    public void setEssay_images(ArrayList<String> essay_images) {
        this.essay_images = essay_images;
    }
}
