<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../header.jsp" %>

<!--breadcrumbs start-->
<div class="breadcrumbs">
   <div class="container">
      <div class="row">
         <div class="col-lg-4 col-sm-4">
            <h1>
               아이디 찾기
            </h1>
         </div>
         <div class="col-lg-8 col-sm-8">
            <ol class="breadcrumb pull-right">
               <li>
                  <a href="../home.do">
                     Home
                  </a>
               </li>
               <li class="">
                  아이디 찾기
               </li>
            </ol>
         </div>
      </div>
   </div>
</div>
<!--breadcrumbs end-->

<!--container start-->
<div class="login-bg">
    <div class="container">
        <div class="form-wrapper">
        <form class="form-signin wow fadeInUp" id="findIDfrm" method="post" action="findID.do" onsubmit="return findIDCheck()">
        <h2 class="form-signin-heading">아이디 찾기</h2>
        <div class="login-wrap">
            <input type="text" class="form-control" id="p_name" name="p_name" maxlength="20" placeholder="이름을 입력해 주세요." autofocus required>
            <input type="text" class="form-control" id="p_tell" name="p_tell" maxlength="11" placeholder="핸드폰 번호를 입력해 주세요." required>
            <button class="btn btn-lg btn-login btn-block" type="submit" id="find_button">찾기</button>
        </div>
      </form>
      </div>
    </div>
</div>

<script>
function findIDCheck() {
//아이디찾기 유효성 검사
//1)이름 2글자 이상인지?
	  let p_name=document.getElementById("p_name").value;
	  p_name=p_name.trim();
      if(p_name.length<2) {
		  alert("이름을 2글자 이상 입력해 주세요");
		  document.getElementById("p_name").focus();
		  return false;
	  }//if end

//2)전화번호가 10~11자리의 숫자인지?
	  let p_tell=document.getElementById("p_tell").value;
	  p_tell=p_tell.trim();
      if(p_tell.length<10 || p_tell.length>11 || isNaN(p_tell)) {
		  alert("전화번호는 '-' 기호를 생략하고 숫자형식으로 입력해 주세요");
		  document.getElementById("p_tell").focus();
		  return false;
	  }//if end

	  return true;
}//findIDCheck() end
</script>
<!--container end-->
        
<%@ include file="../footer.jsp" %>
