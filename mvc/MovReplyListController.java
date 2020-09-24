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

@WebServlet("/movReplyList.do")
public class MovReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Gdata = request.getParameter("idx"); // gdata = revnum
		MovDAO md = new MovDAO();
		MovRevDAO mvd = new MovRevDAO();
		MovReplyDAO mpd = new MovReplyDAO();
		MovDTO rv=mvd.dbRevDetail(Gdata);
		String movName = mvd.dbMovNameN(Gdata);
		int movCode = mvd.dbMovCode(movName);
		ArrayList<MovDTO> rp=mpd.dbReplySelect(Gdata);
		
		
		request.setAttribute("movCode",movCode);
		request.setAttribute("movName",movName);
		request.setAttribute("rv", rv);
		request.setAttribute("rp", rp);
		
		//RequestDispatcher dis = request.getRequestDispatcher("movrevList.do");
		RequestDispatcher dis = request.getRequestDispatcher("Mov_ReplyList.jsp");
		dis.forward(request, response);
		
 	
	}

}
