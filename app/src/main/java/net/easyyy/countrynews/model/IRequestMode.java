package net.easyyy.countrynews.model;


import android.support.annotation.Nullable;

import net.easyyy.countrynews.util.Constant;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by xyl on 2017/4/6.
 */

public interface IRequestMode {


    @FormUrlEncoded
    @POST("index.php?g=Member&m=Member&a=AddMember")
    Flowable<ResponseBody> QueryRegister(@Field(Constant.SECRET) String secret,
                                         @Field(Constant.TEL) String tel,
                                         @Field(Constant.PASSWORD) String password,
                                         @Field(Constant.NAME) String name,
                                         @Field(Constant.VERIIFY) String verify,
                                         @Field(Constant.VCODE) String vcode,
                                         @Nullable @Field(Constant.REFEREE) String referee);

   }
