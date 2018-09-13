package com.cg.ems.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.Employee;
import com.cg.ems.bean.Leave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.service.EmployeeServiceImpl;
import com.cg.ems.service.IEmployeeService;
import com.cg.ems.service.ILeaveService;
import com.cg.ems.service.LeaveServiceImpl;

public class UserConsole {

	// private DateTimeFormatter dtFormat;
	private Scanner scanner;
	private String user;
	private ILeaveService leaveService;
	private IEmployeeService employeeService = null;
	private Logger logger = Logger.getRootLogger();
	private String userId;

	public UserConsole(String user, String userId) {
		this.user = user;
		this.userId = userId;
	}

	public void start() throws EMSException {
		PropertyConfigurator.configure("resources//log4j.properties");
		scanner = new Scanner(System.in);
		
		System.out.println("Welcome " + user);
		System.out.println("----------------");

		String choice = null;
		while (true) {
			System.out
					.println("1.Search Employee Details\n2.Apply Leave\n3.Check Leave Status\n4.Exit\n");
			System.out.println("----------------------");
			System.out.print("Enter your choice :");
			choice = scanner.next();
			System.out.println("-----------------------");

			switch (choice) {

			case "1":
				searchEmployee();
				break;
			case "2":
				applyLeave();
				break;
			case "3":
				checkLeaveStatus();
				break;
			case "4":
				System.out.print("Thank you!!");
				System.exit(0);
				break;
			default:
				System.out.println("Please enter a valid option[1-4]");
			}
		}
	}

	private void checkLeaveStatus() {
		leaveService = new LeaveServiceImpl();
		List<Leave> leaveList;
		try {
			leaveList = leaveService.checkAppliedLeaves(userId);
		if (leaveList != null) {
			for (Leave leave : leaveList) {
				System.out.println(leave);
			}
		} else {
			System.out.println("No Records Found!");
		}
		}
		catch (EMSException e) {
			System.err.println(e.getMessage());
		}
	}

	private void applyLeave() {
		Leave leave = new Leave();
		leaveService = new LeaveServiceImpl();
		System.out.println("\nEnter Leave Details");
		System.out.println("Employee Id: ");
		leave.setEmployeeId(userId);
		System.out.println(userId);

		LocalDate date1 = null;
		while (date1 == null) {
			System.out
					.println("Enter leave starting date in DD-MM-YYYY format: ");
			String date = scanner.next();
			DateTimeFormatter dateFormat = DateTimeFormatter
					.ofPattern("dd-MM-yyyy");
			try {
				date1 = LocalDate.parse(date, dateFormat);

			} catch (DateTimeException e) {
				System.out
						.println("Invalid date. Please enter the date in the required format [DD-MM-YYYY].");
			}
		}
		leave.setStartdate(date1);
		System.out.println("Enter leave duration: ");
		leave.setDuration(scanner.nextInt());
		try {
			leave.setRemainingLeaves(leaveService.getRemainingLeaves(userId));

			leaveService.validateLeave(leave);
			int leaveId = leaveService.applyLeave(leave);
			leave.setLeaveId(leaveId);
			leave.setLeaveStatus("Applied");
			System.out.println(leave.toString());
			System.out
					.println("Leave applied successfully. Your leaveid is "
							+ leaveId);
		} catch (EMSException e) {
			logger.error("exception occured", e);
			System.err.println("Invalid data:");
			System.err.println(e.getMessage() + " \n Try again..");
			System.exit(0);

		}

	}

	private void searchEmployee() {
		while (true) {
			System.out.println("[1].View Employee by Id");
			System.out.println("[2].View Employee by First Name");
			System.out.println("[3].View Employee by Last Name");
			System.out.println("[4].View Employee by Department Name");
			System.out.println("[5].View Employee by Grade");
			System.out.println("[6].View Employee by Marital Status");
			System.out.println("[7].Exit");
			System.out.println("----------------------------------");
			System.out.println("Enter your choice :");

			try {
				String value;
				value = scanner.next();

				switch (value) {

				case "1":
					searchById();
					break;
				case "2":
					searchByFName();
					break;

				case "3":
					searchByLName();
					break;

				case "4":
					searchByDeptName();
					break;

				case "5":
					searchByGrade();
					break;

				case "6":
					searchByMaritalStatus();
					break;
				case "7":
					System.out.print("Thank you!!");
					System.exit(0);
					break;
				default:
					System.out.println("Enter a valid option[1-7]");
				}
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.err.println("Please enter a numeric value, try again");
			}
		}
	}

	private void searchByMaritalStatus() {
		String maritalStatus = null;
		List<Employee> emplList = new ArrayList<Employee>();
		System.out.println("Enter Marital Status(U/M/S/W/D):");
		maritalStatus = scanner.next();
		emplList = getEmployeeByMaritalStatus(maritalStatus);

		if (emplList != null) {
			Iterator<Employee> i = emplList.iterator();
			while (i.hasNext()) {
				displayEmployee(i.next());
			}
		} else {
			System.err
					.println("There are no employee details associated with given marital status "
							+ maritalStatus);
		}
	}

