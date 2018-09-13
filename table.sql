
DROP TABLE user_master;
DROP TABLE Department;
DROP TABLE Leave_History;
DROP TABLE Employee;


create table User_Master(
UserName VARCHAR2(15), 
UserPassword VARCHAR2(50), 
UserType VARCHAR2(10),
userID VARCHAR2(6) PRIMARY KEY
);

insert into User_Master values('Rashmi','Rashmi123','admin','154472');
insert into User_Master values('Amir','Amir123','user','154474');
insert into User_Master values('Santhosh','Santhosh123','admin','154501');
insert into User_Master values('Hemanth','Hemanth123','user','154832');
insert into User_Master values('Lavyakant','Lavyakant123','admin','154470');
insert into User_Master values('Saisha','Saisha123','user','154575');
 
create table Department(Dept_ID int PRIMARY KEY,Dept_Name VARCHAR2(50));
 
create table Employee(
Emp_ID VARCHAR2(6) PRIMARY KEY, 
Emp_First_Name VARCHAR2(25), 
Emp_Last_Name VARCHAR2(25), 
Emp_Date_of_Birth DATE, 
Emp_Date_of_Joining DATE, 
Emp_Dept_ID int, 
Emp_Grade VARCHAR2(2), 
Emp_Designation VARCHAR2(50), 
Emp_Basic int, 
Emp_Gender VARCHAR2(11), 
Emp_Marital_Status VARCHAR2(11), 
Emp_Home_Address VARCHAR2(100), 
Emp_Contact_Num VARCHAR2(15), 
Mgr_Id varchar2(6), foreign key(Mgr_Id) references employee(emp_ID),
remaining_leaves number
);

 
create table Leave_History(
Leave_Id number PRIMARY KEY, 
Emp_id VARCHAR2(6), foreign key(Emp_id) references employee(emp_ID), 
leave_balance number check (leave_balance>=0),  
noofdays_applied number, 
date_from date, 
date_to date, 
status varchar2(20) check (status in ('applied','approved','rejected')),
mgr_id varchar2(10)
);


create sequence leave_id_sequence
start with 100
increment by 1
nocache;

INSERT INTO employee values('154575','Saisha','Batthula','27-OCT-1995','04-JUL-2018',102,'M5','Manager',90000,'F','U','Bangalore','9491043672','154575',12);
INSERT INTO employee values('154472','Rashmi','Priya','09-MAY-1995','04-JUL-2018',100,'M2','Analyst',24000,'F','U','Bangalore','8714333996','154575',12);
INSERT INTO employee values('154474','Amir','Khan','05-MAR-1996','04-JUL-2018',101,'M4','Consultant',55000,'F','U','Kolkata','8136912198','154575',12);
INSERT INTO employee values('154470','Lavyakant','Lavyakant','12-MAY-1996','04-JUL-2018',103,'M2','Analyst',24000,'F','U','Hyderabad','7736737369','154575',12);
INSERT INTO employee values('154501','Santosh','Sunkara','21-SEP-1996','04-JUL-2018',100,'M2','Analyst',24000,'M','U','Mumbai','9567037017','154575',12);
INSERT INTO employee values('154832','Hemanth','Reddy','10-MAY-1996','04-JUL-2018',102,'M2','Analyst',24000,'M','U','Bangalore','9491043672','154575',12);

INSERT INTO Department VALUES(100,'IT');
INSERT INTO Department VALUES(101,'Sales');
INSERT INTO Department VALUES(102,'LD');
INSERT INTO Department VALUES(103,'HR');
INSERT INTO Department VALUES(104,'FS');


desc leave_history;
desc user_master;
desc employee;
desc department;