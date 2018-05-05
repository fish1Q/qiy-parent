package com.qdu.qiy.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qdu.qiy.dao.IStaffDao;
import com.qdu.qiy.pojo.Staff;
import com.qdu.qiy.service.IStaffService;
import com.qdu.qiy.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired 
	private IStaffDao staffDao;
	
	@Override
	public void save(Staff model) {
		staffDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	
	/**
	 * 逻辑删除
	 * */
	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.executeUpdate( "staff.delete", id);
			}
		}
	}

	@Override
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	
	/**
	 * 根据id修改取派员
	 */
	@Override
	public void update(Staff staff) {
		staffDao.update(staff);
	}
	
}
