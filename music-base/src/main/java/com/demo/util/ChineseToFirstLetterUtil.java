package com.demo.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * 提取汉字首字母工具类
 */
public class ChineseToFirstLetterUtil {

    public static String ChineseToFirstLetter(String c) {
        String string = "";
        char b;
        int a = c.length();
        for (int k = 0; k < a; k++) {
            b = c.charAt(k);
            String d = String.valueOf(b);
            String str = converterToFirstSpell(d);
            String s = str.toUpperCase();
            String g = s;
            char h;
            int j = g.length();
            for (int y = 0; y <= 0; y++) {
                h = g.charAt(0);
                string += h;
            }
        }
        return string;
    }

    //只要第一个汉字的首字母
    public static String ChineseToFirstLetterFirst(String str) {
//        String string = "";
//        char b;
//        int a = c.length();
//        for (int k = 0; k < a; k++) {
//            b = c.charAt(k);
//            String d = String.valueOf(b);
//            String str = converterToFirstSpell(d);
//            String s = str.toUpperCase();
//            String g = s;
//            char h;
//            int j = g.length();
//            for (int y = 0; y <= 0; y++) {
//                h = g.charAt(0);
//                string += h;
//            }
//        }
//        return string;
        String first = String.valueOf(str.charAt(0));
        str = converterToFirstSpell(first).toUpperCase();
        return str.charAt(0) + "";
    }

    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            String s = String.valueOf(nameChar[i]);
            if (s.matches("[\\u4e00-\\u9fa5]")) {
                try {
                    String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    pinyinName += mPinyinArray[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    public static void main(String[] args) {
//        System.err.println(ChineseToFirstLetter("犯我中华者虽远必诛"));
//        System.out.println(ChineseToFirstLetterFirst("ss"));
        String str = ChineseToFirstLetterFirst("犯我中华者虽远必诛112ss");
        System.out.println(str);
        String regex = "[A-Z]";
        boolean flag = Pattern.matches(regex, str);
        System.out.println(flag);

    }
}


