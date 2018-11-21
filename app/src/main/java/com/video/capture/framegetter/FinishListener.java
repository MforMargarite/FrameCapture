package com.video.capture.framegetter;

/**
 * Created by Administrator on 2018/11/21.
 */
public interface FinishListener {
    void finishDownload(String name, int sec, boolean status);
}
