package vn.shp.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

//import vn.shp.portal.config.KeycloakClientConfig;

@Controller
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

}
