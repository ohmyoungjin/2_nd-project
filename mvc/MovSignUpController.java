package mov.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mov.sql.MovLoginDAO;
import mov.sql.MovLoginDTO;

@WebServlet("/movSignUp.do")
public class MovSignUpController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}//GetEnd

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request, response);
	}//postEnd
	protected void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out  =response.getWriter();
		
		String Signid = request.getParameter("id");
		String SignPWD = request.getParameter("pwd");
		String SignNick = request.getParameter("nick");
		String SignTel = request.getParameter("tel");
		String Signaddr = request.getParameter("juso1");
		String Signaddr2 = request.getParameter("juso2");
		String SignEmail = request.getParameter("email");
		String SignBirth = request.getParameter("birth");
		
		
	
		MovLoginDTO MLDTO = new MovLoginDTO();
		MovLoginDAO MLDAO = new MovLoginDAO();
		
		MLDTO.setUserId(Signid);
		MLDTO.setUserPwd(SignPWD);
		MLDTO.setUserNick(SignNick);
		MLDTO.setUserTel(SignTel);
		MLDTO.setUserAddr(Signaddr);
		MLDTO.setUserAddr2(Signaddr2);
		MLDTO.setUserEmail(SignEmail);
		MLDTO.setUserBirth(SignBirth);
		int Mcnt = MLDAO.dbIdCheck(Signid);
		int Mcnt1 = MLDAO.dbNickCheck(SignNick);
		if(Mcnt ==0 && Mcnt ==0 ) {
			MLDAO.dbSignInsert(MLDTO);
			
			out.println("<script>");
			out.println("alert('회원가입이 성공했습니다!'); location.href='Mov_Login.jsp';");
			out.println("</script>");
			
			//response.sendRedirect("Mov_Login.jsp");
		}
		else {
			request.setAttribute("Mcnt", Mcnt);
			request.setAttribute("Mcnt1", Mcnt1);
			
			RequestDispatcher dis = request.getRequestDispatcher("Mov_SignCheck.jsp");
			dis.forward(request,response);
		}
		
		
	}//UserEnd
	
}//servlet end
