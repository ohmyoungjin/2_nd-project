package mov.sql;

import java.sql.*;
import java.util.ArrayList;

import mov.common.DB;

public class MovDAO {
	Connection CN ;
	Statement ST ;
	PreparedStatement PST;
	CallableStatement CST;
	ResultSet RS,RS1,RS2 ;
	String msg="";
	ArrayList<MovDTO> list = new ArrayList<MovDTO>();
	public MovDAO() {
		CN = DB.getConnection();
	}
	
	public ArrayList<MovDTO> dbReserveList(){
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		System.out.println("두번째");
			try {
				msg = "select  DISTINCT i.mov_name,i.mov_code\r\n" + 
						"         from mov_info i\r\n" + 
						"            inner join mov_detail d\r\n" + 
						"                on i.mov_code = d.mov_code\r\n" + 
						"            inner join  mov_schedule s\r\n" + 
						"                on d.mov_detail_code = s.mov_detail_code\r\n" + 
						"                and s.schedule_date between sysdate and sysdate+10";
				ST=CN.createStatement();
				RS=ST.executeQuery(msg);
				while(RS.next()==true) {
					MovDTO mdto = new MovDTO();
					mdto.setMovName(RS.getString("mov_name"));
					mdto.setMovCode(RS.getInt("mov_code"));
					
					list.add(mdto);
				}
			}catch(Exception e ) {
				
			}
			System.out.println("네번째");
		return list;
	}
	
	public ArrayList<MovDTO> dbReserveDay(String code){
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		System.out.println("두번째");
			try {
				msg = "select  DISTINCT to_char(s.schedule_date,'dd')as scheduleday,d.mov_code\r\n" + 
						"         from mov_info i\r\n" + 
						"            inner join mov_detail d\r\n" + 
						"                on i.mov_code = d.mov_code\r\n" + 
						"                and d.mov_code ='"+code+"'\r\n" + 
						"            inner join  mov_schedule s\r\n" + 
						"                on d.mov_detail_code = s.mov_detail_code\r\n" + 
						"                and s.schedule_date between sysdate and sysdate+10\r\n" + 
						"                order by to_char(s.schedule_date,'dd')";
				ST=CN.createStatement();
				RS=ST.executeQuery(msg);
				while(RS.next()==true) {
					MovDTO mdto = new MovDTO();
					mdto.setScheduleday(RS.getString("scheduleday"));
					mdto.setMovCode(RS.getInt("mov_code"));
					
					list.add(mdto);
				}
			}catch(Exception e ) {
				
			}
			
		return list;
	}
	
	public ArrayList<MovDTO> dbReserveDaytime(String code,String day){
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		System.out.println("두번째");
			try {
				msg = "  select d.mov_type ,to_char(s.schedule_date,'dd') scheduleday, to_char(s.schedule_date,'yy/mm/dd/hh24:mi') time,d.mov_code,\r\n" + 
						"					      nvl(a.count,80) count, nvl(b.count,0) bcount, to_char(s.schedule_date,'hh24:mi')scheduletimeSeat \r\n" + 
						"					    from mov_schedule s\r\n" + 
						"					        inner join mov_detail d\r\n" + 
						"					            on d.mov_detail_code = s.mov_detail_code\r\n" + 
						"					            and d.mov_code = " + code + 
						"					          and to_char(schedule_date,'dd') = " +day + 
						"					         left outer join (select count(booked_tf) as count, schedule_code \r\n" + 
						"					              from mov_booked_seat \r\n" + 
						"					                     where  booked_tf=0 \r\n" + 
						"					                         group by schedule_code) a \r\n" + 
						"					              on a.schedule_code=s.schedule_code \r\n" + 
						"					         left outer join (select count(booked_tf) as count, schedule_code \r\n" + 
						"					                from mov_booked_seat \r\n" + 
						"					                                 where  booked_tf=1\r\n" + 
						"					                                group by schedule_code) b  \r\n" + 
						"					                on b.schedule_code=s.schedule_code    \r\n" + 
						"					     order by to_char(s.schedule_date,'hh24:mi')";
				ST=CN.createStatement();
				RS=ST.executeQuery(msg);
				System.out.println("aaaaaaaaaaaaaaaaa");
				while(RS.next()==true) {
					MovDTO mdto = new MovDTO();
					mdto.setScheduletimeSeat(RS.getString("scheduletimeSeat"));
					mdto.setScheduletimeString(RS.getString("time"));
					mdto.setScheduleday(RS.getString("scheduleday"));
					mdto.setMovCode(RS.getInt("mov_code"));
					mdto.setMovType(RS.getString("mov_type"));
					mdto.setCountSeat(RS.getInt("count"));
					mdto.setEmptySeat(RS.getInt("bcount"));
					list.add(mdto);
				}
			}catch(Exception e ) {
				System.out.println("dbReserveDaytime 에러 : " +e);
			}
			
		return list;
	}
	
