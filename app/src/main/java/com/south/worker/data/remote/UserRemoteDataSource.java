package com.south.worker.data.remote;

import com.south.worker.constant.NetConfig;
import com.south.worker.data.UserDataSource;
import com.south.worker.data.bean.RespondBean;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.data.bean.UserLoginBean;
import com.south.worker.data.network.HttpKLogingInterceptor;
import com.south.worker.data.network.NetHelper;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public interface UserRemoteDataSource {

    //用户登录
    @FormUrlEncoded
    @POST("Home/Login")
    Observable<UserLoginBean> login(@Field("UserName") String userName, @Field("Password") String userPassword);

    //用户信息修改
    @POST("User/Edit")
    Observable<RespondBean> editUserInfo(@Body UserInfoBean bean);

    //用户头像修改
    @FormUrlEncoded
    @POST("Home/EditPic")
    Observable<UserLoginBean> changeAvatar(@Field("Id") int userId, @Field("pic") String pic);

    @FormUrlEncoded
    //查询用户密码是否匹配
    @POST("Home/GetUser")
    Observable<RespondBean> checkPassword(@Field("Id") int userId, @Field("Password") String userPassword);

    @FormUrlEncoded
    //获取用户信息
    @POST("User/GetUser")
    Observable<UserInfoBean> getUserInfo(@Field("Id") int userId);


    //修改密码
    @FormUrlEncoded
    @POST("Home/EditPwd")
    Observable<RespondBean> changePassword(@Field("Id") int userId, @Field("Password") String userPassword);

}
