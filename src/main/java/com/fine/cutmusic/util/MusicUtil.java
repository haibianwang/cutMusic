package com.fine.cutmusic.util;

import com.fine.cutmusic.entity.Param;
import com.fine.cutmusic.exception.MusicException;
import com.fine.cutmusic.service.DumpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicUtil {

    private static final String ffmpegEXE = "D:\\ffmpeg\\bin\\ffmpeg";
    private static final List<String> VIDEO_LIST = new ArrayList<>(Arrays.asList("mov", "mpg", "wmv", "3gp", "asf", "asx", "avi", "wmv9", "rm", "rmvb", "flv"));
    private static final List<String> AUDIO_LIST = new ArrayList<>(Arrays.asList("mp3", "acm", "wav", "wma", "mp1", "aif"));
    private static final String NET_EASY_TYPE = "ncm";
    private static final Logger logger = LoggerFactory.getLogger(MusicUtil.class);

    /**
     * 执行对应命令进行截取
     * @param montageInputPath
     * @param montageOutputPath
     * @param montageStart
     * @param montageEnd
     * @return
     */
    public static boolean montageAudioOrVedio(String montageInputPath, String montageOutputPath, String montageStart, String montageEnd) {
        boolean isSuccess = false;
        try {
            String command = getCommandList(montageInputPath, montageOutputPath, montageStart, montageEnd);
            if (command == null) {
                return isSuccess;
            }
            logger.info("生成命令:" + command);
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec(command, null);
            InputStream inputStream = process.getInputStream();
            InputStream errorInputStream = process.getErrorStream();
            new Thread() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }.start();
            new Thread() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorInputStream));
                    try {
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            errorInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }.start();
            process.waitFor();
            process.destroy();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }
   /**
    * 格局参数得到对应命令行
    * */
    public static String getCommandList(String montageInputPath, String montageOutputPath, String montageStart, String montageEnd) {
        String suffix = montageInputPath.substring(montageInputPath.lastIndexOf(".") + 1);
        StringBuffer command = new StringBuffer();
        String netEasy;
        command.append("cmd /c").append(" ");
        command.append(ffmpegEXE).append(" ");
        if (!StringUtils.isEmpty(montageStart)) {
            command.append("-ss").append(" ");
            command.append(montageStart).append(" ");
        }
        if (!StringUtils.isEmpty(montageEnd)) {
            command.append("-to").append(" ");
            command.append(montageEnd).append(" ");
        }
        if (VIDEO_LIST.contains(suffix)) {
            //视频格式
            command.append("-i").append(" ");
            command.append("\"" + montageInputPath + "\"").append(" ");
            command.append("-c:v").append(" ");
            command.append("libx264").append(" ");
            command.append("-c:a").append(" ");
            command.append("aac").append(" ");
            command.append("-strict").append(" ");
            command.append("experimental").append(" ");
            command.append("-b:a").append(" ");
            command.append("98k").append(" ");
            command.append("\"" + montageOutputPath + "\"").append(" ");
            command.append("-y");
        } else if (AUDIO_LIST.contains(suffix)) {
            //音频格式
            command.append("-i").append(" ");
            command.append("\"" + montageInputPath + "\"").append(" ");
            command.append("\"" + montageOutputPath + "\"").append(" ");
            command.append("-y").append(" ");
        } else if (NET_EASY_TYPE.equals(suffix)) {
            netEasy = convertFromNcmToOther(montageInputPath);
            montageAudioOrVedio(netEasy, montageOutputPath, montageStart, montageEnd);
        } else {
            return null;
        }
        return command.toString();
    }
    /**
     * 将ncm格式转成mp3或者flc音乐格式
     * */
    private static String convertFromNcmToOther(String ncmPath) {
        String netEasy;
        try {
            File ncm_f = new File(ncmPath);
            DumpService dump = new DumpService(ncm_f);
            netEasy = dump.execute();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return netEasy;
    }

    /**
     * 递归创建文件夹，并且该地址如果有文件则删除
     */
    public static boolean createDirs(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        return file.getParentFile().mkdirs();

    }
    /**
    根据参数截取视频或音乐片段
    */
    public static void convert(Param param) {
        try {
            isExist(param.getInput());
            createDirs(param.getOut());
            montageAudioOrVedio(param.getInput(), param.getOut(), param.getStart(), param.getEnd());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断源地址是否存在
     * @param input
     */
    public static void isExist(String input){
        File file=new File(input);
        if (!file.exists()) {
            logger.error("该文件不存在");
            throw new MusicException("该文件不存在");
        }
    }
}
