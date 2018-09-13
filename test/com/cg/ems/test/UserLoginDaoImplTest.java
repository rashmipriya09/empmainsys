package com.cg.ems.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.dao.UserLoginDaoImpl;
import com.cg.ems.exception.EMSException;

public class UserLoginDaoImplTest {
	static UserLoginDaoImpl dao;
	
	@BeforeClass
	public static void initialize() {
		System.out.println("test");
		dao = new UserLoginDaoImpl();
	}
	
	
	@Test
	public void testUserName() throws EMSException {
		assertNotNull(dao.getUserByName("Amir"));
	}

}
