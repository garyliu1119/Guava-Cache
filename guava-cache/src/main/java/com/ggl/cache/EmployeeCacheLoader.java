package com.ggl.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.cache.CacheLoader;

public class EmployeeCacheLoader extends CacheLoader<String, Optional<Employee>>
{
	private static Logger LOG = LoggerFactory.getLogger(EmployeeCacheLoader.class);
	
	private EmployeeDao employeeDao;
	
	public EmployeeCacheLoader() {
		this.employeeDao = new EmployeeDao();
	}
	
	@Override
	public Optional<Employee> load(String name) throws Exception {
		LOG.info("Invoke load(): " + name);
		return Optional.fromNullable(employeeDao.getEmployeeByName(name));
	}

}
