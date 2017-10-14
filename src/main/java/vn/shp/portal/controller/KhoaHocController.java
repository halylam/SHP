package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.KhoaHocModel;
import vn.shp.portal.model.KhoaHocModel;
import vn.shp.portal.service.BacDaoTaoService;
import vn.shp.portal.service.KhoaHocService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/khoahoc")
public class KhoaHocController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    KhoaHocService khoaHocService;

    @Autowired
    BacDaoTaoService bacDaoTaoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        KhoaHocModel bean = new KhoaHocModel();
        List<KhoaHoc> lstData = khoaHocService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        return "portal/khoahoc/khoahoc_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        KhoaHocModel bean = new KhoaHocModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
        model.addAttribute("khoaHocModel", bean);
        return "portal/khoahoc/khoahoc_create";
    }

    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "khoaHocModel") @Valid KhoaHocModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            KhoaHoc khoaHoc = bean.getEntity();
            khoaHoc.setKhoaHocCode(khoaHoc.getKhoaHocCode().toUpperCase());
            khoaHoc.setKhoaHocName(khoaHoc.getKhoaHocName().toUpperCase());
            khoaHocService.save(khoaHoc);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.khoaHocCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
            model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
        }
        return "portal/khoahoc/khoahoc_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          KhoaHocModel bean, Model model) {
        KhoaHoc khoaHoc = khoaHocService.findOne(id);
        bean.setEntity(khoaHoc);
        model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
        bean.setPageMode(PageMode.EDIT);
        return "portal/khoahoc/khoahoc_edit";
    }

    /**
     * EDIT - POST
     */
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(KhoaHocModel bean, Model model, Locale locale, BindingResult bindingResult) {
        KhoaHoc entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setKhoaHocCode(entity.getKhoaHocCode().toUpperCase());
            entity.setKhoaHocName(entity.getKhoaHocName().toUpperCase());
            khoaHocService.save(entity);
            String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        } catch (Exception e) {
            e.printStackTrace();
            messageLst.setStatus(Message.ERROR);
            String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
        return "portal/khoahoc/khoahoc_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            khoaHocService.delete(id);
            String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        } catch (Exception e) {
            e.printStackTrace();
            messageLst.setStatus(Message.ERROR);
            String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        return "redirect:/portal/khoahoc/list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
    @RequestMapping(value = "/dangky", method = GET)
    public String getDangKy() {
        return "/portal/khoahoc/khoahoc_dangkykhoahoc";
    }
}
