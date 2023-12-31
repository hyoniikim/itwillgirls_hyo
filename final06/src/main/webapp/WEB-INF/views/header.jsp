<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Developed By M Abdur Rokib Promy">
	<meta name="author" content="cosmic">
	<meta name="keywords" content="Bootstrap 3, Template, Theme, Responsive, Corporate, Business">
	<link rel="shortcut icon" href="img/favicon.png">
	
	<title>
		도담도담어린이문화센터
	</title>

		
	<!-- Bootstrap core CSS -->
	<link href="../css/bootstrap.min.css" rel="stylesheet">
	<link href="../css/theme.css" rel="stylesheet">
	<link href="../css/bootstrap-reset.css" rel="stylesheet">
	<!-- <link href="css/bootstrap.min.css" rel="stylesheet">-->
	
	<!--external css-->
	<link href="../assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
	<link rel="stylesheet" href="../css/flexslider.css"/>
	<link href="../assets/bxslider/jquery.bxslider.css" rel="stylesheet" />
	<link rel="stylesheet" href="../css/animate.css">
	<link rel="stylesheet" href="../assets/owlcarousel/owl.carousel.css">
	<link rel="stylesheet" href="../assets/owlcarousel/owl.theme.css">
	
	<link href="../css/superfish.css" rel="stylesheet" media="screen">
	<link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
	<!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'> -->
	
	
	<!-- Custom styles for this template -->
	<link rel="stylesheet" type="text/css" href="../css/component.css">
	<link href="../css/style.css" rel="stylesheet">
	<link href="../css/style-responsive.css" rel="stylesheet" />
	
	<link rel="stylesheet" type="text/css" href="../css/parallax-slider/parallax-slider.css" />
	<script type="text/javascript" src="../js/parallax-slider/modernizr.custom.28468.js"></script>
	<script src="../js/jquery-1.8.3.min.js"></script>	
</head>

<body>

<!--header start-->
<header class="head-section">
	<div class="navbar navbar-default navbar-static-top container">
		<div class="navbar-header">
			<button class="navbar-toggle" data-target=".navbar-collapse" data-toggle="collapse" type="button">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="../home.do" style="text-align:center;">도담도담<br>어린이문화센터</span></a>
		</div>
		<div class="navbar-collapse collapse">
			<c:choose>
				<c:when test="${member_dto != null}">
					<div style="text-align:right;">
						<b>반갑습니다, '${member_dto.p_name} (${member_dto.p_id})' 님</b>
					</div>
				</c:when>
			<c:otherwise><div></div></c:otherwise>
			</c:choose>
			<ul class="nav navbar-nav">
				<li class="dropdown">
					<a class="dropdown-toggle" data-close-others="false" data-delay="0" data-hover=
					"dropdown" data-toggle="dropdown" href="#">프로그램 <i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="../program/list.do">프로그램 소개</a>
						</li>
						<li>
							<a href="../teacher/list.do">강사 소개</a>
						</li>
						<li>
							<a href="../tendency/list.do">MBTi란?</a>
						</li>
						<li>
							<a href="../test/main.do">MBTi테스트하기</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="../performance/list.do">공연  </a>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-close-others="false" data-delay="0" data-hover=
					"dropdown" data-toggle="dropdown" href="#">시설 <i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
					<!-- 
						<li>
							<a href="../#">프로그램 예약</a>
						</li>
						<li>
							<a href="../#">공연 예매</a>
						</li>
						 -->
						 
						<li>
							<a href="../where/classroom.do">공간 소개</a> <!-- where/about.do -->
						</li>
						<li>
							<a href="../rent/info.do">대관 신청</a>
						</li>
					</ul>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-close-others="false" data-delay="0" data-hover=
					"dropdown" data-toggle="dropdown" href="#">소식 <i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="../notice/list.do">공지사항</a>
						</li>
						<li>
							<a href="../faq/list.do">자주 묻는 질문</a>
						</li>
						<li>
							<a href="../qna/list.do">문의하기</a>
						</li>
					</ul>
				</li>
				<li class="dropdown">
					<!-- 마이페이지의 경우 글자에 링크거는것보다 마이페이지 아이콘 이미지에 링크거는것도 좋을것 같음 -->
					<c:choose>
						<c:when test="${member_dto != null}">
							<!-- 관리자에게만 보이는 페이지 -->
							<c:if test="${member_dto.p_id == 'webmaster'}">
							<a class="dropdown-toggle" data-close-others="false" data-delay="0" data-hover=
								"dropdown" data-toggle="dropdown" href="#">관리자페이지 <i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="../member/memberList.do">회원목록</a>
								</li>
								<li>
									<a href="../tendency/adminlist.do">MBTi타입 목록</a>
								</li>
							</ul>
							</c:if>
							
							<!-- 일반 회원에게만 보이는 페이지 -->
							<c:if test="${member_dto.p_id != 'webmaster'}">
							<a class="dropdown-toggle" data-close-others="false" data-delay="0" data-hover=
							"dropdown" data-toggle="dropdown" href="#">마이페이지 <i class="fa fa-angle-down"></i>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="../program/myLikeList.do">나의 관심내역</a>
								</li>
								<li>
									<a href="../cart/list">나의 장바구니</a>
								</li>
								<li>
									<a href="../order/myorderlist">나의 예매내역</a>
								</li>
								<li>
									<a href="../member_kid/kidList.do">자녀회원 등록/목록</a>
								</li>
								<li>
									<a href="../member/memberModify.do">개인정보 수정</a>
								</li>
								<li>
									<a href="../member/memberWithdraw.do">회원탈퇴</a>
								</li>
							</ul>
							</c:if>
						</c:when>
					</c:choose>
					</li>
					<li>
						<c:choose>
							<c:when test="${member_dto == null}">
								<a href="../member/login.do">로그인</a>
							</c:when>
							<c:otherwise>
								<a href="../member/logout.do">로그아웃</a>
							</c:otherwise>
						</c:choose>
					</li>
				<li><input class="form-control search" placeholder=" Search" type="text" style="cursor:pointer;"></li>
			</ul>
		</div>
	</div>
</header>
<!--header end-->
