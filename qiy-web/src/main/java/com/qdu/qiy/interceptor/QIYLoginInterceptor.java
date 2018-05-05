package com.qdu.qiy.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.qdu.qiy.pojo.User;
import com.qdu.qiy.utils.QIYUtils;
/**
 * 
 * 自定义的拦截器
 * 实现用户未登录跳转到登录页面
 */
public class QIYLoginInterceptor extends MethodFilterInterceptor {
	
	//拦截方法
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		String url = actionName + namespace;
		System.out.println(url);
		User user = QIYUtils.getLoginUser();
		if (user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
