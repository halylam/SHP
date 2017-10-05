package vn.shp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

	@Autowired
	private MessageSource messageSource;

	public String generateErrorMessage(final int error_code) {
		String message = "";
		switch (error_code) {
		case 400:
			message = messageSource.getMessage("400", null, null);
			break;
		case 401:
			message = messageSource.getMessage("401", null, null);
			break;
		case 403:
			message = messageSource.getMessage("403", null, null);
			break;
		case 404:
			message = messageSource.getMessage("404", null, null);
			break;
		case 405:
			message = messageSource.getMessage("405", null, null);
			break;
		case 406:
			message = messageSource.getMessage("406", null, null);
			break;
		case 407:
			message = messageSource.getMessage("407", null, null);
			break;
		case 408:
			message = messageSource.getMessage("408", null, null);
			break;
		case 409:
			message = messageSource.getMessage("409", null, null);
			break;
		case 410:
			message = messageSource.getMessage("410", null, null);
			break;
		case 411:
			message = messageSource.getMessage("411", null, null);
			break;
		case 412:
			message = messageSource.getMessage("412", null, null);
			break;
		case 413:
			message = messageSource.getMessage("413", null, null);
			break;
		case 414:
			message = messageSource.getMessage("414", null, null);
			break;
		case 415:
			message = messageSource.getMessage("415", null, null);
			break;
		case 416:
			message = messageSource.getMessage("416", null, null);
			break;
		case 417:
			message = messageSource.getMessage("417", null, null);
			break;
		case 500:
			message = messageSource.getMessage("500", null, null);
			break;
		case 501:
			message = messageSource.getMessage("501", null, null);
			break;
		case 502:
			message = messageSource.getMessage("502", null, null);
			break;
		case 503:
			message = messageSource.getMessage("503", null, null);
			break;
		case 504:
			message = messageSource.getMessage("504", null, null);
			break;
		case 505:
			message = messageSource.getMessage("505", null, null);
			break;
		case 511:
			message = messageSource.getMessage("511", null, null);
			break;
		// etc
		// Put in all Http error codes here.
		}
		return message;
	}
}
