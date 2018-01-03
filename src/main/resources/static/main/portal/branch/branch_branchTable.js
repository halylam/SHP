$(document).ready(function(){
	
	$(".box-table").datatables({
		url		: BASE_URL + 'portal/branch/ajaxList',
		type	: 'POST',
		setData	: setConditionSearch
	});
	
	$(".portal-branch-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var accountId = row.data("portal-branch-id");
		var url = BASE_URL + "portal/branch/edit?branchId=" + accountId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-branch-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var branchId = row.data("portal-branch-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/branch/delete?branchId=" + branchId;
    			redirect(url);
			}	
    	});
	});
});

function setConditionSearch() {
	var condition = {};
	condition["entity.branchName"] = $("#branch_branchName").val();
	condition["entity.branchCode"] = $("#branch_branchCode").val();
	return condition;
}
