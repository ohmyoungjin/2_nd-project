package mov.mvc;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mov.sql.MovDAO;
import mov.sql.MovDTO;

@WebServlet("/movSeatChoose.do")
public class MovSeatChooseController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}
	
	protected void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		MovDAO md = new MovDAO();
		String schedule = request.getParameter("schedule");
		String detail = request.getParameter("detail");
		request.setAttribute("dto",md.movScheduleSel(schedule, detail));
		request.setAttribute("schedule",schedule);
		request.setAttribute("detail",detail);
		
		RequestDispatcher dis = request.getRequestDispatcher("Mov_SeatChoose.jsp");
		dis.forward(request, response);
	}
}
