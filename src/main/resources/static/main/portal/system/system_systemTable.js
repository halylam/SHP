$(document).ready(function(){
	
	$(".box-table").datatables({
		url		: BASE_URL + 'portal/system/ajaxList',
		type	: 'POST',
		setData	: setConditionSearch
	});
	
	$(".portal-system-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var accountId = row.data("portal-system-id");
		var url = BASE_URL + "portal/system/edit?systemId=" + accountId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-system-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var systemId = row.data("portal-system-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/system/delete?systemId=" + systemId;
    			redirect(url);
			}	
    	});
	});
});

function setConditionSearch() {
	var condition = {};
	condition["entity.systemCode"] = $("#system_systemCode").val();
	condition["entity.systemName"] = $("#system_systemName").val();
	condition["entity.status"] = $("#system_status").val();
	return condition;
}
