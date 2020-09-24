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
import mov.sql.MovRevDAO;

@WebServlet("/movDetail.do")
public class MovDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Gdata = request.getParameter("idx");
		String starpoint = request.getParameter("point");
		String Date = request.getParameter("date");
		PrintWriter out = response.getWriter();
		
		if(Date==null||Date.length()==0)
			Date="20";
		
		MovDAO md = new MovDAO();
		MovRevDAO mrd = new MovRevDAO();
		MovLoginDAO mld = new MovLoginDAO();
		
		if(starpoint!=null&&!starpoint.equals("0")) {
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("LoginId");
			if(mld.dbIdCheck(id)!=1) {
				out.println("<script>");	
        out.println("alert('로그인이 필요합니다.'); location.href='Mov_Login.jsp';");
        out.println("</script>");
        return;
			}
			int code = mld.getUserCode(id);
			session.setAttribute("userCode", code);
			int point=Integer.parseInt(starpoint);
			md.starUpdate(point,code,Gdata);
		}
		
		request.setAttribute("dto", md.dbDetail(Gdata));
		request.setAttribute("list", md.movTimeTable(Gdata, Date));
		request.setAttribute("rv", mrd.dbRevSelect3(Gdata));
		request.setAttribute("schedule", md.selSchedule());
		
		RequestDispatcher dis = request.getRequestDispatcher("Mov_Detail.jsp");
		dis.forward(request, response);			
	}
}
