package com.r_time_run.newmess.subactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.r_time_run.newmess.FirstActivity;
import com.r_time_run.newmess.MyInfoActivity;
import com.r_time_run.newmess.R;
import com.r_time_run.newmess.constant.Constant;
import com.r_time_run.newmess.net.NMParameters;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisteActivity extends BaseActivity {

    private EditText et_register_name,et_register_password,et_register_again_password,et_student_id,et_student_subserver_password,
            et_email,et_phone;
    private Button bt_register_ok;
    private Switch sw_logintype;
    private LinearLayout ll_registelayout,ll_student_id,ll_student_subserver_password;
    private String loginType;
    private boolean isRegisted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        ll_registelayout = (LinearLayout)findViewById(R.id.ll_registelayout);
        ll_student_id = (LinearLayout)findViewById(R.id.ll_student_id);
        ll_student_subserver_password = (LinearLayout)findViewById(R.id.ll_student_subserver_password);
        et_register_name = (EditText)findViewById(R.id.et_register_name);
        et_register_name.setOnFocusChangeListener(new RegisterNameFocusListener());    //为用户名添加失去焦点事件
        et_register_password = (EditText)findViewById(R.id.et_register_password);
        et_register_again_password = (EditText)findViewById(R.id.et_register_again_password);
        et_student_id = (EditText)findViewById(R.id.et_student_id);
        et_student_subserver_password = (EditText)findViewById(R.id.et_student_subserver_password);
        et_email = (EditText)findViewById(R.id.et_email);
        et_phone = (EditText)findViewById(R.id.et_phone);
        bt_register_ok = (Button)findViewById(R.id.bt_register_ok);
        sw_logintype = (Switch)findViewById(R.id.sw_logintype);
        sw_logintype.setChecked(true);
        loginType = "学生";
        sw_logintype.setOnCheckedChangeListener(new SwitchListener());
        bt_register_ok.setOnClickListener(new RegisterOkListener());    //为注册按钮添加点击事件
    }

    /**
     * 商家和学生切换按钮
     */
    private class SwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                ll_student_id.setVisibility(View.VISIBLE);
                ll_student_subserver_password.setVisibility(View.VISIBLE);
                loginType = "学生";
            } else {
                ll_student_id.setVisibility(View.GONE);
                ll_student_subserver_password.setVisibility(View.GONE);
                ll_registelayout.invalidate();
                loginType = "商家";
            }
        }
    }

    /**
     * 当用户名编辑框失去焦点时事件
     */
    private class RegisterNameFocusListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus){
                et_register_name.setBackgroundResource(R.drawable.registe_edittext_back);
            } else {
                String register_name = et_register_name.getText().toString();
                if (register_name == null || register_name.equals("")){
                    Toast.makeText(RegisteActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    et_register_name.setBackgroundResource(R.drawable.error_edittext_back);
                } else {
                    if (!isNameExist()){
                        Toast.makeText(RegisteActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                        et_register_name.setBackgroundResource(R.drawable.error_edittext_back);
                    }
                }
            }
        }
    }
    /**
     * 为注册按钮点击事件
     */
    private class RegisterOkListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //对密码编辑的判断
            String register_password = et_register_password.getText().toString();
            String register_again_password = et_register_again_password.getText().toString();
            String student_id = et_student_id.getText().toString();
            String subserverPassword = et_student_subserver_password.getText().toString();
            if (!isNameExist()){
                Toast.makeText(RegisteActivity.this, "用户名已经存在", Toast.LENGTH_SHORT).show();
                et_register_name.setBackgroundResource(R.drawable.error_edittext_back);
            } else if (register_password == null || register_password.equals("")){
                Toast.makeText(RegisteActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            } else if (register_again_password == null || register_again_password.equals("")){
                Toast.makeText(RegisteActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
            } else if (!register_again_password.equals(register_password)){
                Toast.makeText(RegisteActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            } else if (sw_logintype.isChecked()&&(student_id == null || student_id.equals(""))){
                Toast.makeText(RegisteActivity.this,"学号不能为空",Toast.LENGTH_SHORT).show();
            } else if (sw_logintype.isChecked()&&(subserverPassword == null || subserverPassword.equals(""))){
                Toast.makeText(RegisteActivity.this,"子服务器密码不能为空",Toast.LENGTH_SHORT).show();
            } else if (sw_logintype.isChecked()&&(!isStudentIdExist())){
                Toast.makeText(RegisteActivity.this,"该学号不存在",Toast.LENGTH_SHORT).show();
            } else if (sw_logintype.isChecked()&&(!isSubservicePassTrue())){
                Toast.makeText(RegisteActivity.this,"子服务系统密码输入错误",Toast.LENGTH_SHORT).show();
            } else {
                putUpToService();   //将数据存入数据库
            }
        }
    }

    /**
     * 未实现功能：
     * 判断该用户名是否已存在
     */
    private boolean isNameExist(){
        /**
         *
         */
        return true;
    }
    /**
     * 未实现功能：
     * 判断该学号是否已存在
     */
    private boolean isStudentIdExist(){
        /**
         *
         */
        return true;
    }
    /**
     * 未实现功能：
     * 判断该学号与自服务系统密码是否一致是否已存在
     */
    private boolean isSubservicePassTrue(){
        /**
         *
         */
        return true;
    }
    /**
     * 未实现功能：
     * 将数据存入数据库
     */
    private void putUpToService(){
        NMParameters registerParames = new NMParameters();
        registerParames.add("action", "register");
        registerParames.add("name",et_register_name.getText().toString());
        registerParames.add("password",et_register_password.getText().toString());
        registerParames.add("id",et_student_id.getText().toString());
        registerParames.add("subpassword",et_student_subserver_password.getText().toString());
        registerParames.add("email",et_email.getText().toString());
        registerParames.add("phone",et_phone.getText().toString());
        registerParames.add("logintype",loginType);
        getData(TAG_REGISTER_PUT, Constant.URL_FOODS_TEXT,registerParames,"POST");
    }

    @Override
    public void handleMsg(Message msg) {
        Bundle data = msg.getData();
        String json = data.getString("json");
        try {
            JSONObject obj = new JSONObject(json);
            if (msg.what == TAG_REGISTER_PUT){
                isRegisted = obj.getBoolean("state");
                Toast.makeText(RegisteActivity.this,isRegisted+"",Toast.LENGTH_SHORT).show();
                if (isRegisted){
                    Toast.makeText(RegisteActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
                    Log.e("RegisteActivity","注册成功");
                    backToFirstActivity(); //返回主页进行登录
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /**
     * 返回主页
     */
    private void backToFirstActivity(){
        MyInfoActivity.registeToFirst = true;
        Bundle data = new Bundle();
        data.putString("user_name",et_register_name.getText().toString());
        data.putString("user_password", et_register_password.getText().toString());
        Intent intent = new Intent(this, MyInfoActivity.class);
        intent.putExtras(data);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(this,FirstActivity.class));
            finish();
        }
        return false;
    }
}
