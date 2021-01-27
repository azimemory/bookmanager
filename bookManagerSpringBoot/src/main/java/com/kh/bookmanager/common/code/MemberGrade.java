package com.kh.bookmanager.common.code;

/**
 * @PackageName: com.uclass.common.code
 * @FileName : TableStatus.java
 * @Date : 2020. 6. 16.
 * @프로그램 설명 : 
 * @author :하명도
 */
public enum MemberGrade {
	
	ME00("일반",1,"ROLE_MEMBER"),
	ME01("성실",2,"ROLE_MEMBER"),
	ME02("우수",3,"ROLE_MEMBER"),
	ME03("vip",4,"ROLE_MEMBER");

	public final String DESC;
	public final int EXTENDABLECNT;
	public final String ROLE;
	
	MemberGrade(String desc, int extendableCnt, String role) {
		this.DESC = desc;
		this.EXTENDABLECNT = extendableCnt;
		this.ROLE = role;
	}
}
