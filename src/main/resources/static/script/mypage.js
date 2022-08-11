function go_cart(){
	if(document.formm.quantity.value==""){
		alert("수량을 입력하세요");
		document.formm.quantity.focus();
	}else{
		document.formm.action="cartInsert";
		document.formm.submit();
	}
}

function go_cart_delete(){
	var count = 0;
	if(document.formm.cseq.length == undefined){
		if(document.formm.cseq.checked==true) count++;
	}else{
		for(var i=0; i<document.formm.cseq.length; i++){
			if(document.formm.cseq[i].checked==true) count++;
		}
	}
	if(count==0)
		alert("삭제할 항목을 선택하세요.");
	else{
		document.formm.action="cartDelete";
		document.formm.submit();
		//jsp파일에 있는 체크된 checkbox들의 value들(cseq값들)이 배열로 전송됩니다.
	}
}

function go_order_insert(){
	document.formm.action="orderInsert";
	document.formm.submit();
}

function go_order(){
	document.formm.action="orderInsertOne";
	document.formm.submit();
}