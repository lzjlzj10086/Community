package com.community.util;

public class CodeUtil {
    /**
     * 随机生成usercode
     * @return
     */
    public static String CodeRandom(){
        long longTime = System.currentTimeMillis();
        String code = "U"+Long.toString(longTime);
        return code;
    }
    public static String ImageCodeRandom(){
        long longTime = System.currentTimeMillis();
        String code = "Image"+Long.toString(longTime);
        return code;
    }
    public static String QuestionCodeRandom(){
        long longTime = System.currentTimeMillis();
        String code = "qt"+Long.toString(longTime);
        return code;
    }
    public static String CommentCodeRandom(){
        long longTime = System.currentTimeMillis();
        String code = "cm"+Long.toString(longTime);
        return code;
    }
    public static String NotifyCodeRandom(){
        long longTime = System.currentTimeMillis();
        String code = "nt"+Long.toString(longTime);
        return code;
    }
}
