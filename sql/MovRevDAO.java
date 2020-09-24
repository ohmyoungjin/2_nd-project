package mov.sql;

import java.sql.*;
import java.util.ArrayList;

import mov.common.DB;

public class MovRevDAO {
	Connection CN ;
	Statement ST ;
	PreparedStatement PST;
	CallableStatement CST;
	ResultSet RS,RS1,RS2 ;
	String msg="";
	
	public MovRevDAO() {
		CN = DB.getConnection();
	}
	
	
	public String dbMovNameC(String Gdata) { // gdata = movCode
		String movName="";
		try {
			msg="select mov_name from mov_info where mov_code="+Gdata;
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {
				movName=RS.getString("mov_name");
			}
		}catch(Exception e) {System.out.println("dbmovnameC 에러 :"+e);}
		return movName;
	}
	
	public String dbMovNameN(String Gdata) {//gdata=revNum
		String movName="";
		try {
			
			msg="select mov_name from mov_info m inner join mov_revboard r on m.mov_code = r.mov_code and r.rev_num="+Gdata;
			
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {
				movName=RS.getString("mov_name");
			}
		}catch(Exception e) {System.out.println("dbMovNameN 에러 :"+e);}
		
		return movName;
	}
	
	public int dbMovCode(String movName) {
		int movCode=0;
		try {
			msg="select mov_code from mov_info where mov_name='"+movName+"'";
			ST=CN.createStatement();
			RS=ST.executeQuery(msg);
			while(RS.next()) {
				movCode=RS.getInt("mov_code");
			}
		}catch(Exception e) {System.out.println("dbMovCode 에러 :"+e);}
		return movCode;
	}
	
	public int dbRevCountAll() {
		int count=0;
		try {
			msg="select count(*) as cnt from mov_revboard ";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		if(RS.next()==true) { count=RS.getInt("cnt");}
		}catch(Exception e) {
			System.out.println("dbrevcount에러 : "+e);
		}
		return count;
	}
	
	public int dbRevCount(String Gdata) {//Gdata = movCode
		int count=0;
		try {
			msg="select count(*) as cnt from mov_revboard where mov_code="+Gdata;
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		if(RS.next()==true) { count=RS.getInt("cnt");}
		}catch(Exception e) {
			System.out.println("dbrevcount에러 : "+e);
		}
		return count;
	}
//	public int dbRevSCountAll(String skey, String sval) {
//		int count =0;
//		try {
//			msg="select count(*) as cnt from mov_revboard where "+skey+" like '%"+sval+"%'";
//			RS=ST.executeQuery(msg);
//	  		if(RS.next()==true) { count=RS.getInt("cnt");}
//		}catch(Exception e) {
//			System.out.println("dbrevScountAll에러 :"+e);
//		}
//		
//		return count;
//	}
//	public int dbRevSCount(String Gdata, String skey, String sval) {
//		int count =0;
//		try {
//			msg="select count(*) as cnt from mov_revboard where mov_code="+Gdata+" and "+skey+" like '%"+sval+"%'";
//			RS=ST.executeQuery(msg);
//	  		if(RS.next()==true) { count=RS.getInt("cnt");}
//		}catch(Exception e) {
//			System.out.println("dbrevScount에러 :"+e);
//		}
//		
//		return count;
//	}
	public ArrayList<MovDTO> dbRevSelectAll(int start, int end, String skey, String sval){//최근 리뷰 전체
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		String a="",b="",c="";
		try {
			if(skey.equals("rev_title")) {
			
			a="select * from (";
			b="select rownum rn, r.* from (select * from mov_revboard order by rev_writedate desc) r where "+skey+" like '%"+sval+"%'";
			c=") where rn between "+start+" and "+end;
			}else if(skey.equals("user_nick")) {
				a="select * from (";
	  			b="select rownum rn, r.* from mov_revboard r inner join mov_user u on r.user_code = u.user_code and u."+skey+" like '%"+sval+"%' ";
	  			c=") where rn between "+start+" and "+end;
			}else if(skey.equals("mov_name")){

				a="select * from (";
				b="select rownum rn, r.* from (select * from mov_revboard order by rev_writedate desc) r inner join mov_info u on r.mov_code=u.mov_code and u."+skey+" like '%"+sval+"%' order by r.rev_writedate desc";
				c=") where rn between "+start+" and "+end;
			}
			msg=a+b+c;
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setRevRn(RS.getInt("rn"));
	            rdto.setRevNum(RS.getInt("rev_num"));
	            rdto.setRevTitle(RS.getString("rev_title"));
	            rdto.setUserCode(RS.getInt("user_code"));
	            rdto.setRevWritedate(RS.getDate("rev_writedate"));
	            rdto.setRevHit(RS.getInt("rev_hit"));
	            rdto.setRevLike(RS.getInt("rev_like"));
	            rdto.setRevDislike(RS.getInt("rev_dislike"));
	            rdto.setRevFile(RS.getString("rev_file"));
	            rdto.setMovCode(RS.getInt("mov_code"));
	            
	            msg="select count(*) as repcnt from mov_replyboard where rev_num="+rdto.getRevNum();
	            ST=CN.createStatement();
	            RS1=ST.executeQuery(msg);
	            while(RS1.next()) {
	               rdto.setRepCnt(RS1.getInt("repcnt"));
	            }
	            
	            msg="select u.user_nick from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rdto.getUserCode();
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("user_nick"));
	            }
	            
