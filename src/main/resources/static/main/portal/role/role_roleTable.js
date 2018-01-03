$(document).ready(function(){
	
	$(".box-table").datatables({
		url		: BASE_URL + 'portal/role/ajaxList',
		type	: 'POST',
		setData	: setConditionSearch
	});
	
	$(".portal-role-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var accountId = row.data("portal-role-id");
		var url = BASE_URL + "portal/role/edit?roleId=" + accountId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-role-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var roleId = row.data("portal-role-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/role/delete?roleId=" + roleId;
    			redirect(url);
			}	
    	});
	});
});

function setConditionSearch() {
	var condition = {};
	condition["entity.roleCode"] = $("#role_roleCode").val();
	condition["entity.roleName"] = $("#role_roleName").val();
	condition["entity.status"] = $("#role_status").val();
	return condition;
}
