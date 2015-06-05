package com.acrs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * json 字符串转换成Map对象,必须配合gson 包
 * @author leeyns@163.com
 * @data: 2014-9-18
 */
public class JsonMap {
	 /**
     * json数组转换成map，封装在list
     * @param jsonArrayStr json数组：<br>String jsonStr = "[{'name':'zhang','age':38},{'name':'test','age':23,'address':'成都'}]";
     * @return listmap
     */
    public static List<Map<String, String>> jsonArrayToListInnerMap(String jsonArrayStr)
                                                                                        throws Exception {
        JsonParser jsonParser = new JsonParser();
        JsonArray ja = jsonParser.parse(jsonArrayStr).getAsJsonArray();
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < ja.size(); i++) {
            JsonElement je = ja.get(i);
            JsonObject jo = je.getAsJsonObject();
            Map<String, String> map = new HashMap<String, String>();
            for (Entry<String, JsonElement> em : jo.entrySet()) {
                map.put(em.getKey(),new String(em.getValue().getAsString().getBytes()));
            }
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * json to map
     * @param jsonStr json对象字符串{'name':'zhang','age':38}
     * @return
     */
    public static Map<String, String> jsonToMap(String jsonStr) throws Exception {
        JsonParser jsonParser = new JsonParser();
        JsonElement je = jsonParser.parse(jsonStr);
        JsonObject jo = je.getAsJsonObject();
        Map<String, String> map = new HashMap<String, String>();
        for (Entry<String, JsonElement> em : jo.entrySet()) {
            map.put(em.getKey(),new String( em.getValue().getAsString().getBytes()));
        }
        return map;
    }
    
    public static String toJson(Object obj){
    	Gson gson = new Gson();
    	return gson.toJson(obj);
    }
}
