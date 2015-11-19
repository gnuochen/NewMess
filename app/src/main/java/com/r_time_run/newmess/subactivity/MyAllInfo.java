package com.r_time_run.newmess.subactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.r_time_run.newmess.R;
import com.r_time_run.newmess.utils.FileUtils;
import com.r_time_run.newmess.utils.RoundImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MyAllInfo extends Activity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private TextView bt_allinfo_back;
    private Button bt_myallinfo_edit,bt_myallinfo_finish;
    private LinearLayout ll_myallinfo_touxiang;
    private EditText et_myallinfo_name,et_myallinfo_phone,et_myallinfo_email;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private RoundImageView riv_myallinfo_touxiang;
    private int width,height;
    private String picName;
    private boolean isChanged = false,isChanging = false;
    private Bitmap scaledBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_info);

        bt_allinfo_back = (TextView)findViewById(R.id.bt_allinfo_back);
        bt_myallinfo_edit = (Button)findViewById(R.id.bt_myallinfo_edit);
        bt_myallinfo_finish = (Button)findViewById(R.id.bt_myallinfo_finish);
        bt_myallinfo_finish.setClickable(false);
        bt_myallinfo_finish.setEnabled(false);
        ll_myallinfo_touxiang = (LinearLayout)findViewById(R.id.ll_myallinfo_touxiang);
        ll_myallinfo_touxiang.setClickable(false);
        ll_myallinfo_touxiang.setEnabled(false);
        et_myallinfo_name = (EditText)findViewById(R.id.et_myallinfo_name);
        et_myallinfo_name.setEnabled(false);
        et_myallinfo_phone = (EditText)findViewById(R.id.et_myallinfo_phone);
        et_myallinfo_phone.setEnabled(false);
        et_myallinfo_email = (EditText)findViewById(R.id.et_myallinfo_email);
        et_myallinfo_email.setEnabled(false);
        riv_myallinfo_touxiang = (RoundImageView)findViewById(R.id.riv_myallinfo_touxiang);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        //初始化设置头像
        Bitmap touxiang = BitmapFactory.decodeFile(sp.getString("picPath", ""));
        if (touxiang != null){
            riv_myallinfo_touxiang.setImageBitmap(touxiang);
        }
        //初始化账号
        String name = sp.getString("username","");
        et_myallinfo_name.setText(name);

        WindowManager wm = this.getWindowManager();

        width= wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        bt_allinfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAllInfo.this.finish();
            }
        });
        bt_myallinfo_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChanging = true;
                final String userPass = sp.getString("userpassword", "");
                final LinearLayout ll_myallinfo_pass = (LinearLayout)getLayoutInflater().inflate(R.layout.password_again, null);
                new AlertDialog.Builder(MyAllInfo.this)
                        .setIcon(R.drawable.tool)
                        .setTitle("解锁信息修改")
                        .setView(ll_myallinfo_pass)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_myallinfo_pass = (EditText)ll_myallinfo_pass.findViewById(R.id.et_myallinfo_pass);
                                if (et_myallinfo_pass == null) {
                                    Toast.makeText(MyAllInfo.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                                } else if (userPass.equals(et_myallinfo_pass.getText().toString())) {
                                    bt_myallinfo_finish.setClickable(true);
                                    bt_myallinfo_finish.setEnabled(true);
                                    ll_myallinfo_touxiang.setClickable(true);
                                    ll_myallinfo_touxiang.setEnabled(true);
                                    et_myallinfo_name.setEnabled(true);
                                    et_myallinfo_phone.setEnabled(true);
                                    et_myallinfo_email.setEnabled(true);
                                } else {
                                    Toast.makeText(MyAllInfo.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("取消", null)
                        .create()
                        .show();


            }
        });
        ll_myallinfo_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        bt_myallinfo_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChanged){
                    //删除上次的头像
                    String prePicName = sp.getString("picPath","");
                    File picFile = new File(prePicName);
                    if (picFile.exists()){
                        picFile.delete();
                    }
                    //保存头像
                    editor.putString("picPath", FileUtils.SDPICPATH+picName+ ".jpg");
                    editor.commit();
                }
                Toast.makeText(MyAllInfo.this,"保存成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(1,intent);
                MyAllInfo.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.5), (int) (bitmap.getHeight() * 0.5), true);
            riv_myallinfo_touxiang.setImageBitmap(scaledBitmap);
            picName = String.valueOf(System.currentTimeMillis());
            Intent intent = new Intent(MyAllInfo.this,PhotoActivity.class);
            intent.putExtra("picName", picName);
            startActivityForResult(intent, 1);
            FileUtils.saveBitmap(scaledBitmap, picName);
        }
        if (resultCode == 2){
            System.out.println("!!!!!!!!!!!!!!!!!!");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 80;//个人喜欢从80开始,
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > 100) {
                baos.reset();
                options -= 10;
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
            try {
                FileOutputStream fos = new FileOutputStream(FileUtils.SDPICPATH+picName+ ".jpg");
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //FileUtils.saveBitmap(scaledBitmap, picName);
        }
        isChanged = true;       //信息已经更新
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isChanging){
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAllInfo.this);
            builder.setMessage("正在编辑...是否退出编辑");
            builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyAllInfo.this.finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.show();
        }
        return super.onKeyDown(keyCode,event);
    }
}
