package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.Location;
import vn.shp.app.utils.Utils;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/hocvien")
public class HocVienController {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    @Autowired
    private MessageSource messageSource;

    @Autowired
    SystemConfig systemConfig;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        return "portal/hocvien/hocvien_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        return "portal/hocvien/hocvien_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit", method = GET)
    public String getEdit() {
        return "portal/hocvien/hocvien_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete() {
        return "redirect:/portal/hocvien/list";
    }


    //------AJAX-----
    @RequestMapping(value = "/ajax_loadLocationDet", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, String> getLocationDetail(@RequestParam(value = "parentCode") String parentCode) {
        Map<String, String> results = new LinkedHashMap<>();
        if (parentCode != null) {
            List<Location> lstLocUnit = systemConfig.getLstDebLoc();
            if (!CollectionUtils.isEmpty(lstLocUnit)) {
                for (Location locUnit : lstLocUnit) {
                    if (parentCode.equals(locUnit.getParentCode())) {
                        results.put(locUnit.getLocCode(), locUnit.getLocName().toUpperCase());
                    }
                }
            }
            return results;
        }

        return null;
    }

    @ModelAttribute(value = "locationCatalog")
    public List<Location> getListLocation() {
        List<Location> result = new ArrayList<>();
        List<Location> lstDebLoc = systemConfig.getLstDebLoc();
        for (Location location : lstDebLoc) {
            if (Utils.isNullOrEmpty(location.getParentCode())) {
                result.add(location);
            }
        }
        return result;
    }
}
