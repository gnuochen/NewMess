package com.r_time_run.newmess.net;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by nuochen on 2015/9/14.
 */
public class NMParameters {
    private ArrayList<String> mKeys = new ArrayList<String >();
    private ArrayList<String> mValues = new ArrayList<String>();
    /**
     * 添加String key,String value;
     * @param key
     * @param value
     * */
    public void add(String key,String value){
        if((!TextUtils.isEmpty(key))&&(!TextUtils.isEmpty(value))){
            this.mKeys.add(key);
            this.mValues.add(value);
        }
    }
    /**
     * 添加String key,<String>int value
     * @param key
     * @param value
     */
    public void add(String key, int value)
    {
        this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }

    /**
     * 添加String key,<String>Long value
     * @param key
     * @param value
     */
    public void add(String key, long value) {
        this.mKeys.add(key);
        this.mValues.add(String.valueOf(value));
    }

    /**
     * 通过传入key的ArrayLIst的key 获取 对应的 index
     * @param key
     * @return
     */
    private int getLocation(String key)
    {
        if (this.mKeys.contains(key)) {
            return this.mKeys.indexOf(key);
        }
        return -1;
    }

    /**
     *  通过传入key的ArrayLIst的index 获取 对应的 key
     * @param location
     * @return
     */
    public String getKey(int location) {
        if ((location >= 0) && (location < this.mKeys.size())) {
            return (String)this.mKeys.get(location);
        }
        return "";
    }

    /**
     * 通过String key的ArrayLIst 获取Value的ArrayLIst对应 的String value
     * @param key
     * @return
     */
    public String getValue(String key)
    {
        int index = getLocation(key);
        if ((index >= 0) && (index < this.mKeys.size())) {
            return (String)this.mValues.get(index);
        }

        return null;
    }

    /**
     * 通过key的ArrayLIst的 index 获取 value ArrayList的 String vaule
     * @param location
     * @return
     */
    public String getValue(int location)
    {
        if ((location >= 0) && (location < this.mKeys.size())) {
            String rlt = (String)this.mValues.get(location);
            return rlt;
        }

        return null;
    }

    /**
     * 获取key 对应ArrayList的size()
     * @return
     */
    public int size()
    {
        return this.mKeys.size();
    }

    public void addAll(NMParameters parameters) {
        for (int i = 0; i < parameters.size(); i++)
            add(parameters.getKey(i), parameters.getValue(i));
    }

    /**
     * 清除 ArrayList 中的所有数据
     */
    public void clear()
    {
        this.mKeys.clear();
        this.mValues.clear();
    }


}
