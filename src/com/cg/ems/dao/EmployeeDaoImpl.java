package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.bean.Employee;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.DBConnection;

public class EmployeeDaoImpl implements IEmployeeDao {

	Logger logger = Logger.getRootLogger();

	public EmployeeDaoImpl() {
		PropertyConfigurator.configure("resources//log4j.properties");
	}

	public Employee selectStatements(ResultSet resultSet) throws SQLException {
		Employee employee = new Employee();
		employee.setEmpId(resultSet.getString(1));
		employee.setFirstName(resultSet.getString(2));
		employee.setLastName(resultSet.getString(3));
		employee.setDateOfBirth(resultSet.getDate(4));
		employee.setDateOfJoining(resultSet.getDate(5));
		employee.setDeptId(resultSet.getInt(6));
		employee.setEmpGrade(resultSet.getString(7));
		employee.setDesignation(resultSet.getString(8));
		employee.setBasicSalary(resultSet.getDouble(9));
		employee.setGender(resultSet.getString(10));
		employee.setMaritalStatus(resultSet.getString(11));
		employee.setHomeAddress(resultSet.getString(12));
		employee.setContactNo(resultSet.getString(13));
		employee.setManagerId(resultSet.getString(14));

		return employee;
	}

	@Override
	public String addEmployee(Employee employee) throws EMSException {

		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		//PreparedStatement preparedStatement1 = null;

		java.sql.Date sqlDOB = new java.sql.Date(employee.getDateOfBirth()
				.getTime());
		java.sql.Date sqlDOJ = new java.sql.Date(employee.getDateOfJoining()
				.getTime());

		String empId = null;
		int queryResult = 0;
		//int queryResult1 = 0;

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.ADD_EMPLOYEE);

			preparedStatement.setString(1, employee.getEmpId());
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setDate(4, sqlDOB);
			preparedStatement.setDate(5, sqlDOJ);
			preparedStatement.setInt(6, employee.getDeptId());
			preparedStatement.setString(7, employee.getEmpGrade());
			preparedStatement.setString(8, employee.getDesignation());
			preparedStatement.setDouble(9, employee.getBasicSalary());
			preparedStatement.setString(10, employee.getGender());
			preparedStatement.setString(11, employee.getMaritalStatus());
			preparedStatement.setString(12, employee.getHomeAddress());
			preparedStatement.setString(13, employee.getContactNo());
			preparedStatement.setString(14, employee.getManagerId());
			preparedStatement.setInt(15, 12);

			queryResult = preparedStatement.executeUpdate();
//			empId = employee.getEmpId();
//
//			preparedStatement1 = connection.prepareStatement(IQueryMapper.ADD_USER_DETAILS);
//
//			preparedStatement1.setString(1, employee.getFirstName());
//			preparedStatement1.setString(2, employee.getLastName());
//			preparedStatement1.setString(3, employee.getEmpId());
//			queryResult1 = preparedStatement1.executeUpdate();
//			if (queryResult1 == 0) {
//				logger.error("Adding details failed!! ");
//				throw new EMSException("Adding employee details failed!! ");
//			}

			if (queryResult == 0) {
				logger.error(" Adding details failed!! ");
				throw new EMSException("Adding employee details failed!!");
			}

