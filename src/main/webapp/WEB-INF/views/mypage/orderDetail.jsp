<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resources/headerfooter/header.jsp" %>
<%@ include file="/resources/sub03/sub_image_menu.jsp" %>
<article>
	<h2>My Page(주문 상세 정보)</h2>
	<form>
		<h3>주문자 정보</h3>
		<table id="cartList">
			<tr>
				<th>주문일자</th>
				<th>주문번호</th>
				<th>주문자</th>
				<th>주문총액</th>
			</tr>
			<tr>
				<td><fmt:formatDate value="${orderDetail.indate}" type="date" /></td>
				<td>${orderDetail.oseq}</td>
				<td>${orderDetail.mname}</td>
				<td><fmt:formatNumber value="${totalPrice}" type="currency" /></td>
			</tr>
			<tr>
				<td colspan="4">배송지 : ${orderDetail.address1} ${orderDetail.address2} ${orderDetail.address3}</td>
			</tr>
		</table>
		<h3>주문 상품 정보</h3>
		<table id="cartList">
			<tr>
				<th>상품명</th>
				<th>상품별 주문번호</th>
				<th>수량</th>
				<th>가격</th>
				<th>처리 상태</th>
			</tr>
			<c:forEach items="${orderList}" var="orderVO">
				<tr>
					<td>${orderVO.pname}</td>
					<td>${orderVO.odseq}</td>
					<td>${orderVO.quantity}</td>
					<td>
						<fmt:formatNumber value="${orderVO.price2*orderVO.quantity}" type="currency" />
					</td>
					<td>
						<c:if test="${orderVO.result=='1'}">진행중</c:if>
						<c:if test="${orderVO.result=='2'}">
							<span style="color: orange">처리 완료</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="clear"></div>
		<div id="buttons" style="float: right">
			<input type="button" value="뒤로" class="cancel" onClick="history.go(-1)">
		</div>
	</form>
</article>
<%@ include file="/resources/headerfooter/footer.jsp" %>