package com.fine.cutmusic.main;

import com.fine.cutmusic.entity.Param;
import com.fine.cutmusic.util.MusicUtil;

public class ConvertMain {
    public static void main(String[] args) {
     /*   String input = "D:\\CloudMusic\\DEAR JOHN.ncm";
        String out = "D:\\test\\test\\3.mp3";
        String start = "00:01:15";
        String end = "00:04:45";*/
        Param param=new Param("D:\\CloudMusic\\DEAR JOHN.ncm","D:\\test\\test\\3.mp3","00:01:15","00:04:45");
        MusicUtil.convert(param);

    }

}