	            list.add(rdto);
	  		}
		}catch(Exception e) {System.out.println("dbRevSelectAll 에러:"+e);}
		return list;
	}
	
	public ArrayList<MovDTO> dbRevSelect10(){//최근 리뷰 데이터 10개
		ArrayList<MovDTO> list = new ArrayList<MovDTO>();
		try {
			msg="select * from (select rownum rn, r.* from (select * from mov_revboard order by rev_writedate desc) r) where rn<11 order by rn";
			ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setRevRn(RS.getInt("rn"));
	            rdto.setRevNum(RS.getInt("rev_num"));
	            rdto.setRevTitle(RS.getString("rev_title"));
	            rdto.setUserCode(RS.getInt("user_code"));
	            rdto.setRevWritedate(RS.getDate("rev_writedate"));
	            rdto.setRevHit(RS.getInt("rev_hit"));
	            rdto.setRevLike(RS.getInt("rev_like"));
	            rdto.setRevDislike(RS.getInt("rev_dislike"));
	            rdto.setRevFile(RS.getString("rev_file"));
	            rdto.setMovCode(RS.getInt("mov_code"));
	            
	            msg="select count(*) as repcnt from mov_replyboard where rev_num="+rdto.getRevNum();
	            ST=CN.createStatement();
	            RS1=ST.executeQuery(msg);
	            while(RS1.next()) {
	               rdto.setRepCnt(RS1.getInt("repcnt"));
	            }
	            
	            msg="select u.user_nick from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rdto.getUserCode();
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("user_nick"));
	            }
	            
	            list.add(rdto);
	  		}
		}catch(Exception e) {
			System.out.println("dbRevSelect10 에러 :"+e);
		}
		return list;
	}
	
	public ArrayList<MovDTO> dbRevSelect(int start, int end, String skey, String sval, String Rdata){
	  	ArrayList<MovDTO> list = new ArrayList<MovDTO>();
	  	String a="",b="",c="";
	  	try {
	  		StringBuffer sb = new StringBuffer();

	  		
	  		if(skey.equals("rev_title")) {
	  		a="select * from (";
	  		b="select rownum rn, r.* from (select * from mov_revboard where mov_code="+Rdata+" and "+skey+" like '%"+sval+"%'";
	  		c="order by rev_writedate desc) r) where rn between "+start+" and "+ end;
	  		}
	  		else if(skey.equals("user_nick")) {
	  			a="select * from (";
	  			b="select rownum rn, r.* from mov_revboard r inner join mov_user u on r.user_code = u.user_code and u.user_nick like '%"+sval+"%' where r.mov_code="+Rdata;
	  			c=") where rn between "+start+" and "+end;
	  		}
	  		else {
	  			a="select * from (";
		  		b="select rownum rn, r.* from (select * from mov_revboard  where mov_code="+Rdata+" and "+skey+" like '%"+sval+"%') r";
		  		c="order by rev_writedate desc) where rn between "+start+" and "+ end;
	  		}
	  		msg=a+b+c;
	  		ST=CN.createStatement();
	  		RS=ST.executeQuery(msg);
	  		while(RS.next()) {
	  			MovDTO rdto = new MovDTO();
	  			rdto.setRevRn(RS.getInt("rn"));
	            rdto.setRevNum(RS.getInt("rev_num"));
	            rdto.setRevTitle(RS.getString("rev_title"));
	            rdto.setUserCode(RS.getInt("user_code"));
	            rdto.setRevWritedate(RS.getDate("rev_writedate"));
	            rdto.setRevHit(RS.getInt("rev_hit"));
	            rdto.setRevLike(RS.getInt("rev_like"));
	            rdto.setRevDislike(RS.getInt("rev_dislike"));
	            rdto.setRevFile(RS.getString("rev_file"));
	            rdto.setMovCode(RS.getInt("mov_code"));
	            
	            msg="select count(*) as repcnt from mov_replyboard where rev_num="+rdto.getRevNum();
	            ST=CN.createStatement();
	            RS1=ST.executeQuery(msg);
	            while(RS1.next()) {
	               rdto.setRepCnt(RS1.getInt("repcnt"));
	            }
	            
	            msg="select u.user_nick from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rdto.getUserCode();
	            ST=CN.createStatement();
	            RS2=ST.executeQuery(msg);
	            while(RS2.next()) {
	               rdto.setNickName(RS2.getString("user_nick"));
	            }
	            
	            list.add(rdto);
	  		}
	  	}catch(Exception e) {System.out.println("dbrevselect 오류"+e);}
	  	return list;
	}
	
  public ArrayList<MovDTO> dbRevSelect3(String Rdata){
    ArrayList<MovDTO> list = new ArrayList<MovDTO>();
    try {
       StringBuffer sb = new StringBuffer();
//       msg="select user_nick from mov_user ";
//       String nickName = "";
//       msg="select * from mov_revboard where rev_num<4 and mov_code="+Rdata;
//       msg="select rownum rn, r.rev_title, u.user_nick,r.rev_hit from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.mov_code ="+Rdata;
       
       msg="select * from (select rownum rn, r.* from (select * from mov_revboard where mov_code="+Rdata+" order by rev_writedate desc) r) where rn<4";
       
       ST=CN.createStatement();
       RS=ST.executeQuery(msg);
       while(RS.next()) {
          MovDTO rdto = new MovDTO();
          
          rdto.setRevRn(RS.getInt("rn"));
          rdto.setRevNum(RS.getInt("rev_num"));
          rdto.setRevTitle(RS.getString("rev_title"));
          rdto.setUserCode(RS.getInt("user_code"));
          rdto.setRevWritedate(RS.getDate("rev_writedate"));
          rdto.setRevHit(RS.getInt("rev_hit"));
          rdto.setRevLike(RS.getInt("rev_like"));
          rdto.setRevDislike(RS.getInt("rev_dislike"));
          rdto.setRevFile(RS.getString("rev_file"));
          rdto.setMovCode(RS.getInt("mov_code"));
          
          msg="select count(*) as repcnt from mov_replyboard where rev_num="+rdto.getRevNum();
          ST=CN.createStatement();
          RS1=ST.executeQuery(msg);
          while(RS1.next()) {
             rdto.setRepCnt(RS1.getInt("repcnt"));
          }
          
          msg="select u.user_nick from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rdto.getUserCode();
          ST=CN.createStatement();
          RS2=ST.executeQuery(msg);
          while(RS2.next()) {
             rdto.setNickName(RS2.getString("user_nick"));
          }

          list.add(rdto);
       }
    }catch(Exception e) {System.out.println("dbrevselect3 에러"+e);}
    return list;
 }

  public MovDTO dbRevDetail(String Rdata){ // Rdata = revNum
       ArrayList<MovDTO> list = new ArrayList<MovDTO>();
       MovDTO rvdto = new MovDTO();
       try {
          StringBuffer sb = new StringBuffer();              
          
          msg="select rownum rn, r.* from mov_revboard r where rev_num= "+Rdata;
          
          ST=CN.createStatement();
          RS=ST.executeQuery(msg);
          while(RS.next()) {
             //MovDTO rdto = new MovDTO();
             
             
             rvdto.setRevRn(RS.getInt("rn"));
             rvdto.setRevNum(RS.getInt("rev_num"));
             rvdto.setRevTitle(RS.getString("rev_title"));
             
             rvdto.setUserCode(RS.getInt("user_code"));
             rvdto.setRevWritedate(RS.getDate("rev_writedate"));
             rvdto.setRevHit(RS.getInt("rev_hit"));
             rvdto.setRevLike(RS.getInt("rev_like"));
             rvdto.setRevDislike(RS.getInt("rev_dislike"));
             rvdto.setRevFile(RS.getString("rev_file"));
             rvdto.setMovCode(RS.getInt("mov_code"));
             rvdto.setRevContent(RS.getString("rev_content"));
          
             
             msg="select u.user_nick from mov_revboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rvdto.getUserCode();
             //ST=CN.createStatement();
             RS1=ST.executeQuery(msg);
             while(RS1.next()) {
                rvdto.setNickName(RS1.getString("user_nick"));
             }

                //list.add(rdto);
          }
       }catch(Exception e) {System.out.println("dbrevdetail 에러"+e);}
       return rvdto;
    }

  public void dbRevInsert(String Rdata, String title, String content) {//rdata=movcode
		try {
		
			msg="insert into mov_revboard values(seq_revboard.nextval,?,?,sysdate,0,0,0,null,"+Rdata+",?) ";
			PST=CN.prepareStatement(msg);
		  	PST.setString(1,title );
		  	PST.setInt(2, 6000);// user_code 일단 기본값 6000
		  	//PST.setString(3, file);
		  	PST.setString(3, content); 
		  	
	
		    PST.executeUpdate();
		    
		}catch(Exception e) { System.out.println("dbRevInsert 에러 :"+e);}
  }
  
  public void dbRevDelete(String Rdata) {
		try {
		msg="delete from mov_revboard where rev_num="+Rdata;
		ST=CN.createStatement();
	    ST.executeUpdate(msg);
	    //System.out.println(Rdata + " 데이터 삭제처리 성공했습니다");
		}catch(Exception e) {System.out.println("dbrevdelete 에러 :"+e);}
	}
  
	public void dbRevUpdate(String Rdata, String title, String content) {//Rdata = revNum
		MovDTO rvdto = new MovDTO();
		try {
			msg="update mov_revboard set rev_title=?,rev_writedate=sysdate,"
					+ "rev_content=? where rev_num= "+Rdata;
			PST=CN.prepareStatement(msg);
	  	PST.setString(1, title);
	  	PST.setString(2, content);
	
	    PST.executeUpdate();
	    
	 }catch(Exception e) {System.out.println("dbrevupdate 에러"+e);}
	 
	}



}
