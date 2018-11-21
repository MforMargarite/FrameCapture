package com.video.capture.framegetter;


public class Common {
    public static String[] videoUrl = {"https://media.w3.org/2010/05/sintel/trailer.mp4", "http://www.w3school.com.cn/example/html5/mov_bbb.mp4"};
    public static int[] sec = {3,5};
    public static int getTotal(){
        return videoUrl.length *sec.length;
    }
}
