package com.video.capture.framegetter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;


public class VideoUtil{
    private FinishListener finishListener;
    private Activity context;
    public VideoUtil(Activity context){
        this.context = context;
    }
    public void setOnFinishListener(FinishListener listener){
        finishListener = listener;
    }

    public void getFrameBySec(final int sec, final String path, final String name){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    //根据url获取缩略图
                    retriever.setDataSource(path, new HashMap());
                    //获得第一帧图片
                    Bitmap bitmap = retriever.getFrameAtTime(sec * 1000000);
                    //写入存储
                    String storagePath = Environment.getExternalStorageDirectory() + File.separator + "FrameCapture" + File.separator;
                    if(!new File(storagePath).exists())
                        new File(storagePath).mkdirs();
                    File saveImg = new File(storagePath + name +"_"+ sec +".png");
                    FileOutputStream fos = new FileOutputStream(saveImg);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.flush();
                    fos.close();
                    retriever.release();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishListener.finishDownload(name, sec, true);
                        }
                    });
                } catch (Exception e) {
                    retriever.release();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finishListener.finishDownload(name, sec, false);
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