	public MovDTO dbReserveSeat(String code,String date) {
		MovDTO dto = new MovDTO();
		msg = "  select i.mov_name,s.schedule_code,i.mov_code,s.theater_code, s.mov_detail_code, \r\n" + 
				"        d.mov_type,i.mov_age, to_char(s.schedule_date,'yy/mm/dd')scheduleyear\r\n" + 
				"        from mov_info i\r\n" + 
				"            inner join mov_detail d\r\n" + 
				"                on i.mov_code = d.mov_code\r\n" + 
				"                 and i.mov_code = '"+code+"'\r\n" + 
				"            inner join mov_schedule s\r\n" + 
				"                on s.mov_detail_code = d.mov_detail_code\r\n" + 
				"                and  to_char(s.schedule_date,'yy/mm/dd/hh24:mi') = '"+date+"'";
		
		try {
			ST =CN.createStatement();
			RS = ST.executeQuery(msg);
			if(RS.next()==true) {
				dto.setMovName(RS.getString("mov_name"));
				dto.setMovCode(RS.getInt("mov_code"));
				dto.setScheduleCode(RS.getString("schedule_code"));
				dto.setTheaterCode(RS.getInt("theater_code"));
				dto.setMovType(RS.getString("mov_type"));
				dto.setMovAge(RS.getString("mov_age"));
				dto.setScheduleYear(RS.getString("scheduleyear"));
				dto.setMovDetailCode(RS.getInt("mov_detail_code"));
			}
		}catch(Exception e) {
			System.out.println("예매좌석오류 : "+e);
		}
		return dto;
	}
	
	public MovDTO dbDetail(String Gdata) {
		MovDTO dto = new MovDTO();
		try {			
			msg = "select * from mov_info where mov_code = " + Gdata;
			ST=CN.createStatement();			
		    RS=ST.executeQuery(msg);
		    if(RS.next()==true) {
		    	 //dto.setRn(RS.getInt("rn")); 
					dto.setMovCode(RS.getInt("mov_code"));
					dto.setMovName(RS.getString("mov_name"));
					dto.setMovSummary(RS.getString("mov_summary"));
					dto.setMovOpendate(RS.getDate("mov_opendate"));
					dto.setMovRuntime(RS.getInt("mov_runtime"));
					dto.setMovAge(RS.getString("mov_age"));
					dto.setMovGenre(RS.getString("mov_genre"));
					dto.setMovDirector(RS.getString("mov_director"));
					dto.setMovActor1(RS.getString("mov_actor1"));
					dto.setMovActor2(RS.getString("mov_actor2"));
					dto.setMovActor3(RS.getString("mov_actor3"));		
					dto.setMovCountry(RS.getString("mov_country"));				
					dto.setMovPoster(RS.getString("mov_poster"));
					dto.setMovStarAvg(RS.getFloat("mov_star_avg"));
		     }
		}catch(Exception e) {System.out.println("dbDetail 에러 :"+e);}
		return dto;
	}
	
