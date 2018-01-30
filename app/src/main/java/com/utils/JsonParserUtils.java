package com.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Json转化工具
 * 
 */
public class JsonParserUtils {

	private static Gson gson = new Gson();

	public static <T> T deserializeFromJson(String json, Class<T> clz) {
		if (TextUtils.isValidate(json)) {
			return gson.fromJson(json, clz);
		}
		return null;
	}

	public static <T> T deserializeFromJson(String json, Type type) {
		if (TextUtils.isValidate(json)) {

			try {
				return gson.fromJson(json, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static String serializeToJson(Object obj) {
		if (obj == null) {
			return null;
		}
		return gson.toJson(obj);
	}
 

}
