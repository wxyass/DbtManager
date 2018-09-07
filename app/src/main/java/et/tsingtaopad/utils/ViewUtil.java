package et.tsingtaopad.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;


import et.tsingtaopad.R;
import et.tsingtaopad.main.ConstValues;


/**
 * UI工具类
 */
public class ViewUtil {
    
    // 以下两属性用于防快速重复单击
    private static int clickViewId;
    private static long prevClick;
    
    private static int clickPrevId;
    
    // ListView防行重复单击
    private static int prevLvId = -1;

    /**
     * 设置ListView的高度
     * 
     * @param lv            ListView对象
     * @param maxHeight     最大高度
     */
    public static void initListViewHeight(ListView lv, int maxHeight) {
        
        if (lv != null ) {
            ListAdapter listAdapter = lv.getAdapter();   
            if (listAdapter == null) return;

            int totalHeight = 0; 
            for (int i = 0; i < listAdapter.getCount(); i++) {  
                View listItem = listAdapter.getView(i, null, lv);
                int w = View.MeasureSpec.makeMeasureSpec(
                                        0,View.MeasureSpec.UNSPECIFIED);  
                int h = View.MeasureSpec.makeMeasureSpec(
                                        0,View.MeasureSpec.UNSPECIFIED);  
                listItem.measure(w, h);
                totalHeight += listItem.getMeasuredHeight(); 
            }  

            ViewGroup.LayoutParams params = lv.getLayoutParams(); 
            totalHeight += (lv.getDividerHeight() * 
                                (listAdapter.getCount() - 1));  
            if (totalHeight > maxHeight && maxHeight > 0) {
                totalHeight = maxHeight;
            }
            params.height = totalHeight; 
            lv.setLayoutParams(params);  
        }  
    }
    /**
     * 设置ListView的高度
     * @param listView
     */
    public static void setListViewHeight(ListView listView) {  
        
        // 获取ListView对应的Adapter  
        ListAdapter listAdapter = listView.getAdapter();
      
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目  
            View listItem = listAdapter.getView(i, null, listView);
            /*if (listItem.getLayoutParams() == null) {
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }*/
            listItem.measure(0, 0); // 计算子项View 的宽高  
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度  
        }  
      
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        // params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        // 最后再加上分割线的高度和padding高度，否则显示不完整。
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * 向界面发送提示消息
     * 
     * @param context   上下文环境
     * @param msg       消息内容或对应的资源ID
     */
    public static void sendMsg(Context context, Object msg) {
        sendMsg(context, msg, null);
    }
    public static void sendMsg(Context context, Object msg, Handler handler) {
        
        if (context == null || msg == null) return;
        
        Bundle bundle = null;
        Message message = null;
        if (handler != null) {
            message = handler.obtainMessage(ConstValues.WAIT1);
            bundle = message.getData();
            
        } else {
            message = ConstValues.msgHandler.obtainMessage(ConstValues.WAIT1);
            bundle = message.getData();
        }

        if (msg instanceof Integer) {
            bundle.putString("msg", context
                    .getString(Integer.valueOf(msg.toString())));
        } else {
            bundle.putString("msg", String.valueOf(msg));
        }
        message.what = ConstValues.WAIT1;
        message.setData(bundle);
        if (handler != null) {
            handler.sendMessageDelayed(message, 500);
        } else {
            ConstValues.msgHandler.sendMessageDelayed(message, 500);
        }
    }
    

    /**
     * 处理RadioGroup里二次单击取消选中状态
     * 
     * @param rg
     * @param rbId
     */
    public static void initRadioButton(RadioGroup rg, int rbId) {
        int rgId = rg.getTag() == null ? 0 : Integer.parseInt(rg.getTag().toString());
        if (rgId == rbId) {
            rg.clearCheck();
            rg.setTag(null);
        } else {
            rg.setTag(rbId);
        }
    }
    /**
     * 处理RadioGroup里二次单击不做取消选中状态
     * 
     * @param rg
     * @param rbId
     */
    public static void initRadioButton2(RadioGroup rg, int rbId) {
    		rg.setTag(rbId);
    }
    
    /**
     * 防止按钮快速重复单击
     * 
     * @param viewId    被单击组会的Id
     * @param interval  间隔多少毫秒
     * @return
     */
    public static boolean isDoubleClick(int viewId, int interval) {
        long currClick = System.currentTimeMillis();
        if (clickViewId == viewId && currClick - prevClick < interval) {
            return true;
        } else {
            clickViewId = viewId; 
            prevClick = currClick;
            return false;
        }
    }
    public static boolean isDoubleClick(int viewId) {
        return isDoubleClick(viewId, 2000);
    }
    
    /**
     * 防止重复单击
     * 
     * @param v 防止重复单击的View; 如果不想校验, 则添加v.setTag(R.id.tag_viewclick, false);
     * @return true:重复单击
     */
    public static boolean isDoubleClick(View v) {
        
        // 获取校验标志，默认值为true(校验)
        Object tagObj = v.getTag(R.id.tag_viewclick);
        boolean validFlag = tagObj == null ? true : (Boolean)tagObj;
        
        // 如果要校验且与上次单击组件id一致，则认为重复单击
        if (validFlag && (clickPrevId == v.getId())) {
            clickPrevId = v.getId();
            return true;
            
        } else {
            clickPrevId = v.getId();
            return false;
        }
    }
    /**
     * 强制隐藏软键盘
     * @param v
     */
    public static void hideSoftInputFromWindow(Activity activity,View v){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘 
    }
    
    /**
     * 清除防重复单击状态
     */
    public static void clearDoubleClick() {
        clickPrevId = -1;
        prevLvId = -1;
    }
    
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 
     * @param context 上下文
     * @param dpValue dp值
     * @return        px值
     */
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * 
     * @param context 上下文
     * @param pxValue px值
     * @return        dp值
     */
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
    
}
