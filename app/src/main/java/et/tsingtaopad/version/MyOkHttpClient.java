package et.tsingtaopad.version;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装OKhttp3.0
 *
 * Created by Administrator on 2016/4/16.
 */
public class MyOkHttpClient {
    private static MyOkHttpClient myOkHttpClient;
    private OkHttpClient okHttpClient;
    private Handler handler;


    private MyOkHttpClient() {
        okHttpClient = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());
    }

    public static MyOkHttpClient getInstance() {
        if (myOkHttpClient == null) {
            synchronized (MyOkHttpClient.class) {
                if (myOkHttpClient == null) {
                    myOkHttpClient = new MyOkHttpClient();
                }
            }
        }

        return myOkHttpClient;
    }



    public void asyncGet(String url, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }


    // // 发送异步post请求
    public void asyncPost(String url, FormBody formBody, HttpCallBack httpCallBack) {
        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new StringCallBack(request, httpCallBack));
    }


    public interface HttpCallBack {
        void onError(Request request, IOException e);

        void onSuccess(Request request, String response);
    }

    public interface FileBack {
        void onError(Request request, IOException e);

        void onProgress(Request request, int progress);
        void onSuccess(Request request, Response response);
    }

    /**
     * 下载文件
     * @param url
     * @param fileDir
     * @param fileName
     */
    public void downFile(String url, final String fileDir, final String fileName, HttpCallBack httpCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    //---增加的代码---
                    //计算进度
                    long totalSize = response.body().contentLength();
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        //progress就是进度值
                        int progress = (int) (sum * 1.0f/totalSize * 100);
                        //---增加的代码---
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }



    class StringCallBack implements Callback {
        private HttpCallBack httpCallBack;
        private Request request;

        public StringCallBack(Request request, HttpCallBack httpCallBack) {
            this.request = request;
            this.httpCallBack = httpCallBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onError(request, fe);
                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String result = response.body().string();
            if (httpCallBack != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpCallBack.onSuccess(request, result);
                    }
                });
            }
        }
    }

    class FileCallBack implements Callback {
        private FileBack fileback;
        private Request request;
        private String fileDir;
        private String fileName;

        public FileCallBack(Request request, FileBack fileback, String fileDir, String fileName) {
            this.request = request;
            this.fileback = fileback;
            this.fileDir = fileDir;
            this.fileName = fileName;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            final IOException fe = e;
            if (fileback != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fileback.onError(request, fe);
                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final Response res = response;

            InputStream inputStream = response.body().byteStream();
            FileOutputStream fileOutputStream = null;
            long totalSize = response.body().contentLength();
            long sum = 0;
            try {
                File file = new File(fileDir+fileName);
                fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[2048];
                int len = 0;

                while ((len = inputStream.read(buffer)) != -1) {
                    sum += len;
                    //progress就是进度值
                    final int progress = (int) (sum * 1.0f/totalSize * 100);

                    // 当前进度
                    //final int curlength = (int) file.length();
                    // 发送下载进度的回调
                    if (fileback != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                fileback.onProgress(request, progress);// curlength  progress
                            }
                        });
                    }

                    fileOutputStream.write(buffer, 0, len);
                }
                fileOutputStream.flush();
            } catch (IOException e) {
                Log.i("wangshu", "IOException");
                e.printStackTrace();
            }

            // 发送保存成功的回调
            if (fileback != null&&totalSize==sum) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fileback.onSuccess(request, res);
                    }
                });
            }


        }
    }


    /**
     * ywm下载文件
     * @param url
     * @param fileDir
     * @param fileName
     */
    public void downloadFile(String url, String fileDir, String fileName, FileBack fileback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new FileCallBack(request, fileback,fileDir,fileName));
    }


}



