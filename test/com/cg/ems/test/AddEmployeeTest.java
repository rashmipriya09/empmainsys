/**
 * 
 */
package com.cg.ems.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.EmployeeDaoImpl;

import com.cg.ems.exception.EMSException;

/**
 * @author Rashmi Priya
 *
 */
public class AddEmployeeTest {

	/**
	 * @throws java.lang.Exception
	 */
	static EmployeeDaoImpl dao;
	@BeforeClass
	public static void setUpBeforeClass() throws EMSException {
		System.out.println("test");
		dao = new EmployeeDaoImpl();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws EMSException {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws EMSException {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws EMSException {
	}

	@Test
	public void test() throws EMSException {
		Employee bean=new Employee();
		bean.setBasicSalary(987456);
		bean.setContactNo("9876543210");
		String date="09-05-1996";
		DateTimeFormatter dateFormat = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

			LocalDate dob=LocalDate.parse(date, dateFormat);
		
		bean.setDateOfBirth(java.sql.Date.valueOf(dob));
		String dateofJoining="04-07-2018";
		DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

			LocalDate doj=LocalDate.parse(dateofJoining, dateFormatter);
		bean.setDateOfJoining(java.sql.Date.valueOf(doj));
		bean.setDeptId(101);
		bean.setDesignation("Analyst");
		bean.setEmpGrade("M5");
		bean.setEmpId("784391");
		bean.setFirstName("qwerty");
		bean.setGender("F");
		bean.setHomeAddress("Bangalore");
		bean.setLastName("asdfgh");
		bean.setManagerId("154575");
		bean.setMaritalStatus("U");
		
		try{
			String id=dao.addEmployee(bean);
		assertNotSame(id,"784392");
		}
		catch (EMSException exception) {
			fail(" exception occured "+exception.getMessage());
		}
	}

}
