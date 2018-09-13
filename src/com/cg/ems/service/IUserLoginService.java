package com.cg.ems.service;

import com.cg.ems.exception.EMSException;

public interface IUserLoginService {
	String getRole(String userName, String password) throws EMSException;

	String getUserId(String userName, String password) throws EMSException;
}
