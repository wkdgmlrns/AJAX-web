package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegsterServlet")
public class UserRegsterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String user_Name = request.getParameter("user_Name");
		String user_Age = request.getParameter("user_Age");
		String user_Gender = request.getParameter("user_Gender");
		String user_Email = request.getParameter("user_Email");
		response.getWriter().write(register(user_Name,user_Age,user_Gender,user_Email)+"");
	}
	public int register(String user_Name, String user_Age,String user_Gender,String user_Email) {
		User_ajax user = new User_ajax();
		try {
			user.setUser_Name(user_Name);
			user.setUser_Age(Integer.parseInt(user_Age));
			user.setUser_Gender(user_Gender);
			user.setUser_Email(user_Email);	
		}catch(Exception e) {
			return 0;
			}
		int x = new User_Dao().regster(user);
		return x;
	}

}
