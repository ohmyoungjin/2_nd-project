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
		System.out.println("�Ѿ�� ���̵� : "+ id);
		System.out.println("�Ѿ�� ���ϰ� : "+ birth);
		System.out.println("�Ѿ�� ��ȭ��ȣ�� : "+ tel);
		
		MovLoginDAO md = new MovLoginDAO();
		MovLoginDTO dto = new MovLoginDTO();
		if(id!=null && tel==null && birth==null) {
			System.out.println("���������������鶰�ߵ�");
			MovLoginDTO my = md.dbMyPage(id);
			request.setAttribute("my", my);
			request.setAttribute("id", id);
			RequestDispatcher dis = request.getRequestDispatcher("Mov_MyPage.jsp");
			dis.forward(request, response);
			
		}
		else {
			System.out.println("���������� ���ߵ�");
			md.dbEditMyPage(birth, tel, id);
			System.out.println("���������� ����Ʈ����");
//			MovLoginDTO my = md.dbMyPage(id);
//			System.out.println("�����ϰ� ����Ʈ");
//			request.setAttribute("my", my);
			out.println("<script>");
			out.println("alert('������ �����߽��ϴ�!'); location.href='movMyPage.do?id="+id+"';");
			out.println("</script>");
		       out.close();
		         
//			RequestDispatcher dis = request.getRequestDispatcher("Mov_MyPage.jsp");
//			dis.forward(request, response);
			
		}
	}
}
