package com.demo.util;

import com.google.gson.JsonParser;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create : zzy
 * date : 2019/03/15
 */
public class CommonUtils {

    private static final String MOBILE_REG = "(1)(\\d{10})";

    /**
     * 检测字符串是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return false;
        }

        Pattern pattern = Pattern.compile(MOBILE_REG);
        Matcher m = pattern.matcher(mobile);

        if (m.find()) {
            return true;
        }

        return false;
    }

    /**
     * 生成一个n位数的随机数字验证码
     *
     * @param n
     * @return
     */
    public static String genVerificationCode(int n) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, n)));
    }


    /**
     * 生成密码，jingshang 统一使用此加密方式
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String genMd5Password(String pwd, String salt) {
        return MD5Tools.MD5(MD5Tools.MD5(pwd) + salt);
    }


    /**
     * 生成加密盐
     *
     * @return
     */
    public static String genSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase().substring(0, 20);
    }


    public static boolean isGoodJson(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static BigDecimal nullToDefault(BigDecimal num) {
        return num == null ? new BigDecimal(0) : num;
    }

    public static Integer nullToDefault(Integer num) {
        return num == null ? 0 : num;
    }

    public static String getServerHost() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName = addr.getHostName().toString(); //获取本机计算机名称
            return hostName;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getServerIP() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress(); //获取本机计算机名称
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