	public void starUpdate(int point, int code, String Gdata) {
		try {			
			msg = "select count(*) as count from mov_star_log"
					+ " where user_code = " + code
					+ " and mov_code = " + Gdata;
			
			ST=CN.createStatement();
		  RS=ST.executeQuery(msg);
		  RS.next();
		  int count = RS.getInt("count");
		  String avg=null;
		  if(count>0) {		  		
		  	msg  = "select star_score from mov_star_log where mov_code = ? and user_code=?";
		  	PST = CN.prepareStatement(msg);
		  	PST.setString(1, Gdata);
		  	PST.setInt(2, code);
				RS=PST.executeQuery();
				RS.next();
		  	int preScore = RS.getInt("star_score");
				
		  	msg="update mov_star_log set star_score=? where user_code = ?";
		  	PST = CN.prepareStatement(msg);
		  	PST.setInt(1, point);
		  	PST.setInt(2, code);
		  	PST.executeUpdate();		
		  	avg=getUpdateStar(Gdata,point-preScore);
		  }else {
		  	msg="insert into mov_star_log values (?,?,?) ";
		  	PST = CN.prepareStatement(msg);
		  	PST.setString(1, Gdata);
		  	PST.setInt(2, code);
		  	PST.setInt(3, point);
		  	PST.executeUpdate();
		  	avg=getInsertStar(Gdata,point);
		  }
		  msg="update mov_info set mov_star_avg=? where mov_code = ?";
	  	PST = CN.prepareStatement(msg);
	  	PST.setString(1, avg);
	  	PST.setString(2, Gdata);
	  	PST.executeUpdate();		
		  
		}catch(Exception e) {System.out.println("starUpdate 에러 :"+e);}
	}
	
	public String getUpdateStar(String Gdata, int point) {
		int starCount=0;
		int starSum=0;
		double avg=0.0;
		try {				
			msg  = "select * from mov_star where mov_code = " + Gdata;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {				
				starSum = RS.getInt("star_sum")+point;
				starCount = RS.getInt("star_count");
				avg=(double)starSum/starCount;
//				System.out.println("합계 : "+starSum);
//				System.out.println("카운트 : "+starCount);
//				System.out.println("평균 : "+avg);
			}			
			msg="update mov_star set star_sum=? where mov_code = ?";
	  	PST = CN.prepareStatement(msg);
	  	PST.setInt(1, starSum);
	  	PST.setString(2, Gdata);
	  	PST.executeUpdate();				
		}catch (Exception e) {	System.out.println("getUpdateStar 메소드 에러  : "+e);	}
		return String.format("%.1f", avg);
	}
	
	public String getInsertStar(String Gdata, int point) {
		int starCount=0;
		int starSum=0;
		double avg=0.0;
		try {				
			msg  = "select * from mov_star where mov_code = " + Gdata;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {				
				starSum = RS.getInt("star_sum")+point;
				starCount = RS.getInt("star_count")+1;
				avg=(double)starSum/starCount;
			}			
			msg="update mov_star set star_sum=?, star_count=? where mov_code = ?";
	  	PST = CN.prepareStatement(msg);
	  	PST.setInt(1, starSum);
	  	PST.setInt(2, starCount);
	  	PST.setString(3, Gdata);
	  	PST.executeUpdate();				
		}catch (Exception e) {	System.out.println("getInsertStar 메소드 에러  : "+e);	}
		return String.format("%.1f", avg);
	}
	
	public ArrayList<MovDTO> movTimeTable(String Gdata, String Date) {		
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		try {

			msg="select s.schedule_code, to_char(s.schedule_date,'mm') as schedule_month,\r\n" + 
					"    to_char(s.schedule_date,'dd') as schedule_date,\r\n" +
					"    to_char(s.schedule_date,'hh24:mi') as schedule_time,\r\n" +
					"    s.theater_code, s.mov_detail_code, d.mov_code, d.mov_type,\r\n" + 
					"    nvl(a.count,0) as count, nvl(b.count,0) as bcount\r\n" + 
					"        from mov_schedule s \r\n" + 
					"            inner join mov_detail d \r\n" + 
					"                on d.mov_code = " + Gdata + 
					"                and d.mov_detail_code=s.mov_detail_code \r\n" + 
					"                    and to_char(schedule_date,'dd') = " + Date +  
					"            left outer join (select count(booked_tf) as count, schedule_code \r\n" + 
					"                        from mov_booked_seat bs \r\n" + 
					"                        where  booked_tf=0\r\n" + 
					"                        group by schedule_code) a \r\n" + 
					"                 on a.schedule_code=s.schedule_code \r\n" + 
					"            left outer join (select count(booked_tf) as count, schedule_code \r\n" + 
					"                        from mov_booked_seat bs \r\n" + 
					"                        where  booked_tf=1\r\n" + 
					"                        group by schedule_code) b \r\n" + 
					"                 on b.schedule_code=s.schedule_code \r\n" + 
					"        order by s.theater_code ";
			
			ST=CN.createStatement();
		  RS=ST.executeQuery(msg);
		    while(RS.next()) {
		    	MovDTO dto = new MovDTO();		
		    	dto.setSchedule(RS.getString("schedule_code"));
		    	dto.setScheduleMonth(RS.getString("schedule_month"));
		    	dto.setScheduleDate(RS.getString("schedule_date"));
		    	dto.setScheduleTime(RS.getString("schedule_time"));
		    	dto.setMovDetailCode(RS.getInt("mov_detail_code"));
					dto.setMovCode(RS.getInt("mov_code"));
					dto.setMovType(RS.getString("mov_type"));
					dto.setTheaterCode(RS.getInt("theater_code"));
					dto.setCountSeat(RS.getInt("count"));
					dto.setEmptySeat(RS.getInt("bcount"));
					list.add(dto);
		    }		     
		}catch(Exception e) {System.out.println("movTimeTable 에러 :"+e);}
		return list;
	}
	
