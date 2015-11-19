package com.r_time_run.newmess.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zouxiaobang on 15/9/19.
 */
public class LoginTest {
    private String name, password;
    private Context context;
    public LoginTest(String name, String password, Context context){
        this.name = name;
        this.password = password;
        this.context = context;
    }

    //对账号和密码进行判断
    public boolean judge(){
        if (name == null || name.equals("")){
            Toast.makeText(context, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (password == null || password.equals("")){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
        } else if (!judgeNameToPassword()){
            Toast.makeText(context,"密码输入错误",Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    /**
     * 对账号和密码进行匹配判断
     */
    private boolean judgeNameToPassword(){

        return true;
    }

    /**
     * 获取数据
     */
    private void getData(){

    }
}
