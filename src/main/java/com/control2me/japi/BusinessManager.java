package com.control2me.japi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.control2me.japi.services.v1.User;

public class BusinessManager {

	private static final Logger log = Logger.getLogger(BusinessManager.class.getName());
	
	
	private static BusinessManager INSTANCE = new BusinessManager();
	
	public static BusinessManager getInstance() {
		return INSTANCE;
	}

	private BusinessManager() {
		
	}
	
	public User findUser(String userId) throws Exception {
		
		log.info("BusinessManager::findUser started");
		

		User user = DataManager.getInstance().findUserById(userId);
		
		if (user == null) {
			throw new Exception("Nothing Found");
		}
		
		return user;
	}

	public List<User> findUsers() {
		
		
		return DataManager.getInstance().findAllUsers();

	}
	
	public User addUser(User user) {
		
		User newUser = DataManager.getInstance().insertUser(user);
		
		return newUser;
		
	}
	
	public User updateUserAttribute(String userId, String attribute, String value) {
				


		return DataManager.getInstance().updateUserAttribute(userId, attribute, value);
		
	}
	
	public void deleteUser(String userId) {
		
		return;
		
	}



	
}
