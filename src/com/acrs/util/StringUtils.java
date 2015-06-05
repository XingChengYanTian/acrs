package com.acrs.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串工具类.
 * 
 */
public class StringUtils {
    /**
     * 判断字符串是否为空,空串返回false
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字，带.
     * 
     * @param input
     * @return
     */
    public static boolean isNumber(String input) {
        String regex = "^\\d+\\.*\\d*$";
        if (input != null && input.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将字符串转化为map，exp<str=“id=123;name=xiaoqiang”>
     * 
     * @param str
     * @param splitStr1 键值对分隔符exp< ";">
     * @param splitStr2 键和值分隔符exp<"=">
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getStr2Map(String str, String splitStr1, String splitStr2, Map resultMap) {
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        if (!isBlank(str)) {
            String[] a = str.split(splitStr1);//,或者其它分隔符
            if (a != null && a.length > 0) {
                for (String s : a) {
                    if (!isBlank(s)) {
                        String[] b = s.split(splitStr2);//=
                        if (b != null && b.length >= 1) {
                            resultMap.put(b[0], b.length == 2 ? b[1] : "");
                        }
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 将数组转化为字符串
     * @param array 数组
     * @param splitStr 分隔符
     * @return
     */
    public static String array2String(Object[] array, String splitStr) {
        String str = "";
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                str += array[i] + ((i == array.length - 1) ? "" : splitStr);
            }
        }
        return str;
    }

    /**
     * 判断字符是否为中文
     * 
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (isBlank(str)) {
            return false;
        }
        String reg = "[\u4e00-\u9fa5]+";
        for (int i = 0; i < str.length(); i++) {
            if (!str.substring(i, i + 1).matches(reg)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 取得字符串的真实长度，一个汉字长度为2
     * 
     * @param str
     * @return
     */
    public static int getRealLength(String str) {
        if (str == null) {
            return 0;
        }

        char separator = 256;
        int realLength = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= separator) {
                realLength += 2;
            } else {
                realLength++;
            }
        }
        return realLength;
    }

    /**
     * 截取固定长度的字符串，超长部分用suffix代替，最终字符串真实长度不会超过maxLength.
     * 
     * @param str
     * @param maxLength 字符串真实长度最大值
     * @param suffix
     * @return
     */
    public static String cutOut(String str, int maxLength, String suffix) {
        if (isBlank(str)) {
            return str;
        }

        int byteIndex = 0;
        int charIndex = 0;

        while (charIndex < str.length() && byteIndex <= maxLength) {
            char c = str.charAt(charIndex);
            if (c >= 256) {
                byteIndex += 2;
            } else {
                byteIndex++;
            }
            charIndex++;
        }

        if (byteIndex <= maxLength) {
            return str;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, charIndex));
        stringBuffer.append(suffix);

        while (StringUtils.getRealLength(stringBuffer.toString()) > maxLength) {
            stringBuffer.deleteCharAt(--charIndex);
        }
        return stringBuffer.toString();
    }

    /**
     * 格式化电话号码*显示<br>
     * 如:<br>
     * 电话号码格式化前：13598787832
     * 格式化后为：135****7832
     * @return
     */
    public static String phoneFormat(String phone) {
        if (!isBlank(phone)) {
            String startNo = phone.substring(0, 3);
            String endNo = phone.substring(7);
            phone = startNo + "****" + endNo;
        }
        return phone;
    }

    /**
     * 获取字符串的编码方式
     * @param str 字符串
     * @return 编码方式
     */
    public static String getEncoding(String str) {

        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";

    }

    /**
     * 获取指定编码方式字符串
     * @param str 被编码的字符串
     * @param encoding 编码方式
     * @return 编码后的字符串
     * @throws Exception
     */
    public static String getEncodString(String str, String encoding) throws Exception {
        String oldEnding = getEncoding(str);
        String strs = new String(str.getBytes(oldEnding), encoding);
        return strs;
    }

    /**
     * 清除字符串左侧的空格
     * 
     * @param str
     * @return
     */
    public static String ltrim(String str) {
        return StringUtils.ltrim(str, " ");
    }

    /**
     * 清除字符串左侧的指定字符串
     * @param str
     * @param remove
     * @return
     */
    public static String ltrim(String str, String remove) {
        if (str == null || str.length() == 0 || remove == null || remove.length() == 0) {
            return str;
        }

        while (str.startsWith(remove)) {
            str = str.substring(remove.length());
        }
        return str;
    }

    /**
     * 清除字符串右侧的空格
     * 
     * @param str
     * @return
     */
    public static String rtrim(String str) {
        return StringUtils.rtrim(str, " ");
    }

    /**
     * 清除字符串右侧的指定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String rtrim(String str, String remove) {
        if (str == null || str.length() == 0 || remove == null || remove.length() == 0) {
            return str;
        }

        while (str.endsWith(remove) && (str.length() - remove.length()) >= 0) {
            str = str.substring(0, str.length() - remove.length());
        }
        return str;
    }
}
