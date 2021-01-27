package com.kh.bookmanager.common.code;

public enum MemberGrade {
	
	ME00("일반",1),
	ME01("성실",2),
	ME02("우수",3),
	ME03("vip",4);
	
	public final String DESC;
	public final int EXTENDABLECNT;
	
	MemberGrade(String desc, int extendableCnt) {
		this.DESC = desc;
		this.EXTENDABLECNT = extendableCnt;
	}
}
