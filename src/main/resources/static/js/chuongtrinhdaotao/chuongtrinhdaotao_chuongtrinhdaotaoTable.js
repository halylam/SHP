$(document).ready(function(){
	

	
	$(".portal-chuongtrinhdaotao-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var accountId = row.data("portal-chuongtrinhdaotao-id");
		var url = BASE_URL + "portal/chuongtrinhdaotao/edit?chuongTrinhDaoTaoId=" + accountId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-chuongtrinhdaotao-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var chuongtrinhdaotaoId = row.data("portal-chuongtrinhdaotao-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/chuongtrinhdaotao/delete?chuongTrinhDaoTaoId=" + chuongtrinhdaotaoId;
    			redirect(url);
			}	
    	});
	});
});

function setConditionSearch() {
	var condition = {};
	condition["entity.chuongTrinhDaoTaoName"] = $("#chuongtrinhdaotao_chuongtrinhdaotaoName").val();
	condition["entity.chuongTrinhDaoTaoCode"] = $("#chuongtrinhdaotao_chuongtrinhdaotaoCode").val();
	return condition;
}
