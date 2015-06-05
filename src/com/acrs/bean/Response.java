package com.acrs.bean;

import com.google.gson.Gson;

/**
 * 返回数据
 * @author Administrator
 *
 * @param <T>
 */
public class Response<T> {
	private int state;
	private String msg;
	private T   data;
	
	public Response() {
		super();
	}
	public Response(int state) {
		this.state = state;
	}
	public Response(int state, T data) {
		super();
		this.state = state;
		this.data = data;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Response [state=" + state + ", msg=" + msg + ", data=" + data
				+ "]";
	}
	 
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
