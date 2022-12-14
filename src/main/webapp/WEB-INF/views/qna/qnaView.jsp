<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub04/sub_image_menu.jsp" %>
<article>
	<h2>1:1 고객 게시판</h2>
	<h3>고객님의 질문에 대해 운영자가 1:1 답변을 드립니다.</h3>
	<form name="formm" method="post">
		<table>
			<tr>
				<th width="100" align="center">제목</th>
				<td width="500">${qnaVO.SUBJECT}</td>
			</tr>
			<tr>
				<th align="center">등록일</th>
				<td align="left">
					<fmt:formatDate value="${qnaVO.INDATE}" type="date" />
				</td>
			</tr>
			<tr>
				<th align="center">질문 내용</th>
				<td align="left"><pre>${qnaVO.CONTENT}</pre></td>
			</tr>
			<tr>
				<th align="center">답변 내용</th>
				<td align="left">${qnaVO.REPLY}</td>
			</tr>
		</table>
		<div class="clear"></div>
		<div id="buttons" style="float: right">
			<input type="button" value="목록보기" class="submit" onClick="location.href='qnaList'">
			<input type="button" value="쇼핑 계속하기" class="cancel" onClick="location.href='/'">
		</div>
	</form>
</article>
<%@ include file="../include/headerfooter/footer.jsp" %>