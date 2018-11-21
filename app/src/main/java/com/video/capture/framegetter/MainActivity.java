package com.video.capture.framegetter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements FinishListener, View.OnClickListener{
    TextView tv;
    TextView beginBtn;
    StringBuilder info;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.info);
        beginBtn = (TextView) findViewById(R.id.begin);
        beginBtn.setOnClickListener(this);
    }

    @Override
    public void finishDownload(String name, int sec, boolean status) {
        info.append(name+"获取第"+ sec +"帧" + (status?"成功":"失败")+"\n");
        tv.setText(info.toString());
        count += 1;
        if(count == Common.getTotal()){
            info.append("完成\n");
            tv.setText(info.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.begin:
                Toast.makeText(MainActivity.this, "开始获取...", Toast.LENGTH_SHORT).show();
                info = new StringBuilder();
                VideoUtil vu = new VideoUtil(this);
                vu.setOnFinishListener(this);
                for(int index=0;index<Common.videoUrl.length;index++) {
                    for (int sec:Common.sec)
                        vu.getFrameBySec(sec, Common.videoUrl[index], "video_"+index);
                }
                break;
            default:
                break;
        }
    }
}
