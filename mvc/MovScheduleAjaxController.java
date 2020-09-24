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
import mov.sql.MovLoginDAO;

@WebServlet("/movScheduleAjax.do")
public class MovScheduleAjaxController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String Gdata = request.getParameter("idx");
		String Date = request.getParameter("date");
		System.out.println(Gdata);
		System.out.println(Date);
		MovDAO md = new MovDAO();
		ArrayList<MovDTO> list = md.movTimeTable(Gdata, Date);
		
		String scheduleJson = "["; 
		for(int i=0;i<list.size();i++) {
			scheduleJson =	scheduleJson+					
					"{\"theaterCode\":\""+list.get(i).getTheaterCode()
			    +"\",\"movType\":\""+list.get(i).getMovType()
			    +"\",\"scheduleMonth\":\""+list.get(i).getScheduleMonth()
			    +"\",\"scheduleDate\":\""+list.get(i).getScheduleDate()
			    +"\",\"scheduleTime\":\""+list.get(i).getScheduleTime()
			    +"\",\"seat\":\""+list.get(i).getCountSeat()+"\"}";
			if(list.size()>0&&i<list.size()-1) {
				scheduleJson=scheduleJson+",";
			}
		}
		scheduleJson=scheduleJson+"]";
//			System.out.println(scheduleJson);
		try {
	       response.getWriter().print(scheduleJson);
	   } catch (IOException e) {
	       e.printStackTrace();
	   }   				
	}
}
