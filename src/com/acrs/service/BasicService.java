package com.acrs.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.acrs.util.CreateFileUtil;
import com.google.gson.Gson;


public abstract class BasicService {
	 protected Gson gson = new Gson();
	 public String readJson( File file ){
		 BufferedReader br = null;
		 try {
			br = new BufferedReader(new FileReader(file));
			StringBuffer sbf = new StringBuffer();
			String str = null;
			while((str = br.readLine()) != null){
				sbf.append(str);
			}
			str = new String(sbf.toString().getBytes(), System.getProperty("file.encoding"));
			return str;
		} catch (Exception e) {
			return null;
		}finally{
				try {
					if(br != null){
						br.close();
					}
				} catch (IOException e) {
				}
		}
	 }
	 
	 
	 public void writeJsonToFile(String fileName,Object obj){
		 FileWriter f = null;
		 String str = null;
		 try {
			str =  gson.toJson(obj);
			f= new FileWriter(this.getFile(fileName));
			f.write(str);
			f.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				f.close();
			} catch (Exception e) {
			}
		}
	 }
	 
	 public File getFile(String fileName) throws Exception{
			String sysPath = System.getProperty("user.dir");
			String filePath = sysPath+"/data/"+fileName;
			File file = new File(filePath);
			if(!file.exists()){
					CreateFileUtil.createFile(filePath);
			}
			return file;
		}
	 @SuppressWarnings("rawtypes")
	public List json2Object(String json,Type type) {
			List list = null;
			try {
				list  =  gson.fromJson(json, type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
}
