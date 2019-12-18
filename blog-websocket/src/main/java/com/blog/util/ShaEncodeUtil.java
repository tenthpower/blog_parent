package com.blog.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * 字符串工具类型
 */
@Component
public class ShaEncodeUtil {

    private static String STR_CODE = "yenroc.Ho";

    public static String shaEncode(String inStr) throws Exception {
        inStr = inStr + STR_CODE;
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 通过两个字符串生产一个字符串
     * @param aStr,bStr
     * @return
     * @throws Exception
     */
    public static String shaEncode(String aStr, String bStr) throws Exception {
        return shaEncode(strAppend(aStr, bStr));
    }


    /**
     * sidA + sidB 返回唯一 字符串
     */
    public static String strAppend(String sidA,String sidB) {
        if (sidA.compareTo(sidB) < 0){
            return sidA + sidB;
        } else {
            return sidB + sidA;
        }
    }
}
