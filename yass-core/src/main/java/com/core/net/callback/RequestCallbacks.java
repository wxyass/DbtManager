package com.core.net.callback;

import android.os.Handler;


import com.core.ui.loader.LatteLoader;
import com.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求的回调 ??
 * Created by yangwenmin on 2017/10/15.
 */

public class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYPE;
    private static final Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error, LoaderStyle style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYPE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }

        stopLoading();

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

        if (FAILURE != null) {
            FAILURE.onFailure();
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        
        stopLoading();
    }

    // 关闭loading页
    private void stopLoading() {//1
        if (LOADER_STYPE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, 1000);
        }
    }
}
