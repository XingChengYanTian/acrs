package com.acrs.ui;

import java.awt.BorderLayout;

import com.acrs.digest.Base64Digest;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;

public class RegUi extends BaseUI {

	private UiModel regUi;

	public RegUi() {
		// login 700 400
		// tab

		regUi = new UiModel(700, 400, "Administer  register....");
		
		this.jframe = regUi;
		
		final String s = System.getProperty("user.dir");
		regUi.add(browser.getView().getComponent(), BorderLayout.CENTER);

		// 注册一个Javascript 函数，让 js调用
		browser.registerFunction("entryUserInfo", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... args) {

				String userName = args[0].getString();
				String userPassword = args[1].getString();
				String jsonStr = entryUserInfo(userName, userPassword);
				// 返回json字符串
				return JSValue.create(Base64Digest.encode(jsonStr));
			}
		});

		browser.registerFunction("registerSuccess", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				regUi.dispose();
				new LoginUI();
				return JSValue.createNull();
			}
		});

		browser.loadURL(s + "/resource/register.html");
		regUi.setVisible(true);
	}
}
