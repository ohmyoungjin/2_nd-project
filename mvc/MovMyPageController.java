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

import mov.sql.MovLoginDAO;
import mov.sql.MovLoginDTO;

@WebServlet("/movMyPage.do")
public class MovMyPageController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}
	
	protected void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out  =response.getWriter();
		String id = request.getParameter("id");
		String birth = request.getParameter("birth");
		String tel = request.getParameter("tel");
		System.out.println("넘어온 아이디값 : "+ id);
		System.out.println("넘어온 생일값 : "+ birth);
		System.out.println("넘어온 전화번호값 : "+ tel);
		
		MovLoginDAO md = new MovLoginDAO();
		MovLoginDTO dto = new MovLoginDTO();
		if(id!=null && tel==null && birth==null) {
			System.out.println("마이페이지들어오면떠야돼");
			MovLoginDTO my = md.dbMyPage(id);
			request.setAttribute("my", my);
			request.setAttribute("id", id);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_MyPage.jsp");
			dis.forward(request, response);
			
		}
		else {
			System.out.println("수정누르면 떠야돼");
			md.dbEditMyPage(birth, tel, id);
			System.out.println("수정끝나고 셀렉트전에");
//			MovLoginDTO my = md.dbMyPage(id);
//			System.out.println("수정하고 셀렉트");
//			request.setAttribute("my", my);
			out.println("<script>");
			out.println("alert('수정이 성공했습니다!'); location.href='movMyPage.do?id="+id+"';");
			out.println("</script>");
		       out.close();
		         
//			RequestDispatcher dis = request.getRequestDispatcher("Mov_MyPage.jsp");
//			dis.forward(request, response);
			
		}
	}
}
