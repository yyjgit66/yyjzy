package com.yyjzy.utils;

/**
 * @author yyj
 */
public class YyjUtil {

    public static String getCapital(String str){
        // 转为char数组
        char[] ch = str.toCharArray();
        // 得到大写字母
        for(int i = 0; i < ch.length ; i++){
            if(ch[i] >= 'A' && ch[i] <= 'Z'){
                return String.valueOf(ch[i]);
            }
        }
        return null;
    }

}
