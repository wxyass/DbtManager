package com.core.net.download;

/**
 * 下载进度的回调接口
 * Created by yangwenmin on 2017/10/15.
 */

public interface WriteToDiskCallBack {
    void onProgressUpdate(int progress);
}
