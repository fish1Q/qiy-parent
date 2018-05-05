package com.qdu.qiy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qdu.qiy.dao.IUserDao;
import com.qdu.qiy.pojo.User;
import com.qdu.qiy.service.IUserService;
import com.qdu.qiy.utils.MD5Utils;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	
	@Override
	public User login(User user) {
		//使用md5加密
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
		
	}
	/**
	 * 根据用户id修改密码 
	 */
	@Override
	public void editPassword(String id, String password) {
		//使用md5加密
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}

}
