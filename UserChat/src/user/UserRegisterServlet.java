package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword1 = request.getParameter("userPassword1");
		String userPassword2 = request.getParameter("userPassword2");
		String userName = request.getParameter("userName");
		String userAge = request.getParameter("userAge");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		//String userProfile = request.getParameter("userProfile");
		if(userID==null || userID.equals("") || userPassword1==null || userPassword1.equals("") || userPassword2==null || userPassword2.equals("")
				||  userName==null || userName.equals("") || userAge==null || userAge.equals("") || userGender==null || userGender.equals("")
				|| userEmail==null || userEmail.equals("") ) {
			request.getSession().setAttribute("messageType", "err");
			request.getSession().setAttribute("messageContent", "inputcontent");
			response.sendRedirect("join.jsp");
			return;
		} else if(!userPassword1.equals(userPassword2)) {
			request.getSession().setAttribute("messageType", "err");
			request.getSession().setAttribute("messageContent", "notpass");
			response.sendRedirect("join.jsp");
			return;
		}else {
			int result= new UserDAO().register(userID, userPassword1, userName, userAge, userEmail, userGender);
			if(result==1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "success");
			request.getSession().setAttribute("messageContent", "success");
			response.sendRedirect("index.jsp");
			return;
			}else {
			request.getSession().setAttribute("messageType", "err");
			request.getSession().setAttribute("messageContent", "illgar");
			response.sendRedirect("index.jsp");
			return;
			}
		}
	}
}
