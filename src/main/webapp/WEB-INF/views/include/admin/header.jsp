<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>main</title>
	<link href="/admin/admin.css" rel="stylesheet">
	<script src="/admin/admin.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$('#myButton').click(function(){
			var formselect = $('#fileupForm')[0]; //지목된 폼을 변수에 저장
			var formdata = new FormData(formselect); //전송용 폼 객체에 다시 저장
			//웹페이지 이동 또는 새로고침이 필요 없는 request 요청
			$.ajax({
				url:"<%=request.getContextPath()%>/fileup",
				//Controller가 있는 경로에서 fileup이라는 리퀘스트를 찾아 이동하기 위한 경로 설정
				type : "POST",
				enctype : "multipart/form-data",
				async : false,
				data : formdata,
				timeout : 10000,
				contentType : false,
				processData : false,
				success : function(data){ //result -> data
					if(data.STATUS == 1){
						$("#filename").append("<div>"+data.FILENAME+"</div>");
						$("#image").val(data.FILENAME);
						$("#filename").append(
								"<img src='product_images/"+data.FILENAME+"' height='150'>");
					}	
				},
				error : function(){
					alert("실패");
				}
			});
		});
	});
	</script>
</head>
<body>
	<div id="wrap">
		<header>
			<div id="logo">
				<a href="admin">
					<img src="/admin/bar_01.gif" style="float: left;">
					<img src="/admin/text.gif">
				</a>
			</div>
			<input type="button" class="btn" value="logout" style="float: right;"
				onClick="location.href='adminLogout'">
		</header>
		<div class="clear"></div>