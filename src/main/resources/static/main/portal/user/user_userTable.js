$(document).ready(function(){
	
	$(".box-table").datatables({
		url		: BASE_URL + 'portal/user/ajaxList',
		type	: 'POST',
		setData	: setConditionSearch
	});
	
	$(".portal-user-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var accountId = row.data("portal-user-id");
		var url = BASE_URL + "portal/user/edit?userId=" + accountId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-user-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var userId = row.data("portal-user-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/user/delete?userId=" + userId;
    			redirect(url);
			}	
    	});
	});
});

function setConditionSearch() {
	var condition = {};
	condition["entity.username"] = $("#user_username").val();
	condition["entity.email"] = $("#user_email").val();
	condition["entity.fullName"] = $("#user_fullName").val();
	return condition;
}
