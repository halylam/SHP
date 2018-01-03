$(document).ready(function(){
	
	$(".portal-department-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var departmentId = row.data("portal-department-id");
		var url = BASE_URL + "portal/department/edit?departmentId=" + departmentId;
		
		// Redirect to page detail
		redirect(url);
	});
	
	$(".portal-department-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var departmentId = row.data("portal-department-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/department/delete?departmentId=" + departmentId;
    			redirect(url);
			}	
    	});
	});
});

