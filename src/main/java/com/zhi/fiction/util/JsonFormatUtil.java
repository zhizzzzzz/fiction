package com.zhi.fiction.util;

import com.alibaba.fastjson.JSONObject;


public class JsonFormatUtil {
    /**
     * author: yongzhi.zhao 2017年11月21日
     * json格式化
     * @param response
     * @return
     */
    public static StringBuffer formatJson(JSONObject response) {  
        String start = "  ";  //调整缩进
        String jsonStr = response.toString();// 先将json对象转化为string对象  
        int level = 0;// 用户标记层级  
        StringBuffer jsonResultStr = new StringBuffer();// 新建stringbuffer对象，用户接收转化好的string字符串  
        jsonResultStr.append("\n"); //顶部换行
        for (int i = 0; i < jsonStr.length(); i++) {// 循环遍历每一个字符  
            char piece = jsonStr.charAt(i);// 获取当前字符  
            // 如果上一个字符是断行，则在本行开始按照level数值添加标记符，排除第一行  
            if (i != 0 && '\n' == jsonResultStr.charAt(jsonResultStr.length() - 1)) {  
                for (int k = 0; k < level; k++) {  
                    jsonResultStr.append(start);  
                }  
            }  
            switch (piece) {  
            case '{':  
            case '[':  
                // 如果字符是{或者[，则断行，level加1  
                jsonResultStr.append(piece + "\n");  
                level++;  
                break;  
            case ',':  
                // 如果是“,”，则断行  
                jsonResultStr.append(piece + "\n");  
                break;  
            case '}':  
            case ']':  
                // 如果是}或者]，则断行，level减1  
                jsonResultStr.append("\n");  
                level--;  
                for (int k = 0; k < level; k++) {  
                    jsonResultStr.append(start);  
                }  
                jsonResultStr.append(piece);  
                break;  
            default:  
                jsonResultStr.append(piece);  
                break;  
            }  
        }  
       return jsonResultStr;
    } 
}