	public ArrayList<MovDTO> selSchedule(){
		
		try {				
			msg="select * from ("+ 
						"select to_char(schedule_date,'mm/dd') schedule"+
							" from mov_schedule) " + 
						"group by schedule " + 
						"order by schedule ";
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			
			while(RS.next()) {
				MovDTO md = new MovDTO();
				md.setSchedule(RS.getString("schedule"));
				list.add(md);
			}				
		}catch (Exception e) {	System.out.println("posterSel메소드 에러  : "+e);	}
		
		return list;
	}
	
	public ArrayList<MovDTO> dbRevSelect(int Rdata){	  	
	  	try {
	  		StringBuffer sb = new StringBuffer();
	  		sb.append("select rownum rn, g.sabun, r.num, r.writer,r.content from guest g ");
	  		sb.append("inner join guestreply r ");
	  		sb.append("on g.sabun = r.sabun ");
	  		sb.append("and r.sabun = "+Rdata);
	  		ST=CN.createStatement();
	  		RS=ST.executeQuery(sb.toString());
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();	  				
	  				list.add(rdto);
	  		}
	  	}catch(Exception e) {System.out.println("dbRevSelect 오류"+e);}
	  	return list;
	}
	
	public ArrayList<MovDTO> posterSel() {
		try {				
			String a = "select * from ( ";
			String b = " select rownum rn, mov_code, mov_poster from mov_info order by mov_star_avg";
			String c = ") where rn in(1,2) order by rn";
			
			msg = a+b+c;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			
			while(RS.next()) {
				MovDTO md = new MovDTO();
				md.setMovCode(RS.getInt("mov_code"));
				md.setPoster(RS.getString("mov_poster"));
				list.add(md);
			}				
		}catch (Exception e) {	System.out.println("posterSel메소드 에러  : "+e);	}
		return list;
	}
	
	public ArrayList<MovDTO> movListSel() {
		ArrayList<MovDTO> al=new ArrayList<MovDTO>();
		try {				
			String a = "select * from ( ";
			String b = " select rownum rn, mov_code, mov_name, mov_poster, mov_star_avg ";
			String c = "from mov_info order by mov_star_avg ";
			String d = ") where rn <=10 order by rn";
			
			msg = a+b+c+d;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			
			while(RS.next()) {
				MovDTO md = new MovDTO();
				md.setMovCode(RS.getInt("mov_code"));
				md.setMovName(RS.getString("mov_name"));
				md.setMovStarAvg(RS.getFloat("mov_star_avg"));
				md.setPoster(RS.getString("mov_poster"));
				al.add(md);
			}				
		}catch (Exception e) {	System.out.println("posterSel메소드 에러  : "+e);	}
		return al;
	}
	
	public String dbMovName(String Gdata) {
		String movName="";
		try {
			msg="select mov_name from mov_info where mov_code="+Gdata;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {
				movName=RS.getString("mov_name");
			}
		}catch(Exception e) {System.out.println("dbmovname 에러 :"+e);}
		return movName;
	}
	
	public MovDTO movScheduleSel(String schedule, String detail) {
		MovDTO dto = new MovDTO();
//		System.out.println("sce" + schedule);
//		System.out.println("detail" + detail);
		try {			
			msg = "select de.mov_name, de.mov_age, de.mov_type, sc.theater_code,\r\n" + 
					"        to_char(sc.schedule_date,'mm/dd') scheduleDate,\r\n" + 
					"        to_char(sc.schedule_date,'hh24:mi') scheduleTime\r\n" + 
					"from mov_schedule sc\r\n" + 
					"    inner join (select i.mov_name, i.mov_age, d.mov_type,d.mov_detail_code\r\n" + 
					"                    from mov_info i\r\n" + 
					"                        inner join mov_detail d\r\n" + 
					"                        on d.mov_code = i.mov_code\r\n" + 
					"                            and mov_detail_code = '"+detail+"') de\r\n" + 
					"        on de.mov_detail_code=sc.mov_detail_code\r\n" + 
					"            and schedule_code = " + schedule;
			ST=CN.createStatement();			
//			System.out.println("aaaaaa");
		    RS=ST.executeQuery(msg);
		    if(RS.next()==true) {
					dto.setMovName(RS.getString("mov_name"));
					dto.setMovAge(RS.getString("mov_age"));
					dto.setMovType(RS.getString("mov_type"));
					dto.setTheaterCode(RS.getInt("theater_code"));
					dto.setScheduleDate(RS.getString("scheduleDate"));
					dto.setScheduleTime(RS.getString("scheduleTime"));
		     }
		}catch(Exception e) {System.out.println("movScheduleSel 에러 :"+e);}
		return dto;
	}
	
	public int movTotalPaySel(String movType, String perType, int num) {
		int a=0;
		try {			
			msg = "select price*? as price from mov_price where mov_type=? and person_type=?";
			PST=CN.prepareStatement(msg);
	  	PST.setInt(1, num);
	  	PST.setString(2, movType);
	  	PST.setString(3, perType);
	    RS=PST.executeQuery();
		  RS.next();
			a=RS.getInt("price");		     
		}catch(Exception e) {System.out.println("movTotalPaySel 에러 :"+e);}		
		return a;
	}

	public ArrayList<MovDTO> dbNoticeSelectAll(int start, int end, String skey, String sval){//최근 리뷰 전체
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		String a="",b="",c="";
		try {
			if(skey.equals("notice_title")) {
			
			a="select * from (";
			b="select rownum rn, r.* from (select * from mov_noticeboard order by notice_writedate desc) r where "+skey+" like '%"+sval+"%'";
			c=") where rn between "+start+" and "+end;
			}else if(skey.equals("admin_nick")) {
				a="select * from (";
	  			b="select rownum rn, r.* from mov_noticeboard r inner join mov_admin u on r."+skey+" like '%"+sval+"%'";
	  			c=") where rn between "+start+" and "+end;
			}
			msg=a+b+c;
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setNoticeRn(RS.getInt("rn"));
	            rdto.setNoticeNum(RS.getInt("notice_num"));
	            rdto.setNoticeTitle(RS.getString("notice_title"));
	            
	            rdto.setNoticeWritedate(RS.getDate("notice_writedate"));
	            rdto.setNoticeHit(RS.getInt("notice_hit"));
	            
	            rdto.setNoticeFile(RS.getString("notice_file"));
	            
	           
	            
	            msg="select a.admin_nick from mov_noticeboard n inner join mov_admin a on n.admin_nick = a.admin_nick ";
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("admin_nick"));
	            }
	            
	            list.add(rdto);
	  		}
		}catch(Exception e) {System.out.println("dbNoticeSelectAll 에러:"+e);}
		return list;
	}
	
	public ArrayList<MovDTO> dbNoticeSelect10(){//최근 리뷰 데이터 10개
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		try {
			msg="select * from (select rownum rn, r.* from (select * from mov_noticeboard order by notice_writedate desc) r) where rn<11 order by rn";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setNoticeRn(RS.getInt("rn"));
	            rdto.setNoticeNum(RS.getInt("notice_num"));
	            rdto.setNoticeTitle(RS.getString("notice_title"));
	            //rdto.setAdminCode(RS.getInt("admin_code"));
	            rdto.setNoticeWritedate(RS.getDate("notice_writedate"));
	            rdto.setNoticeHit(RS.getInt("notice_hit"));
	            rdto.setNoticeFile(RS.getString("notice_file"));
	            
	            msg="select a.admin_nick from mov_noticeboard n inner join mov_admin a on n.admin_nick = a.admin_nick ";
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("admin_nick"));
	            }
	            
	            list.add(rdto);
	  		}
		}catch(Exception e) {
			System.out.println("dbNoticeSelect10 에러 :"+e);
		}
		return list;
	}

	public int dbNoticeCountAll() {
		int count=0;
		try {
			msg="select count(*) as cnt from mov_noticeboard ";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		if(RS.next()==true) { count=RS.getInt("cnt");}
		}catch(Exception e) {
			System.out.println("dbNoticeCountAll에러 : "+e);
		}
		return count;
	}

	public MovDTO dbNoticeDetail(String Ndata){//최근 리뷰 데이터 10개
		
		MovDTO ndto = new MovDTO();
		try {
			msg="select * from (select rownum rn, r.* from (select * from mov_noticeboard order by notice_writedate desc) r) where rn<11 order by rn";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			
	  			ndto.setNoticeRn(RS.getInt("rn"));
	            ndto.setNoticeNum(RS.getInt("notice_num"));
	            ndto.setNoticeTitle(RS.getString("notice_title"));
	            ndto.setNoticeContent(RS.getString("notice_content"));
	            //rdto.setAdminCode(RS.getInt("admin_code"));
	            ndto.setNoticeWritedate(RS.getDate("notice_writedate"));
	            ndto.setNoticeHit(RS.getInt("notice_hit"));
	            ndto.setNoticeFile(RS.getString("notice_file"));
	            
	            msg="select a.admin_nick from mov_noticeboard n inner join mov_admin a on n.admin_nick = a.admin_nick ";
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               ndto.setNickName(RS2.getString("admin_nick"));
	            }
	            
	            
	  		}
		}catch(Exception e) {
			System.out.println("dbNoticeDetail 에러 :"+e);
		}
		return ndto;
	}

	public ArrayList<MovDTO> dbNoticeSelectAll(){//최근 리뷰 데이터 10개
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		try {
			msg="select * from (select rownum rn, r.* from (select * from mov_noticeboard order by notice_writedate desc) r) where rn<11 order by rn";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setNoticeRn(RS.getInt("rn"));
	            rdto.setNoticeNum(RS.getInt("notice_num"));
	            rdto.setNoticeTitle(RS.getString("notice_title"));
	            //rdto.setAdminCode(RS.getInt("admin_code"));
	            rdto.setNoticeWritedate(RS.getDate("notice_writedate"));
	            rdto.setNoticeHit(RS.getInt("notice_hit"));
	            rdto.setNoticeFile(RS.getString("notice_file"));
	            
	            msg="select a.admin_nick from mov_noticeboard n inner join mov_admin a on n.admin_nick = a.admin_nick ";
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("admin_nick"));
	            }
	            
	            list.add(rdto);
	  		}
		}catch(Exception e) {
			System.out.println("dbNoticeSelectAll 에러 :"+e);
		}
		return list;
	}
	public ArrayList<MovDTO> dbReserveCheck(String id){
		ArrayList<MovDTO> Chlist = new ArrayList<MovDTO>();
		msg ="select DISTINCT r.schedule_code,i.mov_name,\r\n" + 
				"    to_char(s.schedule_date,'yy-mm-dd-hh24:mi')as scheduledate  \r\n" + 
				"      from mov_reserve r\r\n" + 
				"        inner join mov_user u\r\n" + 
				"            on r.user_code = u.user_code\r\n" + 
				"            and u.user_id ='"+id+"'	\r\n" + 
				"        inner join mov_schedule s\r\n" + 
				"            on s.schedule_code =r.schedule_code\r\n" + 
				"        inner join mov_detail d\r\n" + 
				"            on r.mov_detail_code = d.mov_detail_code\r\n" + 
				"        inner join mov_info i\r\n" + 
				"            on d.mov_code = i.mov_code";
		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			while(RS.next()==true) {
				MovDTO dto = new MovDTO();
				dto.setScheduleCode(RS.getString("schedule_code"));
				dto.setMovName(RS.getString("mov_name"));
				dto.setScheduleDate(RS.getNString("scheduledate"));
				Chlist.add(dto);
			}
		}catch(Exception e) {
			System.out.println("예매확인 오류 : " +e);
		}
		return Chlist;
	}
}
