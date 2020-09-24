package mov.sql;

import java.util.Date;

public class MovDTO {
	private int movCode, movDetailCode, theaterCode;
	private String movName, movType;
	private String movSummary;
	private Date movOpendate, noticeWritedate;
	private int movRuntime, countSeat, emptySeat, noticeHit;
	private String movAge, scheduleString, movGenre;
	private String movDirector, movActor1, movActor2, movActor3;
	private String movCountry, movPoster, scheduleday, scheduleCode, scheduleYear;
	private String scheduletimeString, scheduletimeSeat;
	private float movStarAvg;
	private String poster, schedule, scheduleDate, scheduleMonth, scheduleTime;
	private int starCount, starSum, noticeRn, noticeNum, adminCode;

	public int getNoticeRn() {
		return noticeRn;
	}

	public void setNoticeRn(int noticeRn) {
		this.noticeRn = noticeRn;
	}

	String noticeTitle, noticeFile, noticeContent;

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getNoticeNum() {
		return noticeNum;
	}

	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}

	public int getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(int adminCode) {
		this.adminCode = adminCode;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public Date getNoticeWritedate() {
		return noticeWritedate;
	}

	public void setNoticeWritedate(Date noticeWritedate) {
		this.noticeWritedate = noticeWritedate;
	}

	public int getNoticeHit() {
		return noticeHit;
	}

	public void setNoticeHit(int noticeHit) {
		this.noticeHit = noticeHit;
	}

	public String getNoticeFile() {
		return noticeFile;
	}

	public void setNoticeFile(String noticeFile) {
		this.noticeFile = noticeFile;
	}

	public String getScheduletimeSeat() {
		return scheduletimeSeat;
	}

	public void setScheduletimeSeat(String scheduletimeSeat) {
		this.scheduletimeSeat = scheduletimeSeat;
	}

	public String getScheduleCode() {
		return scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public String getScheduletimeString() {
		return scheduletimeString;
	}

	public void setScheduletimeString(String scheduletimeString) {
		this.scheduletimeString = scheduletimeString;
	}

	public String getScheduleYear() {
		return scheduleYear;
	}

	public void setScheduleYear(String scheduleYear) {
		this.scheduleYear = scheduleYear;
	}

	public String getScheduleday() {
		return scheduleday;
	}

	public void setScheduleday(String scheduleday) {
		this.scheduleday = scheduleday;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getScheduleMonth() {
		return scheduleMonth;
	}

	public void setScheduleMonth(String scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public int getStarSum() {
		return starSum;
	}

	public void setStarSum(int starSum) {
		this.starSum = starSum;
	}

	int repCnt, repRn, repNum, repLike, repDislike;

	String repContent, repWritedate;

	int userCode;
	String revTitle, revFile, revContent, nickName;
	int revNum, revHit, revRn, revLike, revDislike;
	Date revWritedate;

	public int getRepNum() {
		return repNum;
	}

	public void setRepNum(int repNum) {
		this.repNum = repNum;
	}

	public int getRepLike() {
		return repLike;
	}

	public void setRepLike(int repLike) {
		this.repLike = repLike;
	}

	public int getRepDislike() {
		return repDislike;
	}

	public void setRepDislike(int repDislike) {
		this.repDislike = repDislike;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}

	public String getRepWritedate() {
		return repWritedate;
	}

	public void setRepWritedate(String repWritedate) {
		this.repWritedate = repWritedate;
	}

	public int getRepCnt() {
		return repCnt;
	}

	public void setRepCnt(int repCnt) {
		this.repCnt = repCnt;
	}

	public int getRevRn() {
		return revRn;
	}

	public void setRevRn(int revRn) {
		this.revRn = revRn;
	}

	public int getRepRn() {
		return repRn;
	}

	public void setRepRn(int repRn) {
		this.repRn = repRn;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getRevTitle() {
		return revTitle;
	}

	public void setRevTitle(String revTitle) {
		this.revTitle = revTitle;
	}

	public String getRevFile() {
		return revFile;
	}

	public void setRevFile(String revFile) {
		this.revFile = revFile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getRevNum() {
		return revNum;
	}

	public void setRevNum(int revNum) {
		this.revNum = revNum;
	}

	public int getRevHit() {
		return revHit;
	}

	public void setRevHit(int revHit) {
		this.revHit = revHit;
	}

	public int getRevLike() {
		return revLike;
	}

	public void setRevLike(int revLike) {
		this.revLike = revLike;
	}

	public int getRevDislike() {
		return revDislike;
	}

	public void setRevDislike(int revDislike) {
		this.revDislike = revDislike;
	}

	public Date getRevWritedate() {
		return revWritedate;
	}

	public void setRevWritedate(Date revWritedate) {
		this.revWritedate = revWritedate;
	}

	public String getRevContent() {
		return revContent;
	}

	public void setRevContent(String rev_content) {
		this.revContent = rev_content;
	}

	public int getEmptySeat() {
		return emptySeat;
	}

	public void setEmptySeat(int emptySeat) {
		this.emptySeat = emptySeat;
	}

	public int getCountSeat() {
		return countSeat;
	}

	public void setCountSeat(int countSeat) {
		this.countSeat = countSeat;
	}

	public String getScheduleString() {
		return scheduleString;
	}

	public void setScheduleString(String scheduleString) {
		this.scheduleString = scheduleString;
	}

	public int getMovDetailCode() {
		return movDetailCode;
	}

	public void setMovDetailCode(int movDetailCode) {
		this.movDetailCode = movDetailCode;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public int getTheaterCode() {
		return theaterCode;
	}

	public void setTheaterCode(int theaterCode) {
		this.theaterCode = theaterCode;
	}

	public void setMovType(String movType) {
		this.movType = movType;
	}

	public String getMovType() {
		return movType;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getPoster() {
		return poster;
	}

	public int getMovCode() {
		return movCode;
	}

	public void setMovCode(int movCode) {
		this.movCode = movCode;
	}

	public String getMovName() {
		return movName;
	}

	public void setMovName(String movName) {
		this.movName = movName;
	}

	public String getMovSummary() {
		return movSummary;
	}

	public void setMovSummary(String movSummary) {
		this.movSummary = movSummary;
	}

	public Date getMovOpendate() {
		return movOpendate;
	}

	public void setMovOpendate(Date movOpendate) {
		this.movOpendate = movOpendate;
	}

	public int getMovRuntime() {
		return movRuntime;
	}

	public void setMovRuntime(int movRuntime) {
		this.movRuntime = movRuntime;
	}

	public String getMovAge() {
		return movAge;
	}

	public void setMovAge(String movAge) {
		this.movAge = movAge;
	}

	public String getMovGenre() {
		return movGenre;
	}

	public void setMovGenre(String movGenre) {
		this.movGenre = movGenre;
	}

	public String getMovDirector() {
		return movDirector;
	}

	public void setMovDirector(String movDirector) {
		this.movDirector = movDirector;
	}

	public String getMovActor1() {
		return movActor1;
	}

	public void setMovActor1(String movActor1) {
		this.movActor1 = movActor1;
	}

	public String getMovActor2() {
		return movActor2;
	}

	public void setMovActor2(String movActor2) {
		this.movActor2 = movActor2;
	}

	public String getMovActor3() {
		return movActor3;
	}

	public void setMovActor3(String movActor3) {
		this.movActor3 = movActor3;
	}

	public String getMovCountry() {
		return movCountry;
	}

	public void setMovCountry(String movCountry) {
		this.movCountry = movCountry;
	}

	public String getMovPoster() {
		return movPoster;
	}

	public void setMovPoster(String movPoster) {
		this.movPoster = movPoster;
	}

	public float getMovStarAvg() {
		return movStarAvg;
	}

	public void setMovStarAvg(float movStarAvg) {
		this.movStarAvg = movStarAvg;
	}

}
