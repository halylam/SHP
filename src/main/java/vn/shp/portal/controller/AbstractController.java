package vn.shp.portal.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public abstract class AbstractController {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
