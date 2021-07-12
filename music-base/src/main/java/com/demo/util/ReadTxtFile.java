package com.demo.util;

import com.demo.common.BizException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 读取文件
 */
public class ReadTxtFile {

    /**
     * 读取文件内容方法
     * @param filePath
     */
    public static String readTxt(String filePath) {
        String result = "";
        try {
            File file = new File(filePath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt;
                while ((lineTxt = br.readLine()) != null) {
                    result += lineTxt + "\n";
                }
                br.close();
            } else {
                BizException.fail(filePath + "文件不存在");
            }
        } catch (Exception e) {
//            BizException.fail(filePath + "文件读取异常");
            return "歌词读取错误";
        }
        return result;

    }


    public static void main(String[] args) {
        String filePath = "/Users/zl/Downloads/music_Dowload/黎明前的黑暗.txt";
        readTxt(filePath);
    }

}
