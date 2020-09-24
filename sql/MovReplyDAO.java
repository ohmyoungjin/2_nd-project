package mov.sql;

import java.sql.*;
import java.util.ArrayList;

import mov.common.DB;

public class MovReplyDAO {
	Connection CN ;
	Statement ST ;
	PreparedStatement PST;
	CallableStatement CST;
	ResultSet RS,RS1,RS2 ;
	String msg="";
	
	public MovReplyDAO() {
		CN = DB.getConnection();
	}
	

	public ArrayList<MovDTO> dbReplySelect(String Rdata){ //Rdata = revNum
    ArrayList<MovDTO> list = new ArrayList<MovDTO>();

    try {
       msg="select rownum rn, r.* from mov_replyboard r where rev_num="+Rdata ;
       
       ST=CN.createStatement();
       RS=ST.executeQuery(msg);
       while(RS.next()) {
          MovDTO rpdto = new MovDTO();

          rpdto.setRepRn(RS.getInt("rn"));
          rpdto.setRepNum(RS.getInt("reply_num"));
          rpdto.setRevNum(Integer.parseInt(Rdata));
          rpdto.setRepContent(RS.getString("reply_content"));
          rpdto.setUserCode(RS.getInt("user_code"));
          rpdto.setRepLike(RS.getInt("reply_like"));
          rpdto.setRepDislike(RS.getInt("reply_dislike"));        
          rpdto.setRepWritedate(RS.getString("reply_writedate"));

          msg="select u.user_nick from mov_replyboard r inner join mov_user u on r.user_code = u.user_code and r.user_code="+rpdto.getUserCode();
          ST=CN.createStatement();
          RS1=ST.executeQuery(msg);
          while(RS1.next()) {
             rpdto.setNickName(RS1.getString("user_nick"));
          }

          list.add(rpdto);
       }
    }catch(Exception e) {System.out.println("dbreplyselect 에러"+e);}
    return list;
 }

	public void dbReplyInsert(String Rdata, String content) {
		try {
		msg="insert into mov_replyboard values (seq_replyboard.nextval,?,?,?,0,0,sysdate )";
		
		PST=CN.prepareStatement(msg);
	  	PST.setString(1, Rdata);
	  	PST.setString(2, content);
	  	PST.setInt(3, 6000); // user_code 일단 기본값 6000	
	    PST.executeUpdate();
		}catch(Exception e) {System.out.println("dbreplyinsert 에러 :"+e);}
		
	 }


}
