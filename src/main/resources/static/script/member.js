function go_next(){
	if(document.contractFrm.okon[1].checked==true){
		alert("회원 약관에 동의하셔야 회원으로 가입이 가능합니다.");
	}else{
		document.contractFrm.action = "joinForm";
		document.contractFrm.submit();
	}
}

function idcheck(){
	if(document.formm.id.value==""){
		alert("아이디를 입력하고 중복체크를 진행하세요.");
		document.formm.id.focus();
		return;
	}
	var url = "idCheckForm?id="+document.formm.id.value;
	var opt = "toolbar=no, menubar=no, resizable=no, width=500, height=250, scrollbars=no";
	window.open(url,"IdCheck",opt);
}