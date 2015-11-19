package com.r_time_run.newmess.listener;

/**
 * Created by nuochen on 2015/9/14.
 */
public interface RequestListener {
    public void onComlete(int tag,String json);

    public void onException(int tag,String json);
}
