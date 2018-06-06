package com.south.worker.data.remote;

import com.south.worker.constant.NetConfig;
import com.south.worker.data.UserDataSource;
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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 描述   ：用户模块
 * <p>
 * 作者   ：Created by DR on 2018/6/1.
 */

public interface UserRemoteDataSource {


    @FormUrlEncoded
    @POST("Home/Login")
    Observable<UserLoginBean> login(@Field("UserName") String userName, @Field("Password") String userPassword);

    @POST("Home/EditPic")
    Observable<UserLoginBean> changeAvatar(@Field("Id") int userId, @Field("pic") String pic);

    @POST("User/GetUser")
    Observable<UserLoginBean> getUserInfo(@Field("Id") int userId);


    @POST("User/GetUser")
    Observable<UserLoginBean> changePassword(@Field("Id") int userId);


}
