$("#removeRole").click(function() {
	$("input[name=checkRoleRight]").each(function() {
		if (this.checked) {
			this.checked = false;
			var row = $(this).closest("tr");
			var table = $(this).closest("table");
			row.detach();
			if (table.is("#tableRightRole")) {
				$("#tableLeftRole").append(row);
			}
			row.fadeIn();
			this.name = "checkRoleLeft";
			this.id = "checkRoleLeft";
		}

	});
});

$("#removeAllRole").click(function() {
	$("input[name=checkRoleRight]").each(function() {
		this.checked = false;
		var row = $(this).closest("tr");
		var table = $(this).closest("table");
		row.detach();
		if (table.is("#tableRightRole")) {
			$("#tableLeftRole").append(row);
		}
		row.fadeIn();
		this.name = "checkRoleLeft";
		this.id = "checkRoleLeft";
	});
});

$("#addRole").click(function() {
	$("input[name=checkRoleLeft]").each(function() {
		if (this.checked) {
			this.checked = false;
			var row = $(this).closest("tr");
			var table = $(this).closest("table");
			row.detach();
			if (table.is("#tableLeftRole")) {
				$("#tableRightRole").append(row);
			}
			row.fadeIn();
			this.name = "checkRoleRight";
			this.id = "checkRoleRight";
		}

	});
});

$("#addAllRole").click(function() {
	$("input[name=checkRoleLeft]").each(function() {
		this.checked = false;
		var row = $(this).closest("tr");
		var table = $(this).closest("table");
		row.detach();
		if (table.is("#tableLeftRole")) {
			$("#tableRightRole").append(row);
		}
		row.fadeIn();
		this.name = "checkRoleRight";
		this.id = "checkRoleRight";
		
	});
});

$("#btn-submit").click(function(){
	$("input[name=checkRoleRight]").each(function() {
		this.checked = true;
	});
});

