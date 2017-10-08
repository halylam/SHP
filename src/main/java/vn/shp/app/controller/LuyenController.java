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
@RequestMapping("luyen")
public class LuyenController {
	

	@RequestMapping(value = "/quanlyhv", method = GET)
	public String quanLyHv(Model model, HttpServletRequest request) {
		return "portal/quanly/quanlyhv";
	}

	@RequestMapping(value = "/quanlygv", method = GET)
	public String quanLyGv(Model model, HttpServletRequest request) {
		return "portal/quanly/quanlygv";
	}

	@RequestMapping(value = "/dangkyhocvienmoistep2", method = GET)
	public String dangkyHv2(Model model, HttpServletRequest request) {
		return "portal/hocvien/hocvien_create_step2";
	}
}
