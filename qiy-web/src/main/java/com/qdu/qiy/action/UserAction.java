package com.qdu.qiy.action;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.qdu.qiy.action.base.BaseAction;
import com.qdu.qiy.pojo.User;
import com.qdu.qiy.service.IUserService;
import com.qdu.qiy.utils.QIYUtils;


@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * 用户登录
	 */
	@Autowired
	private IUserService userService;
	public String login(){
		//首先校验验证码
		String  validatecode = (String)ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)){
			//正确
			User user = userService.login(model);
			if(user!= null){
				//登录成功,将user放在session中跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser",user);
				return HOME;
			}else{
				//登录失败,设置提示信息跳转页面
				this.addActionError("用户名或密码输入错误");
				return LOGIN;
			}
		}else{
			//错误,设置提示信息跳转页面
			this.addActionError("输入的验证码错误");
			return LOGIN;
		}
	}
	/**
	 * 用户注销
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	/**
	 * 修改当前用户的密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException{
		String f = "1";
		User user = QIYUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(),model.getPassword());
		} catch (Exception e) {
			f="0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
}
