package com.hackerthon.main;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hackerthon.common.UtilTRANSFORM;
import com.hackerthon.service.EmployeeServiceImpl;

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final Logger logger = Logger.getLogger(ExecuteMain.class.getName());
		 EmployeeServiceImpl employeeService = new  EmployeeServiceImpl();
		 
		try {
			UtilTRANSFORM.rEQUESTtRANSFORM();
			employeeService.employeesFromXML();
			employeeService.employeesFromXML();
			employeeService.createEmployeeTable();
			employeeService.addEmployees();
		    employeeService.getEmployeeById("EMP10004");
		    employeeService.deleteEmployee("EMP10001");
			employeeService.displayEmployee();
		}  catch (SQLException e) {
			logger.log(Level.SEVERE,"SQL error",e);
			e.printStackTrace();
		} catch (Exception e)  {
			logger.log(Level.SEVERE,"Common error",e);
		    e.printStackTrace();
		} 
	}

}
