package com.hackerthon.service;

import java.util.ArrayList;

import com.hackerthon.model.Employee;

public interface EmployeeService {

	public void getEmpService();
	
	public void employeesFromXML();
	
	public void createEmployeeTable();
	
	public void addEmployees();
	
	public void getEmployeeById(String empId);
	
	public void deleteEmployee(String empId);
	
	public void displayEmployee();
	
	public void employeeOutput(ArrayList<Employee> empList);
	

}
