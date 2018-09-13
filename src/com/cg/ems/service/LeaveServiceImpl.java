package com.cg.ems.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.ems.bean.Leave;
import com.cg.ems.dao.ILeaveDao;
import com.cg.ems.dao.LeaveDaoImpl;
import com.cg.ems.exception.EMSException;

public class LeaveServiceImpl implements ILeaveService {
	ILeaveDao dao = new LeaveDaoImpl();

	@Override
	public int applyLeave(Leave leave) throws EMSException {
		int leaveId = 0;
		try {
			leaveId = dao.applyLeave(leave);
		} catch (EMSException e) {
			throw new EMSException ("Applying Leave Failed");
		}
		return leaveId;
	}

	@Override
	public boolean leaveSanction(int leaveId, String status) throws EMSException {
		boolean updateStatus = false;
		try {
			updateStatus = dao.leaveSanction(leaveId, status);
		} catch (EMSException e) {
			throw new EMSException ("Leave Sanctioning Failed");
		}
		return updateStatus;
	}

	@Override
	public List<Leave> viewLeaveApplications() throws EMSException {
		List<Leave> leaveList = new ArrayList<Leave>();
		try {
			leaveList = dao.viewLeaveApplications();
		} catch (EMSException e) {
			throw new EMSException ("Unable to view Leave");
		}
		return leaveList;
	}

	@Override
	public void validateLeave(Leave bean) throws EMSException {

		List<String> validationErrors = new ArrayList<String>();

		if (!(isValidStartDate(bean.getStartdate()))) {
			validationErrors
					.add("\n Leave start date should be after current date \n");
		}

		if (!(sufficientLeaveRemaining(bean.getDuration(),
				bean.getRemainingLeaves()))) {
			validationErrors
					.add("\n Leave duration must not exceed remaining leaves \n");
		}

		if (!validationErrors.isEmpty())
			throw new EMSException(validationErrors + "");
	}

	public boolean isValidStartDate(LocalDate startDate) {
		LocalDate date = LocalDate.now();
		return (!startDate.isBefore(date));
	}

	public boolean sufficientLeaveRemaining(int duration, int remainingLeaves) {
		return (remainingLeaves >= duration);
	}

	@Override
	public int getRemainingLeaves(String empId) throws EMSException {
		return dao.getRemainingLeaves(empId);
	}

	@Override
	public List<Leave> checkAppliedLeaves(String empId) throws EMSException {
		List<Leave> leaveList = new ArrayList<Leave>();
		try {
			leaveList = dao.checkAppliedLeaves(empId);
		} catch (EMSException e) {
			throw new EMSException ("Some error occured while checking leaves");
		}
		return leaveList;
	}

}
