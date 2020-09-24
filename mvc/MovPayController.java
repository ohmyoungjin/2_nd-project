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

@WebServlet("/movPay.do")
public class MovPayController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		System.out.println("movPay.do 도착");
		String array =request.getParameter("peopleArr");			
		String movType = request.getParameter("movType");
		String movScheduleCode = request.getParameter("movScheduleCode");
		String movDetailCode = request.getParameter("movDetailCode");
		String movTheater = request.getParameter("movTheater");
		String movIdList = request.getParameter("movIdList");		
		
		MovDAO dao = new MovDAO();
		String perType;
		int sum=0;		
		
		String arr[]=array.split(",");		
		int nums[]=new int[arr.length];
		for(int i=0;i<nums.length;i++) {
			nums[i]=Integer.parseInt(arr[i]);
			if(nums[i]>0) {
					switch(i) {
						case 0:
								perType="성인";
								break;
						case 2:
								perType="청소년";
								break;
						case 4:
								perType="장애인";
								break;
						default:
								perType="노약자";
								break;
					}
					sum+=dao.movTotalPaySel(movType, perType, nums[i]);
			}
		}
		request.setAttribute("totalPay", sum);
		request.setAttribute("array", array);	
		request.setAttribute("movType", movType);	
		request.setAttribute("movScheduleCode", movScheduleCode);	
		request.setAttribute("movDetailCode", movDetailCode);	
		request.setAttribute("movTheater", movTheater);
		request.setAttribute("movIdList", movIdList);
		
		RequestDispatcher dis = request.getRequestDispatcher("Mov_Pay.jsp");
		dis.forward(request, response);			
	}	
}
