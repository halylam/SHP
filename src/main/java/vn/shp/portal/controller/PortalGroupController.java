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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalGroupModel;
import vn.shp.portal.model.UserGroup;
import vn.shp.portal.service.*;
import vn.shp.portal.utils.ExcelHelper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/group")
public class PortalGroupController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PortalGroupService portalGroupService;

	@Autowired
	PortalRoleService portalRoleService;

	@Autowired
	PortalUserService portalUserService;

	@Value("${code.nodefine}")
	String codeNoDefine;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_LIST')")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String getList(@ModelAttribute(value = "portalGroupModel") PortalGroupModel portalGroupModel, Model model,
			HttpServletRequest request) {

		;
		portalGroupModel.setPageMode(PageMode.LIST);
		if (StringUtils.equals(request.getMethod(), RequestMethod.GET.name())) {
			portalGroupModel.setEntity(new PortalGroup());
		}
		PortalGroupModel result = portalGroupService.findByData(portalGroupModel);
		model.addAttribute("portalGroupModel", result);
		return "portal/group/group_list";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(Model model, HttpServletRequest request) {
		;
		PortalGroupModel portalGroupModel = new PortalGroupModel();

		List<PortalRole> roleLeftLst = portalRoleService.findAll();
		portalGroupModel.setRoleLeftLst(roleLeftLst);

		// =========
		portalGroupModel.setPageMode(PageMode.CREATE);

		ModelAndView mav = new ModelAndView("portal/group/group_create");
		mav.addObject("portalGroupModel", portalGroupModel);

		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "portalGroupModel") @Valid PortalGroupModel portalGroupModel,
			BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			Locale locale) {
		;
		ModelAndView mav = new ModelAndView("redirect:/portal/group/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}

			String[] chkRoleRight = request.getParameterValues("checkRoleRight");
			this.createRoleList(chkRoleRight, portalGroupModel);

			List<PortalRole> roleLst = portalGroupModel.getRoleRightLst();
			PortalGroup group = portalGroupModel.getEntity();

			// Save group to keycloak

			portalGroupService.save(group);

			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.groupCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("portal/group/group_create");

			List<PortalRole> roleLeftLst = portalRoleService.findAll();
			portalGroupModel.setRoleLeftLst(roleLeftLst);
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_EDIT')")
	@RequestMapping(value = "/edit", method = GET)
	public ModelAndView getEdit(@RequestParam(value = "groupId") Long groupId,
			@ModelAttribute(value = "portalGroupModel") PortalGroupModel portalGroupModel, Model model) {

		;

		PortalGroup portalGroup = portalGroupService.findOne(groupId);
		portalGroupModel.setEntity(portalGroup);

		List<PortalRole> roleNotLst = new ArrayList<PortalRole>();
		List<PortalRole> roleAll = portalRoleService.findAll();

		List<PortalRole> roleLst = portalGroup.getRoleGroupLst();
		for (PortalRole role : roleAll) {
			if (!roleLst.contains(role)) {
				roleNotLst.add(role);
			}
		}
		portalGroupModel.setRoleRightLst(roleLst);
		portalGroupModel.setRoleLeftLst(roleNotLst);

		portalGroupModel.setPageMode(PageMode.EDIT);
		ModelAndView mav = new ModelAndView("portal/group/group_edit");
		mav.addObject("portalGroupModel", portalGroupModel);
		return mav;

	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public ModelAndView postEdit(@ModelAttribute(value = "portalGroupModel") @Valid PortalGroupModel portalGroupModel,
			Model model, Locale locale, HttpServletRequest request, RedirectAttributes redirectAttributes,
			BindingResult bindingResult) {

		;
		ModelAndView mav = new ModelAndView("redirect:/portal/group/edit");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}

			// không get được role right
			String[] checkRoleRight = request.getParameterValues("checkRoleRight");
			this.createRoleList(checkRoleRight, portalGroupModel);

			List<PortalRole> roleLst = portalGroupModel.getRoleRightLst();
			PortalGroup group = portalGroupModel.getEntity();
			PortalGroup portalGroup = portalGroupService.findOne(portalGroupModel.getEntity().getGroupId());
			List<PortalRole> roleLstOld = portalGroup.getRoleGroupLst();
			List<PortalRole> roleLstNew = portalGroupModel.getRoleRightLst();
			List<String> roleLstDel = new ArrayList<String>();
			List<String> roleLstSave = new ArrayList<String>();
			if (!CollectionUtils.isEmpty(roleLstNew)) {
				for (PortalRole role : roleLstNew) {

					// loai bo group da ton tai trong user
					if (!CollectionUtils.isEmpty(roleLstOld) && roleLstOld.contains(role)) {
						continue;
					}

					// set danh sach group cần thêm
					if (CollectionUtils.isEmpty(roleLstOld) || !roleLstOld.contains(role)) {
						roleLstSave.add(role.getRoleKeycloakId());
					}
				}
			}
			if (!CollectionUtils.isEmpty(roleLstOld)) {
				for (PortalRole role : roleLstOld) {
					if (CollectionUtils.isEmpty(roleLstNew) || !roleLstNew.contains(role)) {
						roleLstDel.add(role.getRoleKeycloakId());
					}
				}
			}



			// Save group
			group.setRoleGroupLst(roleLst);
			group.setGroupName(group.getGroupName());
			portalGroupService.save(group);

			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.groupCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("redirect:/portal/group/edit");
		}

		redirectAttributes.addAttribute("groupId", portalGroupModel.getEntity().getGroupId());
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_DELETE')")
	@RequestMapping(value = "/delete", method = GET)
	public String getDelete(@RequestParam(value = "groupId") Long groupId, Model model, HttpServletRequest request,
			Locale locale, RedirectAttributes redirectAttributes) {

		return "redirect:/portal/group/list";
	}

	public void createRoleList(String[] checkRole, PortalGroupModel portalGroupModel) {
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
			portalGroupModel.setRoleRightLst(roleLst);
			portalGroupModel.setRoleLeftLst(roleNotLst);
		} else {
			// Get listAll role for the roleNotOfTeam
			portalGroupModel.setRoleRightLst(roleAll);
			// List of team's role is empty (default value)
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADD_GROUP')")
	@RequestMapping(value = "/addGroupToUser", method = GET)
	public String setGroupToUserKeycloak(@RequestParam(value = "groupId") Long groupId, Model model, Locale locale,
			RedirectAttributes redirectAttributes) {
		;


		return "redirect:/portal/group/list";

	}

	/*
	 * Import group to user
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_IMPORT')")
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String getImport(@ModelAttribute(value = "portalGroupModel") PortalGroupModel portalGroupModel, Model model,
			HttpServletRequest request) {
		;
		portalGroupModel.setPageMode(PageMode.VIEW);
		model.addAttribute("portalGroupModel", portalGroupModel);
		return "portal/group/group_import";
	}

	@SuppressWarnings("resource")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GROUP_IMPORT')")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String postImport(@ModelAttribute(value = "portalGroupModel") PortalGroupModel portalGroupModel,
			@RequestParam("fileUpload") MultipartFile multipartFile, RedirectAttributes redirectAttributes, Model model,
			HttpServletRequest request, HttpServletResponse resp, Locale locale) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss: SSS");
		;
		Boolean isHasError = false;
		Workbook workbook = null;
		try {
			System.out.println("read file:" + df.format(new Date()));
			if (multipartFile.equals(null)) {
				throw new Exception("File not found");
			}

			// Check excel file
			String contentType = multipartFile.getContentType();
			if (StringUtils.equals(contentType, "application/vnd.ms-excel")) {
				workbook = new HSSFWorkbook(multipartFile.getInputStream());
			} else {
				workbook = new XSSFWorkbook(multipartFile.getInputStream());
			}
			Sheet sheetData = workbook.getSheetAt(0);
			Row rowTitle = (Row) sheetData.getRow(5);
			if (!rowTitle.getCell(0).toString().equals("STT") || !rowTitle.getCell(1).toString().equals("Tài khoản")
					|| !rowTitle.getCell(2).toString().equals("Nhóm quyền")) {
				throw new Exception("Error format template");
			}

			// set style error
			ExcelHelper excelHelper = new ExcelHelper();
			CellStyle cellStyleError = workbook.createCellStyle();
			Font fontRed = workbook.createFont();
			fontRed.setColor(HSSFColor.RED.index);
			cellStyleError.setFont(fontRed);

			/*
			 * Check excel
			 */

			// get Users


			System.out.println("read list user keycloak:" + df.format(new Date()));


			// get Group
			System.out.println("get nhóm từ db, thêm dữ liệu nhóm vào danh sách tạm:" + df.format(new Date()));
			List<String> lstGroupStr = new ArrayList<String>();
			List<PortalGroup> lstGroup = portalGroupService.findAll();
			if (!CollectionUtils.isEmpty(lstGroup)) {
				for (PortalGroup group : lstGroup) {
					lstGroupStr.add(group.getGroupName());
				}
			}

			List<UserGroup> datas = new ArrayList<UserGroup>();
			System.out.println("kiểm tra dữ liệu trong sheet: " + df.format(new Date()));
			for (int i = 6; i <= sheetData.getLastRowNum(); i++) {
				Row row = (Row) sheetData.getRow(i);
				StringBuilder err = new StringBuilder();

				Boolean isError = false;
				String username = null;
				String groupName = null;

				// cell 1
				try {

					Cell cell = row.getCell(1);
					if (cell == null) {
						throw new Exception("Tài khoản bị trống", null);
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					username = String.valueOf(cell.getStringCellValue());

					if (StringUtils.isBlank(username)) {
						throw new Exception("Tài khoản bị trống", null);
					}


				} catch (Exception e) {
					row.createCell(3).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 3, err.append(" " + e.getMessage()));
					isHasError = true;
					isError = true;
				}

				// cell 2
				try {

					Cell cell = row.getCell(2);
					if (cell == null) {
						throw new Exception("Nhóm quyền bỏ trống", null);
					}
					cell.setCellType(Cell.CELL_TYPE_STRING);
					groupName = String.valueOf(cell.getStringCellValue());

					if (!lstGroupStr.contains(groupName)) {
						throw new Exception("Nhóm quyền không tồn tại", null);
					}

				} catch (Exception e) {
					row.createCell(3).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 3, err.append(" " + e.getMessage()));
					isHasError = true;
					isError = true;
				}


			}

			// Xử lý
			System.out.println("xử lý dữ liệu:" + df.format(new Date()));
			if (isHasError == false) {
				// Danh sách user tồn tại trong Portal
				List<String> lstStrUsername = new ArrayList<String>();
				List<PortalUser> lstPortalUser = portalUserService.findAll();
				if (!CollectionUtils.isEmpty(lstPortalUser)) {
					for (PortalUser item : portalUserService.findAll()) {
						lstStrUsername.add(item.getUsername());
					}
				}



				MessageList messageLst = new MessageList(Message.SUCCESS);
				String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_SAVE, null, locale);
				messageLst.add(msgInfo);
				redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
			} else {
				resp.setContentType(contentType);
				resp.setHeader("Content-Disposition",
						"attachment; filename=\"OUT_" + multipartFile.getOriginalFilename() + "\"");
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				workbook.write(servletOutputStream);
				resp.flushBuffer();
				return "redirect:/portal/group/import";
			}
			System.out.println("kết thúc:" + df.format(new Date()));

		} catch (Exception e) {
			e.printStackTrace();
			MessageList messageLst = new MessageList(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_SAVE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		}
		return "redirect:/portal/group/import";
	}

}
