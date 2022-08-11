<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/headerfooter/header.jsp" %>
<%@ include file="../include/sub01/sub_image_menu.html" %>
<article>
	<h2>Update Profile</h2>
	<form action="memberUpdate" method="post" name="formm">
		<fieldset>
			<label>User ID</label><input type="text" name="id" size="12" value="${dto.id}" readonly><br> 
			<label>Password</label><input type="password" name="pwd"><br> 
			<label>Retype Password</label><input type="password" name="pwdCheck"><br>
			<label>Name</label><input type="text" name="name"  value="${dto.name}"><br>
			<label>E-Mail</label><input type="text" name="email" value="${dto.email}"><br>
		</fieldset>
		<fieldset>
			 <!-- https://postcode.map.daum.net/guide -->
			 <label>Zip Code</label>
			<input type="text" id="sample6_postcode" placeholder="우편번호" name="zip_num" 
				value="${dto.zip_num}" readonly>
			<input type="button" onclick="sample6_execDaumPostcode()" class="dup" value="우편번호 찾기"><br>
			<label>Address</label>
			<input type="text" id="sample6_address" placeholder="주소" name="address1" size="50" 
				value="${dto.address1}" readonly><br>
			<label>detailAddress</label>
			<input type="text" id="sample6_detailAddress" placeholder="상세주소" name="address2" size="50" 
				value="${dto.address2}"><br>
			<label>extraAddress</label>
			<input type="text" id="sample6_extraAddress" placeholder="참고항목" name="address3" 
				value="${dto.address3}" readonly>

			<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script>
				function sample6_execDaumPostcode() {
					new daum.Postcode(
							{
								oncomplete : function(data) {
									// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

									// 각 주소의 노출 규칙에 따라 주소를 조합한다.
									// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
									var addr = ''; // 주소 변수
									var extraAddr = ''; // 참고항목 변수

									//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
									if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
										addr = data.roadAddress;
									} else { // 사용자가 지번 주소를 선택했을 경우(J)
										addr = data.jibunAddress;
									}

									// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
									if (data.userSelectedType === 'R') {
										// 법정동명이 있을 경우 추가한다. (법정리는 제외)
										// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
										if (data.bname !== ''
												&& /[동|로|가]$/g.test(data.bname)) {
											extraAddr += data.bname;
										}
										// 건물명이 있고, 공동주택일 경우 추가한다.
										if (data.buildingName !== ''
												&& data.apartment === 'Y') {
											extraAddr += (extraAddr !== '' ? ', '
													+ data.buildingName
													: data.buildingName);
										}
										// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
										if (extraAddr !== '') {
											extraAddr = ' (' + extraAddr + ')';
										}
										// 조합된 참고항목을 해당 필드에 넣는다.
										document
												.getElementById("sample6_extraAddress").value = extraAddr;

									} else {
										document
												.getElementById("sample6_extraAddress").value = '';
									}

									// 우편번호와 주소 정보를 해당 필드에 넣는다.
									document.getElementById('sample6_postcode').value = data.zonecode;
									document.getElementById("sample6_address").value = addr;
									// 커서를 상세주소 필드로 이동한다.
									document.getElementById(
											"sample6_detailAddress").focus();
								}
							}).open();
				}
			</script>
			<br>
			<label>Phone Number</label><input type="text" name="phone" value="${dto.phone}"><br>
		</fieldset>
		<div class="clear"></div>
		${message}
		<div id="buttons">
			<input type="submit" value="정보수정" class="submit">
			<input type="reset" value="취소" class="cancel">
		</div>
	</form>
</article>
<%@ include file="../include/headerfooter/footer.jsp" %>