package com.cg.ems.dao;

import java.util.List;

import com.cg.ems.bean.Employee;
import com.cg.ems.exception.EMSException;

public interface IEmployeeDao {

	public String addEmployee(Employee employee) throws EMSException;

	public Employee viewEmployeeById(String empId) throws EMSException;

	public Employee viewEmployeeByFname(String firstName) throws EMSException;

	public Employee viewEmployeeByLname(String lastName) throws EMSException;

	public List<Employee> viewEmployeeByDeptName(String deptName)
			throws EMSException;

	public List<Employee> viewEmployeeByGrade(String empGrade)
			throws EMSException;

	public List<Employee> viewEmployeeByMaritalStatus(String maritalStatus)
			throws EMSException;

	public Employee updateEmployeeById(Employee employee) throws EMSException;

	public List<Employee> retriveAllDetails() throws EMSException;
}
