package com.qdu.qiy.service;

import com.qdu.qiy.pojo.User;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

}
