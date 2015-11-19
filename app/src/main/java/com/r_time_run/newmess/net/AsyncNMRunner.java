package com.r_time_run.newmess.net;

import com.r_time_run.newmess.listener.RequestListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nuochen on 2015/9/14.
 */
public class AsyncNMRunner {
    public static void request(final int tag,final String url,
                               final NMParameters params,
                               final String method,
                               final RequestListener listener){
        ExecutorService es = Executors.newFixedThreadPool(10);
        es.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    String json = HttpManager.openUrl(url,method,params,params.getValue("profil_image"));
                    listener.onComlete(tag,json);
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onException(tag,e.toString()+"请求服务器连接异常");
                }
            }
        });
    }
}
