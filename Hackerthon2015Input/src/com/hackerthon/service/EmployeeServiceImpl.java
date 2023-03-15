package com.hackerthon.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hackerthon.common.UtilC;
import com.hackerthon.common.UtilQ;
import com.hackerthon.common.UtilTRANSFORM;
import com.hackerthon.model.Employee;

public class EmployeeServiceImpl extends UtilC implements EmployeeService{

	
	private final ArrayList<Employee> employees = new ArrayList<Employee>();
	
	private static Connection conn;

	private static Statement statement;

	private PreparedStatement  preparedStatement;

	final Logger logger = Logger.getLogger(EmployeeServiceImpl.class.getName());
	
	/* 
	 *Database Connection 
	 * */
	@Override
	public void getEmpService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
			logger.log(Level.INFO,"Database connected");
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Database connection error",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
	}
	
	/* 
	 *Get employees from XML file
	 * */
	@Override
	public void employeesFromXML() {
		try {
			
			for(Map<String, String> l :  UtilTRANSFORM.XMLXPATHS()) {
				Employee employee = new Employee();
				employee.setEmployeeId(l.get("XpathEmployeeIDKey"));
				employee.setFullName(l.get("XpathEmployeeNameKey"));
				employee.setAddress(l.get("XpathEmployeeAddressKey"));
				employee.setFacultyName(l.get("XpathFacultyNameKey"));
				employee.setDepartment(l.get("XpathDepartmentKey"));
				employee.setDesignation(l.get("XpathDesignationKey"));
				employees.add(employee);
				System.out.println(employee.toString() + "\n");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Employees load from XML is failed",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
		
	}
	
	/* 
	 *Create Employee Table
	 * */
	@Override
	public void createEmployeeTable() {
		try {
			statement = conn.createStatement();
			statement.executeUpdate(UtilQ.Q("q2"));
			statement.executeUpdate(UtilQ.Q("q1"));
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Employee table creation is failed",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
	}
	
    /* 
	 *Add Employees
	 * */
	@Override
	public void addEmployees() {
		
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q3"));
			conn.setAutoCommit(false);
			for(Employee employee : employees) {
				preparedStatement.setString(1, employee.getEmployeeId());
				preparedStatement.setString(2, employee.getFullName());
				preparedStatement.setString(3, employee.getAddress());
				preparedStatement.setString(4, employee.getFacultyName());
				preparedStatement.setString(5, employee.getDepartment());
				preparedStatement.setString(6, employee.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			conn.commit();
		}  catch (SQLException e) {
			logger.log(Level.SEVERE,"Employee adding is failed",e);
	       e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
		
	}
	
	 /* 
	  *Get employees by Id
	  * */
	@Override
	public void getEmployeeById(String empId) {
		
		Employee employee = new Employee();
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q4"));
			preparedStatement.setString(1, empId);
			ResultSet R = preparedStatement.executeQuery();
			while (R.next()) {
				 employee.setEmployeeId(R.getString(1));
				 employee.setFullName(R.getString(2));
				 employee.setAddress(R.getString(3));
				 employee.setFacultyName(R.getString(4));
				 employee.setDepartment(R.getString(5));
				 employee.setDesignation(R.getString(6));
			}
			ArrayList<Employee> empList = new ArrayList<Employee>();
			empList.add(employee);
			 employeeOutput(empList);
		}  catch (SQLException e) {
			logger.log(Level.SEVERE,"Get employee by id is failed",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 	
	}
	
	/* 
	  *Delete Employees
	  * */

	@Override
	public void deleteEmployee(String empId) {
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q6"));
			preparedStatement.setString(1, empId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,"Delete employee is failed",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 	
	}

	/* 
	  *Display Employees
	  * */
	@Override
	public void displayEmployee() {
		
		ArrayList<Employee> empList = new ArrayList<Employee>();
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q5"));
			ResultSet r = preparedStatement.executeQuery();
			while (r.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(r.getString(1));
				employee.setFullName(r.getString(2));
				employee.setAddress(r.getString(3));
				employee.setFacultyName(r.getString(4));
				employee.setDepartment(r.getString(5));
				employee.setDesignation(r.getString(6));
				empList.add(employee);
			}
		}  catch (SQLException e) {
			logger.log(Level.SEVERE,"Display employee is failed",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
		 employeeOutput(empList);	
	}

	/* 
	  *Employee output
	  * */
	@Override
	public void employeeOutput(ArrayList<Employee> empList) {
	
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
	    
		for(Employee employee : empList){
	    	System.out.println(employee.getEmployeeId() + "\t" + employee.getFullName() + "\t\t"
					+ employee.getAddress() + "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
					+ employee.getDesignation() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		
	    }	
	}
}
