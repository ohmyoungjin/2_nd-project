package mov.sql;

import java.sql.*;
import java.util.ArrayList;

import mov.common.DB;

public class MovReserveDAO {
	Connection CN ;
	Statement ST ;
	PreparedStatement PST;
	CallableStatement CST;
	ResultSet RS,RS1,RS2 ;
	String msg="";
	
	public MovReserveDAO() {
		CN = DB.getConnection();
	}	
	
	public String movPriceCodeSel(String movType, String perType) {
		String a=null;
		try {			
			msg = "select price_code from mov_price where mov_type=? and person_type=?";
			PST=CN.prepareStatement(msg);
	  	PST.setString(1, movType);
	  	PST.setString(2, perType);	  	
	    RS=PST.executeQuery();
		  RS.next();
			a=RS.getString("price_code");		     
		}catch(Exception e) {System.out.println("movPriceCodeSel 에러 :"+e);}		
		return a;
	}
	
	public String movMetaSeatCodeSel(String theater, String seatNum) {
		String a=null;
		System.out.println("theater : " + theater);
		System.out.println("seatNum : " + seatNum);
		try {			
			msg = "select mov_seat_code from mov_seat "
					+ " where theater_code=? and seat_num=?";
			PST=CN.prepareStatement(msg);
	  	PST.setString(1, theater);
	  	PST.setString(2, seatNum);	  	
	    RS=PST.executeQuery();
		  RS.next();
			a=RS.getString("mov_seat_code");		
			System.out.println("movMetaSeatCodeSel a : " + a);
		}catch(Exception e) {System.out.println("movMetaSeatCodeSel 에러 :"+e);}		
		return a;
	}
	
	public String movReserveSeatCodeSel(String scheduleCode, String metaSeatCode) {
		String a=null;
		try {			
			
			msg="insert into mov_booked_seat values("
					+ "seq_booked_seat.nextval,?,?,1)";
			PST=CN.prepareStatement(msg);
	  	PST.setString(1, scheduleCode);
	  	PST.setString(2, metaSeatCode);	 
			PST.executeUpdate();
			
			msg = "select booked_seat_code from mov_booked_seat"
					+ " where schedule_code=? and mov_seat_code=?";
			PST=CN.prepareStatement(msg);
	  	PST.setString(1, scheduleCode);
	  	PST.setString(2, metaSeatCode);	  	
	    RS=PST.executeQuery();
		  RS.next();
			a=RS.getString("booked_seat_code");		     
		}catch(Exception e) {System.out.println("movReserveSeatCodeSel 에러 :"+e);}		
		return a;
	}
	
	public void movReserveInsert(String movDetailCode, String movScheduleCode, int usercode,
				String bookedSeatsCode, String priceCode, String movTheater, String perType) {
		try {
			msg="insert into mov_reserve"
					+ " values (seq_reserve.nextval,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi'),"
					+ "?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd hh24:mi')) ";
	  	PST = CN.prepareStatement(msg);
	  	PST.setString(1, movDetailCode);//영화디테일코드
	  	PST.setString(2, movScheduleCode);//스케줄코드
	  	PST.setInt(3, usercode);///유저코드
	  	PST.setString(4, bookedSeatsCode);//예매좌석코드
	  	PST.setString(5, priceCode);//가격코드
	  	PST.setString(6, movTheater);//영화관코드(1관, 2관...)
	  	PST.setString(7, perType);//사람타입(성인, 청소년....)
	  	PST.executeUpdate();
//	  	System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
	  	//log 테이블 insert
	  	msg="insert into mov_reserve_log ("
	  			+ "reserve_code, user_code, reserve_group_code,reserved_time,cancel_tf) "
					+ " values (seq_reserve.currval,?,to_char(sysdate,'yyyy-mm-dd hh24:mi'),"
					+ "to_char(sysdate,'yyyy-mm-dd hh24:mi'),1) ";
	  	PST = CN.prepareStatement(msg);
	  	PST.setInt(1, usercode);//유저코드
	  	PST.executeUpdate();
		} catch (Exception e) {System.out.println("movReserveInsert 에러 :"+e);
		}
	}
	
}
