package com.acrs.ui;

import java.awt.BorderLayout;

import com.acrs.digest.Base64Digest;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

public class LoginUI extends BaseUI {
	private UiModel loginUi;
	public LoginUI() {                                           
		// login 700 400
		// tab
		loginUi = new UiModel(700, 400, "Administer sign to...");
		this.jframe = loginUi;
		final String s = System.getProperty("user.dir");
		loginUi.add(browser.getView().getComponent(), BorderLayout.CENTER);
		
		browser.addLoadListener(new LoadAdapter() {

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				   if (event.isMainFrame()) {
					   System.err.println("loading login frame success!");
				   }
			}
		});

		// 注册一个Javascript 函数，让 js调用
		browser.registerFunction("login", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... args) {

				String userName = args[0].getString();
				String userPassword = args[1].getString();

				String jsonStr = login(userName, userPassword);
				// 返回json字符串
				return JSValue.create(Base64Digest.encode(jsonStr));
			}
		});

		browser.registerFunction("gotoRegisterPage", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				loginUi.dispose();
				new RegUi();
				return JSValue.createNull();
			}
		});
		browser.registerFunction("loginSuccess", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				
				loginUi.dispose();
				new TabUi();
				return JSValue.createNull();
			}
		});
		
		browser.loadURL(s+"/resource/login.html");
		loginUi.setVisible(true);
	}
 
 
	public static void main(String[] args) {
		new LoginUI();
	}
}
