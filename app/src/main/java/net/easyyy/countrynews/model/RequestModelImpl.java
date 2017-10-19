package net.easyyy.countrynews.model;


import net.easyyy.countrynews.util.Constant;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

/**
 * Info:接口实现类
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:42
 */
public class RequestModelImpl implements IRequestMode {
    IRequestMode iRequestMode;



    @Override
    public Flowable<ResponseBody> QueryRegister(@Field(Constant.SECRET) String secret, @Field(Constant.TEL) String tel, @Field(Constant.PASSWORD) String password, @Field(Constant.NAME) String name, @Field(Constant.VERIIFY) String verify, @Field(Constant.VCODE) String vcode, @Field(Constant.REFEREE) String referee) {
        return iRequestMode.QueryRegister(secret, tel, password, name, verify, vcode,referee);
    }





}
