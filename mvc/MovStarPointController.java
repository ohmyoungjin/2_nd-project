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
import mov.sql.MovRevDAO;

@WebServlet("/movStar.do")
public class MovStarPointController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Gdata = request.getParameter("idx");
		String Date = request.getParameter("date");
		if(Date==null||Date.length()==0)
			Date="20";
		
		MovDAO md = new MovDAO();
		MovRevDAO mrd = new MovRevDAO();
		MovDTO dto = md.dbDetail(Gdata);		
		ArrayList<MovDTO> list =md.movTimeTable(Gdata, Date);	
    ArrayList<MovDTO> rv=mrd.dbRevSelect3(Gdata);
		
		request.setAttribute("dto", dto);
		request.setAttribute("list", list);
		request.setAttribute("rv", rv);
		
		RequestDispatcher dis = request.getRequestDispatcher("Mov_Detail.jsp");
		dis.forward(request, response);			
	}
}
