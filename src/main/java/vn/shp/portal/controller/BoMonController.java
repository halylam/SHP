package vn.shp.portal.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.entity.BoMon;
import vn.shp.app.entity.MonHoc;
import vn.shp.app.xlsEntity.BoMonXls;
import vn.shp.app.xlsEntity.MonHocXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.BoMonModel;
import vn.shp.portal.service.BoMonService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/bomon")
public class BoMonController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    BoMonService boMonService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        BoMonModel bean = new BoMonModel();
        List<BoMon> lstData = boMonService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "";
        for (BoMon each : lstData) {
            listExport += each.getBoMonId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/bomon/bomon_list";
    }

    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") @Valid BoMonModel bean, BindingResult bindingResult, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        List<BoMon> lstData = new ArrayList<>();
        if (bean != null) {
            lstData.addAll(boMonService.searchByFilters(bean.getEntity().getBoMonName(), bean.getEntity().getBoMonCode()));
        } else {
            lstData.addAll(boMonService.findAll());
        }

        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "";
        for (BoMon each : lstData) {
            listExport += each.getBoMonId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/bomon/bomon_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOMON_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        BoMonModel bean = new BoMonModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("boMonModel", bean);
        return "portal/bomon/bomon_create";
    }

    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "boMonModel") @Valid BoMonModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            BoMon boMon = bean.getEntity();
            boMon.setBoMonCode(boMon.getBoMonCode().toUpperCase());
            boMon.setBoMonName(boMon.getBoMonName().toUpperCase());
            boMonService.save(boMon);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.boMonCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        return "portal/bomon/bomon_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOMON_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          BoMonModel bean, Model model) {
        BoMon boMon = boMonService.findOne(id);
        bean.setEntity(boMon);
        bean.setPageMode(PageMode.EDIT);
        return "portal/bomon/bomon_edit";
    }

    /**
     * EDIT - POST
     */
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(BoMonModel bean, Model model, Locale locale, BindingResult bindingResult) {
        BoMon entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setBoMonCode(entity.getBoMonCode().toUpperCase());
            entity.setBoMonName(entity.getBoMonName().toUpperCase());
            boMonService.save(entity);
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
        return "portal/bomon/bomon_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOMON_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            boMonService.delete(id);
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
        return "redirect:/portal/bomon/list";
    }

    @Transactional(readOnly = true)
    @RequestMapping(value = "/exportXls/{list}", method = GET)
    public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
        List<BoMonXls> lstResGen = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(list)) {
                String[] arr = list.split("-");
                for (int i = 0; i < arr.length; i++) {
                    if (StringUtils.isNotEmpty(arr[i])) {
                        BoMon each = boMonService.findOne(Long.parseLong(arr[i]));
                        BoMonXls item = new BoMonXls();
                        item.setBoMonCode(each.getBoMonCode());
                        item.setBoMonName(each.getBoMonName());
                        item.setSeq(i + 1);
                        lstResGen.add(item);
                    }
                }
            }
            InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
            List<ECell> lstECells = new ArrayList<ECell>();
            ExcelCreator<BoMonXls> excelCreator = new ExcelCreator<BoMonXls>();
            byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachBoMon.xls" + "\"");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                response.getOutputStream().write(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
