$(document).ready(function(){
	
	$(".portal-group-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var groupId = row.data("portal-group-id");
		var url = BASE_URL + "portal/group/edit?groupId=" + groupId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-group-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var groupId = row.data("portal-group-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/group/delete?groupId=" + groupId;
    			redirect(url);
			}	
    	});
	});

	$(".portal-group-add").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var groupId = row.data("portal-group-id");
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
			if( result ){
				var url = BASE_URL + "portal/group/addGroupToUser?groupId=" + groupId;
				$(this).modal("hide");
				$("body").addClass("loading");
				redirect(url);
			}	
		});
	});
});

