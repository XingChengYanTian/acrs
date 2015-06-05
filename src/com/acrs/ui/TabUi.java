package com.acrs.ui;

import java.awt.BorderLayout;

import com.acrs.digest.Base64Digest;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

public class TabUi extends BaseUI {
	private UiModel tabUi;

	public TabUi() {
		tabUi = new UiModel(990, 640, "Administer account info...");
		this.jframe = tabUi;
		final String s = System.getProperty("user.dir");
		tabUi.add(browser.getView().getComponent(), BorderLayout.CENTER);
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent arg0) {
				System.err.println("load tab frame success!");
			}
		});

		// 注册一个Javascript 函数，让 js调用
		browser.registerFunction("entryAccount", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... args) {
				String str = entryAccount(args[0].getString());
				return JSValue.create(Base64Digest.encode(str));
			}
		});
		browser.registerFunction("modifyAccount", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... args) {
				String str = modifyAccount(args[0].getString());
				return JSValue.create(Base64Digest.encode(str));
			}
		});
		
		
		browser.registerFunction("searchAccount", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... args) {
				int userId = CacheUser.user.getId();
				String us = searchAccount(userId);
				return JSValue.create(Base64Digest.encode(us));
			}
		});

		browser.registerFunction("deleteAccount", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
		 
				String str = deleteAccount(Integer.parseInt(arg0[0].getString()));
				return JSValue.create(Base64Digest.encode(str));
			}
		});
		browser.registerFunction("closeParentModel", new BrowserFunction() {
			@Override
			public JSValue invoke(JSValue... arg0) {
				browser.executeJavaScript("closeModel()");
				return JSValue.createNull();
			}
		});

		browser.loadURL(s + "/resource/tab.html");
		tabUi.setVisible(true);
	}
}
