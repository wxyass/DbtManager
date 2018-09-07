package com.core.net.interceptors;

import android.support.annotation.NonNull;
import android.support.annotation.RawRes;


import com.core.utils.file.FileTool;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 请求拦截器,重写intercept()方法,返回自定义json
 *
 * Created by yangwenmin on 2017/10/17.
 */

public class DebugInterceptor extends BaseInterceptor {

    private final String DEBUG_URL;// 模拟url,
    private final int DEBUG_RAW_ID;// 模拟返回的json数据,放在raw文件夹下

    // 构造
    public DebugInterceptor(String debugUrl, int rawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = rawId;
    }

    // 构建拦截成功后,返回的Response数据
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())// 原始请求
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    // 读取raw文件内容,并将其作为json返回
    private Response debugResponse(Chain chain, @RawRes int rawId) {// @RawRes: 该注解表示必须在R文件生成唯一id的资源
        // 读取raw目录中的文件,并将内容以字符串返回
        final String json = FileTool.getRawFile(rawId);
        return getResponse(chain, json);
    }

    // 重写请求拦截
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // 获取请求的url
        final String url = chain.request().url().toString();
        // 若请求url中包含模拟url
        if (url.contains(DEBUG_URL)) {
            // 返回自定义的json数据
            return debugResponse(chain, DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
