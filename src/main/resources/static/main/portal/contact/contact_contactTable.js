$(document).ready(function(){
	
	$(".box-table").datatables({
		url		: BASE_URL + 'portal/contact/ajaxList',
		type	: 'POST',
		setData	: setConditionSearch
	});
    $(".contactlist-table").datatables({
        url		: BASE_URL + 'portal/contact/contactList',
        type	: 'POST',
        setData	: setConditionSearch
    });

    $(".portal-usercontact-edit").on("click", function(event){
        event.preventDefault();

        // Prepare data
        var row = $(this).parents("tr");
        var accountId = row.data("portal-usercontact-id");
        var url = BASE_URL + "portal/contact/edit?userId=" + accountId;

        // Redirect to page detail
        redirect(url);
    });

});

function setConditionSearch() {
	var condition = {};
	condition["entity.username"] = $("#user_username").val();
	condition["entity.email"] = $("#user_email").val();
	condition["entity.fullName"] = $("#user_fullName").val();
	return condition;
}
