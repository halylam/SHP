/**
 * Redirect url method POST and data
 */
function submitPostData(url, data) {
	var newForm = jQuery("<form>", {
		'action' : url,
		'method' : 'POST'
	});

	for (name in data) {
		newForm.append(jQuery("<input>", {
			"name" : name,
			"value" : data[name],
			"type" : "hidden"
		}));
	}

	newForm.appendTo(document.body);
	newForm.submit();
}

/**
 * Redirect url method GET
 */
function redirect(url) {
	var ua = navigator.userAgent.toLowerCase(), isIE = ua.indexOf('msie') !== -1, version = parseInt(
			ua.substr(4, 2), 10);

	// Internet Explorer 8 and lower
	if (isIE && version < 9) {
		var link = document.createElement('a');
		link.href = url;
		document.body.appendChild(link);
		link.click();
	}

	// All other browsers can use the standard window.location.href (they don't lose HTTP_REFERER like IE8 & lower does)
	else {
		window.location.href = url;
	}
}

/**
 * Open popup confirm
 */
function popupConfirm(msgConfirm, methodCallback) {
	bootbox.setLocale(APP_LOCALE);
	return bootbox.confirm(msgConfirm, methodCallback);
}

/**
 * deAccent, uppercase and delete space
 */
function upperAndReSpaceAndDeAccent(se) {
	var input = deAccent($(se).val()).toUpperCase().replace(/\s/g,'');
	$(se).val(input);
}

/**
 * Uppercase and delete space
 */
function upperAndReSpace(se) {
	var input = $(se).val().toUpperCase().replace(/\s/g,'');
	$(se).val(input);
}

/**
 * Uppercase
 */
function upper(se) {
	var input = $(se).val().toUpperCase();
	$(se).val(input);
}

/**
 * deAccent
 */
function deAccent(str) {
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    return str;
}