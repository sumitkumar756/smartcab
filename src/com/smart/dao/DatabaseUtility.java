package com.smart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smart.entity.Person;

public class DatabaseUtility {

	Connection connection = null;
	String drivername = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/smartcab";
	String user = "root";
	String password = "root";

	public Connection getMysqlConnection() {

		try {
			Class.forName(drivername);
			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public boolean register(Person person) {
		boolean registred=false;
		try{
		Connection connection = getMysqlConnection();
		String query = "insert into " + person.getType() + " (number,name) values(?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, person.getMobileNumber());
		statement.setString(2, person.getName());
		int i = statement.executeUpdate();
		if (i == 1) {
			registred= true;
		} 
	}catch(Exception exception){
		System.out.println("exception occured user not registered :"+exception.getMessage());
	}
	return registred;
	}

	public Person checkPerson(Person person) {
	
		try{
			Connection connection = getMysqlConnection();
			String query = "Select name from " + person.getType() + " where  number = ?";
			System.out.println("query :"+query);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, person.getMobileNumber());
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				System.out.println("user found");
				person.setName(rs.getString("name"));	
			}else{
				person = null;
			}
			
		}catch(Exception exception){
			System.out.println("exception occured user not found :"+exception.getMessage());
		}
		return person;
	}
	
}
