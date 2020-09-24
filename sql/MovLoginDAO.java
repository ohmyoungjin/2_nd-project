package mov.sql;

import java.sql.*;
import java.util.ArrayList;

import mov.common.DB;

import mov.common.DB;

public class MovLoginDAO {
	Connection CN;
	Statement ST;
	PreparedStatement PST;
	CallableStatement CST;
	ResultSet RS;

	String msg;
	String msg1;
	String msg2;
	String msg3;
	int Mcnt;
	int Mcnt1;
	int code;

	public MovLoginDAO() {
		CN = DB.getConnection();
	}

	public int dbLogin(String id, String pwd) {
		msg = "select count(*) as cnt from MOV_USER where user_id='" + id + "' and user_pwd ='" + pwd + "'";

		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
//			System.out.println(msg);
			if (RS.next() == true) {
				Mcnt = RS.getInt("cnt");
			}
//			System.out.println(Mcnt);
		} catch (SQLException e) {
			System.out.println("dbLogin 에러 : " + e);
		}
		return Mcnt;
	}
	
	public int getUserCode(String id) {
		msg = "select user_code from MOV_USER where user_id = '" + id+"'"; 
		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			if (RS.next() == true) {
				code = RS.getInt("user_code");
			}
		} catch (SQLException e) {
			System.out.println("getUserCode 에러 : " + e);
		}
		return code;
	}

	
	
	public int dbIdCheck(String id) {
		msg = "select count(*) as cnt from MOV_USER where user_id='" + id + "'";

		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			if (RS.next() == true) {
				Mcnt = RS.getInt("cnt");
			}
		} catch (SQLException e) {
			System.out.println("dbIdCheck 에러 : " + e);
		}
		return Mcnt;
	}

	public int dbNickCheck(String nick) {
		msg = "select count(*) as cnt from MOV_USER where user_nick='" + nick + "'";

		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			System.out.println(msg);
			if (RS.next() == true) {
				Mcnt1 = RS.getInt("cnt");
			}
			System.out.println(Mcnt1);
		} catch (SQLException e) {
			System.out.println("dbNickCheck 에러 : " + e);
		}
		return Mcnt1;
	}

	public void dbSignInsert(MovLoginDTO dto) {
		msg1 = "insert into mov_user values(";
		msg2 = "seq_user.nextval,?,?,?,?,?,?,?,to_date(?, 'YYYY-MM-DD'))";
		msg = msg1 + msg2;

		try {
			PST = CN.prepareStatement(msg);
			PST.setString(1, dto.getUserId());
			PST.setString(2, dto.getUserPwd());
			PST.setString(3, dto.getUserNick());
			PST.setString(4, dto.getUserTel());
			PST.setString(5, dto.getUserAddr());
			PST.setString(6, dto.getUserAddr2());
			PST.setString(7, dto.getUserEmail());
			PST.setString(8, dto.getUserBirth());
			PST.executeUpdate();
			System.out.println("dbSignInsert" + dto.getUserId());
			System.out.println("dbSignInsert" + dto.getUserPwd());
			System.out.println("dbSignInsert" + dto.getUserNick());
			System.out.println("dbSignInsert" + dto.getUserTel());
			System.out.println("dbSignInsert" + dto.getUserAddr());
			System.out.println("dbSignInsert" + dto.getUserAddr2());

			System.out.println("입력성공 !");

		} catch (SQLException e) {
			System.out.println("dbSignInsert 에러 : " + e);
		}
	}
	
	public String dbFindId(String tel) {
		msg = "select user_id from mov_user where user_tel="+tel;
		String userid=null;
		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			if(RS.next()==true){
				userid = RS.getString("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userid;
	
	}
	public String dbFindpwd(String id,String tel) {
		msg = "select user_pwd from mov_user where user_id='"+id+"' and user_tel ="+tel;
		String userpwd=null;
		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			if(RS.next()==true){
				userpwd = RS.getString("user_pwd");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userpwd;
	
	}
	public MovLoginDTO dbMyPage(String id) {
		msg = "select a.* , to_char(user_birth,'YYYY-MM-DD')as user_birth1 from mov_user a where user_id = '"+id+"'";
		MovLoginDTO dto = new MovLoginDTO();
		try {
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			if(RS.next()==true) {
				dto.setUserId(RS.getString("user_id"));
				dto.setUserPwd(RS.getString("user_pwd"));
				dto.setUserNick(RS.getString("user_nick"));
				dto.setUserTel(RS.getString("user_tel"));
				dto.setUserEmail(RS.getString("user_email"));
				dto.setUserBirth(RS.getString("user_birth1"));
				System.out.println("셀렉트까진성공");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("마이페이지오류  : " + e);
		}
		return dto;
		
	}
	public void dbEditMyPage(String birth,String tel,String id) {
		System.out.println(birth);
		System.out.println(tel);
		System.out.println(id);
		msg = "update mov_user set user_birth =to_date(?,'YYYY-MM-DD'), user_tel= ? where user_id = ?";
		try {
			PST =CN.prepareStatement(msg);
			PST.setString(1, birth);
			PST.setString(2, tel);
			PST.setString(3, id);
			PST.executeUpdate();
			System.out.println("수정성공");
		
		}catch(Exception e) {
			System.out.println("업데이트가 실패하는이유 :" +e);
		}
		
	}
}
