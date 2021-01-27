<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 양식</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/member.css" />
</head>
<body>
	<div class="box_member">
	     <form 
	       action="<%=request.getContextPath()%>/member/joinimpl.do"
	       method="post"
	       class="frm_join"
	       onsubmit="return validate();">
	       <span>회원가입</span>
	       <div class="join-info">
				<p>* id</p>
				<button type="button" class="btn-id-check" onclick="idCheck()">ID 확인</button>
		  		<input type="text" name="userId" id="userId" class="join-text" size="10"/>
	 			<span id="id-check-msg"></span>
	 		</div>
	 		<div class="join-info">
	  			<p>* password</p>
	  			<input type="password" name="password" id="userPwd" class="join-text" size="10"/>
	 		</div>
 			<div class="join-info">
	  			<p>* email</p>
	  			<input type="text" name="email" class="join-text" size="10"/>
	  		</div>
	  		<div class="join-info">
	  			<p>* hp</p>
	  			<input type="text" name="tell" class="join-text" size="10"/>
	  		</div>
	  		<button type="submit" class="btn-join-submit">전송</button>
		</form>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.4.1.js"
	  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 	  crossorigin="anonymous"></script>
	
	<script type="text/javascript">
	var ajaxFlag = false;
	function validate() {
		
        var pass = document.getElementById('userPwd');
        var regExpPw = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;

        function chk(re, e, msg) {
            if(re.test(e.value)) {           		
                return true;          
	    	}else{
           	  alert(msg);
              e.value = "";
              e.focus();
              //기본 이벤트 취소
              return false;
            }
     	}
        
        if(!ajaxFlag){
        	alert("아이디 중복검사를 해주세요");
        	return false;
        }
        
        // 비밀번호 검사
      	// 암호는 영문자와 숫자로 8글자이상  기호문자 한개이상 8글자 이상
        if(!chk(regExpPw, pass,'비밀번호 숫자,영어,특수문자가 하나 이상 포함, 8글자 이상 50글자 이하')){
        	return false;
        }

        return true;
    }
	
	function idCheck(){
		var userId = {"userId":$("#userId").val()};
		var xhr = new XMLHttpRequest();
		var url = '<%=request.getContextPath()%>/member/idcheck.do';
		xhr.open('POST',url);
		xhr.setRequestHeader('Content-Type','application/json');
		xhr.send(JSON.stringify(userId));
		xhr.addEventListener('load', function(){
			if(xhr.status === 200){
				document.querySelector('#id-check-msg').textContent = xhr.response;
				ajaxFlag = true;
			}else{
				document.querySelector('#id-check-msg').textContent = xhr.response;		
				ajaxFlag = false;
			}
		});
	}
	
	</script>
</body>
</html>
