package com.cg.ems.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.ems.bean.Employee;
import com.cg.ems.dao.EmployeeDaoImpl;
import com.cg.ems.dao.IEmployeeDao;
import com.cg.ems.exception.EMSException;

public class EmployeeServiceImpl implements IEmployeeService {

	IEmployeeDao employeeDao;

	@Override
	public String addEmployee(Employee employee) throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		String empId;
		empId = employeeDao.addEmployee(employee);
		return empId;
	}

	@Override
	public Employee viewEmployeeById(String empId) throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		Employee employee = null;
		employee = employeeDao.viewEmployeeById(empId);
		return employee;
	}

	@Override
	public Employee viewEmployeeByFname(String firstName) throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		Employee employee = null;
		employee = employeeDao.viewEmployeeByFname(firstName);
		return employee;
	}

	@Override
	public Employee viewEmployeeByLname(String lastName) throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		Employee employee = null;
		employee = employeeDao.viewEmployeeByLname(lastName);
		return employee;
	}

	@Override
	public List<Employee> viewEmployeeByDeptName(String deptName)
			throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		List<Employee> employeeList = null;
		employeeList = employeeDao.viewEmployeeByDeptName(deptName);
		return employeeList;
	}

	@Override
	public List<Employee> viewEmployeeByGrade(String empGrade)
			throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		List<Employee> employeeList = null;
		employeeList = employeeDao.viewEmployeeByGrade(empGrade);
		return employeeList;
	}

	@Override
	public List<Employee> viewEmployeeByMaritalStatus(String maritalStatus)
			throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		List<Employee> employeeList = null;
		employeeList = employeeDao.viewEmployeeByMaritalStatus(maritalStatus);
		return employeeList;
	}

	@Override
	public Employee updateEmployeeById(Employee employee) throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		Employee updateEmployee = employeeDao.updateEmployeeById(employee);
		return updateEmployee;
	}

	@Override
	public List<Employee> retriveAllDetails() throws EMSException {
		employeeDao = new EmployeeDaoImpl();
		List<Employee> employeeList = null;
		employeeList = employeeDao.retriveAllDetails();
		return employeeList;
	}

	public void validateEmployee(Employee employee) throws EMSException {
		List<String> validationErrors = new ArrayList<String>();

		// Validating employee Id
		if (!(isValidId(employee.getEmpId()))) {
			validationErrors
					.add("\n Employee Id can't be empty and should contain 6 digits with no decimals \n");
		}

		// Validating employee first Name
		if (!(isValidFirstName(employee.getFirstName()))) {
			validationErrors
					.add("\n Employee First Name can't be empty and start with Upper case and should contain only alphabets\n");
		}

		// Validating employee last Name
		if (!(isValidLastName(employee.getLastName()))) {
			validationErrors
					.add("\n Employee Last Name can't be empty and start with Upper case and should contain only alphabets\n");
		}

		// Validating employee DOB
		if (!(isValidDOB(employee.getDateOfBirth()))) {
			validationErrors
					.add("\n Employee Date of Birth can't be empty and enter a valid date.Age should be between 18 and 58\n");
		}
		// Validating employee DOJ
		if (!(isValidDOJ(employee.getDateOfJoining(), employee.getDateOfBirth()))) {
			validationErrors
					.add("\n Employee Date of Joining can't be empty and enter a valid date\n");
		}

		

		if (!(isValidDesignation(employee.getDesignation()))) {
			validationErrors
					.add("\n Employee Designation is limited to 50 characters\n");
		}

		/*
		 * if(!(isValidGender(employee.getGender()))) {
		 * validationErrors.add("\n Employee Gender can not be empty \n"); }
		 */

		if (!(isValidSalary(employee.getBasicSalary(), employee.getEmpGrade()))) {
			validationErrors
					.add("\n Employee Salary should be numeric and should match the employee grade\n");
		}

		if (!(isValidPhoneNumber(employee.getContactNo()))) {
			validationErrors
					.add("\n Contact Number should be alpha numeric and 10 digits \n");
		}

		/*
		 * if(!(isValidMaritalStatus(employee.getMaritalStatus()))) {
		 * validationErrors.add("\n Marital Status cannot be emp \n"); }
		 */

		if (!validationErrors.isEmpty())
			throw new EMSException(validationErrors + "");
	}

	public boolean isValidName(String employeeName) {
		Pattern namePattern = Pattern.compile("^[A-Z]{1}[A-Za-z\\s]{2,}$");
		Matcher nameMatcher = namePattern.matcher(employeeName);
		return nameMatcher.matches();
	}

	public boolean isValidId(String empId) {
		Pattern idPattern = Pattern.compile("^[0-9]{6}$");
		Matcher nameMatcher = idPattern.matcher(empId);
		return nameMatcher.matches();
	}

	public boolean isValidFirstName(String firstName) {
		Pattern firstNamePattern = Pattern.compile("^[A-Z]{1}[A-Za-z\\s]{3,}$");
		Matcher nameMatcher = firstNamePattern.matcher(firstName);
		return nameMatcher.matches();
	}

	public boolean isValidLastName(String lastName) {
		Pattern lastNamePattern = Pattern.compile("^[A-Z]{1}[A-Za-z]{3,}$");
		Matcher nameMatcher = lastNamePattern.matcher(lastName);
		return nameMatcher.matches();
	}

	public boolean isValidDOB(Date dob) throws EMSException {

		DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

		Date minDate = new Date();
		Date maxDate = new Date();
		try {
			minDate = formatter1.parse("01-01-1969");
			maxDate = formatter1.parse("01-01-1998");

		} catch (ParseException exception) {
			throw new EMSException(exception.getMessage());
		}
		return (dob.after(minDate) && dob.before(maxDate));

	}

	public boolean isValidDOJ(Date doj, Date dob) {
		return doj.after(dob);

	}

	public boolean isValidDesignation(String designation) {
		Pattern namePattern = Pattern.compile("^[A-Za-z0-9\\s]{2,50}$");
		Matcher nameMatcher = namePattern.matcher(designation);
		return nameMatcher.matches();
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		Pattern phonePattern = Pattern.compile("^[1-9]{1}[0-9]{9}$");
		Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
		return phoneMatcher.matches();

	}

	public boolean isValidSalary(Double salary, String grade) {
		boolean flag = false;
		String grade1 = null;
		if (salary > 0 && salary <= 15000)
			grade1 = "M1";
		else if (salary > 15000 && salary <= 25000)
			grade1 = "M2";
		else if (salary > 25000 && salary <= 40000)
			grade1 = "M3";
		else if (salary > 40000 && salary <= 75000)
			grade1 = "M4";
		else if (salary > 75000 && salary <= 100000)
			grade1 = "M5";
		else if (salary > 100000 && salary <= 125000)
			grade1 = "M6";
		else if (salary > 125000)
			grade1 = "M7";

		if (grade1.equals(grade)) {
			flag = true;
		}
		return flag;
	}

}
