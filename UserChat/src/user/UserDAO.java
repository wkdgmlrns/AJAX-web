package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserDAO {
	DataSource dataSource;
	public UserDAO() {
		try {
			InitialContext iniCtx = new InitialContext();
			Context envContext = (Context)iniCtx.lookup("java:comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/UserChat");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int login(String userID,String userPassword) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql = "SELECT * FROM userchat WHERE userid = ?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				/*if(rs.getString(1).equals(userPassword)) {*/
			return 1; //로그인 성공
				/*
				 * } return 2;
				 */ //비밀번호 오류
			}else {return 0;}//아이디 없음
		} catch(SQLException e) {e.printStackTrace();}
		finally {
			if(rs!=null) {
				try{rs.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(pstmt!=null) {
				try{pstmt.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(con!=null) {
				try{con.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
		}
		return 1;//DB오류
	}
	public int registerCheck(String userID) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql = "SELECT * FROM userchat WHERE userid = ?";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals("")) {
				return 0; //이미존재하는 회원
			}else {return 1;}//가입가능
		} catch(SQLException e) {e.printStackTrace();}
		finally {
			if(rs!=null) {
				try{rs.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(pstmt!=null) {
				try{pstmt.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(con!=null) {
				try{con.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
		}
		return-1;//DB오류
	}
	public int register(String userID,String userPassword, String userName, String userAge, String userEmail, String userGender,String userProfile) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql = "INSERT INTO userchat VALUES(?,?,?,?,?,?,?)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			return pstmt.executeUpdate();
		} catch(SQLException e) {e.printStackTrace();}
		finally {
			if(pstmt!=null) {
				try{pstmt.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(con!=null) {
				try{con.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
		}
		return-1;//DB오류
	}
	public int register(String userID,String userPassword, String userName, String userAge, String userEmail, String userGender) {
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql = "INSERT INTO userchat(userid,userpassword,username,userage,usergender,useremail) VALUES(?,?,?,?,?,?)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.executeUpdate();
			return 1;
		} catch(SQLException e) {e.printStackTrace();}
		finally {
			if(pstmt!=null) {
				try{pstmt.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
			if(con!=null) {
				try{con.close();
			}
			catch(SQLException e) {e.printStackTrace();}}
		}
		return-1;//DB오류
	}
}
