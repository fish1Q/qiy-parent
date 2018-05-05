package com.qdu.qiy.service;

import com.qdu.qiy.pojo.Staff;
import com.qdu.qiy.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

}
