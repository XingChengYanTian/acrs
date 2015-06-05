package com.acrs.service;

import com.acrs.bean.User;

public interface UserService {
	public User login(String userName,String password)throws Exception;
	public void register(User user)throws Exception;
}
