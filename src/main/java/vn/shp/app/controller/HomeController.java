package vn.shp.app.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.service.ErrorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

//import vn.shp.portal.config.KeycloakClientConfig;

@Controller
@Log4j
public class HomeController {
	

	
//	@Autowired
//	KeycloakClientConfig kcClientConfig;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/portal";
	}
	
	@RequestMapping(value = "/logout", method = GET)
	public String getList(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null){
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			return "redirect:/login?logout";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = GET)
	public ModelAndView login(Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}

	@Autowired
	private ErrorService errorService;

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public String renderErrorPage(final Model model, final HttpServletRequest request) {
log.info("public String renderErrorPage(final Model model, final HttpServletRequest request)");
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
