package com.uclass.bookmanager.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import com.uclass.bookmanager.common.code.Code;

public class FileUtil {
	
	public Map<String,Object> fileUpload(HttpServletRequest request) {
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<FileVo> fileInfoList = new ArrayList<>();
		
		final int maxSize = 1024 * 1024 * 10;
		
		MultipartParser multipartParser;
			
		try {
			multipartParser = new MultipartParser(request,  maxSize);
			multipartParser.setEncoding("UTF-8");
			Part part = null;
			
			while((part = multipartParser.readNextPart()) != null) {
			
				if(part.isFile()) {
					FilePart filePart = (FilePart) part;
					
					//업로드된 파일이 존재하야 getFileName이 null이 아니다.
					if(filePart.getFileName() != null) {
						String originFileName = filePart.getFileName(); //사용자가 올린 파일 이름
						String renameFileName = getRenameFileName(originFileName); //서버에 저장될 파일 이름
					    
					    //서버에 저장할 경로
						String savePath = getSavePath();
						
						//파일정보를 Map에 저장
						FileVo fileVo = new FileVo();
						fileVo.setOriginFileName(originFileName);
						fileVo.setRenameFileName(renameFileName);
						fileVo.setSavePath(savePath);
						
						//사용자가 등록한 파일을 파일저장경로에 저장
						savePath = Code.UPLOAD_PATH.VALUE + savePath;
						saveFile(filePart,savePath,renameFileName);
						
						//다중 파일업로드를 고려하여 list에 파일정보들을 저장
						fileInfoList.add(fileVo);
					}
				}else {
					//Parameter로 넘어온 데이터의 name(Key), value(Value)를 담아줌
					ParamPart paramPart = (ParamPart)part;
					
					//만약 해당 name을 가진 데이터가 없다면 resultMap에 추가
					if(resultMap.get(paramPart.getName()) == null) {
						resultMap.put(paramPart.getName(),paramPart.getStringValue());
					}else {
						//해당 name을 가진 데이터가 있는데 하나라면 
						//새로운 리스트를 만들어서 기존 name의 데이터와 새로은 데이터를 합쳐줌
						if(resultMap.get(paramPart.getName()) instanceof String) {
							List<String> params = new ArrayList<String>();
							params.add(paramPart.getStringValue());
							params.add((String) resultMap.get(paramPart.getName()));
							resultMap.put(paramPart.getName(),params);
						//해당 name을 가진 데이터가 있는데 두개 이상이어서 이미 리스트라면
						//그 리스트에 데이터를 추가
						}else {
							List<String> params = (List)resultMap.get(paramPart.getName());
							params.add(paramPart.getStringValue());
						}
					}
				}
			} 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//파일정보를 담아줌
		resultMap.put("fileInfoList",fileInfoList);
		return resultMap;
	}
	
	public boolean fileDownload(FileVo fileInfo, HttpServletResponse response) {
	
		  boolean res = false;
		  String savePath = Code.UPLOAD_PATH.VALUE + fileInfo.getRenameFileName(); 
		  File downFile = new File(savePath); 
		  String originFileName = fileInfo.getOriginFileName(); 
		  ServletOutputStream downOutput = null;
		  BufferedInputStream bin = null;
		  
		  try { 
			  response.setHeader("Content-Disposition", "attachment; filename="+
			  URLEncoder.encode(originFileName,"UTF-8")); 
			   
			  downOutput = response.getOutputStream(); 
			  bin = new BufferedInputStream(new FileInputStream(downFile)); 
			 
			  int read = 0;
			  while((read = bin.read()) != -1 ) { 
				  downOutput.write(read);
			  }
			  
			  downOutput.flush(); 
			  res = true;
	  
			  }catch (IOException e) { // TODO Auto-generated catch block
				  e.printStackTrace(); 
			  }finally {
				  try {
					downOutput.close();   
					bin.close();
				  } catch (IOException e) {
					e.printStackTrace();
				  } 
			  }
		  return res;
	}
	
	public String getSavePath() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return year + "/" + mon + "/" + day + "/";
	}
	
	public String getRenameFileName(String originFileName) {
		//업로도된 파일명을 UUID를 활용해 생성 
	    //UUID : 유일한 식별자를 생성하는 클래스 
	    UUID renameFileID = UUID.randomUUID(); 
	    return renameFileID.toString() + originFileName.substring(originFileName.lastIndexOf("."));
	}
	
	public void saveFile(FilePart filePart, String savePath, String renameFileName){
		//사용자가 등록한 파일을 옮겨담을 파일 객체 생성
		//savePath : 저장할 경로 + 변경된 파일명
		File fileData = new File(savePath);
		fileData.mkdirs();
		try {
			//파일 업로드 진행
			filePart.writeTo(fileData);
			File oFile = new File(savePath + filePart.getFileName());
			oFile.renameTo(new File(savePath + renameFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteFile(String path) {
		//지정된 경로의 파일 객체를 생성
		path = Code.UPLOAD_PATH.VALUE + path;
		File file = new File(path);
		//delete() 메서드로 파일을 삭제
		file.delete();
	}
}
