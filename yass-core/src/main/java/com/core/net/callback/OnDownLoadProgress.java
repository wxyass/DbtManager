package com.core.net.callback;

/**
 * 下载进度回调接口
 * Created by yangwenmin on 2017/10/15.
 */

public interface OnDownLoadProgress {

    void onProgressUpdate(long fileLength, int downLoadedLength);
}
