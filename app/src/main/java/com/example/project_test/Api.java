package com.example.project_test;

import android.util.Log;

import com.example.project_test.Food.FoodPostList;
import com.example.project_test.Info.InfoPostList;
import com.example.project_test.Meet.MeetPostList;
import com.example.project_test.Mypage.MyContents.MyPostList;
import com.example.project_test.Recipe.RecipePostList;
import com.example.project_test.qa.qaPostList;
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

    @GET("login/checkID")
    Call<UserIdCheck> getID(@Query("id")String var1);

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

    //게시물 목록
    @GET("post")
    Call<RecipePostList> getRecipeList(@Query("board_code") int var1); //요리

    @GET("post")
    Call<FoodPostList> getFoodList(@Query("board_code") int var1); //맛집

    @GET("post")
    Call<MeetPostList> getMeetList(@Query("board_code") int var1); //만남

    @GET("post")
    Call<InfoPostList> getInfoList(@Query("board_code") int var1); //정보

    /*@GET("post")
    Call<PostList> getInfoList(@Query("board_code") int var1); //나눔

    @GET("post")
    Call<PostList> getInfoList(@Query("board_code") int var1); //대여*/

    @GET("post")
    Call<qaPostList> getQnAList(@Query("board_code") int var1); //QA

    //게시물 삭제
    @GET("DeletePost")
    Call<DeletePost> deletepost(@Query("post_title") String var1);

    //마이페이지 글 목록
    @GET("post/checkWriter")
    Call<MyPostList> checkWriter(@Query("id") String var1); //내가 쓴 글



    @FormUrlEncoded
    @POST("Write")
    Call<Write> Write(@Field("id")String var1,@Field("post_title")String var2,@Field("post_con")String var3,@Field("board_code")int var4);

    @FormUrlEncoded
    @POST("CookWrite")
    Call<CookWrite> CookWrite(@Field("cook_src")String var1,@Field("post_con")String var2);

    @FormUrlEncoded
    @POST("FoodWrite")
    Call<FoodWrite> FoodWrite(@Field("food_lct")String var1);

    @FormUrlEncoded
    @POST("MeetWrite")
    Call<MeetWrite> MeetWrite(@Field("meet_tag")int var1,@Field("meet_lct")String var2,@Field("meet_p")int var3);


    public static final class Factory {
        public static final Api.Factory INSTANCE;

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
            return retrofit.create(Api.class);
        }

        static {
            Api.Factory fac = new Api.Factory();
            INSTANCE = fac;
        }
    }
}
