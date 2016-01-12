package com.alibaba.dingtalk.openapi.demo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FileUtils {
	public static final String FILEPATH = "Permanent_Data";

	public static JSON generateJSON(String key, String value) {
		JSONObject json = new JSONObject();
		json.put(key, value);
		return json;
	}

	public static JSON generateJSON(Map<String, String> values) {
		JSONObject json = new JSONObject();
		Iterator<Entry<String, String>> iter = values.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			json.put(entry.getKey().toString(), entry.getValue().toString());
		}
		return json;
	}

	// json写入文件
	public static void write2File(Object json, String fileName) {
		BufferedWriter writer = null;
		File filePath = new File(FILEPATH);
		JSONObject eJSON = null;
		if (!filePath.exists() && !filePath.isDirectory()) {
			filePath.mkdirs();
		}

		File file = new File(FILEPATH + File.separator + fileName + ".xml");
		System.out.println("path:" + file.getPath() + " abs path:" + file.getAbsolutePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.out.println("createNewFile，出现异常:");
				e.printStackTrace();
			}
		} else {
			eJSON = (JSONObject) read2JSON(fileName);
		}

		try {
			writer = new BufferedWriter(new FileWriter(file));

			if (eJSON.equals(null)) {
				writer.write(json.toString());
			} else {
				Object[] array = ((JSONObject) json).keySet().toArray();
				if (eJSON.containsKey(array[0].toString())) {
					@SuppressWarnings("unchecked")
					Map<String, String> values = JSON.parseObject(eJSON.toString(), Map.class);
					values.put(array[0].toString(), ((JSONObject) json).get(array[0].toString()).toString());
				} else {
					eJSON.put(array[0].toString(), ((JSONObject) json).get(array[0].toString()));
				}
				writer.write(eJSON.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static JSONObject read2JSON(String fileName) {
		File file = new File(FILEPATH + File.separator + fileName + ".xml");
		if (!file.exists()) {
			return null;
		}

		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSON.parse(laststr);

		return (JSONObject) JSON.parse(laststr);
	}

	// 通过key值获取文件中的value
	public static void getKey(String key) {

	}

}
