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

@WebServlet("/movreserveList.do")
public class MovReserveListController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String LoginId=(String)session.getAttribute("LoginId");
		String code =request.getParameter("code");
		String Rday = request.getParameter("Rday");
		String date = request.getParameter("date");
		PrintWriter out  =response.getWriter();
		System.out.println(code);
		System.out.println(Rday);
		System.out.println(date);
	if(LoginId !=null) {
		if(code==null &&Rday==null &&date==null) {
			MovDAO md = new MovDAO();
			ArrayList<MovDTO> reser= md.dbReserveList();
			request.setAttribute("reser", reser);
			
			RequestDispatcher dis = request.getRequestDispatcher("Mov_Reserve.jsp");
			dis.forward(request, response);
		}else if(code !=null && Rday==null &&date==null){		
			MovDAO md = new MovDAO();
			ArrayList<MovDTO> reser= md.dbReserveList();
			request.setAttribute("reser", reser);
			ArrayList<MovDTO> day= md.dbReserveDay(code);
			request.setAttribute("day", day);
			request.setAttribute("code", code);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_Reserve.jsp");
			dis.forward(request, response);
		}
		else if(code !=null && Rday !=null &&date ==null){
			MovDAO md = new MovDAO();
			ArrayList<MovDTO> reser= md.dbReserveList();
			request.setAttribute("reser", reser);
			ArrayList<MovDTO> day= md.dbReserveDay(code);
			request.setAttribute("day", day);
			ArrayList<MovDTO> time= md.dbReserveDaytime(code,Rday);
			request.setAttribute("time", time);
			request.setAttribute("code", code);
			request.setAttribute("Rday", Rday);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_Reserve.jsp");
			dis.forward(request, response);
		}
		else {
			MovDAO md = new MovDAO();
			MovDTO dto = new MovDTO();
			ArrayList<MovDTO> reser= md.dbReserveList();
			request.setAttribute("reser", reser);
			ArrayList<MovDTO> day= md.dbReserveDay(code);
			request.setAttribute("day", day);
			ArrayList<MovDTO> time= md.dbReserveDaytime(code,Rday);
			request.setAttribute("time", time);
			request.setAttribute("code", code);
			dto = md.dbReserveSeat(code, date);
			request.setAttribute("seat", dto);
			request.setAttribute("Rday", Rday);
			request.setAttribute("date", date);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_Reserve.jsp");
			dis.forward(request, response);
		}
	}else {
		out.println("<script>");
		out.println("alert('로그인 해주세요!'); location.href='Mov_Login.jsp';");
		out.println("</script>");
	       out.close();
	}
		
 	
	}

}
