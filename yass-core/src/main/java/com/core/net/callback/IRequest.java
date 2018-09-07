package com.core.net.callback;

/**
 * 请求的回调接口
 * Created by yangwenmin on 2017/10/15.
 */

public interface IRequest {
    void onRequestStart();

    void onRequestEnd();
}
