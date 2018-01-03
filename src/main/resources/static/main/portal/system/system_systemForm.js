$("#btn-submit").click(function(){
	var imgBase64 = $('#blah').attr("src");
    $('#imgId').val(imgBase64);
	
	$('#system-form').submit();
});