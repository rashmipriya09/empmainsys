package com.cg.ems.service;

import com.cg.ems.bean.UserLogin;
import com.cg.ems.dao.IUserLoginDao;
import com.cg.ems.dao.UserLoginDaoImpl;
import com.cg.ems.exception.EMSException;

public class UserLoginServiceImpl implements IUserLoginService {

	private IUserLoginDao dao = new UserLoginDaoImpl();

	@Override
	public String getRole(String userName, String password) throws EMSException {

		String role = null;
		UserLogin user = dao.getUserByName(userName);
		if (user == null)
			throw new EMSException("No Such UserName");
		else if (!password.equals(user.getPassword()))
			throw new EMSException("Password Mismatch");
		else
			role = user.getRole();
		return role;
	}

	@Override
	public String getUserId(String userName, String password)
			throws EMSException {
		String userId = null;
		UserLogin user = dao.getUserByName(userName);
		if (user == null)
			throw new EMSException("No Such UserName");
		else if (!password.equals(user.getPassword()))
			throw new EMSException("Password Mismatch");
		else
			userId = user.getUserId();
		return userId;
	}

}
