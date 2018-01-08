package vn.shp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.shp.app.config.UserProfile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
public abstract class AbstractController {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    UserProfile userProfile;

    @ModelAttribute(value = "userProfile")
    public UserProfile getUserProfile() {
        return userProfile;
    }

}
