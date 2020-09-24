package mov.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mov.sql.MovLoginDAO;
import mov.sql.MovLoginDTO;

@WebServlet("/MovLoginController.do")
public class MovLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}//GetEnd

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}//postEnd
	protected void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out  =response.getWriter();
		
		String LoginId = request.getParameter("username");
		String LoginPWD = request.getParameter("password");
		System.out.println(LoginId+"  "+LoginPWD);
		MovLoginDAO MLDAO = new MovLoginDAO();
		MovLoginDTO MLDTO = new MovLoginDTO();
	
	
		int Mcnt1 = MLDAO.dbLogin(LoginId,LoginPWD);
		if(Mcnt1>0) {
			HttpSession session = request.getSession();
		
		String LoginId1=(String)session.getAttribute("LoginId");
		session.setAttribute("LoginId", LoginId);
		System.out.println("첫번째 넘기는 세션" +LoginId1);
		RequestDispatcher dis = request.getRequestDispatcher("movMain.do");
		dis.forward(request,response);}
		else {
			response.sendRedirect("LoginCheck.jsp");
		}
	}//UserEnd
	
}//servlet end
