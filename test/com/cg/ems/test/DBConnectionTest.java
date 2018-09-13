package com.cg.ems.test;

import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.dao.EmployeeDaoImpl;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.DBConnection;

public class DBConnectionTest {

	static EmployeeDaoImpl daotest;
	static Connection dbCon;

	@BeforeClass
	public static void initialise() {
		daotest = new EmployeeDaoImpl();
		dbCon = null;
	}

	@Before
	public void beforeEachTest() {
		System.out.println("DBConnection Test - Start \n");
	}

	@Test
	public void test() throws EMSException {
		Connection dbCon = DBConnection.getInstance().getConnection();
		Assert.assertNotNull(dbCon);
	}

	@After
	public void afterEachTest() {
		System.out.println("DBConnection Test - End\n");
	}

	@AfterClass
	public static void destroy() {
		System.out.println("Testing completed");
		daotest = null;
		dbCon = null;
	}
}
