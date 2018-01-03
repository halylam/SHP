$(document).ready(function(){
	
	$(".portal-title-edit").on("click", function(event){
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var titleId = row.data("portal-title-id");
		var url = BASE_URL + "portal/title/edit?titleId=" + titleId;
		
		// Redirect to page detail
		redirect(url);
	});

    $(".portal-exchangerate-edit").on("click", function(event){
        event.preventDefault();

        // Prepare data
        var row = $(this).parents("tr");
        var id = row.data("portal-exchangerate-id");
        var url = BASE_URL + "portal/exchangerate/edit?id=" + id;

        // Redirect to page detail
        redirect(url);
    });
	
	$(".portal-title-delete").on("click", function( event ) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var titleId = row.data("portal-title-id");
		
		var msgConfirm = $(this).data("msg-confirm");
		
		popupConfirm( msgConfirm, function(result) {
    		if( result ){
    			var url = BASE_URL + "portal/title/delete?titleId=" + titleId;
    			redirect(url);
			}	
    	});
	});
});