			else {
				logger.info("Employee details added successfully:");
				return empId;
			}
		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured refer log");
		}

		finally {
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}

	}

	@Override
	public Employee viewEmployeeById(String empId) throws EMSException {

		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employee = null;

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.SELECT_BY_ID);
			preparedStatement.setString(1, empId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				employee = new Employee();
				employee = selectStatements(resultSet);

			}
			if (employee != null) {
				logger.info("Record Found Successfully");
				return employee;
			} else {
				logger.info("Record Not Found Successfully");
				return null;
			}

		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException exception) {
				logger.error(exception.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}
	}

	@Override
	public Employee viewEmployeeByFname(String firstName) throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employee = null;

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.SELECT_BY_FNAME);
			preparedStatement.setString(1, firstName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				employee = new Employee();
				employee = selectStatements(resultSet);
			}
			if (employee != null) {
				logger.info("Record Found Successfully");
				return employee;
			} else {
				logger.info("Record Not Found Successfully");
				return null;
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException exception) {
				logger.error(exception.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}
	}

	@Override
	public Employee viewEmployeeByLname(String lastName) throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employee = null;

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.SELECT_BY_LNAME);
			preparedStatement.setString(1, lastName);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				employee = new Employee();
				employee = selectStatements(resultSet);
			}
			if (employee != null) {
				logger.info("Record Found Successfully");
				return employee;
			} else {
				logger.info("Record Not Found Successfully");
				return null;
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException exception2) {
				logger.error(exception2.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}
	}

	@Override
	public List<Employee> viewEmployeeByDeptName(String deptName)
			throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		int empCount = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employeeList = new ArrayList<Employee>();

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.SELECT_BY_DEPT_NAME);
			preparedStatement.setString(1, deptName);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Employee employee = new Employee();
				employee = selectStatements(resultSet);

				employeeList.add(employee);
				empCount++;
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new EMSException("Error in closing db connection");

			}
		}
		if (empCount == 0)
			return null;
		else
			return employeeList;
	}

	@Override
	public List<Employee> viewEmployeeByGrade(String empGrade)
			throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		int empCount = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employeeList = new ArrayList<Employee>();

		try {
			preparedStatement = connection.prepareStatement(IQueryMapper.SELECT_BY_GRADE);
			preparedStatement.setString(1, empGrade);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Employee employee = new Employee();
				employee = selectStatements(resultSet);

				employeeList.add(employee);
				empCount++;

			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException exception2) {
				logger.error(exception2.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}
		if (empCount == 0)
			return null;
		else
			return employeeList;
	}

	@Override
	public List<Employee> viewEmployeeByMaritalStatus(String maritalStatus)
			throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		int empCount = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Employee> employeeList = new ArrayList<Employee>();

		try {
			preparedStatement = connection
					.prepareStatement(IQueryMapper.SELECT_BY_MARITAL_STATUS);
			preparedStatement.setString(1, maritalStatus);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Employee employee = new Employee();
				employee = selectStatements(resultSet);

				employeeList.add(employee);
				empCount++;

			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw new EMSException(exception.getMessage());
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException exception2) {
				logger.error(exception2.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}
		if (empCount == 0)
			return null;
		else
			return employeeList;
	}

	@Override
	public Employee updateEmployeeById(Employee employee) throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		int queryResult = 0;

		try {
			preparedStatement = connection
					.prepareStatement(IQueryMapper.UPDATE_EMPLOYEE_BY_ID);
			preparedStatement.setString(11, employee.getEmpId());
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setInt(3, employee.getDeptId());
			preparedStatement.setString(4, employee.getEmpGrade());
			preparedStatement.setString(5, employee.getDesignation());
			preparedStatement.setDouble(6, employee.getBasicSalary());
			preparedStatement.setString(7, employee.getMaritalStatus());
			preparedStatement.setString(8, employee.getHomeAddress());
			preparedStatement.setString(9, employee.getContactNo());
			preparedStatement.setString(10, employee.getManagerId());

			queryResult = preparedStatement.executeUpdate();

			if (queryResult == 0) {
				
				logger.error("Updation failed ");
				throw new EMSException("Updating employee details failed ");

			} else {
				
				logger.info("Employee details updated successfully:");
				return employee;
			}
		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured!!");
		}

		finally {
			try {
				// resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException sqlException) {
				logger.error(sqlException.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}

	}

	@Override
	public List<Employee> retriveAllDetails() throws EMSException {
		Connection con = DBConnection.getInstance().getConnection();
		int empCount = 0;

		PreparedStatement preparedStatement = null;
		ResultSet resultset = null;

		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			preparedStatement = con.prepareStatement(IQueryMapper.RETRIEVE_ALL_DETAILS);
			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				Employee employee = new Employee();
				employee = selectStatements(resultset);

				employeeList.add(employee);
				empCount++;
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured!!");
		}

		finally {
			try {
				resultset.close();
				preparedStatement.close();
				con.close();
			} catch (SQLException exception) {
				logger.error(exception.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}

		if (empCount == 0)
			return null;
		else
			return employeeList;
	}

}
