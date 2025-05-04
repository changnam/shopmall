function addToCart(bookid)
{
   
   if (confirm("장바구니에 도서를 추가합니까?")==true){
	
		document.addForm.action="/cart/book/"+bookid;			
   		document.addForm.submit();
	
   
   }
   
    
}
function removeFromCart(bookid, cartId) {
	console.log("삭제요청됨111"+bookid+","+cartId);
	if (confirm("도서를 삭제하시겠습니까?")==true){
	   document.removeForm.action = "/cart/book/"+bookid;		
	   document.removeForm.submit();	  
	}
}
function clearCart(cartId) {
	if (confirm("모든 도서를 장바구니에서 삭제합니까?")==true){
		
	   document.clearForm.submit();
	   setTimeout('location.reload()',10); 
	
	}
	
}

function deleteConfirm(id){ 
   if (confirm("삭제 합니다!!") == true) location.href ="/books/delete?id="+id; 
   else  return;
} 