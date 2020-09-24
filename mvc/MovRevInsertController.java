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

@WebServlet("/movRevInsert.do")
public class MovRevInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		
		String movCode = request.getParameter("idx");//idx=movcode
		String title =request.getParameter("title");
		String content =request.getParameter("content");
		String file = request.getParameter("file");
		
		MovDAO md = new MovDAO();
		MovRevDAO mvd=new MovRevDAO();
		
		mvd.dbRevInsert(movCode,title,content);

		response.sendRedirect("movRevList.do?idx="+movCode);
		
		//RequestDispatcher dis = request.getRequestDispatcher("movrevList.do");
//		RequestDispatcher dis = request.getRequestDispatcher("movReplyList.do?idx="+Rdata);
//		dis.forward(request, response);
//		
 	
	}

}
