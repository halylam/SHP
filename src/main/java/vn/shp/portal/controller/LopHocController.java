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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.entity.LopHoc;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.KhoaHocModel;
import vn.shp.portal.model.LopHocModel;
import vn.shp.portal.model.LopHocModel;
import vn.shp.portal.service.HocVienService;
import vn.shp.portal.service.KhoaHocService;
import vn.shp.portal.service.LoaiLopHocService;
import vn.shp.portal.service.LopHocHocVienService;
import vn.shp.portal.service.LopHocService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/lophoc")
public class LopHocController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    LopHocService lopHocService;

    @Autowired
    LoaiLopHocService loaiLopHocService;

    @Autowired
    KhoaHocService khoaHocService;

    @Autowired
    HocVienService hocVienService;

    @Autowired
    LopHocHocVienService lopHocHocVienService;

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOPHOC_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        LopHocModel bean = new LopHocModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
        model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
        model.addAttribute("lopHocModel", bean);
        return "portal/lophoc/lophoc_create";
    }

    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "lopHocModel") @Valid LopHocModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            LopHoc lopHoc = bean.getEntity();
            lopHoc.setLopHocCode(lopHoc.getLopHocCode().toUpperCase());
            lopHoc.setLopHocName(lopHoc.getLopHocName().toUpperCase());
            lopHocService.save(lopHoc);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.lopHocCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
        model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
        return "portal/lophoc/lophoc_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOPHOC_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          LopHocModel bean, Model model) {
        LopHoc lopHoc = lopHocService.findOne(id);
        bean.setEntity(lopHoc);
        model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
        model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
        model.addAttribute("lstHocVien", hocVienService.findAll());
        List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(lopHoc.getLopHocId());
        bean.setListLhhv(lopHocHocVienList);
        LopHocHocVien lhhv = new LopHocHocVien();
        lhhv.setLopHoc(lopHoc);
        bean.setLhhv(lhhv);
        model.addAttribute("lopHocModel", bean);
        bean.setPageMode(PageMode.EDIT);
        return "portal/lophoc/lophoc_edit";
    }

    @RequestMapping(value = "/ajax_new_lhhv", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView addLopHocHocVien(Model model, LopHocModel bean, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            lopHocHocVienService.save(bean.getLhhv());
            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
        } catch (Exception e) {
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
        }
        List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(bean.getLhhv().getLopHoc().getLopHocId());
        bean.setListLhhv(lopHocHocVienList);
        LopHocHocVien lhhv = new LopHocHocVien();
        lhhv.setLopHoc(lopHocService.findOne(bean.getLhhv().getLopHoc().getLopHocId()));
        bean.setLhhv(lhhv);
        model.addAttribute("lstHocVien", hocVienService.findAll());
        model.addAttribute("lopHocModel", bean);
        messageLst.add(msgInfo);
        model.addAttribute(CoreConstant.MSG_LST, messageLst);
        return new ModelAndView("/portal/lophoc/lophoc_hocvien :: content");
    }

    @RequestMapping(value = "/ajax_delete_lhhv", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView removeLopHocHocVien(Model model, @RequestParam(value = "id") Long id, Locale locale) {
        LopHocModel bean = new LopHocModel();
        LopHocHocVien entity = lopHocHocVienService.findOne(id);
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            lopHocHocVienService.delete(id);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
        } catch (Exception e) {
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
        }

        List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(entity.getLopHoc().getLopHocId());
        bean.setListLhhv(lopHocHocVienList);

        LopHocHocVien lhhv = new LopHocHocVien();
        lhhv.setLopHoc(entity.getLopHoc());
        bean.setLhhv(lhhv);

        model.addAttribute("lstHocVien", hocVienService.findAll());
        model.addAttribute("lopHocModel", bean);
        messageLst.add(msgInfo);
        model.addAttribute(CoreConstant.MSG_LST, messageLst);
        return new ModelAndView("/portal/lophoc/lophoc_hocvien :: content");
    }

    /**
     * EDIT - POST
     */
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(LopHocModel bean, Model model, Locale locale, BindingResult bindingResult) {
        LopHoc entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setLopHocCode(entity.getLopHocCode().toUpperCase());
            entity.setLopHocName(entity.getLopHocName().toUpperCase());
            lopHocService.save(entity);
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
        model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
        model.addAttribute("lstKhoaHoc", khoaHocService.findAll());

        List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(entity.getLopHocId());
        bean.setListLhhv(lopHocHocVienList);
        LopHocHocVien lhhv = new LopHocHocVien();
        lhhv.setLopHoc(entity);
        bean.setLhhv(lhhv);
        model.addAttribute("lstHocVien", hocVienService.findAll());
        model.addAttribute("khoaHocModel", bean);
        return "portal/lophoc/lophoc_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LOPHOC_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            lopHocService.delete(id);
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
        return "redirect:/portal/lophoc/list";
    }
}
