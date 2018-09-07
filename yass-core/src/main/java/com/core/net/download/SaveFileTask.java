package com.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;


import com.core.app.Latte;
import com.core.net.callback.IRequest;
import com.core.net.callback.ISuccess;
import com.core.net.callback.OnDownLoadProgress;
import com.core.utils.file.FileTool;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 异步执行下载成功后的保存操作
 * Created by yangwenmin on 2017/10/17.
 */

public class SaveFileTask extends AsyncTask<Object, Integer, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private OnDownLoadProgress ONDOWNLOADPROGRESS;
    private long fileLength;// 文件的大小

    public SaveFileTask(IRequest request, ISuccess success, OnDownLoadProgress onDownLoadProgress) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ONDOWNLOADPROGRESS = onDownLoadProgress;
    }

    @Override
    protected File doInBackground(Object... params) {
        // 下载路径
        String downloadDir = (String) params[0];
        // 后缀名
        String extension = (String) params[1];
        // 请求体
        final ResponseBody body = (ResponseBody) params[2];
        // 文件名称
        String name = (String) params[3];
        // 得到输入流
        final InputStream is = body.byteStream();
        //
        if (downloadDir == null || "".equals(downloadDir)) {
            downloadDir = "down_loads";
        }
        //
        if (extension == null || "".equals(extension)) {
            extension= "";
        }

        // 进度回调
        WriteToDiskCallBack writeToDiskCallBack =  new WriteToDiskCallBack() {
            @Override
            public void onProgressUpdate(int progress) {// 子线程
                SaveFileTask.this.onProgressUpdate(progress);
            }
        };

        fileLength = body.contentLength();

        // 创建文件
        if(name==null){
            // 总大小,下载进度回调
            return FileTool.writeToDisk(is,downloadDir,extension.toUpperCase(),extension,fileLength,writeToDiskCallBack);
        }else{
            return FileTool.writeToDisk(is,downloadDir,name,fileLength,writeToDiskCallBack);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (ONDOWNLOADPROGRESS != null) {
            ONDOWNLOADPROGRESS.onProgressUpdate(fileLength, values[0]);
        }
    }

    // 执行完子线程回到主线程后做的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        // autoInstallApk(file);
    }

    // 若是.apk文件, 直接安装
    private void autoInstallApk(File file){
        if(FileTool.getExtension(file.getPath()).equals("apk")){// 后缀名是apk
            final Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(intent);
        }
    }
}
