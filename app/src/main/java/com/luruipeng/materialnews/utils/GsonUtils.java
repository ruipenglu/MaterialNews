package com.luruipeng.materialnews.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by Ruipeng Lu on 2016/2/22 0022.
 */
public class GsonUtils {

    private static Gson mGson = new Gson();

    /**
     * 将对象准换为json字符串
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clz) {
        try {
            return mGson.fromJson(json, clz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize1(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }
}
