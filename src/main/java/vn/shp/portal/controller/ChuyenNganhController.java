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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.entity.BacDaoTao;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.app.xlsEntity.BacDaoTaoXls;
import vn.shp.app.xlsEntity.ChuyenNganhXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.ChuyenNganhModel;
import vn.shp.portal.service.ChuongTrinhDaoTaoService;
import vn.shp.portal.service.ChuyenNganhService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/chuyennganh")
public class ChuyenNganhController extends AbstractController{

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ChuyenNganhService chuyenNganhService;

    @Autowired
    ChuongTrinhDaoTaoService chuongTrinhDaoTaoService;

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
        String listExport = "-";
        for (ChuyenNganh each : lstData) {
            listExport += each.getChuyenNganhId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/chuyennganh/chuyennganh_list";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_LIST')")
    @RequestMapping(value = "/list", method = POST)
    public String postList(@ModelAttribute(value = "bean") @Valid ChuyenNganhModel bean, BindingResult bindingResult, Model model, HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        List<ChuyenNganh> lstData = new ArrayList<>();
        if (bean != null) {
            lstData.addAll(chuyenNganhService.searchByFilters(bean.getEntity().getChuyenNganhName(), bean.getEntity().getChuyenNganhCode()));
        } else {
            lstData.addAll(chuyenNganhService.findAll());
        }

        bean.setData(lstData);
        if (CollectionUtils.isEmpty(lstData)) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Không tìm thấy thông tin");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("bean", bean);
        String listExport = "-";
        for (ChuyenNganh each : lstData) {
            listExport += each.getChuyenNganhId() + "-";
        }
        model.addAttribute("listExport", listExport);
        return "portal/chuyennganh/chuyennganh_list";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        ChuyenNganhModel bean = new ChuyenNganhModel();
        bean.setPageMode(PageMode.CREATE);
        model.addAttribute("lstCtdt", chuongTrinhDaoTaoService.findAll());
        model.addAttribute("chuyenNganhModel", bean);
        return "portal/chuyennganh/chuyennganh_create";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_CREATE')")
    @RequestMapping(value = "/create", method = POST)
    public String postCreate(@ModelAttribute(value = "chuyenNganhModel") @Valid ChuyenNganhModel bean,
                             BindingResult bindingResult, Model model, HttpServletRequest request,
                             RedirectAttributes redirectAttributes, Locale locale) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        String msgInfo = "";
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception();
            }
            ChuyenNganh chuyenNganh = bean.getEntity();
            chuyenNganh.setChuyenNganhCode(chuyenNganh.getChuyenNganhCode().toUpperCase());
            chuyenNganh.setChuyenNganhName(chuyenNganh.getChuyenNganhName().toUpperCase());
            chuyenNganhService.save(chuyenNganh);

            msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                bindingResult.rejectValue("entity.chuyenNganhCode", "Exists.error.code", null, "");
            }
            messageLst.setStatus(Message.ERROR);
            msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
            messageLst.add(msgInfo);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        model.addAttribute("lstCtdt", chuongTrinhDaoTaoService.findAll());
        return "portal/chuyennganh/chuyennganh_create";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEdit(@PathVariable(value = "") Long id,
                          ChuyenNganhModel bean, Model model) {
        ChuyenNganh chuyenNganh = chuyenNganhService.findOne(id);
        bean.setEntity(chuyenNganh);
        model.addAttribute("lstCtdt", chuongTrinhDaoTaoService.findAll());
        bean.setPageMode(PageMode.EDIT);
        return "portal/chuyennganh/chuyennganh_edit";
    }

    /**
     * EDIT - POST
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_EDIT')")
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(ChuyenNganhModel bean, Model model, Locale locale, BindingResult bindingResult) {
        ChuyenNganh entity = bean.getEntity();
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            entity.setChuyenNganhCode(entity.getChuyenNganhCode().toUpperCase());
            entity.setChuyenNganhName(entity.getChuyenNganhName().toUpperCase());
            chuyenNganhService.save(entity);
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
        model.addAttribute("lstCtdt", chuongTrinhDaoTaoService.findAll());
        return "portal/chuyennganh/chuyennganh_edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_DELETE')")
    @RequestMapping(value = "/delete/{id}", method = GET)
    public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
                            Locale locale, RedirectAttributes redirectAttributes) {
        MessageList messageLst = new MessageList(Message.SUCCESS);
        try {
            chuyenNganhService.delete(id);
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
        return "redirect:/portal/chuyennganh/list";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUYENNGANH_EXPORT')")
    @Transactional(readOnly = true)
    @RequestMapping(value = "/exportXls/{list}", method = GET)
    public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
        List<ChuyenNganhXls> lstResGen = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(list)) {
                String[] arr = list.split("-");
                for (int i = 0; i < arr.length; i++) {
                    if (StringUtils.isNotEmpty(arr[i])) {
                        ChuyenNganh each = chuyenNganhService.findOne(Long.parseLong(arr[i]));
                        ChuyenNganhXls item = new ChuyenNganhXls();
                        item.setChuyenNganhCode(each.getChuyenNganhCode());
                        item.setChuyenNganhName(each.getChuyenNganhName());
                        item.setCtdtName(each.getChuongTrinhDaoTao().getChuongTrinhDaoTaoName());
                        item.setSeq(i + 1);
                        lstResGen.add(item);
                    }
                }
            }
            InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
            List<ECell> lstECells = new ArrayList<ECell>();
            ExcelCreator<ChuyenNganhXls> excelCreator = new ExcelCreator<ChuyenNganhXls>();
            byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachChuyenNganh.xls" + "\"");
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
