package com.r_time_run.newmess.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 将对象保存到文件中和从文件中取出对象
 * Created by zouxiaobang on 15/11/5.
 */
public class ObjectBuffer {
    public boolean saveObjectToFile(Object object,String objName){
        //将对象保存进文件中
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File sdFileDir = new File(sdCardDir, objName);
            try
            {
                FileOutputStream fos = new FileOutputStream(sdFileDir,true);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                fos.close();
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    //从文件中获取多个对象
    public ArrayList<Object> readObjectFromFile(String objName){
        ArrayList<Object> objs = new ArrayList<Object>();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File sdFileDir = new File(sdCardDir, objName);
            try
            {
                FileInputStream fis = new FileInputStream(sdFileDir);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object object = null;
                while ((object = ois.readObject())!=null){
                    objs.add(object);
                }
                fis.close();
                return objs;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}
