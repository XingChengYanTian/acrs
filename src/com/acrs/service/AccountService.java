package com.acrs.service;

import java.util.List;

import com.acrs.bean.Account;

public interface AccountService {
	public void entry(Account account)throws Exception;
	public void modify(Account account)throws Exception;
	public List<Account> search(int userId)throws Exception;
	public List<Account> search()throws Exception;
	public void delete(int id)throws Exception;
}
