package com.cg.ems.service;

import java.util.List;

import com.cg.ems.bean.Leave;
import com.cg.ems.exception.EMSException;

public interface ILeaveService {
	public int applyLeave(Leave leave) throws EMSException;

	public boolean leaveSanction(int leaveId, String status)throws EMSException;

	public List<Leave> viewLeaveApplications()throws EMSException;

	public void validateLeave(Leave bean) throws EMSException;

	public int getRemainingLeaves(String empId) throws EMSException;

	public List<Leave> checkAppliedLeaves(String empId)throws EMSException;
}
