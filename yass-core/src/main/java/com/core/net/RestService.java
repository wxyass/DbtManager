package com.core.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 接口: 定义一系列方法,用来具体的请求
 *
 * Created by yangwenmin on 2017/10/15.
 */

public interface RestService {

    // get异步请求
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    /*// get同步请求
    @GET
    Call<String> getSync(@Url String url, @QueryMap Map<String, Object> params);*/

    // post请求
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    // put请求
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    // delete请求
    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    // download请求
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    // upload请求
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);

}
