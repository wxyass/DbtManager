package com.core.utils.dbtutil;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class LogUtils
{
    private static LogUtils logUtils;

    public static LogUtils getInstances()
    {
        if (logUtils == null)
        {
            logUtils = new LogUtils();
        }
        return logUtils;
    }

    /***
     * 往文件中写内容
     * @param filePath
     * @param filename
     * @param value
     */
    public synchronized void write(String filePath, String filename, String value)
    {
        FileOutputStream fileOutputStream = null;
        try
        {
            createFile(filePath, filename);
            fileOutputStream = new FileOutputStream(filePath + filename, true);
            //            byte[] bytes = (getDataFormat(System.currentTimeMillis()) + "  " + value + "\n").getBytes("GBK");
            byte[] bytes = (value + "\n").getBytes("UTF-8");
            fileOutputStream.write(bytes, 0, bytes.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileOutputStream.flush();
                fileOutputStream.close();
                // fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /***
     * 读取文件
     * @param filePath
     * @param filename
     * @return
     */
    public synchronized String readFile(String filePath, String filename)
    {
        String content = "";
        FileInputStream fin = null;
        try
        {
            if (isExsistFile(filePath, filename))
            {
                // 文件流读取文件  
                fin = new FileInputStream(filePath + filename);
                // 获得字符长度  
                int length = fin.available();
                // 创建字节数组  
                byte[] buffer = new byte[length];
                // 把字节流读入数组中  
                fin.read(buffer);
                // 获得编码格式  
                //            String type = codetype(buffer);
                content = EncodingUtils.getString(buffer, "UTF-8");
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    // 关闭文件流  
                    fin.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return content;
    }

    /***
     * 清除文件
     * @param filePath
     * @param filename
     */
    public synchronized void delFile(String filePath, String filename)
    {
        try
        {
            if (isExsistFile(filePath, filename))
            {
                File file = new File(filePath + filename);
                file.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /***
     * 创建文件夹以及文件
     * @param filePath
     * @param filename
     */
    private void createFile(String filePath, String filename)
    {
        try
        {
            if (!isExsistFile(filePath, filename))
            {
                File dir=new File(filePath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(filePath + filename);
                file.createNewFile();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /***
     * 判断文件是否存在
     * @param filePath
     * @param filename
     */
    private Boolean isExsistFile(String filePath, String filename)
    {
        Boolean isExists = false;
        try
        {
            File file = new File(filePath + filename);
            isExists = file.exists();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return isExists;
    }
}
