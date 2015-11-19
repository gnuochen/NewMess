package com.r_time_run.newmess.subactivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.r_time_run.newmess.R;

public class ChangePass extends Activity {
    private TextView bt_changepassword_back;
    private EditText et_changepassword_oripass,et_changepassword_newpass,et_changepassword_newpass_again;
    private Button bt_changepassword_ok;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        bt_changepassword_back = (TextView)findViewById(R.id.bt_changepassword_back);
        et_changepassword_oripass = (EditText)findViewById(R.id.et_changepassword_oripass);
        et_changepassword_newpass = (EditText)findViewById(R.id.et_changepassword_newpass);
        et_changepassword_newpass_again = (EditText)findViewById(R.id.et_changepassword_newpass_again);
        bt_changepassword_ok = (Button)findViewById(R.id.bt_changepassword_ok);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        bt_changepassword_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oriPass = sp.getString("userpassword","");
                if (et_changepassword_oripass == null || et_changepassword_newpass == null || et_changepassword_newpass_again == null){
                    Toast.makeText(ChangePass.this,"请填写完信息",Toast.LENGTH_SHORT).show();
                } else if(!oriPass.equals(et_changepassword_oripass.getText().toString())){
                    Toast.makeText(ChangePass.this,"原密码错误",Toast.LENGTH_SHORT).show();
                } else if (!et_changepassword_newpass.getText().toString().equals(et_changepassword_newpass_again.getText().toString())){
                    Toast.makeText(ChangePass.this,"新密码两次输入不一致",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePass.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangePass.this.finish();
                }
            }
        });

        bt_changepassword_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePass.this.finish();
            }
        });
    }

}
