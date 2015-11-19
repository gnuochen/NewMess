package com.r_time_run.newmess.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.r_time_run.newmess.utils.NetworkUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.r_time_run.newmess.R;

/*
   * 异步加载图片 内存、磁盘双层缓存
   * xyl
   * 2013-12-15 21:20:50
   */
public class LoadImage {
        // SoftReference是软引用，是为了更好的为了系统回收变量，内存缓存图片
        private static HashMap<String, WeakReference<Bitmap>> imageCache = new HashMap<String, WeakReference<Bitmap>>();

        private Bitmap loadingImage, errorImage;
        private Context context;
        AnimationDrawable anim;

        public LoadImage(Context context) {
            super();
            this.context = context;

            errorImage = BitmapFactory.decodeResource(context.getResources(),
            R.drawable.no_image);
        }

        public void loadDrawable(final String imageUrl, final ImageView imageView) {

            if (!NetworkUtil.isNetwork(context)) {
                imageView.setImageBitmap(errorImage);
                Toast.makeText(context, "网络连接失败。。", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageCache.containsKey(imageUrl)) {
                // 从缓存中获取
                WeakReference<Bitmap> weakReference = imageCache.get(imageUrl);
                Bitmap drawable = weakReference.get();
                if (drawable != null) {
                    imageView.setImageBitmap(drawable);
                    return;
                }
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundResource(R.drawable.animloading);
            anim = (AnimationDrawable) imageView.getBackground();
            // anim.start();
            final Handler handler = new Handler() {
                public void handleMessage(Message message) {

                    switch (message.what) {
                        case 1:
                            anim.start();
                            break;
                        case 2:
                            // anim.setVisible(false, false);
                            imageView.setImageBitmap(((Bitmap) message.obj));
                            if (anim.isVisible()) {
                                anim.stop();
                                imageView.setBackgroundDrawable(null);
                            }
                            break;
                    }
                }
            };
            // 建立新一个新的线程下载图片
            new Thread() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                    Bitmap drawable = loadImageFromUrl(imageUrl);
                    if (drawable == null) {
                        drawable =errorImage;
                    }
                    imageCache.put(imageUrl, new WeakReference<Bitmap>(drawable));
                    Message message = handler.obtainMessage();
                    message.obj = drawable;
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }.start();

        }

        private Bitmap loadImageFromUrl(String url) {
            Bitmap d =null;
            try {
                InputStream is = null;
                try {
                    is = context
                            .openFileInput(url.substring(url.lastIndexOf("/") + 1));
                } catch (Exception e) {
                    System.out.println("没有缓存。。");
                }
                if (is != null) {
                    d = BitmapFactory.decodeStream(is);
                    return d;
                }
                URL m = new URL(url);
                HttpURLConnection huc = (HttpURLConnection) m.openConnection();
                is = huc.getInputStream();
                // 磁盘缓存图片
                FileOutputStream os = context.openFileOutput(
                        url.substring(url.lastIndexOf("/") + 1),
                        context.MODE_PRIVATE);
                byte[] b = new byte[100];
                int num = 0;
                while ((num = is.read(b)) > 0) {
                    os.write(b, 0, num);
                }
                d = BitmapFactory.decodeStream(context.openFileInput(url
                        .substring(url.lastIndexOf("/") + 1)));
                os.close();
                is.close();
            } catch (IOException e) {
                Log.d("xxx", e.toString() + "----连接服务器失败。。");

            }

            return d;
        }
    }
