package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.shp.portal.entity.*;
import vn.shp.portal.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("portal")
@PreAuthorize("isAuthenticated()")
public class PortalHomeController  extends AbstractController {


	@Autowired
	PortalUserService portalUserService;

    @RequestMapping(method = GET)
    public String getHome(Model model, HttpServletRequest request) {
        String username = "hant1";
        model.addAttribute("username", username);
        return "portal/index";
    }

    @RequestMapping(value = "/logout", method = GET)
    public String getList(Model model, HttpServletRequest request) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/error", method = GET)
    public String getError(Model model, HttpServletRequest request) {

        String username = request.getUserPrincipal().getName();
        model.addAttribute("username", username);
        return "portal/error";
    }


    @RequestMapping(value =  "/detail", method = RequestMethod.GET)
    public String gethelpdeskAllProcessingRequest1(Model model) {

        return "portal/index_processing :: frprocessing";

    }

    @RequestMapping("/requests/hist/{id}")
    public String getRequestHistById(@PathVariable Long id) {
            return "redirect:/document/request/completed/" + id ;
    }




    @RequestMapping("/requests/{id}/created")
    public String getCreatedRequestById(@PathVariable Long id, Model model) {
        return "redirect:portal";
    }





}

