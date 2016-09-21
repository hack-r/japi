package com.control2me.japi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.control2me.japi.services.v1.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class DataManager {

	private static final Logger log = Logger.getLogger(DataManager.class.getName());
	
	
	private static DB japiDB;
	
	private static DBCollection userCollection;

	
	private static DataManager INSTANCE;
	
	public static DataManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DataManager();
		}

		return INSTANCE;
	}

	private DataManager() {
		
		try {
			MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017)); //default port for MongoDB
			
			japiDB = mongoClient.getDB("japi");
			
			userCollection = japiDB.getCollection("users"); //collection is essentially a JSON document, no concept of tables in MongoDB
			

		}
		catch (Exception e) {
			log.error("db connection error e=", e);
		}

	}
	
	// Insert User into database
	public User insertUser(User user) {
		
		// Get a document object
		BasicDBObject doc = new BasicDBObject();

		// Add in name
		doc.put("name", user.getName());

		// Insert document into users collection
		userCollection.insert(doc);
		
		// Put new id into user object
		user.setId(doc.get("_id").toString());
		
		// return new object
		return user;
		
		
	}

	
	public User mapUserFromdDBObject(DBObject dbObject) {

		User user = new User();

		user.setId(dbObject.get("_id").toString());

		user.setName((String) dbObject.get("name"));

		return user;
	}
	

	// Find User by Id
	public User findUserById(String userIdString) {
		
		if (userIdString == null)
			return null;

	
		try {
			DBObject searchById = new BasicDBObject("_id", new ObjectId(userIdString));

			DBObject userObject = userCollection.findOne(searchById);

			if (userObject != null) {
				return mapUserFromdDBObject(userObject);
			} else {
				return null;
			}

		} catch (Exception e) {
			log.error("DBManager::findUserById Exception e=", e);
		}

		return null;
	
	
	}
	
	public List<User> findAllUsers() {

		List<User> users = new ArrayList<User>();

		try {


			DBCursor cursor = userCollection.find();

			if (cursor != null) {

				while (cursor.hasNext()) {

					BasicDBObject doc = (BasicDBObject) cursor.next();

					User item = mapUserFromdDBObject(doc);

					users.add(item);
				}

				return users;
			}

			return null;
		} catch (Exception e) {

		}

		return null;
	}

	
	public User updateUserAttribute(String userId, String attribute,
			String value) {

		String updateValue = value;

		BasicDBObject doc = new BasicDBObject();

		doc.append("$set", new BasicDBObject().append(attribute, updateValue));

		DBObject searchById = new BasicDBObject("_id", new ObjectId(userId));

		userCollection.update(searchById, doc);


		return findUserById(userId);
	}

	
}
