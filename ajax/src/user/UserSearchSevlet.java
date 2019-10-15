package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UserSearchSevlet")
public class UserSearchSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_Name = request.getParameter("user_Name");
		response.getWriter().write(getJSON(user_Name));
	}
	public String getJSON(String user_Name) {
		if(user_Name ==null) user_Name = "";
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		User_Dao userDao = new User_Dao();
		ArrayList<User_ajax> userList = userDao.search(user_Name);
		for(int i = 0; i<userList.size();i++) {
			result.append("[{\"value\": \"" + userList.get(i).getUser_Name() + "\"},");
			result.append("{\"value\": \""+userList.get(i).getUser_Name()+"\"},");
			result.append("{\"value\": \""+userList.get(i).getUser_Name()+"\"},");
			result.append("{\"value\": \""+userList.get(i).getUser_Name()+"\"}],");

		}
		result.append("]}");
		return result.toString();
	}
}
