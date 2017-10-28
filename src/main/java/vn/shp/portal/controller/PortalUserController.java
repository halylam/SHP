package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.app.utils.Utils;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.*;
import vn.shp.portal.model.PortalUserModel;
import vn.shp.portal.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

	@Autowired
	PortalBranchService portalBranchService;

	@Autowired
	PortalDepartmentService portalDepartmentService;

//	@Autowired
//	PortalTitleService portalTitleService;


	@ModelAttribute("portalUserModel")
	public PortalUserModel portalUserModel() {
		
		PortalUserModel portalUserModel = new PortalUserModel();
		PortalUser portalUser = new PortalUser();
		portalUser.setBranch(new PortalBranch());
		portalUser.setDepartment(new PortalDepartment());
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
	public ModelAndView getList(Model model, HttpServletRequest request) {
		
		PortalUserModel portalUserModel = new PortalUserModel();
		portalUserModel.setPageMode(PageMode.LIST);

		ModelAndView mav = portalUserService.initSearch(portalUserModel, request);
		mav.addObject("portalUserModel", portalUserModel);
		mav.setViewName("portal/user/user_list");
		return mav;
	}

	@RequestMapping(value = "/ajaxList", method = GET)
	@ResponseBody
	public ModelAndView ajaxList(@ModelAttribute(value = "portalUserModel") PortalUserModel portalUserModel,
			HttpServletRequest request) {

		ModelAndView mav = portalUserService.initSearch(portalUserModel, request);
		mav.setViewName("portal/user/user_table");
		return mav;
	}

	@RequestMapping(value = "/list", method = POST)
	public ModelAndView postList(@ModelAttribute(value = "portalUserModel") PortalUserModel portalUserModel,
			Model model, HttpServletRequest request) {
		
		ModelAndView mav = portalUserService.initSearch(portalUserModel, request);
		mav.setViewName("portal/user/user_list");
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(@ModelAttribute(value = "portalUserModel") PortalUserModel portalUserModel,
			Model model, ModelMap modelMap) {
		
		ModelAndView mav = new ModelAndView("portal/user/user_create");
		portalUserModel.setPageMode(PageMode.CREATE);
		modelMap.put(BindingResult.MODEL_KEY_PREFIX + "portalUserModel", modelMap.get(CoreConstant.ERRORS));
		
		// List Branch
		List<PortalBranch> branchLst = portalBranchService.findAll();
		portalUserModel.setBranchLst(branchLst);
		portalUserModel.getEntity().getBranch().setBranchId(null);

		// List Department
		List<PortalDepartment> departmentLst = portalDepartmentService.findAll();
		portalUserModel.setDepartmentLst(departmentLst);

		// List title
//		List<PortalTitle> titleLst = portalTitleService.findAll();
//		portalUserModel.setTitleLst(titleLst);

		// User keycloak
		try {
			// List user contain keycloakId
			
			List<PortalUser> portalUserLstInSys = portalUserService.findAll();
			List<String> keycloakIdInSys = new ArrayList<String>();
			if (!CollectionUtils.isEmpty(portalUserLstInSys)) {
				for (PortalUser user : portalUserLstInSys) {
					if (Utils.isNotNullOrEmpty(user.getUsername())) {
						keycloakIdInSys.add(user.getUsername());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("groupLeftLst", portalGroupService.findAll());
		mav.addObject("portalUserModel", portalUserModel);
		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "portalUserModel") @Valid PortalUserModel portalUserModel,
			BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			Locale locale) throws Exception {
		
		ModelAndView mav = new ModelAndView("redirect:/portal/user/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		Long branchId = null;
		Long departmentId = null;
		Long titleId = null;
		try {

			branchId = portalUserModel.getEntity().getBranch().getBranchId();
			departmentId = portalUserModel.getEntity().getDepartment().getDepartmentId();
//			titleId = portalUserModel.getEntity().getTitle().getTitleId();
			if (bindingResult.hasErrors() || branchId == null || departmentId == null) {
				if (departmentId == null) {
					bindingResult.rejectValue("entity.department.departmentId", "user.field.department.error.empty", null,
							"");
				}
				if (titleId == null) {
					bindingResult.rejectValue("entity.title.titleId", "user.field.title.error.empty", null,
							"");
				}
				if (branchId == null) {
					bindingResult.rejectValue("entity.branch.branchId", "user.field.branch.error.empty", null, "");
				}
				redirectAttributes.addFlashAttribute(CoreConstant.ERRORS, bindingResult);
				redirectAttributes.addFlashAttribute("portalUserModel", portalUserModel);
				mav.setViewName("redirect:/portal/user/create");
				return mav;
			}

			String[] chkgroupRight = request.getParameterValues("checkRoleRight");
			if (chkgroupRight != null) {
				this.creategroupList(chkgroupRight, portalUserModel);
			}
			// create user
			PortalUser user = portalUserModel.getEntity();
			List<PortalGroup> groupLst = portalUserModel.getGroupRightLst();
			user.setGroups(groupLst);




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
			redirectAttributes.addFlashAttribute("portalUserModel", portalUserModel);
			mav.setViewName("redirect:/portal/user/create");
			return mav;
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_EDIT')")
	@RequestMapping(value = "/edit", method = GET)
	public ModelAndView getEdit(@RequestParam(value = "userId") Long userId,
			@ModelAttribute(value = "portalUserModel") PortalUserModel portalUserModel, Model model,
			ModelMap modelMap) {

		
		ModelAndView mav = new ModelAndView("portal/user/user_edit");
		portalUserModel.setPageMode(PageMode.EDIT);
		modelMap.put(BindingResult.MODEL_KEY_PREFIX + "portalUserModel", modelMap.get(CoreConstant.ERRORS));

		try {

			PortalUser portalUser = portalUserService.findOne(userId);
			portalUserModel.setEntity(portalUser);

			List<PortalGroup> groupNotLst = new ArrayList<PortalGroup>();
			List<PortalGroup> groupAll = portalGroupService.findAll();

			List<PortalGroup> groupLst = portalUser.getGroups();
			for (PortalGroup group : groupAll) {
				if (!groupLst.contains(group)) {
					groupNotLst.add(group);
				}
			}
			portalUserModel.setGroupRightLst(groupLst);
			portalUserModel.setGroupLeftLst(groupNotLst);
			
			List<PortalBranch> branchLst = portalBranchService.findAll();
			portalUserModel.setBranchLst(branchLst);
			List<PortalDepartment> departmentLst = portalDepartmentService.findAll();
			portalUserModel.setDepartmentLst(departmentLst);
//			List<PortalTitle> titleLst = portalTitleService.findAll();
//			portalUserModel.setTitleLst(titleLst);

			mav.addObject("groupLeftLst", groupNotLst);
			mav.addObject("groupRightLst", groupLst);
			mav.addObject("portalUserModel", portalUserModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;

	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public ModelAndView postEdit(@ModelAttribute(value = "portalUserModel") @Valid PortalUserModel portalUserModel,
			Model model, Locale locale, HttpServletRequest request, RedirectAttributes redirectAttributes,
			BindingResult bindingResult) {

		
		ModelAndView mav = new ModelAndView("redirect:/portal/user/edit");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		Long branchId = null;
		Long departmentId = null;
		Long titleId = null;
		try {
			branchId = portalUserModel.getEntity().getBranch().getBranchId();
			departmentId = portalUserModel.getEntity().getDepartment().getDepartmentId();
//			titleId = portalUserModel.getEntity().getTitle().getTitleId();
			if (bindingResult.hasErrors() || branchId == null || departmentId == null || titleId == null) {
				if (new Exception() instanceof DataIntegrityViolationException) {
					bindingResult.rejectValue("entity.username", "Exists.error.code", null, "");
				}
				if (branchId == null) {
					bindingResult.rejectValue("entity.branch.branchId", "user.field.branch.error.empty", null, "");
				}
				if (titleId == null) {
					bindingResult.rejectValue("entity.title.titleId", "user.field.title.error.empty", null, "");
				}
				if (departmentId == null) {
					bindingResult.rejectValue("entity.department.departmentId", "user.field.department.error.empty",
							null, "");
				}

				redirectAttributes.addFlashAttribute(CoreConstant.ERRORS, bindingResult);
				redirectAttributes.addFlashAttribute("portalUserModel", portalUserModel);
				redirectAttributes.addAttribute("userId", portalUserModel.getEntity().getUserId());
				return mav;
			}

			String[] checkgroupRight = request.getParameterValues("checkRoleRight");
			if (checkgroupRight != null) {
				this.creategroupList(checkgroupRight, portalUserModel);
			}

			// Update group for user to keycloak
			PortalUser user = portalUserModel.getEntity();
			PortalUser portalUser = portalUserService.findOne(portalUserModel.getEntity().getUserId());
			List<PortalGroup> groupLstOld = portalUser.getGroups(); 
			List<PortalGroup> groupLstNew = portalUserModel.getGroupRightLst();
			List<String> groupLstDel = new ArrayList<String>();
			List<String> groupLstSave = new ArrayList<String>();
			if (!CollectionUtils.isEmpty(groupLstNew)) {
				for (PortalGroup group : groupLstNew) {
					
					// loai bo group da ton tai trong user
					if (!CollectionUtils.isEmpty(groupLstOld) && groupLstOld.contains(group)) {
						continue;
					}
					
					// set danh sach group cần thêm
					if (CollectionUtils.isEmpty(groupLstOld) || !groupLstOld.contains(group)) {
//						groupLstSave.add(group.getGroupKeycloakId());
					}
				}
			}
			if (!CollectionUtils.isEmpty(groupLstOld)) {
				for (PortalGroup group : groupLstOld) {
					if (CollectionUtils.isEmpty(groupLstNew) || !groupLstNew.contains(group)) {
//						groupLstDel.add(group.getGroupKeycloakId());
					}
				}
			}

						// update user
			user.setGroups(groupLstNew);
			user.setDepartment(portalUserModel.getEntity().getDepartment());
			portalUserService.save(user);

			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			mav.addObject(CoreConstant.MSG_LST, messageLst);
		}

		redirectAttributes.addAttribute("userId", portalUserModel.getEntity().getUserId());
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_DELETE')")
	@RequestMapping(value = "/delete", method = GET)
	public String getDelete(@RequestParam(value = "userId") Long userId, Model model, HttpServletRequest request,
			Locale locale, RedirectAttributes redirectAttributes) {
		return "redirect:/portal/user/list";
	}

	public void creategroupList(String[] checkgroup, PortalUserModel portalUserModel) {
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
}
