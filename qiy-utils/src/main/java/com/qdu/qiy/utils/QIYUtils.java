package com.qdu.qiy.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.qdu.qiy.pojo.User;

public class QIYUtils {
	
	//获取session对象
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	} 
	//获取登录对象
	public static User getLoginUser(){
		return (User) getSession().getAttribute("loginUser");
	}
}
