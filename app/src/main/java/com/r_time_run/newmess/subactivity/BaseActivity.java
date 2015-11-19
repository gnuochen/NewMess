package com.r_time_run.newmess.subactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.r_time_run.newmess.net.AsyncNMRunner;
import com.r_time_run.newmess.net.NMParameters;
import com.r_time_run.newmess.listener.RequestListener;
import com.r_time_run.newmess.utils.ACache;
import com.r_time_run.newmess.utils.NetworkUtil;

/**
 * Created by nuochen on 2015/9/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements RequestListener {
    public static final int TAG_SELECT_FOODS = 101;
    public static final int TAG_SELECT_SHOPS = 103;
    public static final int TAG_SELECT_SubCategory_FOODS = 105;
    public static final int TAG_REGISTER_PUT = 301;
    public static final int TAG_LOGIN = 302;
    private ACache aCache;
    public android.os.Handler mHandler = new android.os.Handler(){
        public void handleMessage(android.os.Message msg) {
            handleMsg(msg);
        }
    };
    /**
     * 具体实现方法需要自己重写
     * @param msg
     */
    public abstract void handleMsg(android.os.Message msg);

    @Override
    public void onComlete(int tag,String json) {
        ACache aCache = ACache.get(this);
        aCache.put(tag+"",json);
        Log.d("GGG", "BaseActivity_onComplete=+++++++=" + json + "\t" + tag);
        Message msg = mHandler.obtainMessage();
        msg.what = tag;
        Bundle data = new Bundle();
        data.putString("json",json);
        msg.setData(data);
        mHandler.sendMessage(msg);
    }

    @Override
    public void onException(int tag,String json) {
        // TODO Auto-generated method stub
        Log.d("vv", "BaseActivity+onException==" + json);
        Toast.makeText(getApplicationContext(), "请求服务器异常",Toast.LENGTH_SHORT).show();
    }
    /**
     * 通过此方法获得数据连接
     *@param tag
     *@param url
     *@param params
     *@param methond
     * */
    public void getData(int tag,String url,NMParameters params,String methond){
        if(!NetworkUtil.isNetwork(this)){
            Toast.makeText(getApplicationContext(),"网络连接异常",Toast.LENGTH_SHORT).show();
        }
        AsyncNMRunner.request(tag, url, params, methond, this);

    }
}
