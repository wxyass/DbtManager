package et.tsingtaopad.version;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.core.net.RestClient;
import com.core.net.callback.IError;
import com.core.net.callback.IFailure;
import com.core.net.callback.ISuccess;
import com.core.net.callback.OnDownLoadProgress;
import com.core.utils.dbtutil.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseFragment;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载apk
 * Created by yangwenmin on 2018/3/12.
 */

public class DownApkFragment extends BaseFragment {

    private final String TAG = "DownApkFragment";

    public static final int SHOWDOWNLOADDIALOG = 88; // 弹出进度条,设置最大值100 //显示正在下载的对话框
    public static final int UPDATEDOWNLOADDIALOG = 99;// 设置进度条  //刷新正在下载对话框的内容
    public static final int DOWNLOADFINISHED = 66;// 下载完成开始安装 //下载完成后进行的操作


    // String apkUrl = "http://oss.wxyass.com/tscs2.4.3.1.0.apk";
    String apkUrl = "http://192.168.0.222/FSA_WEB_2/file/upload/apk/tscs2.4.3.7.3.apk";
    String apkName = "tscs2.4.3.1.0.apk";
    String downPath = "dbt";// apk的存放位置


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dd_downapk, container, false);
        initView(view);
        return view;
    }

    // 初始化控件
    private void initView(View view) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        handler = new MyHandler(this);
        initData();
    }

    // 初始化数据
    private void initData() {

        // 获取传递过来的数据
        Bundle bundle = getArguments();
        apkUrl = bundle.getString("apkUrl");
        apkName = bundle.getString("apkName");
        // apkUrl = "http://oss.wxyass.com/tscs2.4.3.1.0.apk";
        // apkUrl = "http://192.168.0.222/FSA_WEB_2/file/upload/apk/tscs.apk";



        showNoticeDialog();

    }

    private Dialog noticeDialog;

    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("软件版本更新");
        builder.setMessage(R.string.version_msg_prompt);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //                dialog.dismiss();
                downLoadApk();
                // downLoadApkByOkhttp();
                // downApk();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
        noticeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<DownApkFragment> fragmentRef;

        public MyHandler(DownApkFragment fragment) {
            fragmentRef = new SoftReference<DownApkFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            DownApkFragment fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }

            // 处理UI 变化
            switch (msg.what) {
                case SHOWDOWNLOADDIALOG:
                    fragment.showDownloadDialog();
                    break;
                case UPDATEDOWNLOADDIALOG: // 督导输入数据后
                    fragment.showDownloading(msg);
                    break;
                case DOWNLOADFINISHED: // 督导输入数据后
                    fragment.stopDownloadDialog(msg);
                    break;
            }
        }
    }

    // 下载apk
    private void downLoadApk() {

        Message msg = Message.obtain();
        msg.what = SHOWDOWNLOADDIALOG;
        handler.sendMessage(msg);

        RestClient.builder()
                .url(apkUrl)
                // .params("data", jsonZip)
                // .loader(getContext())// 滚动条
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //用handle通知主线程 下载完成 -> 开始安装
                        Message finishedMsg = Message.obtain();
                        finishedMsg.what = DOWNLOADFINISHED;
                        // finishedMsg.obj = downPath+apkName;// 文件路径
                        handler.sendMessage(finishedMsg);
                    }
                })
                .onDownLoadProgress(new OnDownLoadProgress() {
                    @Override
                    public void onProgressUpdate(long fileLength, int downLoadedLength) {
                        // 用handle通知主线程刷新进度, progress: 是1-100的正整数
                        Message updateMsg = Message.obtain();
                        updateMsg.what = UPDATEDOWNLOADDIALOG;
                        updateMsg.obj = fileLength;
                        updateMsg.arg1 = downLoadedLength;
                        handler.sendMessage(updateMsg);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .name(apkName)
                .dir(downPath)
                //.dir("")
                .builde()
                .download();
    }


    /**
     * 下载apk  OKhttp3.0  下载  ywm  2017年9月5日09:41:12
     */
    protected void downLoadApkByOkhttp() {

        downPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dbt/et.tsingtaopad" + "/bug/";

        //通过handle发个空消息告诉主线程 弹出进度条,将进度条最大值设为100
        Message msg = Message.obtain();
        msg.what = SHOWDOWNLOADDIALOG;
        handler.sendMessage(msg);


        /**
         *
         */
        MyOkHttpClient.getInstance().downloadFile(apkUrl, downPath, apkName, new MyOkHttpClient.FileBack() {

            @Override
            public void onProgress(Request request, int progress) {

                // 用handle通知主线程刷新进度, progress: 是1-100的正整数
                Message updateMsg = Message.obtain();
                updateMsg.what = UPDATEDOWNLOADDIALOG;
                updateMsg.obj = progress;
                handler.sendMessage(updateMsg);

            }

            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Request request, Response response) {
                Toast.makeText(getActivity(), "请求成功1", Toast.LENGTH_SHORT).show();

                //用handle通知主线程 下载完成 -> 开始安装
                Message finishedMsg = Message.obtain();
                finishedMsg.what = DOWNLOADFINISHED;
                finishedMsg.obj = downPath + apkName;// 文件路径
                handler.sendMessage(finishedMsg);
            }
        });
    }

    /**
     * 下载安装程序
     */
    private boolean interceptFlag = false;

    // dbtplus
    public void downApk() {

        //通过handle发个空消息告诉主线程 弹出进度条,将进度条最大值设为100
        Message msg = Message.obtain();
        msg.what = SHOWDOWNLOADDIALOG;
        handler.sendMessage(msg);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    //                    conn.setConnectTimeout(3000);
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        System.out.println("hjkkllll");
                    }
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    //File file = context.getFilesDir();
                    String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String path = sdcardPath + "/dbt/et.tsingtaopad" + "/bug";
                    File file = new File(path);

                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String apkPath = file.getPath() + "/DbtPlus.apk";
                    File ApkFile = new File(apkPath);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        int progress = (int) (((float) count / length) * 100);
                        //更新进度
                        // handler.sendEmptyMessage(ConstValues.WAIT1);
                        // 用handle通知主线程刷新进度, progress: 是1-100的正整数
                        Message updateMsg = Message.obtain();
                        updateMsg.what = UPDATEDOWNLOADDIALOG;
                        updateMsg.obj = progress;
                        handler.sendMessage(updateMsg);

                        if (numread <= 0) {
                            //下载完成通知安装
                            // handler.sendEmptyMessage(ConstValues.WAIT2);
                            //用handle通知主线程 下载完成 -> 开始安装
                            Message finishedMsg = Message.obtain();
                            finishedMsg.what = DOWNLOADFINISHED;
                            finishedMsg.obj = apkPath;// 文件路径
                            handler.sendMessage(finishedMsg);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);//点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    Log.e(TAG, "", e);
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }).start();
    }

    /**
     * 显示正在下载的对话框
     */
    //private HttpHandler httpHandler;
    private AlertDialog downloadDialog;//正在下载的对话框
    private TextView tvCur;//当前下载的百分比
    private ProgressBar pb;//下载的进度条
    private TextView tvCanCel;//停止下载
    private TextView tvHidden;//隐藏对话框的按钮
    private int contentLength;//要下载文件的大小
    private boolean isDownloading = false;//是否正在下载
    private boolean isCancel = false;//是否取消升级
    private DecimalFormat df = new DecimalFormat("###.00");//设置结果保留两位小数

    private void showDownloadDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setCancelable(false);// 不可消失
        downloadDialog = adb.create();
        View view = View.inflate(getActivity(), R.layout.download_dialog_layout, null);
        downloadDialog.setView(view, 0, 0, 0, 0);
        tvCur = (TextView) view.findViewById(R.id.tv_cursize);
        tvCanCel = (TextView) view.findViewById(R.id.tv_cancel);
        tvHidden = (TextView) view.findViewById(R.id.tv_hidden);
        pb = (ProgressBar) view.findViewById(R.id.download_pb);

        downloadDialog.show();
    }

    private void showDownloading(Message msg) {

        // 自己写的
        long totalSize = (long) msg.obj;// 总进度
        int curSize = (int) msg.arg1;// 获取当前进度
        pb.setMax((int)totalSize);
        pb.setProgress(curSize);

        // 3 laidi\
        /*int curSize = (int) msg.obj;
        pb.setMax(100);
        pb.setProgress(curSize);*/
    }

    private void stopDownloadDialog(Message msg) {
        // Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT).show();


        isDownloading = false;
        if (downloadDialog.isShowing()) {
            downloadDialog.dismiss();
            // supportFragmentManager.popBackStack();
        }

        showNoticeDialog();

        // 安装apk

        // 自己写的
        String sdcardPath = FileUtil.getSDPath() + "/";
        String apkpath = sdcardPath + downPath+  "/" + apkName;

        // 3来的
        //String apkpath = downPath + apkName;

        // dbtplus
        // String apkpath = (String)msg.obj;

        // 安装apk
        InstallAPK(apkpath);

    }

    /**
     * 安装apk
     *
     * @param apkpath
     */
    private void InstallAPK(String apkpath) {

        try {
            String[] args2 = { "chmod", "604", apkpath};
            Runtime.getRuntime().exec(args2);
        } catch (IOException e) {
            e.printStackTrace();
        }


        File apkfile = new File(apkpath);
        if (!apkfile.exists()) {
            return;
        }

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getActivity(), "et.tsingtaopad.fileProvider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            getActivity().startActivity(intent);
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            getActivity().startActivity(intent);
        }
    }
}
