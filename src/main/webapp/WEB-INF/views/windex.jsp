<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/headerfooter/header.jsp" %>
<!-- 메인이미지 -->
<div id="main_img">
	<!-- <img src="/images/main_img.jpg"
		style="border-radius: 20px 20px 20px 20px; border: 2px solid white;"> -->
	<div id="view" style="position:relative; width:970px; height:350px; border:1px solid red; overflow:hidden">
		<div id="imgs" style="position:absolute; left:0px; top:0px; width:4840px; height:350px; text-align:left">
			<c:forEach items="${bannerList}" var="bannerVO">
				<img src="/product_images/${bannerVO.IMAGE}" width="968">
			</c:forEach>
		</div>
	</div>
</div>
<!-- 신상품 -->
<div id="front">
	<h2>New Item</h2>
	<div id="bestProduct">
		<c:forEach items="${newProductList}" var="productVO">
			<div id="item">
				<a href="productDetail?pseq=${productVO.PSEQ}">
					<img src="/product_images/${productVO.IMAGE}">
					<h3>
						${productVO.NAME} -
						<fmt:formatNumber value="${productVO.PRICE2}" type="currency" />
					</h3>
				</a>
			</div>
		</c:forEach>
	</div>
</div>
<div class="clear"></div>
<!-- 베스트 상품 -->
<div id="front">
	<h2>Best Item</h2>
	<div id="bestProduct">
		<c:forEach items="${bestProductList}" var="productVO">
			<div id="item">
				<a href="productDetail?pseq=${productVO.PSEQ}"> 
					<img src="/product_images/${productVO.IMAGE}">
					<h3>
						${productVO.NAME} -
						<fmt:formatNumber value="${productVO.PRICE2}" type="currency" />
					</h3>
				</a>
			</div>
		</c:forEach>
	</div>
</div>
<div class="clear"></div>
<%@ include file="include/headerfooter/footer.jsp" %>