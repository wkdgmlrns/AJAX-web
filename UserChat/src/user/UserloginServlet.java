package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserloginServlet")
public class UserloginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		if ( userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "err");
			request.getSession().setAttribute("messageContent", "dbERR");
			response.sendRedirect("login.jsp");
			return;
		}
		int result = new UserDAO().login(userID, userPassword);
		request.getSession().setAttribute("result", result);
		
		  if (result == 1) { request.getSession().setAttribute("messageType",
		  "seccess"); request.getSession().setAttribute("messageContent", "seccess");
		  response.sendRedirect("index.jsp"); } 
		  else if (result == 2) {
		  request.getSession().setAttribute("messageType", "err");
		  request.getSession().setAttribute("messageContent", "NOT!password");
		  response.sendRedirect("login.jsp"); } 
		  else if (result == 0) {
		  request.getSession().setAttribute("messageType", "err");
		  request.getSession().setAttribute("messageContent", "NOT!userID");
		  response.sendRedirect("login.jsp"); } 
		  else if (result == -1) {
		  request.getSession().setAttribute("messageType", "err");
		  request.getSession().setAttribute("messageContent", "DBERR");
		  response.sendRedirect("login.jsp"); }
		
	}
}
