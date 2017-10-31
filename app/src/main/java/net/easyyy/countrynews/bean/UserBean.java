package net.easyyy.countrynews.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/10/22/022.
 */

public class UserBean extends BmobUser {
    private Integer age;
    private Integer sex;
    private String nickname;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
