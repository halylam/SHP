package vn.shp.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("user")
public class UserController {

	
	@RequestMapping(method = GET)
	public String getProfile(Model model) {

		return "user/profile";
	}
}
