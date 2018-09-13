package com.cg.ems.dao;

public interface IQueryMapper {

	public static final String GET_USER = "SELECT * FROM User_Master WHERE username=?";
	public static final String LEAVE_REQUEST_QUERY = "INSERT INTO leave_history VALUES(leave_id_sequence.NEXTVAL,?,?,?,?,?,?,?)";
	public static final String LEAVE_SANCTION_QUERY = "UPDATE Leave_History SET status = ? WHERE leave_id = ?";
	public static final String LEAVEID_QUERY_SEQUENCE = "SELECT leave_id_sequence.CURRVAL FROM DUAL";
	public static final String RETRIEVE_APPLIED_LEAVE_QUERY = "SELECT Leave_id,Emp_id,date_from,(date_to - date_from),leave_balance ,status FROM Leave_History WHERE status = 'applied'";
	public static final String UPDATE_REMAINING_LEAVES_QUERY = "UPDATE Employee SET remaining_leaves = (remaining_leaves - ?) WHERE emp_id = ?";
	public static final String GET_EMPID_BY_LEAVE_ID_QUERY = "SELECT emp_id, (date_to - date_from) FROM Leave_History WHERE leave_id = ?";
	public static final String RETRIEVE_APPLIED_LEAVE_BY_EMPID_QUERY = "SELECT Leave_id,Emp_id,date_from,(date_to - date_from),leave_balance ,status FROM Leave_History WHERE Emp_Id = ?";
	public static final String GET_LEAVE_BY_ID = "SELECT remaining_leaves FROM Employee WHERE Emp_id=?";
	public static final String GET_REMAINING_BY_EMPID = "SELECT leave_balance FROM LEAVE_HISTORY WHERE Leave_id=?";
	public static final String UPDATE_LEAVE_BALANCE = "UPDATE Leave_History set leave_balance = leave_balance-? WHERE Leave_id = ?";

	public static final String ADD_EMPLOYEE = "INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String RETRIEVE_ALL_DETAILS = "SELECT * FROM Employee";
	public static final String SELECT_BY_ID = "SELECT * FROM Employee WHERE Emp_ID=?";
	public static final String SELECT_BY_FNAME = "SELECT * FROM Employee WHERE Emp_First_Name=?";
	public static final String SELECT_BY_LNAME = "SELECT * FROM Employee WHERE Emp_Last_Name=?";
	public static final String SELECT_BY_DEPT_NAME = "SELECT * FROM Employee WHERE Emp_Dept_ID = (SELECT Dept_ID FROM Department WHERE Dept_NAME=?)";
	public static final String SELECT_BY_GRADE = "SELECT * FROM Employee WHERE Emp_Grade=?";
	public static final String SELECT_BY_MARITAL_STATUS = "SELECT * FROM Employee WHERE Emp_Marital_Status=?";

	public static final String UPDATE_EMPLOYEE_BY_ID = "UPDATE Employee SET Emp_First_Name=?, Emp_Last_Name=?,Emp_Dept_ID=?,Emp_Grade=?,Emp_Designation=?,Emp_Basic=?,Emp_Marital_Status=?,Emp_Home_Address=?,Emp_Contact_Num=?,Mgr_Id=? WHERE Emp_ID=?";

	public static final String ADD_USER_DETAILS = "INSERT INTO USER_MASTER VALUES (?,?,'user',?)";
}
