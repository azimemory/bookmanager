package com.kh.bookmanager.common.code;

/**
 * @PackageName: com.uclass.common.code
 * @FileName : TableStatus.java
 * @Date : 2020. 6. 16.
 * @프로그램 설명 : 
 * @author :하명도
 */
public enum Code {
	UPLOAD_PATH("C:\\CODE\\lecture\\resources\\upload\\image\\"),  //로컬용 파일업로드 경로
    //UPLOAD_PATH("/usr/local/webapps/resources/image/"), //서버용 파일업로드 경로
	UPLOAD_TYPE_BOARD("board");
	public final String VALUE;
	Code(String value){
		this.VALUE = value;
	}
}
