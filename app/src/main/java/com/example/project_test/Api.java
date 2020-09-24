package com.example.project_test;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("login")
    Call<User> getUser(@Query("id")String var1);

    @GET("login/getAllUser")
    Call<UserFeed> getAllUser();

    @FormUrlEncoded
    @POST("mold/get_mold_dispose")
    Call<ResponseBody> aa(@Field("aa")String var1, @Field("bb")String var2);

    @GET("join")
    Call<ValidateID> validateUser(@Query("id")String var1);

    @GET("join/validateName")
    Call<ValidateName> validateName(@Query("name")String var1);

    @GET("join/validateEmail")
    Call<ValidateEmail> validateEmail(@Query("email")String var1);

    @FormUrlEncoded
    @POST("join/joinUser")
    Call<Join> joinUser(@Field("id")String var1,@Field("pw")String var2, @Field("name")String var3,@Field("email")String var4);





    public static final class Factory {
        static final Api.Factory INSTANCE;

        public final Api create() {

            //String uri = "https://apache-php-mysql-rydgd.run.goorm.io/index.php/";
            String uri = "https://project-lzbnp.run.goorm.io/index.php/";

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .writeTimeout(60L, TimeUnit.SECONDS).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(uri)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Log.i("abcdef","Factory.INSTANCE.create");
            return retrofit.create(Api.class);
        }

        static {
            Api.Factory fac = new Api.Factory();
            INSTANCE = fac;
        }
    }
}
