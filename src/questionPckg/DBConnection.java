package questionPckg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public static ResultSet rs;
	public static Statement stmt;
	public static Connection con;

	public DBConnection() {
		con = (Connection) MyDB.getConnection(); 
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Returns a string of the type question
	 * @return
	 */
	public String getType(int questionID) {
		String qType = "";
		try {
			rs = stmt.executeQuery("SELECT questionType FROM questionToType WHERE " +
					"questionID = " + questionID + ";");
		} catch (SQLException e) {
			System.out.println("Cannot query ID: " + questionID + " in questionToType table");
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				qType = rs.getString("questionType");
			}
		} catch (SQLException e) {
			System.out.println("Trouble parsing resultset from table");
			e.printStackTrace();
		}
		
		return qType;
	}


	/*
	public static void main(String[] args) {

		DBConnection myCon = new DBConnection();
		System.out.println("Should say mc: " + myCon.getType(2));
		
	}
	*/
}
