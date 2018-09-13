package com.cg.ems.dao;

import com.cg.ems.bean.UserLogin;
import com.cg.ems.exception.EMSException;

public interface IUserLoginDao {

	UserLogin getUserByName(String userName) throws EMSException;

}