	private void searchByGrade() {
		String empGrade = null;
		List<Employee> emplList = new ArrayList<Employee>();
		System.out.println("Enter Employee Grade:");
		empGrade = scanner.next();
		emplList = getEmployeeByGrade(empGrade);

		if (emplList != null) {
			Iterator<Employee> i = emplList.iterator();
			while (i.hasNext()) {
				displayEmployee(i.next());
			}
		} else {
			System.err
					.println("There are no employee details associated with given employee grade "
							+ empGrade);
		}
	}

	private void searchByDeptName() {
		String deptName = null;
		List<Employee> emplList = new ArrayList<Employee>();

		System.out.println("Enter Department Name:");
		deptName = scanner.next();
		emplList = getEmployeeByDeptName(deptName);

		if (emplList != null) {
			Iterator<Employee> i = emplList.iterator();
			while (i.hasNext()) {
				displayEmployee(i.next());
			}
		} else {
			System.err
					.println("There are no employee details associated with given department name "
							+ deptName);
		}
	}

	private void searchByLName() {
		String lname = null;
		Employee employee = null;
		System.out.println("Enter employee last name:");
		lname = scanner.next();
		employee = getEmployeeByLName(lname);

		if (employee != null) {
			displayEmployee(employee);

		} else {
			System.err
					.println("There are no employee details associated with employee Last Name "
							+ lname);
		}

	}

	private void searchByFName() {
		String fname = null;
		Employee employee = null;
		System.out.println("Enter employee first name:");
		fname = scanner.next();
		employee = getEmployeeByFName(fname);

		if (employee != null) {
			displayEmployee(employee);

		} else {
			System.err
					.println("There are no employee details associated with employee First Name "
							+ fname);
		}

	}

	private void searchById() {
		String empId = null;
		Employee employee = null;
		System.out.println("Enter employee id:");
		empId = scanner.next();
		employee = getEmployeeById(empId);

		if (employee != null) {
			displayEmployee(employee);

		} else {
			System.err
					.println("There are no employee details associated with employee id "
							+ empId);
		}
	}

	public Employee getEmployeeById(String employeeId) {
		Employee employee = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employee = employeeService.viewEmployeeById(employeeId);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		/* employeeService = null; */
		return employee;
	}

	public Employee getEmployeeByFName(String fName) {
		Employee employeeBean = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employeeBean = employeeService.viewEmployeeByFname(fName);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		employeeService = null;
		return employeeBean;
	}

	public Employee getEmployeeByLName(String lName) {
		Employee employeeBean = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employeeBean = employeeService.viewEmployeeByLname(lName);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		employeeService = null;
		return employeeBean;
	}

	public List<Employee> getEmployeeByDeptName(String deptName) {
		List<Employee> employeeBean = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employeeBean = employeeService.viewEmployeeByDeptName(deptName);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		employeeService = null;
		return employeeBean;
	}

	public List<Employee> getEmployeeByGrade(String empGrade) {
		List<Employee> employeeBean = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employeeBean = employeeService.viewEmployeeByGrade(empGrade);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		employeeService = null;
		return employeeBean;
	}

	public List<Employee> getEmployeeByMaritalStatus(String maritalStatus) {
		List<Employee> employeeBean = null;
		employeeService = new EmployeeServiceImpl();

		try {
			employeeBean = employeeService
					.viewEmployeeByMaritalStatus(maritalStatus);
		} catch (EMSException employeeException) {
			logger.error("exception occured ", employeeException);
			System.out.println("ERROR : " + employeeException.getMessage());
		}

		employeeService = null;
		return employeeBean;
	}

	public void displayEmployee(Employee employee) {

		System.out.println("------------Employee Details---------- ");

		System.out.println("Employee Id : " + employee.getEmpId());
		System.out.println("First Name : " + employee.getFirstName());
		System.out.println("Last Name : " + employee.getLastName());
		System.out.println("Date of Birth : " + employee.getDateOfBirth());
		System.out.println("Date of Joining : " + employee.getDateOfJoining());
		System.out.println("Department Id : " + employee.getDeptId());
		System.out.println("Employee Grade : " + employee.getEmpGrade());
		System.out.println("Designation : " + employee.getDesignation());
		System.out.println("Basic Salary : " + employee.getBasicSalary());
		System.out.println("Gender : " + employee.getGender());
		System.out.println("Marital Status : " + employee.getMaritalStatus());
		System.out.println("Home Address : " + employee.getHomeAddress());
		System.out.println("Contact Number : " + employee.getContactNo());
		System.out.println("Manager Id : " + employee.getManagerId());
		System.out.println("-------------------------------------");
	}
}
