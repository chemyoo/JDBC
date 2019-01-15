package com.chemyoo.utils;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年6月25日 上午8:42:39 
 * @since 2018年6月25日 上午8:42:39 
 * @description 类说明 
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	
	public JsonUtils() {
		
	}
	
	public JsonUtils(String encoding) {
		charSet = encoding;
	}
	
	/**
	 * default encoding: utf-8
	 */
	private String charSet = "utf-8";
	
	public JSONObject readJsonObject(File file) {
		if(file.exists() && file.isFile()) {
			try {
				String jsonStr = FileUtils.readFileToString(file, charSet);
				return JSONObject.fromObject(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return new JSONObject();
		
	}
	
	public JSONObject readJsonObject(String path) {
		return readJsonObject(new File(path));
	}
	
	public JSONArray readJsonArray(String path) {
		return readJsonArray(new File(path));
	}
	
	public JSONArray readJsonArray(File file) {
		if(file.exists() && file.isFile()) {
			try {
				String jsonStr = FileUtils.readFileToString(file, charSet);
				return JSONArray.fromObject(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return new JSONArray();
	}
	
	public void setCharset(String charSet) {
		this.charSet = charSet;
	}
	
	public void writeJson(JSONObject json, File dest) {
		try (OutputStream out = new FileOutputStream(dest);) {
			out.write(json.toString().getBytes(charSet));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeJson(JSONObject json, String dest) {
		writeJson(json, new File(dest));
	}
	
	public void writeJson(JSONArray jsonArray, File dest) {
		try (OutputStream out = new FileOutputStream(dest);) {
			out.write(jsonArray.toString().getBytes(charSet));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeJson(JSONArray jsonArray, String dest) {
		writeJson(jsonArray, new File(dest));
	}
	
}
