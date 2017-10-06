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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.ChuongTrinhDaoTaoModel;
import vn.shp.portal.service.ChuongTrinhDaoTaoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/chuongtrinhdaotao")
public class ChuongTrinhDaoTaoController {

    @Autowired
    private MessageSource messageSource;


    @Autowired
    ChuongTrinhDaoTaoService chuongTrinhDaoTaoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {

        ChuongTrinhDaoTaoModel bean = new ChuongTrinhDaoTaoModel();

        List<ChuongTrinhDaoTao> lstData = chuongTrinhDaoTaoService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }

        model.addAttribute("bean", bean);

        return "portal/chuongtrinhdaotao/chuongtrinhdaotao_list";
    }

    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") ChuongTrinhDaoTaoModel bean,
                           Model model, HttpServletRequest request) {

        List<ChuongTrinhDaoTao> lstData = chuongTrinhDaoTaoService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }

        model.addAttribute("chuongTrinhDaoTaoModel", bean);

        return "portal/chuongtrinhdaotao/chuongtrinhdaotao_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public ModelAndView getCreate(Model model, HttpServletRequest request) {
        ;
        ChuongTrinhDaoTaoModel bean = new ChuongTrinhDaoTaoModel();

        // =========
        bean.setPageMode(PageMode.CREATE);

        ModelAndView mav = new ModelAndView("portal/chuongtrinhdaotao/chuongtrinhdaotao_create");
        mav.addObject("chuongTrinhDaoTaoModel", bean);

        return mav;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView postCreate(@ModelAttribute(value = "chuongTrinhDaoTaoModel") @Valid ChuongTrinhDaoTaoModel bean,
                                   BindingResult bindingResult, Model model, HttpServletRequest request,
                                   RedirectAttributes redirectAttributes, Locale locale) {
        ;
        ModelAndView mav = new ModelAndView("redirect:/portal/chuongtrinhdaotao/list");

        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            // create user
            ChuongTrinhDaoTao chuongTrinhDaoTao = bean.getEntity();
            chuongTrinhDaoTao.setChuongTrinhDaoTaoCode(chuongTrinhDaoTao.getChuongTrinhDaoTaoCode().toUpperCase());
            chuongTrinhDaoTao.setChuongTrinhDaoTaoName(chuongTrinhDaoTao.getChuongTrinhDaoTaoName().toUpperCase());
            chuongTrinhDaoTaoService.save(chuongTrinhDaoTao);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.chuongTrinhDaoTaoCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            mav.addObject(CoreConstant.MSG_LST, messageLst);
            mav.setViewName("portal/chuongtrinhdaotao/chuongtrinhdaotao_create");
        }
        return mav;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          ChuongTrinhDaoTaoModel bean, Model model) {

        ChuongTrinhDaoTao chuongTrinhDaoTao = chuongTrinhDaoTaoService.findOne(id);
        bean.setEntity(chuongTrinhDaoTao);

        bean.setPageMode(PageMode.EDIT);

        return "portal/chuongtrinhdaotao/ctdt_edit";

    }

    /**
     * EDIT - POST
     */
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(ChuongTrinhDaoTaoModel bean, Model model, Locale locale,  BindingResult bindingResult) {

        ChuongTrinhDaoTao entity = bean.getEntity();

        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {

            entity.setChuongTrinhDaoTaoCode(entity.getChuongTrinhDaoTaoCode().toUpperCase());
            entity.setChuongTrinhDaoTaoName(entity.getChuongTrinhDaoTaoName().toUpperCase());
            chuongTrinhDaoTaoService.save(entity);

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

        return "portal/chuongtrinhdaotao/ctdt_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete(@RequestParam(value = "chuongTrinhDaoTaoId") Long chuongTrinhDaoTaoId, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        try {
            chuongTrinhDaoTaoService.delete(chuongTrinhDaoTaoId);

            MessageList messageLst = new MessageList(Message.SUCCESS);
            String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
            messageLst.add(msgInfo);
            redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/portal/chuongtrinhdaotao/list";
    }
}
