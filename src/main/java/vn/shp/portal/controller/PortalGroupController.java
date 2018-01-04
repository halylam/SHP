package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.config.Constants;
import vn.shp.app.utils.Utils;
import vn.shp.app.xlsEntity.PortalGroupXls;
import vn.shp.app.xlsEntity.PortalRoleXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalGroupBean;
import vn.shp.portal.model.UserGroup;
import vn.shp.portal.service.*;
import vn.shp.portal.utils.ExcelHelper;

import javax.servlet.ServletOutputStream;
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
@RequestMapping("portal/rolegroup")
public class PortalGroupController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PortalGroupService portalGroupService;

	@Autowired
	PortalRoleService portalRoleService;

	@Autowired
	PortalUserService portalUserService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		PortalGroupBean bean = new PortalGroupBean();
		List<PortalRole> roleLeftLst = portalRoleService.findAll();
		bean.setRoleLeftLst(roleLeftLst);

		bean.setPageMode(PageMode.CREATE);
		model.addAttribute("bean", bean);
		return "portal/rolegroup/rolegroup_create";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/create", method = POST)
	public String postCreate(Model model, PortalGroupBean bean, HttpServletRequest request) {
		PortalGroup group = bean.getEntity();
		if (group != null && Utils.isAllNotNullOrEmpty(group.getGroupName(), group.getGroupCode())) {
			try {
				group.setGroupCode(group.getGroupCode().toUpperCase());
				group.setGroupName(group.getGroupName().toUpperCase());
				group.setStatus(Constants.RECORD_STATUS_OPEN);
				group.setTimeCreated(new Date());
				//group.setUserCreated();

				String[] chkRoleRight = request.getParameterValues("checkRoleRight");
				this.createRoleList(chkRoleRight, bean);

				List<PortalRole> roleLst = bean.getRoleRightLst();
				group.setRoleGroupLst(roleLst);

				portalGroupService.save(group);

				MessageList messageList = new MessageList(Message.SUCCESS, "Thêm mới nhóm quyền thành công.");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
				bean.setEntity(new PortalGroup());
			} catch (Exception e) {
				e.printStackTrace();
				MessageList messageList = new MessageList(Message.ERROR, "Nhóm quyền đã tồn tại trong hệ thống");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			}
		}
		model.addAttribute("bean", bean);
		return "portal/rolegroup/rolegroup_create";
	}

	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
						  PortalGroupBean bean, Model model)
	{
		PortalGroup portalGroup = portalGroupService.findOne(id);
		bean.setEntity(portalGroup);
		bean.setRoleRightLst(portalGroup.getRoleGroupLst());
		List<PortalRole> roleLeftLst = new ArrayList<PortalRole>();
		for (PortalRole each : portalRoleService.findAll()) {
			if (!portalGroup.getRoleGroupLst().contains(each)) {
				roleLeftLst.add(each);
			}
		}
		bean.setRoleLeftLst(roleLeftLst);
		bean.setPageMode(PageMode.EDIT);
		model.addAttribute("bean", bean);
		return "portal/rolegroup/rolegroup_edit";
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(PortalGroupBean bean, Model model, Locale locale, BindingResult bindingResult, HttpServletRequest request) {
		PortalGroup group = bean.getEntity();
		if (group != null && Utils.isAllNotNullOrEmpty(group.getGroupName(), group.getGroupCode())) {
			try {
				group.setGroupCode(group.getGroupCode().toUpperCase());
				group.setGroupName(group.getGroupName().toUpperCase());
				group.setStatus(Constants.RECORD_STATUS_OPEN);
				group.setTimeCreated(new Date());
				//group.setUserCreated();

				String[] chkRoleRight = request.getParameterValues("checkRoleRight");
				this.createRoleList(chkRoleRight, bean);

				List<PortalRole> roleLst = bean.getRoleRightLst();
				group.setRoleGroupLst(roleLst);

				portalGroupService.save(group);

				MessageList messageList = new MessageList(Message.SUCCESS, "Thêm mới nhóm quyền thành công.");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			} catch (Exception e) {
				e.printStackTrace();
				MessageList messageList = new MessageList(Message.ERROR, "Nhóm quyền đã tồn tại trong hệ thống");
				model.addAttribute(CoreConstant.MSG_LST, messageList);
			}
		}
		model.addAttribute("bean", bean);
		return "portal/rolegroup/rolegroup_edit";
	}

	//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(@ModelAttribute(value = "bean") PortalGroupBean bean, Model model,
						  HttpServletRequest request)
	{

		bean.setPageMode(PageMode.LIST);
		if (StringUtils.equals(request.getMethod(), RequestMethod.GET.name())) {
			bean.setEntity(new PortalGroup());
		}
		List<PortalGroup> lstGroups = portalGroupService.findAll();
		bean.setData(lstGroups);
		model.addAttribute("bean", bean);

		String listExport = "-";
		for (PortalGroup each : lstGroups) {
			listExport += each.getGroupId() + "-";
		}
		model.addAttribute("listExport", listExport);

		return "portal/rolegroup/rolegroup_list";
	}

	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "bean") @Valid PortalGroupBean bean, BindingResult bindingResult, Model model,
						   HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<PortalGroup> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.addAll(portalGroupService.searchByFilters(bean.getEntity().getGroupCode(), bean.getEntity().getGroupName()));
		} else {
			lstData.addAll(portalGroupService.findAll());
		}

		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "-";
		for (PortalGroup each : lstData) {
			listExport += each.getGroupId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/rolegroup/rolegroup_list";
	}

	public void createRoleList(String[] checkRole, PortalGroupBean bean) {
		// Get listAll role
		List<PortalRole> roleAll = portalRoleService.findAll();

		if ((checkRole != null) && (checkRole.length > 0)) {
			List<PortalRole> roleLst = new ArrayList<PortalRole>();
			List<PortalRole> roleNotLst = new ArrayList<PortalRole>();
			boolean flag = false;
			for (PortalRole role : roleAll) {
				flag = false;
				for (int i = 0; i < checkRole.length; i++) {
					if (checkRole[i].equals(role.getRoleId().toString())) {
						roleLst.add(role);
						flag = true;
						break;
					}
				}
				if (!flag) {
					roleNotLst.add(role);
				}
			}
			bean.setRoleRightLst(roleLst);
			bean.setRoleLeftLst(roleNotLst);
		} else {
			// Get listAll role for the roleNotOfTeam
			bean.setRoleRightLst(roleAll);
			// List of team's role is empty (default value)
		}
	}

	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			portalGroupService.delete(id);
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
		return "redirect:/portal/rolegroup/list";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<PortalGroupXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						PortalGroup each = portalGroupService.findOne(Long.parseLong(arr[i]));
						PortalGroupXls item = new PortalGroupXls();
						item.setGroupCode(each.getGroupCode());
						item.setGroupName(each.getGroupName());
						item.setStatus(each.getStatus());
						item.setTimeCreated(dateFormat.format(each.getTimeCreated()));
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<PortalGroupXls> excelCreator = new ExcelCreator<PortalGroupXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachPortalGroup.xls" + "\"");
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
