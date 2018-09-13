package com.cg.ems.dao;

import java.util.List;

import com.cg.ems.bean.Leave;
import com.cg.ems.exception.EMSException;

public interface ILeaveDao {
	public int applyLeave(Leave leave) throws EMSException;

	public boolean leaveSanction(int leaveId, String status)
			throws EMSException;

	public List<Leave> viewLeaveApplications() throws EMSException;

	public int getRemainingLeaves(String empId) throws EMSException;

	public List<Leave> checkAppliedLeaves(String empId) throws EMSException;

}
