package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.shp.app.entity.LopHoc;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.LopHocModel;
import vn.shp.portal.service.LopHocService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("portal/lophoc")
public class LopHocController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    LopHocService lopHocService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        LopHocModel bean = new LopHocModel();
        List<LopHoc> lstData = lopHocService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        return "portal/lophoc/lophoc_list";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        return "portal/lophoc/lophoc_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit", method = GET)
    public String getEdit() {
        return "portal/lophoc/lophoc_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete() {
        return "redirect:/portal/lophoc/list";
    }
}
