
$("#btn-submit").click(function(){
	$('#user-form').submit();
});

$(document).ready(function(){
	
});

$(document).on('change', '.branchSelectClass', function(e) {
	var userId = $("#userId").val();
	if (userId == undefined) {
		userId = null;
	}
	var branchId = this.options[e.target.selectedIndex].value;
});

