package com.ggl.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;



public class CachingWithGuava {
	
	private static Logger LOG = LoggerFactory.getLogger(CachingWithGuava.class);
	
	private LoadingCache<String, Optional<Employee>> cache;
	
	private EmployeeDao employeeDao;
	
	public CachingWithGuava () {
		this.init();
	}
	
	private void init() {
		
		CacheLoader<String, Optional<Employee>> loader = new EmployeeCacheLoader();

		cache = CacheBuilder.
				newBuilder().
				maximumSize(1000).
				expireAfterWrite(1L, TimeUnit.DAYS).
				expireAfterAccess(1, TimeUnit.DAYS).
				build(loader);
		
		this.employeeDao = new EmployeeDao();
	}
	

	public void put(String name, Employee emp) 
	{
		this.cache.put(name, Optional.fromNullable(emp));
	}
	
	public Employee get(String name) throws ExecutionException {
		return this.cache.get(name).get();
	}
	
	public long size() {
		return this.cache.size();
	}
	
	public Optional<Employee> getObjectById(final String key) throws Exception {
		Optional<Employee> newEmployee = this.cache.get(key, new Callable<Optional<Employee>>() {
			@Override
			public Optional<Employee> call() throws Exception {
				Employee anEmp = employeeDao.getEmployeeByName(key);
				return Optional.fromNullable(anEmp);
			}
		});
		return newEmployee;
	}

	public void invalidAll() {
		this.cache.invalidateAll();
	}

	public static void main(String args []) {
		
		CachingWithGuava employeeCache = new CachingWithGuava();

		LOG.info("Initial Cache Size: " + employeeCache.size());
		
		employeeCache.put("Joshua", new Employee(1, "Joshua Wang", "Joshua Wang's address"));
	
		LOG.info("Employee Cache Size: " + employeeCache.size());

		employeeCache.put("Bill", new Employee(2, "Bill Clinton", "251 Pennsovinia Ave, Washington, DC, USA"));

		LOG.info("Employee Cache Size: " + employeeCache.size());
		
		try {
			
			Employee joshua = employeeCache.get("Joshua");
			
			LOG.info(joshua.toString());
			
			Employee bill = employeeCache.get("Bill");
			
			LOG.info(bill.toString());

			Employee gary = employeeCache.get("Gary");
			LOG.info(gary.toString());
			
			
			//Test get(K, Callable(V))
			gary = employeeCache.getObjectById("Gary").get();
			
			LOG.info(gary.toString());
			
			LOG.info("Employee Cache Size: " + employeeCache.size());
			
			employeeCache.invalidAll();

			LOG.info("Employee Cache Size after call invalidAll(): " + employeeCache.size());

		} catch (ExecutionException e) {
			
			e.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

}
