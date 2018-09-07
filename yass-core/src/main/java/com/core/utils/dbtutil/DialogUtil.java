package com.core.utils.dbtutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.core.R;
import com.core.adapter.MultipleKeyValueAdapter;
import com.core.adapter.MultipleSelectKeyValueAdapter;
import com.core.adapter.SingleKeyValueAdapter;
import com.core.initbase.KvStc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 */
public class DialogUtil {
    public static Boolean onclick;
    /**
     * 多选弹出框 
     * 
     * @param context           上下文环境
     * @param tiggerBt          弹出框触发view
     * @param titleId           弹出框标题
     * @param dataLst           要显示数据源
     * @param fieldName         0:编码对应属性名称， 1:显示内容对应属性名称
     * @param defaultTextId        默认选中项的编码集合，若为numm按tiggerBt.getTag()
     * @param defaultTextId     无选中项时，弹出框触发view的显示内容
     */
    @SuppressWarnings("rawtypes")
    public static void showMultipleDialog(final Activity context, 
                final Button tiggerBt, int titleId, final List dataLst, 
                                final String[] fieldName, final int defaultTextId){
        showMultipleDialog(context, tiggerBt, titleId, dataLst, fieldName, null, defaultTextId);
    }

    /**
     * 多选弹出框 
     * 
     * @param context           上下文环境
     * @param tiggerBt          弹出框触发view
     * @param titleId           弹出框标题
     * @param dataLst           要显示数据源
     * @param fieldName         0:编码对应属性名称， 1:显示内容对应属性名称
     * @param selectedId        默认选中项的编码集合，若为numm按tiggerBt.getTag()
     * @param defaultTextId     无选中项时，弹出框触发view的显示内容
     */
    @SuppressWarnings("rawtypes")
    public static void showMultipleDialog(final Activity context, 
                final Button tiggerBt, int titleId, final List dataLst, 
                    final String[] fieldName, List<String> selectedId , final int defaultTextId) {
        
        // 让触发者不可用，防重复单击
        tiggerBt.setEnabled(false);
        
        // 加载视图
        View formView = LayoutInflater.from(context).inflate(R.layout.common_multiple_dialog, null);
        final ListView dataLv = (ListView) formView.findViewById(R.id.common_multiple_lv);
        final Button submitBt = (Button) formView.findViewById(R.id.common_multiple_bt_submit);
        final Button cancelBt = (Button) formView.findViewById(R.id.common_multiple_bt_cancel);
        TextView titleTv = (TextView) formView.findViewById(R.id.common_multiple_tv_title);
        titleTv.setText(context.getString(titleId, ""));

       // 绑定列表数据
       if (tiggerBt.getTag() != null) {
           selectedId =Arrays.asList(tiggerBt.getTag().toString().replace("||", ",").replace("|", "").split(","));
       } 
       if (CheckUtil.IsEmpty(selectedId)) {
           tiggerBt.setTag("");
       } else {
           StringBuffer buffer = new StringBuffer();
           for (int j = 0; j < selectedId.size(); j++) {
               buffer.append("|").append(selectedId.get(j)).append("|");
           }
           tiggerBt.setTag(buffer.toString());
           cancelBt.setTag(buffer.toString());
       }
       MultipleKeyValueAdapter adapter = new MultipleKeyValueAdapter(context, dataLst, fieldName, selectedId);
       dataLv.setAdapter(adapter);
       dataLv.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               CheckBox itemCB = (CheckBox) arg1.findViewById(R.id.common_multiple_cb_lvitem);
               TextView itemTv = (TextView) arg1.findViewById(R.id.common_multiple_tv_lvitem);
               itemCB.toggle();//点击整行可以显示效果
               String idStr = "|" + FunUtil.isBlankOrNullTo(itemTv.getHint(), " ") + "|";
               if (itemCB.isChecked()) {
                   tiggerBt.setTag(tiggerBt.getTag().toString() + idStr);
               } else {
                   tiggerBt.setTag(tiggerBt.getTag().toString().replace(idStr, ""));
               }
           }
       });
     
       // 显示对话框
       final AlertDialog dialog = new AlertDialog.Builder(context).create();
       dialog.setView(formView, 0, 0, 0, 0);
       dialog.setCancelable(false);
       dialog.show();
      
       // 确定
       submitBt.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View arg0) {
               if (!CheckUtil.IsEmpty(dataLst)) {
                   String[] checkIds = tiggerBt.getTag().toString().replace("||",",").replace("|", "").split(",");
                   List<String> idLst = FunUtil.getPropertyByName(dataLst, fieldName[0], String.class);
                   List<String> nameLst = FunUtil.getPropertyByName(dataLst, fieldName[1], String.class);
                   StringBuffer keyBuffer = new StringBuffer();
                   StringBuffer nameBuffer = new StringBuffer();
                   int total = 0;
                   for (int i = 0; i < idLst.size(); i++) {
                       for (String id : checkIds) {
                            if (id.equals(idLst.get(i))) {
                                total = total + 1;
                              keyBuffer.append(idLst.get(i)).append(",");
                              nameBuffer.append(nameLst.get(i)).append(",");
                              continue;
                            }
                       }
                       if (total == checkIds.length) {
                           break;
                       }
                   }
                   if (keyBuffer.length() > 1 && nameBuffer.length() > 1) {
                       tiggerBt.setText(nameBuffer.deleteCharAt(nameBuffer.length() -1));
                       tiggerBt.setTag(keyBuffer.deleteCharAt(keyBuffer.length() -1));
                   }else {
                       tiggerBt.setText(context.getString(defaultTextId, ""));
                       tiggerBt.setTag(null);
                   }
               }
               tiggerBt.setEnabled(true);
               dialog.dismiss();
           }
       });
       
       // 取消
       cancelBt.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View arg0) {
               tiggerBt.setEnabled(true);
               tiggerBt.setTag(cancelBt.getTag());
               onclick=false;
               dialog.dismiss();
           }
       });
    }
    
    /**
     * 多选弹出框 (带全选)
     * 
     * @param context           上下文环境
     * @param tiggerBt          弹出框触发view
     * @param titleId           弹出框标题
     * @param dataLst           要显示数据源
     * @param fieldName         0:编码对应属性名称， 1:显示内容对应属性名称
     * @param selectedId        默认选中项的编码集合，若为numm按tiggerBt.getTag()
     * @param defaultTextId     无选中项时，弹出框触发view的显示内容
     */
    @SuppressWarnings("rawtypes")
    public static void showAllMultipleDialog(final Activity context, 
                final Button tiggerBt, int titleId, final List dataLst, 
                    final String[] fieldName, List<String> selectedId , final int defaultTextId) {
    	
    	final List<KvStc> typeLst = new ArrayList<KvStc>(dataLst);
        
        // 让触发者不可用，防重复单击
        tiggerBt.setEnabled(false);
        
        // 加载视图
        View formView = LayoutInflater.from(context).inflate(R.layout.common_multiple_dialog, null);
        final ListView dataLv = (ListView) formView.findViewById(R.id.common_multiple_lv);
        final Button submitBt = (Button) formView.findViewById(R.id.common_multiple_bt_submit);
        final Button cancelBt = (Button) formView.findViewById(R.id.common_multiple_bt_cancel);
        TextView titleTv = (TextView) formView.findViewById(R.id.common_multiple_tv_title);
        titleTv.setText(context.getString(titleId, ""));

       // 从按钮tag中获取已选中数据 
       selectedId = new ArrayList<String>();// 已选中的集合  key的集合
       if (tiggerBt.getTag() != null) {
           selectedId =Arrays.asList(tiggerBt.getTag().toString().replace("||", ",").replace("|", "").split(","));
       } 
       if (CheckUtil.IsEmpty(selectedId)) {
           tiggerBt.setTag("");
       } else {
           StringBuffer buffer = new StringBuffer();
           for (int j = 0; j < selectedId.size(); j++) {
               buffer.append("|").append(selectedId.get(j)).append("|");
           }
           tiggerBt.setTag(buffer.toString());
           cancelBt.setTag(buffer.toString());
       }

		// ----
		for (KvStc kvstc : typeLst) {
			for (String item : selectedId) {
				if (kvstc.getKey().equals(item)) {
					kvstc.setIsDefault("1");// 0:没选中 1已选中
				}
			}
		}

       final MultipleSelectKeyValueAdapter sadapter = new MultipleSelectKeyValueAdapter(context, typeLst, fieldName, tiggerBt);
       dataLv.setAdapter(sadapter);  
       dataLv.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
    	   public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    		   CheckBox itemCB = (CheckBox) arg1.findViewById(R.id.common_multiple_cb_lvitem);
               TextView itemTv = (TextView) arg1.findViewById(R.id.common_multiple_tv_lvitem);
               itemCB.toggle();//点击整行可以显示效果
               
               if(arg2==0){
            	   if (itemCB.isChecked()) {
            		   StringBuffer buffer = new StringBuffer();
                       for (int j = 0; j < dataLst.size(); j++) {
                    	   // 
                           buffer.append("|").append(((KvStc)dataLst.get(j)).getKey()).append("|");
                           ((KvStc)typeLst.get(j)).setIsDefault("1");
                       }
                       tiggerBt.setTag(buffer.toString());
                   } else {
                	   for (int j = 0; j < dataLst.size(); j++) {
                           ((KvStc)typeLst.get(j)).setIsDefault("0");
                       }
                	   tiggerBt.setTag("");
                   } 
               }else{
            	   String idStr = "|" + FunUtil.isBlankOrNullTo(itemTv.getHint(), " ") + "|";
                   if (itemCB.isChecked()) {
                       tiggerBt.setTag(tiggerBt.getTag().toString() + idStr);
                       ((KvStc)typeLst.get(arg2)).setIsDefault("1");
                   } else {
                       tiggerBt.setTag(tiggerBt.getTag().toString().replace(idStr, ""));
                       ((KvStc)typeLst.get(arg2)).setIsDefault("0");
                   } 
               }
               sadapter.notifyDataSetChanged();
    	}
       });
       
       //----
     
       // 显示对话框
       final AlertDialog dialog = new AlertDialog.Builder(context).create();
       dialog.setView(formView, 0, 0, 0, 0);
       dialog.setCancelable(false);
       dialog.show();
      
       // 确定
       submitBt.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View arg0) {
               if (!CheckUtil.IsEmpty(dataLst)) {
                   String[] checkIds = tiggerBt.getTag().toString().replace("||",",").replace("|", "").split(",");
                   List<String> idLst = FunUtil.getPropertyByName(dataLst, fieldName[0], String.class);
                   List<String> nameLst = FunUtil.getPropertyByName(dataLst, fieldName[1], String.class);
                   StringBuffer keyBuffer = new StringBuffer();
                   StringBuffer nameBuffer = new StringBuffer();
                   int total = 0;
                   for (int i = 0; i < idLst.size(); i++) {
                       for (String id : checkIds) {
                            if (id.equals(idLst.get(i))) {
                                total = total + 1;
                              keyBuffer.append(idLst.get(i)).append(",");
                              nameBuffer.append(nameLst.get(i)).append(",");
                              continue;
                            }
                       }
                       if (total == checkIds.length) {
                           break;
                       }
                   }
                   if (keyBuffer.length() > 1 && nameBuffer.length() > 1) {
                       tiggerBt.setText(nameBuffer.deleteCharAt(nameBuffer.length() -1));
                       tiggerBt.setTag(keyBuffer.deleteCharAt(keyBuffer.length() -1));
                   }else {
                       tiggerBt.setText(context.getString(defaultTextId, ""));
                       tiggerBt.setTag(null);
                   }
               }
               tiggerBt.setEnabled(true);
               dialog.dismiss();
           }
       });
       
       // 取消
       cancelBt.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View arg0) {
               tiggerBt.setEnabled(true);
               tiggerBt.setTag(cancelBt.getTag());
               onclick=false;
               dialog.dismiss();
           }
       });
    }
    
    /**
     * 单选弹出框 
     * 
     * @param context           上下文环境
     * @param tiggerBt          弹出框触发view， tiggerBt.getTag()存默认或选中的主键
     * @param dataLst           弹出框标题
     * @param dataLst           要显示数据源
     * @param fieldName         0:编码对应属性名称， 1:显示内容对应属性名称
     */
    @SuppressWarnings({ "rawtypes", "unused" })
    public static void showSingleDialog(final Activity context, 
                final Button tiggerBt, final List dataLst, final String[] fieldName) {
        
        // 让触发者不可用，防重复单击
        tiggerBt.setEnabled(false);
        // 加载视图
        View formView = LayoutInflater.from(context).inflate(R.layout.common_single_dialog, null);
        final ListView dataLv = (ListView) formView.findViewById(R.id.common_single_lv);
        final Button submitBt = (Button) formView.findViewById(R.id.common_single_bt_submit);
        final Button cancelBt = (Button) formView.findViewById(R.id.common_single_bt_cancel);
        TextView titleTv = (TextView) formView.findViewById(R.id.common_single_tv_title);
        
        // 显示对话框
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(formView, 0, 0, 0, 0);
        dialog.show();
        dialog.setOnCancelListener(new OnCancelListener() {
            
            @Override
            public void onCancel(DialogInterface dialog) {
                tiggerBt.setEnabled(true);
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            
            @Override
            public void onDismiss(DialogInterface arg0) {
                tiggerBt.setEnabled(true);
            }
        });

       // 绑定列表数据
       String selectedId = null;
       if (tiggerBt.getTag() != null) {
           selectedId = tiggerBt.getTag().toString();
           tiggerBt.setTag(selectedId);
           cancelBt.setTag(selectedId);
       } 
       SingleKeyValueAdapter adapter = new SingleKeyValueAdapter(context, dataLst, fieldName, selectedId);
       dataLv.setAdapter(adapter);
       dataLv.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               RadioButton itemRB = (RadioButton) arg1.findViewById(R.id.common_single_rb_lvitem);
               TextView itemTv = (TextView) arg1.findViewById(R.id.common_single_tv_lvitem);
               itemRB.toggle();
               tiggerBt.setEnabled(true);
               tiggerBt.setTag(itemTv.getTag());
               tiggerBt.setText(itemTv.getText().toString());
               dialog.dismiss();
           }
       });

       // 确定 (待改进--在显示确定及取消按钮时需处理)
       submitBt.setOnClickListener(new OnClickListener() {

           @Override
           public void onClick(View arg0) {
               if (!CheckUtil.IsEmpty(dataLst)) {
                   String[] checkIds = tiggerBt.getTag().toString().replace("||",",").replace("|", "").split(",");
                   List<String> idLst = FunUtil.getPropertyByName(dataLst, fieldName[0], String.class);
                   List<String> nameLst = FunUtil.getPropertyByName(dataLst, fieldName[1], String.class);
                   StringBuffer keyBuffer = new StringBuffer();
                   StringBuffer nameBuffer = new StringBuffer();
                   int total = 0;
                   for (int i = 0; i < idLst.size(); i++) {
                       for (String id : checkIds) {
                            if (id.equals(idLst.get(i))) {
                                total = total + 1;
                              keyBuffer.append(idLst.get(i)).append(",");
                              nameBuffer.append(nameLst.get(i)).append(",");
                              continue;
                            }
                       }
                       if (total == checkIds.length) {
                           break;
                       }
                   }
                   if (keyBuffer.length() > 1 && nameBuffer.length() > 1) {
                       tiggerBt.setText(nameBuffer.deleteCharAt(nameBuffer.length() -1));
                       tiggerBt.setTag(keyBuffer.deleteCharAt(keyBuffer.length() -1));
                   }else {
                       // tiggerBt.setText(context.getString(defaultTextId, ""));
                       tiggerBt.setTag(null);
                   }
               }
               tiggerBt.setEnabled(true);
               dialog.dismiss();
           }
       });
       
       // 取消
       cancelBt.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View arg0) {
               tiggerBt.setEnabled(true);
               tiggerBt.setTag(cancelBt.getTag());
               dialog.dismiss();
           }
       });
   
    }
    
    /**
     * 查询等待提示框
     * 
     * @param context
     * @param msgId     提示信息资源ID,为0时不显示提示信息
     * @return
     */
    public static AlertDialog showProgressDialog(Activity context, int msgId) {

        AlertDialog dialog = new AlertDialog.Builder(context).setCancelable(false).create();
        View view = context.getLayoutInflater().inflate(R.layout.dialog_progress,null);
        TextView msgTv = (TextView)view.findViewById(R.id.textView1);
        if (msgId == 0) {
            msgTv.setVisibility(View.GONE);
        } else {
            msgTv.setText(context.getString(msgId, ""));
        }
        dialog.setView(view, 0, 0, 0, 0);
        return dialog;
    }
}
