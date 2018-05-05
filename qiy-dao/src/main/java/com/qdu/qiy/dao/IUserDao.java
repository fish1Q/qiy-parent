package com.qdu.qiy.dao;

import com.qdu.qiy.dao.base.IBaseDao;
import com.qdu.qiy.pojo.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);
	
}
