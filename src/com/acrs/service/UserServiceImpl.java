package com.acrs.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.acrs.bean.User;
import com.google.gson.reflect.TypeToken;

public class UserServiceImpl extends BasicService implements UserService{
	private final String FILE_NAME = "user.data";
	@Override
	public User login(String userName, String password) throws Exception {
		List<User> list  = this.getUserList();
		User user =null;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getUserName().equals(userName) && list.get(i).getEncryptPassword().equals(password)){
				user = list.get(i);
				break;
			}
		}
		return user;
	}

	@Override
	public void register(User user) throws Exception {
		List<User> userList = this.getUserList();
		if(userList == null || userList.size() <= 0){
			user.setId(1);
			userList = new ArrayList<User>();
		}else{
			user.setId(userList.size()+1);
		}
		userList.add(user);
		this.writeJsonToFile(FILE_NAME,userList);
	}
	
	public List<User> getUserList(){
		try{
			File file = getFile(FILE_NAME);
			String json = this.readJson(file);
			if(json != null && !"".equals(json.trim())){
				@SuppressWarnings("unchecked")
				List<User> list =  this.json2Object(json,new TypeToken<List<User>>(){}.getType());
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
