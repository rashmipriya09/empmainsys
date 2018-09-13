package com.cg.ems.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.ems.bean.Employee;
import com.cg.ems.dao.EmployeeDaoImpl;
import com.cg.ems.exception.EMSException;

public class EmployeeDaoImplTest {

	static EmployeeDaoImpl dao;
	static Employee employee;

	@BeforeClass
	public static void initialize() {
		System.out.println("test");
		dao = new EmployeeDaoImpl();
		employee = new Employee();
		
	}
	
	/************************************************
	 * Test case for retriveAllDetails()
	 ************************************************/
	@Test
	public void testViewAll() throws EMSException {
		assertNotNull(dao.retriveAllDetails());
	}

	/*******************************
	 * Test case for viewById()
	 *******************************/

	@Test
	public void testById() throws EMSException {
		assertNotNull(dao.viewEmployeeById("154472"));
	}

	@Test
	public void testByFName() throws EMSException {
		assertNotNull(dao.viewEmployeeByFname("Rashmi"));
	}

	@Test
	public void testByLName() throws EMSException {
		assertNotNull(dao.viewEmployeeByLname("Priya"));
	}

	@Test
	public void testByMaritalStatus() throws EMSException {
		assertNotNull(dao.viewEmployeeByMaritalStatus("U"));
	}

	@Test
	public void testByDeptName() throws EMSException {
		assertNotNull(dao.viewEmployeeByDeptName("IT"));
	}

}
