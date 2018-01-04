package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.entity.ChuyenNganh;
import vn.shp.app.xlsEntity.ChuyenNganhXls;
import vn.shp.app.xlsEntity.PortalUserXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.*;
import vn.shp.portal.model.PortalUserBean;
import vn.shp.portal.model.PortalUserModel;
import vn.shp.portal.service.*;

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
@RequestMapping("portal/user")
public class PortalUserController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PortalUserService portalUserService;

	@Autowired
	PortalGroupService portalGroupService;

//	@Autowired
//	PortalTitleService portalTitleService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@ModelAttribute("portalUserModel")
	public PortalUserBean portalUserModel() {

		PortalUserBean portalUserModel = new PortalUserBean();
		PortalUser portalUser = new PortalUser();
//		portalUser.setTitle(new PortalTitle());
		portalUserModel.setEntity(portalUser);

		return portalUserModel;
	}

	@InitBinder
	public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
		// The date format to parse or output your dates
		// String patternDate = (String) request.getSession().getAttribute();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		PortalUserBean bean = new PortalUserBean();
		portalUserService.findAll();
		List<PortalUser> lstData = portalUserService.findAll();
		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "-";
		for (PortalUser each : lstData) {
			listExport += each.getUserId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/user/user_list";
	}

	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "bean") @Valid PortalUserModel bean, BindingResult bindingResult, Model model,
						   HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<PortalUser> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.addAll(portalUserService
					.searchByFilters(bean.getEntity().getUsername(), bean.getEntity().getEmail(), bean.getEntity().getFullName()));
		} else {
			lstData.addAll(portalUserService.findAll());
		}

		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "-";
		for (PortalUser each : lstData) {
			listExport += each.getUserId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/user/user_list";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<PortalUserXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						PortalUser each = portalUserService.findOne(Long.parseLong(arr[i]));
						PortalUserXls item = new PortalUserXls();
						item.setUsername(each.getUsername());
						item.setFullName(each.getFullName());
						item.setEmail(each.getEmail());
						item.setBirthday(dateFormat.format(each.getBirthday()));
						item.setMobile(each.getMobile());
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<PortalUserXls> excelCreator = new ExcelCreator<PortalUserXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachUser.xls" + "\"");
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

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(@ModelAttribute(value = "portalUserModel") PortalUserBean bean,
								  Model model, ModelMap modelMap)
	{

		ModelAndView mav = new ModelAndView("portal/user/user_create");
		bean.setPageMode(PageMode.CREATE);
		modelMap.put(BindingResult.MODEL_KEY_PREFIX + "portalUserModel", modelMap.get(CoreConstant.ERRORS));

		mav.addObject("groupLeftLst", portalGroupService.findAll());
		mav.addObject("bean", bean);
		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "bean") @Valid PortalUserBean bean,
								   BindingResult bindingResult, Model model, HttpServletRequest request,
								   RedirectAttributes redirectAttributes,
								   Locale locale) throws Exception
	{

		ModelAndView mav = new ModelAndView("redirect:/portal/user/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {

			String[] chkgroupRight = request.getParameterValues("checkRoleRight");
			if (chkgroupRight != null) {
				this.creategroupList(chkgroupRight, bean);
			}
			// create user
			PortalUser user = bean.getEntity();
			if (user.getPassword1().equals(user.getPassword2())) {
				user.setPassword(user.getPassword1());
			}
			List<PortalGroup> groupLst = bean.getGroupRightLst();
			user.setGroups(groupLst);
			user.setEnabled(true);
			user.setTimeCreated(new Date());

			// save user
			portalUserService.save(user);

			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.ERRORS, bindingResult);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
			redirectAttributes.addFlashAttribute("bean", bean);
			mav.setViewName("redirect:/portal/user/create");
			return mav;
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_EDIT')")
	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
						  PortalUserBean bean, Model model)
	{
		PortalUser portalUser = portalUserService.findOne(id);
		bean.setEntity(portalUser);
		bean.setGroupRightLst(portalUser.getGroups());
		List<PortalGroup> groupLeftLst = new ArrayList<PortalGroup>();
		for (PortalGroup each : portalGroupService.findAll()) {
			if (!portalUser.getGroups().contains(each)) {
				groupLeftLst.add(each);
			}
		}
		bean.setGroupLeftLst(groupLeftLst);
		bean.setPageMode(PageMode.EDIT);
		model.addAttribute("bean", bean);
		return "portal/user/user_edit";
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(PortalUserBean bean, Model model, Locale locale, BindingResult bindingResult, HttpServletRequest request) {
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			String[] chkgroupRight = request.getParameterValues("checkRoleRight");
			if (chkgroupRight != null) {
				this.creategroupList(chkgroupRight, bean);
			}
			// create user
			PortalUser user = bean.getEntity();
			if (StringUtils.isNotEmpty(user.getPassword1()) && user.getPassword1().equals(user.getPassword2())) {
				user.setPassword(user.getPassword1());
			}
			List<PortalGroup> groupLst = bean.getGroupRightLst();
			user.setGroups(groupLst);
			user.setEnabled(true);
			user.setTimeCreated(new Date());

			// save user
			portalUserService.save(user);
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
		model.addAttribute("bean", bean);
		return "portal/user/user_edit";
	}

	public void creategroupList(String[] checkgroup, PortalUserBean portalUserModel) {
		// Get listAll group
		List<PortalGroup> groupAll = portalGroupService.findAll();

		if ((checkgroup != null) && (checkgroup.length > 0)) {
			List<PortalGroup> groupLst = new ArrayList<PortalGroup>();
			List<PortalGroup> groupNotLst = new ArrayList<PortalGroup>();
			boolean flag = false;
			for (PortalGroup group : groupAll) {
				flag = false;
				for (int i = 0; i < checkgroup.length; i++) {
					if (checkgroup[i].equals(group.getGroupId().toString())) {
						groupLst.add(group);
						flag = true;
						break;
					}
				}
				if (!flag) {
					groupNotLst.add(group);
				}
			}
			portalUserModel.setGroupRightLst(groupLst);
			portalUserModel.setGroupLeftLst(groupNotLst);
		} else {
			// Get listAll group for the groupNotOfTeam
			portalUserModel.setGroupRightLst(groupAll);
			// List of team's group is empty (default value)
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_DELETE')")
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			portalUserService.delete(id);
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
		return "redirect:/portal/user/list";
	}
}
