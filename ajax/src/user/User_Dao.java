package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class User_Dao {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public User_Dao() {
		try {
			/*
			Context iniCtx = new InitialContext();
			Context envCtx = (Context)iniCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/oracle");
			con = ds.getConnection();
			*/
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@dalma.dongguk.ac.kr:1521:stud1","wkd123","wkd123");
	}catch(Exception e) {e.printStackTrace();}
	}
	public ArrayList<User_ajax> search(String user_Name){
		String sql = "SELECT * FROM use_ajax WHERE user_name = ?";
		ArrayList<User_ajax> list = new ArrayList<User_ajax>();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,user_Name);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				User_ajax user = new User_ajax();
				user.setUser_Name(rs.getString(1));
				user.setUser_Age(rs.getInt(2));
				user.setUser_Gender(rs.getString(3));
				user.setUser_Email(rs.getString(4));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}
	public int regster(User_ajax user) {
		String sql = "INSERT INTO use_ajax VALUES(?,?,?,?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUser_Name());
			pstmt.setInt(2, user.getUser_Age());
			pstmt.setString(3, user.getUser_Gender());
			pstmt.setString(4, user.getUser_Email());
			pstmt.executeUpdate();
			return 1;//성공
		}catch(Exception e) {e.printStackTrace();}
		return -1;//DB오류
	}
}
