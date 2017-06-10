package com.db;



import org.json.JSONObject;

import com.model.Employee;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class TempDB {
	
	public static DBCollection getConnection()
	{
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("EmpDB");
			DBCollection coll = db.getCollection("EmpTable");
			return coll;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return null;
		
	}

	public static boolean saveData(Employee emp)
	{
		try{
				DBCollection coll = getConnection();
				if(coll!=null)
				{
					BasicDBObject doc = new BasicDBObject("name", emp.getName()).append("email", emp.getEmail()).append("mobile",emp.getMobile()).append("password",emp.getPassword());
					coll.insert(doc);
					return true;
				}
				else
				{
					return false;
				}
			}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return false;
		
	}
	
	public static JSONObject getData(String email, String password)
	{
		try{
			DBCollection coll = getConnection();
			DBCursor cursor = coll.find();
			while (cursor.hasNext()) 
			{ 
				DBObject ss=cursor.next();
		        String serialize = JSON.serialize(ss);
				JSONObject emp=new JSONObject(serialize);
				
				if(email.equals(emp.getString("email")) && password.equals(emp.getString("password")))
				{
					return emp;
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return null;
		
	}
}
