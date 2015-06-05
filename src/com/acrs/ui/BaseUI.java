package com.acrs.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.acrs.bean.Account;
import com.acrs.bean.Response;
import com.acrs.bean.ResponseState;
import com.acrs.bean.User;
import com.acrs.digest.Base64Digest;
import com.acrs.digest.MD5Digest;
import com.acrs.service.AccountService;
import com.acrs.service.AccountServiceImpl;
import com.acrs.service.UserService;
import com.acrs.service.UserServiceImpl;
import com.acrs.util.JsonMap;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.demo.BrowserFactory;

public class BaseUI{
	protected UserService userService = new UserServiceImpl();
	protected AccountService accountService = new AccountServiceImpl();
	protected JFrame jframe;
	protected Browser browser = BrowserFactory.create();
	public BaseUI() {
		super();
		// 退出系统
		browser.registerFunction("closeFrame", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				System.exit(0);
				return JSValue.createNull();
			}
		});
		// 窗口最小化
		browser.registerFunction("minFrame", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				jframe.setExtendedState(JFrame.ICONIFIED);
				return JSValue.createNull();
			}
		});
		browser.registerFunction("moveFrame", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				int moveX = (int) arg0[0].getNumber();
				int moveY = (int) arg0[1].getNumber();

//				int windowWidth = jframe.getWidth(); // 获得窗口宽
//				int windowHeight = jframe.getHeight(); // 获得窗口高
				int locationX = jframe.getX();
				int locationY = jframe.getY();
				
				int x = locationX+moveX;
				int y = locationY+moveY;
				
				jframe.setLocation(x, y);
				return null;
			}
		});
		
		browser.registerFunction("openDefaultBrowser", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				String href = arg0[0].getString();
				System.out.println(href);
				 try {
					URI uri = new URI(href);   
					Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
				} 
				return null;
			}
		});
		
		browser.registerFunction("outConsole", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				String msg = arg0[0].getString();
				System.out.println(msg);
				return null;
			}
		});
	}

	public void loadURL(String url) {
		this.browser.loadURL(url);
	}

	/**
	 * 注册User
	 * 
	 * @param jsonStr
	 * @throws Exception
	 */
	public String entryUserInfo(String userName, String encryptPassword) {
		Response<String> userResp = new Response<String>(ResponseState.FAILED);
		try {
			User user = new User();
			String userPassword = encryptPassword;

			user.setEncryptPassword(userPassword);
			user.setUserName(userName);
			userService.register(user);

			userResp.setState(ResponseState.SUCCESS);
			userResp.setMsg("注册成功");

		} catch (Exception e) {
			e.printStackTrace();
			userResp.setMsg("注册失败！");
		}
		return userResp.toJson();
	}

	/**
	 * 用户登录
	 * 
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public String login(String userName, String userPassword) {
		Response<User> resp = new Response<User>(ResponseState.FAILED);
		try {
			// MD5 加密 密码
			userPassword = MD5Digest.md5Crypt(userPassword);
			User user = userService.login(userName, userPassword);
			if (user != null) {
				resp.setState(ResponseState.SUCCESS);
				resp.setMsg("登录成功！");
				resp.setData(user);
				CacheUser.user = user;
			} else {
				resp.setMsg("用户名或密码错误！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setMsg("用户名或密码错误！");
		}
		return resp.toJson();
	}

	/**
	 * 添加一个账号
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String entryAccount(String jsonStr) {
		Map<String, String> map = null;
		Response<String> resp = new Response<String>(ResponseState.FAILED);
		try {

			jsonStr = Base64Digest.decode(jsonStr);

			map = JsonMap.jsonToMap(jsonStr);
			String siteName = map.get("siteName");
			String siteUrl = map.get("siteUrl");
			String encryptName = map.get("encryptName");
			String encryptPwd = map.get("encryptPwd");
			String accountMemo = map.get("accountMemo");

			Account account = new Account(siteName, siteUrl, encryptName,
					encryptPwd, accountMemo);
			account.setGmtCreate(new Date());
			account.setGmtModified(new Date());
			account.setUserId(CacheUser.user.getId());

			accountService.entry(account);
			resp.setState(ResponseState.SUCCESS);

		} catch (Exception e) {
			resp.setMsg("entry account is failed");
		}
		return resp.toJson();
	}

	/**
	 * 修改用户的账号
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String modifyAccount(String jsonStr) {
		Map<String, String> map = null;
		Response<String> resp = new Response<String>(ResponseState.FAILED);
		try {
			map = JsonMap.jsonToMap(Base64Digest.decode(jsonStr));

			String siteName = map.get("siteName");
			int id = Integer.parseInt(map.get("id"));
			String siteUrl = map.get("siteUrl");
			String encryptName = map.get("encryptName");
			String encryptPwd = map.get("encryptPwd");
			String accountMemo = map.get("accountMemo");
			Account account = new Account(siteName, siteUrl, encryptName,
					encryptPwd, accountMemo);

			account.setId(id);
			account.setGmtCreate(new Date());
			account.setGmtModified(new Date());
			account.setUserId(CacheUser.user.getId());
			accountService.modify(account);
			resp.setState(ResponseState.SUCCESS);
			resp.setMsg("modify account is successed");
		} catch (Exception e) {
			resp.setMsg("modify account is failed");
		}
		return resp.toJson();
	}

	/**
	 * 查询用户的所有账号
	 * 
	 * @param userId
	 * @return
	 */
	public String searchAccount(int userId) {
		Response<List<Account>> resp = new Response<List<Account>>(
				ResponseState.FAILED);
		try {
			List<Account> accountList = accountService.search(userId);
			resp.setState(ResponseState.SUCCESS);
			resp.setMsg("load user account successed ");
			resp.setData(accountList);
		} catch (Exception e) {
			resp.setMsg("load user account failed ");
		}
		return resp.toJson();
	}

	/**
	 * 删除一个账号
	 * 
	 * @param id
	 * @return
	 */
	public String deleteAccount(int id) {
		Response<String> resp = new Response<String>(ResponseState.FAILED);
		if (id <= 0) {
			resp.setMsg("delete user account failed");
		} else {
			try {
				accountService.delete(id);
				resp.setMsg("delete user account successed");
				resp.setState(ResponseState.SUCCESS);
			} catch (Exception e) {
				resp.setMsg("delete user account failed");
			}
		}

		return resp.toJson();
	}
}
