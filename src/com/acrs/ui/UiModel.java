package com.acrs.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class UiModel extends JFrame{
	public UiModel(int w,int h,String title){
		super(title);
		try {
			this.setIconImage(ImageIO.read(new File(UiModel.class.getClassLoader().getResource("").getPath()+"/resource/images/logo.png")));
		} catch (IOException e) {
		}
		this.setUndecorated(true);
		 
		this.setDefaultCloseOperation(3);
		 this.setSize(w, h);
		 AWTUtilities.setWindowOpacity(this, 0.99f);
		 int windowWidth = this.getWidth();                     //获得窗口宽  
		 int windowHeight = this.getHeight();                   //获得窗口高  
		 Toolkit kit = Toolkit.getDefaultToolkit();              //定义工具包  
		 Dimension screenSize = kit.getScreenSize();             //获取屏幕的尺寸  
		 int screenWidth = screenSize.width;                     //获取屏幕的宽  
		 int screenHeight = screenSize.height;                   //获取屏幕的高  
		 this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
	}
}
