package vn.shp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.shp.app.service.ErrorService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

	@Autowired
	private ErrorService errorService;

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public String renderErrorPage(final Model model, final HttpServletRequest request) {

		// Get the Http error code.
		final int error_code = getHttpStatusCode(request);

		// Generates Error message for corresponding Http Error Code.
		final String error_message = errorService.generateErrorMessage(error_code);

		model.addAttribute("errorMsg", error_message);
		model.addAttribute("errorCode", error_code);
		return "error";
	}

	private int getHttpStatusCode(final HttpServletRequest request) {
		return (int) request.getAttribute("javax.servlet.error.status_code");
	}
}
