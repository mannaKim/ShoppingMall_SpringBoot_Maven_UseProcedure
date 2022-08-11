<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>main</title>
	<link href="resources/admin/admin.css" rel="stylesheet">
	<script src="resources/admin/admin.js"></script>
</head>
<body>
	<div id="wrap">
		<header>
			<div id="logo">
				<a href="admin">
					<img src="resources/admin/bar_01.gif" style="float: left;">
					<img src="resources/admin/text.gif">
				</a>
			</div>
			<input type="button" class="btn" value="logout" style="float: right;"
				onClick="location.href='adminLogout'">
		</header>
		<div class="clear"></div>