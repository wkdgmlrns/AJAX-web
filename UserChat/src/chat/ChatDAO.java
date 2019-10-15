package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ChatDAO {
	DataSource dataSource;

	public ChatDAO() {
		try {
			InitialContext iniCtx = new InitialContext();
			Context envContext = (Context) iniCtx.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID){
		ArrayList<ChatDTO> chatList = null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("CHATID"));
				chat.setFromID(rs.getString("FROMID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));//스크립트 해킹의 방어를 위해서
				chat.setToID(rs.getString("TOID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("CHATCONTENT").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("CHATTIME").substring(11,13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0,11) + " " + timeType + " " + rs.getString("chatTime").substring(14,16));
				chatList.add(chat);
			}
		}catch(SQLException e) {e.printStackTrace();}
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
		return chatList;
	}
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
		ArrayList<ChatDTO> chatList = null;
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql = "SELECT * FROM CHAT WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (SELECT MAX(chatID) - ? FROM chat) ORDER BY chatTime";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("CHATID"));
				chat.setFromID(rs.getString("FROMID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));//스크립트 해킹의 방어를 위해서
				chat.setToID(rs.getString("TOID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("CHATCONTENT").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("CHATTIME").substring(11,13));
				String timeType = "오전";
				if(chatTime >= 12) {
					timeType = "오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0,11) + " " + timeType + " " + rs.getString("chatTime").substring(14,16));
				chatList.add(chat);
			}
		}catch(SQLException e) {e.printStackTrace();}
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
		return chatList;
	}
	public int submit(String fromID, String toID, String chatContent){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql = "INSERT INTO chat values (chat_seq.Nextval, ?, ?, ?, SYSDATE)";
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			int x = pstmt.executeUpdate();
			return x;
			
		}catch(SQLException e) {e.printStackTrace();}
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
		return -1; //db오류
	}
}
