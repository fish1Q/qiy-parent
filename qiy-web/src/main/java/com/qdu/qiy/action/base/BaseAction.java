package com.qdu.qiy.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qdu.qiy.pojo.Region;
import com.qdu.qiy.pojo.Staff;
import com.qdu.qiy.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	public static final String HOME = "home";
	public static final String LIST = "list";
	protected T model;
	
	protected PageBean pageBean = new PageBean();
	
	protected int page;
	protected int rows;

	DetachedCriteria detachedCriteria =null;
	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
		
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	@Override
	public T getModel() {
		return model;
	}
	
	//动态获取实体类型,通过反射创建model对象
	public BaseAction(){
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>)actualTypeArguments[0];
		detachedCriteria= DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		//通过反射创建model对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

/**
 * 
 * <p>Title: java2json</p>
 * <p>Description: 将制定java对象转为json</p>
 * @param obj
 * @param excludes
 * @throws Exception
 */
	public void java2json(Object obj,String[] excludes){
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String json = JSONObject.fromObject(obj).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
