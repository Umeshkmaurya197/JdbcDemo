package com.JdbcDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcDemo {

	static Scanner scannerStr = new Scanner(System.in);
	static Scanner scannerInt = new Scanner(System.in);
	static Connection con;
	Statement stmt;
	static boolean tableFlag = false;
	ResultSet resultSet;

	public void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcdemo", "root", "root");

			System.out.println("Connection created successfully ....");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTable() {

		try {
			String query = "create table student(studentId int(10) primary key auto_increment, studentName varchar(30) not null ,studentCity varchar(50) )";

			stmt = con.createStatement();

			stmt.executeUpdate(query);

			System.out.println("table created successfully in database ....");
			tableFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertData() {
		char ch = 'n';
		while (ch != 'y') {

			System.out.println("\n Enter 1 to insert student data \n Enter 2 to exit  ");
			System.out.print(" Enter your choice : ");
			int input = scannerInt.nextInt();
			switch (input) {
			case 1:
				try {
					String query = "insert into student(studentName , studentCity ) values(? , ? )";

					PreparedStatement stmt = con.prepareStatement(query);

					System.out.print("\n Enter the name : ");
					stmt.setString(1, scannerStr.nextLine());

					System.out.print(" Enter the city : ");
					stmt.setString(2, scannerStr.nextLine());

					stmt.executeUpdate();

					System.out.println(" Data successfully inserted into the table ....");

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("____________exited _____________");
				ch = 'y';
				break;
			default:
				System.out.println("____________wrong input__________");
				break;
			}
		}

	}

	public void showData() {

		try {
			String query = "select * from student  ";
			PreparedStatement stmt = con.prepareStatement(query);
			resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {

				int id = resultSet.getInt("studentId");
				String name = resultSet.getString("studentName");
				String city = resultSet.getString("studentCity");
				System.out.println(id + "  --------> " + name + "  --------> " + city);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDatabaseConnection() {
		try {
			con.close();
			System.out.println("\n_______ Connection closed _________ ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcDemo jdbc = new JdbcDemo();

		jdbc.createConnection();

		if (tableFlag == true) {
			jdbc.createTable();
		} else {
			System.out.println("________Enter the student data________ ");
			jdbc.insertData();
			jdbc.showData();
			jdbc.closeDatabaseConnection();
		}

	}
}
