package com.ggl.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeDao implements Serializable{
	private static ConcurrentHashMap<String, Employee> EmployDatabase;
	static {
		EmployDatabase = new ConcurrentHashMap<String, Employee>();
		EmployDatabase.put("Gary", new Employee(1L, "Gary Liu", "234 John Creek Road, Little Rock, AK, USA"));
		EmployDatabase.put("Anna", new Employee(2L, "Anna Carolina", "234 Anna John's Road, Little Rock, AK, Russia"));
		EmployDatabase.put("Peter", new Employee(3L, "Peter Smith", "589 Clifford Road, Mona Lisa City, MT, USA"));
		EmployDatabase.put("Amber", new Employee(4L, "Amber S Bush", "888 Green CT, Austin, TX, USA"));
	}
	
	public Employee getEmployeeByName (String name) {
		return EmployDatabase.get(name);
	}

}
