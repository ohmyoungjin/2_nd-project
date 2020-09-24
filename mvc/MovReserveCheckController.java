package mov.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mov.sql.MovDAO;
import mov.sql.MovDTO;

@WebServlet("/movReserveCk.do")
public class MovReserveCheckController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}
	
	protected void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out  =response.getWriter();
		//String LoginId2 = request.getParameter("id");
		HttpSession session = request.getSession();
		String LoginId=(String)session.getAttribute("LoginId");
		System.out.println("11111111넘어온값입니다 : "+LoginId);
	
		MovDAO md = new MovDAO();
		
		if(LoginId==null) {
			out.println("<script>");
			out.println("alert('로그인 해주세요!'); location.href='Mov_Login.jsp';");
			out.println("</script>");
			out.close();
		}else {
			ArrayList<MovDTO> Chlist=md.dbReserveCheck(LoginId);
			request.setAttribute("Chlist", Chlist);
			request.setAttribute("id", LoginId);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_ReserveCheckPage.jsp");
			dis.forward(request, response);
	
			
			
		}
	}
}
