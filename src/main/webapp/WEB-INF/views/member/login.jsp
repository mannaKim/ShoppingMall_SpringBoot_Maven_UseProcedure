<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.html" %>
<article>
	<form method="post" action="login" name="loginFrm">
		<fieldset>
			<legend>Login</legend>
			<label>User ID</label><input type="text" name="id" value="${dto.id}"><br>
			<label>Password</label><input type="password" name="pwd"><br>
		</fieldset>
		<div id="buttons">
			<input type="submit" value="Login" class="submit">
			<input type="button" value="Join" class="cancel" onClick="location.href='contract'">
			<input type="button" value="아이디/비밀번호 찾기" class="submit" onClick="">
		</div>
	</form>
	${message}
</article>
<%@ include file="../include/headerfooter/footer.jsp" %>