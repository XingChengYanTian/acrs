package com.acrs.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.acrs.bean.Account;
import com.acrs.ui.CacheUser;
import com.google.gson.reflect.TypeToken;

public class AccountServiceImpl extends BasicService implements AccountService{
	private final String FILE_NAME = "account.data";
	@Override
	public void entry(Account account) throws Exception {
		List<Account> list = this.search(account.getUserId());
		if(list.size()==0 && list.isEmpty()){
			account.setId(1);
		}else{
			account.setId(list.size()+1);
		}
		list.add(account);
		this.writeJsonToFile(FILE_NAME, list);
	}

	@Override
	public void modify(Account account) throws Exception {
		List<Account> list = this.search(account.getUserId());
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId()==account.getId()){
				list.get(i).setSiteName(account.getSiteName());
				list.get(i).setSiteUrl(account.getSiteUrl());
				list.get(i).setEncryptName(account.getEncryptName());
				list.get(i).setEncryptPwd(account.getEncryptPwd());
				list.get(i).setAccountName(account.getAccountName());
				list.get(i).setAccountMemo(account.getAccountMemo());
				list.get(i).setGmtModified(new Date());
				break;
			}
		}
		this.writeJsonToFile(FILE_NAME, list);
	}

	@Override
	public List<Account> search(int userId) throws Exception {
		List<Account> list = this.getAcountList();
		List<Account> list2  = new ArrayList<Account>();
		if(null == list) return list2;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getUserId()==userId){
				list2.add(list.get(i));
			}
		}
		return list2; 
	}
	@Override
	public List<Account> search() throws Exception {
		return null;
	}
	@Override
	public void delete(int id) throws Exception {
		List<Account> list = this.search(CacheUser.user.getId());
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getId()==id){
				 list.remove(i);
				 break;
			}
		}
		this.writeJsonToFile(FILE_NAME, list);
	}
	
	public List<Account> getAcountList(){
		try{
			File file = getFile(FILE_NAME);
			String json = this.readJson(file);
			if(json != null && !"".equals(json.trim())){
				@SuppressWarnings("unchecked")
				List<Account> list = this.json2Object(json,new TypeToken<List<Account>>(){}.getType());
				return list;
			}
		}catch(Exception e){
		}
		return null;
	}
}
