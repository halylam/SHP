package vn.shp.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.ChuyenNganhModel;
import vn.shp.portal.service.ChuyenNganhService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/chuyennganh")
public class ChuyenNganhController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ChuyenNganhService chuyenNganhService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        ChuyenNganhModel bean = new ChuyenNganhModel();
        List<ChuyenNganh> lstData = chuyenNganhService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        return "portal/chuyennganh/chuyennganh_list";
    }

    @RequestMapping(value = "/ajaxList", method = GET)
    @ResponseBody
    public String ajaxList() {
        return "portal/chuyennganh/chuyennganh_table";
    }

    @RequestMapping(value = "/list", method = POST)
    public String postList() {
        return "portal/chuyennganh/chuyennganh_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        return "portal/chuyennganh/chuyennganh_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_EDIT')")
    @RequestMapping(value = "/edit", method = GET)
    public String getEdit() {
        return "portal/chuyennganh/chuyennganh_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete() {
        return "redirect:/portal/chuyennganh/list";
    }
}
