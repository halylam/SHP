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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.bean.RoleBean;
import vn.shp.app.config.Constants;
import vn.shp.app.utils.Utils;
import vn.shp.app.xlsEntity.PortalRoleXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.model.PortalRoleModel;
import vn.shp.portal.model.PortalRoleModel;
import vn.shp.portal.service.PortalRoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/role")
public class PortalRoleController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PortalRoleService portalRoleService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		model.addAttribute("bean", bean);
		return "portal/role/role_create";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/create", method = POST)
	public String postCreate(Model model, RoleBean bean, HttpServletRequest request) {
		PortalRole role = bean.getEntity();
		if (role != null && Utils.isAllNotNullOrEmpty(role.getRoleName(), role.getRoleCode())) {
			try {
				role.setRoleCode(role.getRoleCode().toUpperCase());
				role.setRoleName(role.getRoleName().toUpperCase());
				role.setStatus(Constants.RECORD_STATUS_OPEN);
				role.setTimeCreated(new Date());
				//role.setUserCreated();
				portalRoleService.save(role);
				MessageList messageList = new MessageList(Message.SUCCESS, "Thêm mới quyền thành công.");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
				bean.setEntity(new PortalRole());
			} catch (Exception e) {
				e.printStackTrace();
				MessageList messageList = new MessageList(Message.ERROR, "Quyền đã tồn tại trong hệ thống");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			}
		}
		model.addAttribute("bean", bean);
		return "portal/role/role_create";
	}

	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
						  PortalRoleModel bean, Model model)
	{
		PortalRole portalRole = portalRoleService.findOne(id);
		bean.setEntity(portalRole);
		bean.setPageMode(PageMode.EDIT);
		return "portal/role/role_edit";
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(PortalRoleModel bean, Model model, Locale locale, BindingResult bindingResult) {
		PortalRole role = bean.getEntity();
		if (role != null && Utils.isAllNotNullOrEmpty(role.getRoleName(), role.getRoleCode())) {
			try {
				role.setRoleCode(role.getRoleCode().toUpperCase());
				role.setRoleName(role.getRoleName().toUpperCase());
				role.setStatus(Constants.RECORD_STATUS_OPEN);
				role.setTimeCreated(new Date());
				//role.setUserCreated();
				portalRoleService.save(role);
				MessageList messageList = new MessageList(Message.SUCCESS, "Thêm mới quyền thành công.");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			} catch (Exception e) {
				e.printStackTrace();
				MessageList messageList = new MessageList(Message.ERROR, "Quyền đã tồn tại trong hệ thống");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			}
		}
		return "portal/role/role_edit";
	}

	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		model.addAttribute("bean", bean);

		List<PortalRole> lstRole = portalRoleService.findAll();
		bean.setLstData(lstRole);

		String listExport = "-";
		for (PortalRole each : lstRole) {
			listExport += each.getRoleId() + "-";
		}
		model.addAttribute("listExport", listExport);

		return "portal/role/role_list";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "bean") @Valid RoleBean bean, BindingResult bindingResult, Model model,
						   HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<PortalRole> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.addAll(portalRoleService.searchByFilters(bean.getEntity().getRoleName(), bean.getEntity().getRoleCode()));
		} else {
			lstData.addAll(portalRoleService.findAll());
		}

		bean.setLstData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "-";
		for (PortalRole each : lstData) {
			listExport += each.getRoleId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/role/role_list";
	}

	//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_DELETE')")
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			portalRoleService.delete(id);
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
		return "redirect:/portal/role/list";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<PortalRoleXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						PortalRole each = portalRoleService.findOne(Long.parseLong(arr[i]));
						PortalRoleXls item = new PortalRoleXls();
						item.setRoleCode(each.getRoleCode());
						item.setRoleName(each.getRoleName());
						item.setStatus(each.getStatus());
						item.setTimeCreated(dateFormat.format(each.getTimeCreated()));
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<PortalRoleXls> excelCreator = new ExcelCreator<PortalRoleXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachPortalRole.xls" + "\"");
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
