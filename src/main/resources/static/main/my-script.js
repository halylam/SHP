
/**
 * Submit form with url and data
 * 
 * @param url
 * @param data
 * @returns
 */
function makePostRequest( url, data ) {
	var newForm = jQuery("<form>", {
        'action' : url,
        'method' : 'POST'
    });
	
	// add fields
	for (name in data) {
		newForm.append(jQuery("<input>", {	
	        "name" : name,
	        "value": data[name],
	        "type" : "hidden"
	    }));
	}
	
	newForm.appendTo(document.body);
	newForm.submit();
}

/**
 * Redirect url method GET
 * 
 * @param url
 * @returns
 */
function redirect(url) {
    var ua        = navigator.userAgent.toLowerCase(),
        isIE      = ua.indexOf('msie') !== -1,
        version   = parseInt(ua.substr(4, 2), 10);

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
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirm( msgConfirm, methodCallback) {
	bootbox.setLocale( APP_LOCALE );
	return bootbox.confirm( msgConfirm, methodCallback ); 
}

function activaTab(tabId){
    $('.nav-tabs a[href="#' + tabId + '"]').tab('show');
};