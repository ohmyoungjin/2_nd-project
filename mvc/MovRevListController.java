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
import mov.sql.MovRevDAO;

@WebServlet("/movRevList.do")
public class MovRevListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String Gdata = request.getParameter("idx");//Rdata = movCode
		MovDAO md = new MovDAO();
		MovRevDAO mvd = new MovRevDAO();
		
		String pnum;
		int pageNUM, pagecount;
		int start, end;
		int startpage, endpage;
		int tmp;
		
		String sqry=" ";
		String skey="", sval="";
		String returnpage="";
		
		skey = request.getParameter("keyfield");
	    sval = request.getParameter("keyword");
	    if(skey=="" || skey==null || sval=="" || sval==null) {
	        skey="rev_title"; 
	   	 	sval="";
	    }
	    returnpage="&idx="+Gdata+"&keyfield="+skey+"&keyword="+sval;
	    
	    pnum = request.getParameter("pageNum");//14
		if(pnum==null||pnum=="") { pnum="1";}
		
		pageNUM=Integer.parseInt(pnum);
		start = (pageNUM-1)*10+1;//131
		end = pageNUM*10;//140
		
		tmp=(pageNUM-1)%10;
		startpage=pageNUM-tmp;//11
		endpage=startpage+9;//20
		
		int revTotal = mvd.dbRevCount(Gdata);
//		int revSTotal = mvd.dbRevSCount(Gdata, skey,sval);
		
		if(revTotal%10==0) {pagecount=revTotal/10;}
		else {pagecount = (revTotal/10)+1;}
		if(endpage>pagecount) {endpage=pagecount;}
		
		//System.out.println(Gdata);
		ArrayList<MovDTO> rv = mvd.dbRevSelect(start, end, skey, sval, Gdata);
		String movName = md.dbMovName(Gdata);
		request.setAttribute("movCode", Gdata);
		request.setAttribute("rv", rv);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("movName", movName);
		request.setAttribute("Gtotal", revTotal);
//		request.setAttribute("Atotal", revSTotal);
		request.setAttribute("pageNUM", pageNUM);
		
		request.setAttribute("returnpage", returnpage);
    request.setAttribute("skey", skey);
    request.setAttribute("sval", sval);
		
		//RequestDispatcher dis = request.getRequestDispatcher("movrevList.do");
		RequestDispatcher dis = request.getRequestDispatcher("Mov_RevList.jsp");
		dis.forward(request, response);
		
 	
	}

}
