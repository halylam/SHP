package vn.shp.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import vn.shp.app.entity.BacDaoTao;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.BacDaoTaoModel;
import vn.shp.portal.service.BacDaoTaoService;
import vn.shp.portal.service.ChuyenNganhService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/bacdaotao")
public class BacDaoTaoController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    BacDaoTaoService bacDaoTaoService;

    @Autowired
    ChuyenNganhService chuyenNganhService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACDAOTAO_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        BacDaoTaoModel bean = new BacDaoTaoModel();
        List<BacDaoTao> lstData = bacDaoTaoService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        return "portal/bacdaotao/bacdaotao_list";
    }

    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") @Valid BacDaoTaoModel bean, BindingResult bindingResult, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        List<BacDaoTao> lstData = new ArrayList<>();
        if (bean != null) {
            lstData.addAll(bacDaoTaoService.searchByFilters(bean.getEntity().getBacDaoTaoName(), bean.getEntity().getBacDaoTaoCode()));
        } else {
            lstData.addAll(bacDaoTaoService.findAll());
        }

        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        return "portal/bacdaotao/bacdaotao_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACDAOTAO_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        BacDaoTaoModel bean = new BacDaoTaoModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("lstChuyenNganh", chuyenNganhService.findAll());
        model.addAttribute("bacDaoTaoModel", bean);
        return "portal/bacdaotao/bacdaotao_create";
    }

    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "bacDaoTaoModel") @Valid BacDaoTaoModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            BacDaoTao bacDaoTao = bean.getEntity();
            bacDaoTao.setBacDaoTaoCode(bacDaoTao.getBacDaoTaoCode().toUpperCase());
            bacDaoTao.setBacDaoTaoName(bacDaoTao.getBacDaoTaoName().toUpperCase());
            bacDaoTaoService.save(bacDaoTao);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.bacDaoTaoCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("lstChuyenNganh", chuyenNganhService.findAll());
        return "portal/bacdaotao/bacdaotao_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACDAOTAO_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          BacDaoTaoModel bean, Model model) {
        BacDaoTao bacDaoTao = bacDaoTaoService.findOne(id);
        bean.setEntity(bacDaoTao);
        model.addAttribute("lstChuyenNganh", chuyenNganhService.findAll());
        bean.setPageMode(PageMode.EDIT);
        return "portal/bacdaotao/bacdaotao_edit";
    }

    /**
     * EDIT - POST
     */
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(BacDaoTaoModel bean, Model model, Locale locale, BindingResult bindingResult) {
        BacDaoTao entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setBacDaoTaoCode(entity.getBacDaoTaoCode().toUpperCase());
            entity.setBacDaoTaoName(entity.getBacDaoTaoName().toUpperCase());
            bacDaoTaoService.save(entity);
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
        model.addAttribute("lstChuyenNganh", chuyenNganhService.findAll());
        return "portal/bacdaotao/bacdaotao_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACDAOTAO_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            bacDaoTaoService.delete(id);
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
        return "redirect:/portal/bacdaotao/list";
    }
}
