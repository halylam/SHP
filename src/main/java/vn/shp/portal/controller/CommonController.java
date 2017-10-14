package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import vn.shp.app.config.Constants;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.Location;
import vn.shp.app.utils.Utils;
import vn.shp.portal.entity.AlfFile;
import vn.shp.portal.entity.JsonReturn;
import vn.shp.portal.service.AlfFileService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("common")
public class CommonController {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AlfFileService alfFileService;

    @InitBinder
    public void initBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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

    @RequestMapping(value = "/ajax_delete_file", method = RequestMethod.GET)
    public @ResponseBody
    JsonReturn deleteFile(@RequestParam(value = "id") Long id) {
        JsonReturn jsonReturn = new JsonReturn();
        jsonReturn.setStatus(Constants.FAIL);
        alfFileService.deleteById(id);
        AlfFile file = alfFileService.findOne(id);
        if(file == null){
            jsonReturn.setStatus(Constants.SUCCESS);
            jsonReturn.setResult(id);
        }
        return jsonReturn;
    }
}
