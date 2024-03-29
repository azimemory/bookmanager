package com.kh.bookmanager.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

import com.kh.bookmanager.common.code.Code;
import com.kh.bookmanager.common.code.ErrorCode;
import com.kh.bookmanager.common.exception.CustomException;


public class FileUtil {
	
	public List<FileVo> fileUpload(
			List<MultipartFile> files) {
		
		//파일과 관련된 정보를 가지고 반환될 list	
		List<FileVo> fileData = new ArrayList<FileVo>();
		
		for(MultipartFile mf : files) {
			
			//사용자가 올린 파일 이름
			String originFileName = mf.getOriginalFilename();
			//서버에 저장될 파일 이름
			String renameFileName = getRenameFileName(originFileName);
			//서버에 저장할 경로
			String savePath = getSavePath() + renameFileName;
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("originFileName", originFileName);
			map.put("renameFileName", renameFileName);
			map.put("savePath",savePath);
			
			FileVo fileVo = new FileVo();
			fileVo.setOriginFileName(originFileName);
			fileVo.setRenameFileName(renameFileName);
			fileVo.setSavePath(savePath);
			
			//tb_file에 저장할 데이터를 list에 추가
			fileData.add(fileVo);
			//사용자가 등록한 파일을 파일저장경로에 저장
			savePath = Code.UPLOAD_PATH.VALUE + savePath;
			saveFile(mf,savePath);
		}
	
		return fileData;	
	}
	
	public String getSavePath() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return year + "/" + mon + "/" + day + "/";
	}
	
	public String getRenameFileName(String originFileName) {
		 UUID renameFileID = UUID.randomUUID(); 
	     return renameFileID.toString() + originFileName.substring(originFileName.lastIndexOf("."));
	}
	
	public void saveFile(MultipartFile mf, String savePath)  {
		//사용자가 등록한 파일을 옮겨담을 파일 객체 생성
		//savePath : 저장할 경로 + 변경된 파일명
		File fileData = new File(savePath);
		fileData.mkdirs();
		System.out.println(savePath);
		try {
			mf.transferTo(fileData);
		} catch (IllegalStateException | IOException e) {
			throw new CustomException(ErrorCode.IF01);
		}
	}
	
	public void deleteFile(String path) {
		//지정된 경로의 파일 객체를 생성
		File file = new File(Code.UPLOAD_PATH.VALUE + path);
		//delete() 메서드로 파일을 삭제
		file.delete();
	}
}
