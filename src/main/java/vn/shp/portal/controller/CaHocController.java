package vn.shp.portal.controller;

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
import vn.shp.app.entity.CaHoc;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.xlsEntity.CaHocXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.CaHocModel;
import vn.shp.portal.service.CaHocService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/cahoc")
public class CaHocController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    CaHocService caHocService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        CaHocModel bean = new CaHocModel();
        List<CaHoc> lstData = caHocService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "-";
        for (CaHoc each : lstData) {
            listExport += each.getCaHocId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/cahoc/cahoc_list";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_LIST')")
    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") @Valid CaHocModel bean, BindingResult bindingResult, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        List<CaHoc> lstData = new ArrayList<>();
        if (bean != null) {
            lstData.addAll(caHocService.searchByFilters(bean.getEntity().getCaHocName(), bean.getEntity().getCaHocCode()));
        } else {
            lstData.addAll(caHocService.findAll());
        }

        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "-";
        for (CaHoc each : lstData) {
            listExport += each.getCaHocId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/cahoc/cahoc_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        CaHocModel bean = new CaHocModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("caHocModel", bean);
        return "portal/cahoc/cahoc_create";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_CREATE')")
    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "caHocModel") @Valid CaHocModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            CaHoc caHoc = bean.getEntity();
            caHoc.setCaHocCode(caHoc.getCaHocCode().toUpperCase());
            caHoc.setCaHocName(caHoc.getCaHocName().toUpperCase());
            caHocService.save(caHoc);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.caHocCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        return "portal/cahoc/cahoc_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          CaHocModel bean, Model model) {
        CaHoc caHoc = caHocService.findOne(id);
        bean.setEntity(caHoc);
        bean.setPageMode(PageMode.EDIT);
        return "portal/cahoc/cahoc_edit";
    }

    /**
     * EDIT - POST
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_EDIT')")
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(CaHocModel bean, Model model, Locale locale, BindingResult bindingResult) {
        CaHoc entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setCaHocCode(entity.getCaHocCode().toUpperCase());
            entity.setCaHocName(entity.getCaHocName().toUpperCase());
            caHocService.save(entity);
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
        return "portal/cahoc/cahoc_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            caHocService.delete(id);
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
        return "redirect:/portal/cahoc/list";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CAHOC_EXPORT')")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/exportXls/{list}", method = GET)
    public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
        List<CaHocXls> lstResGen = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(list)) {
                String[] arr = list.split("-");
                for (int i = 0; i < arr.length; i++) {
                    if (StringUtils.isNotEmpty(arr[i])) {
                        CaHoc each = caHocService.findOne(Long.parseLong(arr[i]));
                        CaHocXls item = new CaHocXls();
                        item.setCaHocCode(each.getCaHocCode());
                        item.setCaHocName(each.getCaHocName());
                        item.setLoaiCaHoc(each.getLoaiCa());
                        item.setTuGio(each.getTuGio());
                        item.setDenGio(each.getDenGio());
                        item.setSeq(i + 1);
                        lstResGen.add(item);
                    }
                }
            }
            InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
            List<ECell> lstECells = new ArrayList<ECell>();
            ExcelCreator<CaHocXls> excelCreator = new ExcelCreator<CaHocXls>();
            byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachCaHoc.xls" + "\"");
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
