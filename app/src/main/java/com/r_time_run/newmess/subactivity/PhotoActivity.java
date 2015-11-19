package com.r_time_run.newmess.subactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.utils.FileUtils;

import java.io.File;

public class PhotoActivity extends Activity {

    private ImageView iv_pre_photo;
    private SharedPreferences sp;
    private String picName;
    private Button bt_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        iv_pre_photo = (ImageView) findViewById(R.id.iv_pre_photo);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        bt_ok = (Button) findViewById(R.id.bt_ok);

        Intent intent = getIntent();
        picName = intent.getStringExtra("picName");
        if (picName != null){
            /**
             * 预览头像图片
             */

            //取出照片
            Bitmap touxiang = BitmapFactory.decodeFile(FileUtils.SDPICPATH+picName+ ".jpg");
            //显示照片
            if (touxiang != null){
                iv_pre_photo.setImageBitmap(touxiang);
            }
        }
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 删除缓冲区的照片
                 */
                File picFile = new File(FileUtils.SDPICPATH+picName+ ".jpg");
                if (picFile.exists()){
                    System.out.println("the file is delete .....");
                    picFile.delete();
                }
                /**
                 * 退出该页面，刷新我的详细信息界面上的头像
                 */
                Intent intent1 = new Intent();
                setResult(2,intent1);
                PhotoActivity.this.finish();
            }
        });
    }


}
