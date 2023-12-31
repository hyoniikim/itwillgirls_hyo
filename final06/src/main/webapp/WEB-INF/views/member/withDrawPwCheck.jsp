<%@page import="kr.co.itwill.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<%@ include file="../header.jsp"%>

<!--breadcrumbs start-->
<div class="breadcrumbs">
   <div class="container">
      <div class="row">
         <div class="col-lg-4 col-sm-4">
            <h1>
               마이페이지
            </h1>
         </div>
         <div class="col-lg-8 col-sm-8">
            <ol class="breadcrumb pull-right">
               <li>
                  <a href="../home.do">
                     Home
                  </a>
               </li>
               <li>
               		마이페이지
               </li>
               <li class="">
                  회원 탈퇴
               </li>
            </ol>
         </div>
      </div>
   </div>
</div>
<!--breadcrumbs end-->


<!-- 본문시작 withDrawPwCheck.jsp-->
<!--container start-->
<div class="login-bg">
    <div class="container">
        <div class="form-wrapper">
        <form class="form-signin wow fadeInUp" id="pwCheckfrm" method="post" action="/member/memberDelete.do">
        <h2 class="form-signin-heading">회원 탈퇴</h2>
        <div class="login-wrap">
            <input type="text" class="form-control" placeholder="아이디" id="p_id" name="p_id" value="${member_dto.p_id}" readonly>
            <input type="text" class="form-control" type="password" placeholder="비밀번호" id="p_passwd" name="p_passwd" size="20">
            <div style="color: red;">
            	<c:if test="${msg == false}">
					비밀번호가 일치하지 않습니다.
				</c:if>
            </div>
            <div style="font-size: 12px; text-align: center; padding: 5px;">탈퇴를 진행하려면 비밀번호를 입력해 주세요.</div>
            <button class="btn btn-lg btn-login btn-block" type="submit" id="pwCheck_button">탈퇴하기</button>
        </div>
      </form>
      </div>
    </div>
</div>


<script>
$(document).ready(function(){

	// 탈퇴
	$("#pwCheck_button").on("click", function(){
		
		if($("#p_id").val()==""){
			alert("로그인 후 다시 시도해 주세요.");
			$("#pwCheckfrm").attr("action", "/member/login.do");
			$("#pwCheckfrm").submit();
			return true;
		}//if end
		
		if($("#p_passwd").val()==""){
			alert("비밀번호를 입력해 주세요.");
			$("#p_passwd").focus();
			return false;
		}//if end
		
		var deleteYN = confirm("탈퇴하시겠습니까?");
		if(deleteYN == true){
			$("#pwCheckfrm").attr("action", "/member/memberDelete.do");
			$("#pwCheckfrm").submit();
		}//if end
	});
});
</script>
<!--container end-->
<!-- 본문 끝 -->

<%@ include file="../footer.jsp"%>