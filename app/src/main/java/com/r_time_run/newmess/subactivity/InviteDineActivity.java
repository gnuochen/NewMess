package com.r_time_run.newmess.subactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.r_time_run.newmess.R;

public class InviteDineActivity extends AppCompatActivity {
    private ImageView ivBack;
    private ImageView ivInvite;
    private ImageView ivFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_dine);
        ivBack= (ImageView) findViewById(R.id.iv_back);
        ivInvite= (ImageView) findViewById(R.id.iv_invite);
        ivFriend= (ImageView) findViewById(R.id.iv_friend);
        View.OnClickListener inviteActivityListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_back:
                        finish();
                        break;
                    case R.id.iv_invite:
                        break;
                    case R.id.iv_friend:
                        break;
                }
            }
        };
        ivBack.setOnClickListener(inviteActivityListener);
        ivInvite.setOnClickListener(inviteActivityListener);
        ivFriend.setOnClickListener(inviteActivityListener);

    }
}
