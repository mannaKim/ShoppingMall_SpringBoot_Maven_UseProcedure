<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/admin/header.jsp" %>
<%@ include file="/resources/admin/sub_menu.jsp" %>
<article>
	<h2>Q&amp;A 게시판</h2>
	<form name="frm" method="post">
		<input type="hidden" name="qseq" value="${qnaVO.qseq}">
		<table id="orderList">
			<tr>
				<th width="20%">제목</th>
				<td align="left">${qnaVO.subject} [상태 : ${qnaVO.rep}]</td>
			</tr>
			<tr>
				<th>등록일</th>
				<td align="left">
					<fmt:formatDate value="${qnaVO.indate}" type="date" />
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td align="left"><pre>${qnaVO.content}</pre></td>
			</tr>
		</table>
		<!-- 관리자 답글란 -->
		<c:choose>
			<c:when test="${qnaVO.rep=='1'}">
			<!-- 관리자 답변 전 -->
				<table id="orderList">
					<tr>
						<td colspan="2">
							<img src="resources/admin/opinionimg01.gif">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea name="reply" rows="3" cols="50"></textarea>
							<input type="button" class="btn" value="저장" onClick="go_rep()">
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
			<!-- 관리자 답변 후 -->
				<table id="orderList">
					<tr>
						<th>댓글</th>
						<td>${qnaVO.reply}</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
		<input type="button" value="목록" class="btn" onClick="location.href='adminQnaList'">
	</form>
</article>
<%@ include file="/resources/admin/footer.jsp" %>