package com.qdu.qiy.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.qdu.qiy.action.base.BaseAction;
import com.qdu.qiy.pojo.Staff;
import com.qdu.qiy.service.IStaffService;
import com.qdu.qiy.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;
	
	public String add(){
		staffService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询方法
	 * */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.java2json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	private String ids;
	/**
	 * 批量删除
	 * */
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	} 
	/**
	 * 修改取派员信息 
	 */
	public String edit(){
		//首先先查询数据库,
		Staff staff = staffService.findById(model.getId());
		//使用页面提交的数据覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		
		staffService.update(staff);
		return LIST;
	}
	

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
	