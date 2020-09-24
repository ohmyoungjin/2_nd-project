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
import mov.sql.MovReplyDAO;
import mov.sql.MovRevDAO;

@WebServlet("/movRevUpdate.do")
public class MovRevUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Gdata = request.getParameter("revnum");
		String title = request.getParameter("revtitle");
		String content = request.getParameter("revcontent");
		MovDAO md = new MovDAO();
		MovRevDAO mvd = new MovRevDAO();
		MovReplyDAO mpd = new MovReplyDAO();
		
		mvd.dbRevUpdate(Gdata,title,content);
		
		
		//RequestDispatcher dis = request.getRequestDispatcher("movrevList.do");
		RequestDispatcher dis = request.getRequestDispatcher("movReplyList.do?idx="+Gdata);
		dis.forward(request, response);
		
 	
	}

}
