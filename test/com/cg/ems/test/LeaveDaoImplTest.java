package com.cg.ems.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.ems.bean.Leave;
import com.cg.ems.dao.LeaveDaoImpl;
import com.cg.ems.exception.EMSException;

public class LeaveDaoImplTest {

static LeaveDaoImpl dao;
	
	@BeforeClass
	public static void initialize() {
		System.out.println("test");
		dao = new LeaveDaoImpl();
	}
	
	@Test
	public void testApplyLeave() throws EMSException {
		Leave leave=new Leave();
		leave.setEmployeeId("154472");
		leave.setRemainingLeaves(12);
		leave.setDuration(1);
		leave.setLeaveStatus("applied");
		String date="08-09-2018";
		DateTimeFormatter dateFormat = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

			leave.setStartdate(LocalDate.parse(date, dateFormat));
		
		try {
			int id=dao.applyLeave(leave);
			assertNotNull(id);
		} catch (EMSException exception) {
			fail(" exception occured "+exception.getMessage());
		}
	}

	@Test
	public void testleaveSanction() {
		Leave leave=new Leave();
		leave.setEmployeeId("154470");
		leave.setDuration(2);
		leave.setLeaveStatus("applied");
		
		try {
			boolean result=dao.leaveSanction(103,"approved" );
			//boolean result=dao.leaveSanction(103,"rejected");
			assertNotSame(result,false );
		} catch (EMSException exception) {
			fail(" exception occured "+exception.getMessage());
		}
		
	}

	
}
