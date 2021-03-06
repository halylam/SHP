package vn.shp.app.controller;

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
import vn.shp.app.entity.MonHoc;
import vn.shp.app.xlsEntity.MonHocXls;
import vn.shp.app.constant.PageMode;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.bean.MonHocBean;
import vn.shp.app.service.BoMonService;
import vn.shp.app.service.MonHocService;

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
@RequestMapping("portal/monhoc")
public class MonHocController  extends AbstractController{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    MonHocService monHocService;

    @Autowired
    BoMonService boMonService;

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        MonHocBean bean = new MonHocBean();
        List<MonHoc> lstData = monHocService.findAll();
        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "-";
        for (MonHoc each : lstData) {
            listExport += each.getMonHocId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/monhoc/monhoc_list";
    }
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_LIST')")
    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") @Valid MonHocBean bean, BindingResult bindingResult, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        List<MonHoc> lstData = new ArrayList<>();
        if (bean != null) {
            lstData.addAll(monHocService.searchByFilters(bean.getEntity().getMonHocName(), bean.getEntity().getMonHocCode()));
        } else {
            lstData.addAll(monHocService.findAll());
        }

        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "-";
        for (MonHoc each : lstData) {
            listExport += each.getMonHocId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/monhoc/monhoc_list";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        MonHocBean bean = new MonHocBean();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("lstBoMon", boMonService.findAll());
        model.addAttribute("monHocModel", bean);
        return "portal/monhoc/monhoc_create";
    }
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_CREATE')")
    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "monHocModel") @Valid MonHocBean bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            MonHoc monHoc = bean.getEntity();
            monHoc.setMonHocCode(monHoc.getMonHocCode().toUpperCase());
            monHoc.setMonHocName(monHoc.getMonHocName().toUpperCase());
            monHocService.save(monHoc);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.monHocCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("lstBoMon", boMonService.findAll());
        return "portal/monhoc/monhoc_create";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          MonHocBean bean, Model model) {
        MonHoc monHoc = monHocService.findOne(id);
        bean.setEntity(monHoc);
        model.addAttribute("lstBoMon", boMonService.findAll());
        bean.setPageMode(PageMode.EDIT);
        return "portal/monhoc/monhoc_edit";
    }

    /**
     * EDIT - POST
     */
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_EDIT')")
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(MonHocBean bean, Model model, Locale locale, BindingResult bindingResult) {
        MonHoc entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setMonHocCode(entity.getMonHocCode().toUpperCase());
            entity.setMonHocName(entity.getMonHocName().toUpperCase());
            monHocService.save(entity);
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
        model.addAttribute("lstBoMon", boMonService.findAll());
        return "portal/monhoc/monhoc_edit";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            monHocService.delete(id);
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
        return "redirect:/portal/monhoc/list";
    }
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_MONHOC_EXPORT')")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/exportXls/{list}", method = GET)
    public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
        List<MonHocXls> lstResGen = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(list)) {
                String[] arr = list.split("-");
                for (int i = 0; i < arr.length; i++) {
                    if (StringUtils.isNotEmpty(arr[i])) {
                        MonHoc each = monHocService.findOne(Long.parseLong(arr[i]));
                        MonHocXls item = new MonHocXls();
                        item.setMonHocCode(each.getMonHocCode());
                        item.setMonHocName(each.getMonHocName());
                        item.setTenBoMon(each.getBoMon().getBoMonName());
                        item.setTongGioDay(each.getTongGioDay()+"");
                        item.setHeSo(each.getHeSo()+"");
                        item.setSeq(i + 1);
                        lstResGen.add(item);
                    }
                }
            }
            InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
            List<ECell> lstECells = new ArrayList<ECell>();
            ExcelCreator<MonHocXls> excelCreator = new ExcelCreator<MonHocXls>();
            byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachMonHoc.xls" + "\"");
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
