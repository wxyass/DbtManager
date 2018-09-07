package com.core.net.callback;

/**
 * 请求错误的回调接口
 * Created by yangwenmin on 2017/10/15.
 */

public interface IError {
    void onError(int code, String msg);
}
