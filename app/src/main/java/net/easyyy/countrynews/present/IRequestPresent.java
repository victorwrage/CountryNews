package net.easyyy.countrynews.present;


/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IRequestPresent {


    void QueryRegister(String secret, String tel, String password, String name, String verify, String vcode, String referee);


}
