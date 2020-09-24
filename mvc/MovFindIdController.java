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

@WebServlet("/MovFindId.do")
public class MovFindIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
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
		String tel = request.getParameter("idx");
		String id = request.getParameter("id");
		MovLoginDAO dao = new MovLoginDAO();
		if(id==null) {
			dao.dbFindId(tel);
		    String useridJson = "";     
	         useridJson =   useridJson+               
	       "{\"userid\":\""+dao.dbFindId(tel)+"\"}";
		       System.out.println(useridJson);
			 try {
		          response.getWriter().print(useridJson);
		      } catch (IOException e) {
		          e.printStackTrace();
		      }   
		}else {
			dao.dbFindpwd(id, tel);
			String userpwdJson ="";
			userpwdJson = userpwdJson+
			"{\"userpwd\":\""+dao.dbFindpwd(id,tel)+"\"}";
			System.out.println(userpwdJson);
			try {
		          response.getWriter().print(userpwdJson);
		      } catch (IOException e) {
		          e.printStackTrace();
		      }  
		}
		
	}//UserEnd
	
}//servlet end
