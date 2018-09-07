package com.core.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器基类 获取请求中的参数
 * Created by yangwenmin on 2017/10/17.
 */

public abstract class BaseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

    // 获取get请求中参数 // LinkedHashMap是有序的
    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {// Chain:是okhttp3特有的,拦截器中的接口
        final HttpUrl url = chain.request().url();
        int size = url.querySize();// 请求参数的个数
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        return params;
    }

    // 获取get请求中参数 通过key获取value
    protected String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    // 从post请求体中获取参数
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        // FormBody是okhttp3特有的
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = 0;
        if (formBody != null) {
            size = formBody.size();
        }
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }
}
