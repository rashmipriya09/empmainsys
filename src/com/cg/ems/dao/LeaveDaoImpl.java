package com.cg.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import com.cg.ems.beans.Employee;

import com.cg.ems.bean.Leave;
import com.cg.ems.exception.EMSException;
import com.cg.ems.util.DBConnection;

public class LeaveDaoImpl implements ILeaveDao {

	Logger logger = Logger.getRootLogger();

	public LeaveDaoImpl() {
		PropertyConfigurator.configure("resources//log4j.properties");
	}

	@Override
	public int applyLeave(Leave leave) throws EMSException {
		
		Connection connection = DBConnection.getInstance().getConnection();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int leaveId = 0;
		int queryResult = 0;
		
		
		try {
			preparedStatement = connection
					.prepareStatement(IQueryMapper.LEAVE_REQUEST_QUERY);
			
			preparedStatement.setString(1, leave.getEmployeeId());
			preparedStatement.setInt(2, leave.getRemainingLeaves());
			int days = leave.getDuration();
			preparedStatement.setInt(3, days);
			
			preparedStatement.setDate(4,
					java.sql.Date.valueOf(leave.getStartdate()));
			
			LocalDate startDate = leave.getStartdate();
			LocalDate endDate = addDays(startDate, days);
			
			preparedStatement.setDate(5, java.sql.Date.valueOf(endDate));
			preparedStatement.setString(6, "applied");
			preparedStatement.setString(7,"154575");
			
			queryResult = preparedStatement.executeUpdate();
						preparedStatement.close();
			preparedStatement = connection
					.prepareStatement(IQueryMapper.LEAVEID_QUERY_SEQUENCE);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				
				leaveId = resultSet.getInt(1);
			}

			if (queryResult == 0) {
				
				logger.error("Application for leave failed ");
				throw new EMSException("Inserting Leave details failed ");
			} else {
				
				logger.info("Leave details added successfully:");
				return leaveId;
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
	public boolean leaveSanction(int leaveId, String status)
			throws EMSException {
		Connection connection = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;

		int queryResult = 0;
		try {
			preparedStatement = connection
					.prepareStatement(IQueryMapper.LEAVE_SANCTION_QUERY);
			preparedStatement.setInt(2, leaveId);
			preparedStatement.setString(1, status);
			queryResult = preparedStatement.executeUpdate();
			if (status.equals("approved")) {

				preparedStatement.close();
				preparedStatement = connection
						.prepareStatement(IQueryMapper.GET_EMPID_BY_LEAVE_ID_QUERY);
				preparedStatement.setInt(1, leaveId);
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					String empId = null;
					int duration = 0;
					empId = resultSet.getString(1);
					duration = resultSet.getInt(2);
					PreparedStatement preparedStatement1 = null;
					preparedStatement1 = connection
							.prepareStatement(IQueryMapper.UPDATE_REMAINING_LEAVES_QUERY);
					preparedStatement1.setInt(1, duration);
					preparedStatement1.setString(2, empId);
					preparedStatement1.executeUpdate();
					preparedStatement1 = connection
							.prepareStatement(IQueryMapper.UPDATE_LEAVE_BALANCE);
					preparedStatement1.setInt(1, duration);
					preparedStatement1.setInt(2, leaveId);
					preparedStatement1.executeUpdate();
					preparedStatement1.close();
				}

			}
			if (queryResult == 0) {
				logger.error("Approval for leave failed ");
				throw new EMSException("Approving Leave details failed ");
			} else {
				logger.info("Leave details modified successfully:");
				return true;
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured refer log");
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
	public List<Leave> viewLeaveApplications() throws EMSException {
		Connection con = DBConnection.getInstance().getConnection();
		int leaveCount = 0;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Leave> leaveList = new ArrayList<Leave>();
		try {
			preparedStatement = con.prepareStatement(IQueryMapper.RETRIEVE_APPLIED_LEAVE_QUERY);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Leave bean = new Leave();
				bean.setLeaveId(resultSet.getInt(1));
				bean.setEmployeeId(resultSet.getString(2));
				bean.setStartdate(resultSet.getDate(3).toLocalDate());
				bean.setDuration(resultSet.getInt(4));
				bean.setRemainingLeaves(resultSet.getInt(5));
				bean.setLeaveStatus(resultSet.getString(6));
				leaveList.add(bean);
				leaveCount++;
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured. Refer log");
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (SQLException exception) {
				logger.error(exception.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}

		if (leaveCount == 0)
			return null;
		else
			return leaveList;
	}

	@Override
	public int getRemainingLeaves(String empId) throws EMSException {
		Connection con = DBConnection.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet;
		int remainingLeaves = 0;
		try {
			preparedStatement = con.prepareStatement(IQueryMapper.GET_LEAVE_BY_ID);
			preparedStatement.setString(1, empId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				remainingLeaves = resultSet.getInt(1);
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured. Refer log");
		}

		return remainingLeaves;
	}

	@Override
	public List<Leave> checkAppliedLeaves(String empId) throws EMSException {
		Connection con = DBConnection.getInstance().getConnection();
		int leaveCount = 0;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Leave> leaveList = new ArrayList<Leave>();
		try {
			preparedStatement = con.prepareStatement(IQueryMapper.RETRIEVE_APPLIED_LEAVE_BY_EMPID_QUERY);
			preparedStatement.setString(1, empId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Leave bean = new Leave();
				bean.setLeaveId(resultSet.getInt(1));
				bean.setEmployeeId(resultSet.getString(2));
				bean.setStartdate(resultSet.getDate(3).toLocalDate());
				bean.setDuration(resultSet.getInt(4));
				bean.setRemainingLeaves(resultSet.getInt(5));
				bean.setLeaveStatus(resultSet.getString(6));
				leaveList.add(bean);
				leaveCount++;
			}

		} catch (SQLException sqlException) {
			logger.error(sqlException.getMessage());
			throw new EMSException("Tehnical problem occured. Refer log");
		}

		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				con.close();
			} catch (SQLException exception) {
				logger.error(exception.getMessage());
				throw new EMSException("Error in closing db connection");
			}
		}

		if (leaveCount == 0)
			return null;
		else
			return leaveList;
	}

	// Add Days to leave start date taking into account weekends
	private LocalDate addDays(LocalDate date, int workdays) {
		if (workdays < 1) {
			return date;
		}

		LocalDate result = date;
		int addedDays = 0;
		while (addedDays < workdays) {
			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result
					.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				++addedDays;
			}
		}

		return result;
	}

}
