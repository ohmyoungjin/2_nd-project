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
import mov.sql.MovReserveDAO;

@WebServlet("/movReserveInsert.do")
public class MovReserveInsertController extends HttpServlet {

	MovReserveDAO redao = new MovReserveDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doUser(request,response);
	}
	
	public void doUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		System.out.println("movReserveInsert.do ����");
		String array =request.getParameter("peopleArr");	//��� �� �迭		
		String movType = request.getParameter("movType");// ��ȭŸ�� 2D
		String movScheduleCode = request.getParameter("movScheduleCode");//�������ڵ�
		String movDetailCode = request.getParameter("movDetailCode");// �������ڵ�
		String movTheater = request.getParameter("movTheater");// �� ��ȣ
		String movSeatList=request.getParameter("movIdList");// �¼� ����Ʈ
		String userCode = (String) session.getAttribute("LoginId");
		
//		System.out.println("array : " + array);
//		System.out.println("movType : " + movType);
//		System.out.println("movScheduleCode : " + movScheduleCode);
//		System.out.println("movDetailCode : " + movDetailCode);
//		System.out.println("movTheater : " + movTheater);
//		System.out.println("movSeatList : " + movSeatList);
//		System.out.println("userCode : " + userCode);
		
//		System.out.println("-------------------------------------");
		
		MovLoginDAO mld = new MovLoginDAO();
		int usercode = 0;
		if(mld.getUserCode(userCode)!=0) {
			usercode=mld.getUserCode(userCode);
		}
		int sum = peopleSum(array);
		String[] priceCodes = selPriceCode(movType, array);
		String[] bookedSeatsCodes = selBookedSeatCode(movScheduleCode, movTheater, movSeatList);
		String[] perTypes = selPersonType(movTheater, array);
		
//		System.out.println("�����ڵ� ���� : "+priceCodes.length);
//		System.out.println("�¼����� ���� : "+bookedSeatsCodes.length);
//		System.out.println("���Ÿ�� ���� : "+perTypes.length);
		
		for(int i=0;i<sum;i++) {
			redao.movReserveInsert(movDetailCode,movScheduleCode
					,usercode,bookedSeatsCodes[i],priceCodes[i],movTheater,perTypes[i]);
		}
		
		RequestDispatcher dis = request.getRequestDispatcher("movMain.do");
		dis.forward(request, response);			
	}
	
	//�� ���¼��� �����ߴ���
	public int peopleSum(String array) {
		
		if(array.length()==0||array==null)
			return 0;
		
		int sum=0;
		String peopleArr[]=array.split(",");
		for(int i=0;i<peopleArr.length;i++) {
			sum+=Integer.parseInt(peopleArr[i]);
		}		
		
		return sum;
	}
	
	//��� Ÿ�� �迭 �̱�
	public String[] selPersonType(String movTheater, String array) {
		
		String perType=null;
		String peopleArr[]=array.split(",");		
		int rotate = peopleSum(array);
		String perTypes[]=new String[rotate];
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i=0;i<peopleArr.length;i++) {		
			int tmp = Integer.parseInt(peopleArr[i]);
			if(tmp>0) {
					switch(i) {
						case 0:
								perType="����";
								break;
						case 2:
								perType="û�ҳ�";
								break;
						case 4:
								perType="�����";
								break;
						default:
								perType="�����";
								break;
					}				
					for(int j=0;j<tmp;j++) {
						list.add(perType);	
					}
			}						
		}
		
		for(int i=0;i<perTypes.length;i++) {
			perTypes[i]=list.get(i);
		}
		
		return perTypes;
	}	
	
	//�����ڵ� �̱�
	public String[] selPriceCode(String movType, String array) {
		
		String perType=null;
		String peopleArr[]=array.split(",");		
		int rotate = peopleSum(array);
		String codes[]=new String[rotate];
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i=0;i<peopleArr.length;i++) {		
			int tmp = Integer.parseInt(peopleArr[i]);
			if(tmp>0) {
					switch(i) {
						case 0:
								perType="����";
								break;
						case 2:
								perType="û�ҳ�";
								break;
						case 4:
								perType="�����";
								break;
						default:
								perType="�����";
								break;
					}				
					for(int j=0;j<tmp;j++) {
						list.add(redao.movPriceCodeSel(movType, perType));	
					}
			}						
		}
		
		for(int i=0;i<codes.length;i++) {
			codes[i]=list.get(i);
		}
		
		return codes;
	}
	
	//������ �¼� �ڵ� �̱�
	public String[] selBookedSeatCode(String movScheduleCode,
															String movTheater, String movSeatList) {
		
		if(movTheater==null||movTheater.length()==0||
				movSeatList==null||movSeatList.length()==0||
						movScheduleCode==null||movScheduleCode.length()==0)
			return null;
		
		String[] seatCodes = selMetaSeatCode(movTheater, movSeatList);
		String[] result = new String[seatCodes.length];//���� �� ��
		
		for(int i=0;i<seatCodes.length;i++) {
			result[i]=redao.movReserveSeatCodeSel(movScheduleCode, seatCodes[i]);
		}
		
		return result;
	}
		
	//��Ÿ �¼� ���̺� �ڵ� �̱�
	public String[] selMetaSeatCode(String movTheater, String movSeatList) {
		
		String[]seatLists=movSeatList.split(",");
		String[] result = new String[seatLists.length];
		for(int i=0;i<seatLists.length;i++) {
//			System.out.println("=======selMetaSeatCode==========");
//			System.out.println(seatLists[i]);
			result[i]=redao.movMetaSeatCodeSel(movTheater, seatLists[i]);
		}
//		System.out.println("selMetaSeatCode ���� : " +result.length);
		return result;
	}
	
}
