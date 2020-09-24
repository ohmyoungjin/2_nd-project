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

@WebServlet("/movReplyInsert.do")
public class MovReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Rdata = request.getParameter("revnum"); //revNum
		String content = request.getParameter("repcontent");
		//String movcode= request.getParameter("movcode");
		MovDAO md = new MovDAO();
		MovReplyDAO mpd=new MovReplyDAO();
		
		mpd.dbReplyInsert(Rdata,content);

		response.sendRedirect("movReplyList.do?idx="+Rdata);
		
		//RequestDispatcher dis = request.getRequestDispatcher("movrevList.do");
//		RequestDispatcher dis = request.getRequestDispatcher("movReplyList.do?idx="+Rdata);
//		dis.forward(request, response);
//		
 	
	}

}
