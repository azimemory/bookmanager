# bookmanager
파일업로드 경로 프로젝트외부에서 관리함
Code Enum의 upload Path에 파일저장
tomcat context.xml 파일에 

 <Resources>
      <PreResources className="org.apache.catalina.webresources.DirResourceSet" webAppMount="/upload" base="C:\CODE\lecture\resources\upload"/>
 </Resources>
 
 설정 추가해줄 필요가 있음
