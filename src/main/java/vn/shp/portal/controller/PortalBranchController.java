//package vn.shp.portal.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import vn.shp.portal.common.PageMode;
//import vn.shp.portal.constant.CoreConstant;
//import vn.shp.portal.core.Message;
//import vn.shp.portal.core.MessageList;
//import vn.shp.portal.entity.PortalBranch;
//import vn.shp.portal.entity.PortalGroup;
//import vn.shp.portal.model.PortalBranchModel;
//import vn.shp.portal.service.PortalBranchService;
//import vn.shp.portal.service.PortalGroupService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//@Controller
//@RequestMapping("portal/branch")
//public class PortalBranchController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//
//	@Autowired
//	PortalBranchService portalBranchService;
//
//	@Autowired
//	PortalGroupService portalGroupService;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BRANCH_LIST')")
//	@RequestMapping(value = "/list", method = GET)
//	public ModelAndView getList(Model model, HttpServletRequest request) {
//
//
//		PortalBranchModel portalBranchModel = new PortalBranchModel();
//		portalBranchModel.setPageMode(PageMode.LIST);
//
//		ModelAndView mav = portalBranchService.initSearch(portalBranchModel, request);
//		mav.addObject("portalBranchModel", portalBranchModel);
//		mav.setViewName("portal/branch/branch_list");
//		return mav;
//	}
//
//	@RequestMapping(value = "/ajaxList", method = GET)
//	@ResponseBody
//	public ModelAndView ajaxList(@ModelAttribute(value = "portalBranchModel") PortalBranchModel portalBranchModel,
//			HttpServletRequest request) {
//
//		ModelAndView mav = portalBranchService.initSearch(portalBranchModel, request);
//		mav.setViewName("portal/branch/branch_table");
//		return mav;
//	}
//
//	@RequestMapping(value = "/list", method = POST)
//	public ModelAndView postList(@ModelAttribute(value = "portalBranchModel") PortalBranchModel portalBranchModel,
//			Model model, HttpServletRequest request) {
//		;
//		ModelAndView mav = portalBranchService.initSearch(portalBranchModel, request);
//		mav.setViewName("portal/branch/branch_list");
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BRANCH_CREATE')")
//	@RequestMapping(value = "/create", method = GET)
//	public ModelAndView getCreate(Model model, HttpServletRequest request) {
//		;
//		PortalBranchModel portalBranchModel = new PortalBranchModel();
//
//		List<PortalGroup> groupLeftLst = portalGroupService.findAll();
//		portalBranchModel.setGroupLeftLst(groupLeftLst);
//
//		// =========
//		portalBranchModel.setPageMode(PageMode.CREATE);
//
//		ModelAndView mav = new ModelAndView("portal/branch/branch_create");
//		mav.addObject("portalBranchModel", portalBranchModel);
//
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = POST)
//	public ModelAndView postCreate(@ModelAttribute(value = "portalBranchModel") @Valid PortalBranchModel portalBranchModel,
//			BindingResult bindingResult, Model model, HttpServletRequest request,
//			RedirectAttributes redirectAttributes, Locale locale) {
//		;
//		ModelAndView mav = new ModelAndView("redirect:/portal/branch/list");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//			// create user
//			PortalBranch branch = portalBranchModel.getEntity();
//			branch.setBranchCode(branch.getBranchCode().toUpperCase());
//			branch.setBranchName(branch.getBranchName().toUpperCase());
//			portalBranchService.save(branch);
//
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//
//		} catch (Exception e) {
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.branchCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.setViewName("portal/branch/branch_create");
//
//			List<PortalGroup> roleLeftLst = portalGroupService.findAll();
//			portalBranchModel.setGroupLeftLst(roleLeftLst);
//		}
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BRANCH_EDIT')")
//	@RequestMapping(value = "/edit", method = GET)
//	public ModelAndView getEdit(@RequestParam(value = "branchId") Long branchId,
//			@ModelAttribute(value = "portalBranchModel") PortalBranchModel portalBranchModel, Model model) {
//
//		;
//
//		PortalBranch portalBranch = portalBranchService.findOne(branchId);
//		portalBranchModel.setEntity(portalBranch);
//
//		portalBranchModel.setPageMode(PageMode.EDIT);
//		ModelAndView mav = new ModelAndView("portal/branch/branch_edit");
//		mav.addObject("portalBranchModel", portalBranchModel);
//		return mav;
//
//	}
//
//	/**
//	 * EDIT - POST
//	 */
//	@RequestMapping(value = "/edit", method = POST)
//	public ModelAndView postEdit(@ModelAttribute(value = "portalBranchModel") @Valid PortalBranchModel portalBranchModel,
//			Model model, Locale locale, HttpServletRequest request, RedirectAttributes redirectAttributes,
//			BindingResult bindingResult) {
//
//		;
//		ModelAndView mav = new ModelAndView("redirect:/portal/branch/edit");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		try {
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//			PortalBranch branch = portalBranchModel.getEntity();
//			branch.setBranchCode(branch.getBranchCode().toUpperCase());
//			branch.setBranchName(branch.getBranchName().toUpperCase());
//			portalBranchService.save(branch);
//
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.branchCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.setViewName("redirect:/portal/branch/edit");
//		}
//
//		redirectAttributes.addAttribute("branchId", portalBranchModel.getEntity().getBranchId());
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BRANCH_DELETE')")
//	@RequestMapping(value = "/delete", method = GET)
//	public String getDelete(@RequestParam(value = "branchId") Long branchId, Model model, HttpServletRequest request,
//			Locale locale, RedirectAttributes redirectAttributes) {
//		try {
//			portalBranchService.delete(branchId);
//
//			MessageList messageLst = new MessageList(Message.SUCCESS);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/portal/branch/list";
//	}
//
//	public void createRoleList(String[] checkgroup, PortalBranchModel portalBranchModel) {
//		// Get listAll role
//		List<PortalGroup> groupAll = portalGroupService.findAll();
//
//		if ((checkgroup != null) && (checkgroup.length > 0)) {
//			List<PortalGroup> groupLst = new ArrayList<PortalGroup>();
//			List<PortalGroup> groupNotLst = new ArrayList<PortalGroup>();
//			boolean flag = false;
//			for (PortalGroup group : groupAll) {
//				flag = false;
//				for (int i = 0; i < checkgroup.length; i++) {
//					if (checkgroup[i].equals(group.getGroupId().toString())) {
//						groupLst.add(group);
//						flag = true;
//						break;
//					}
//				}
//				if (!flag) {
//					groupNotLst.add(group);
//				}
//			}
//			portalBranchModel.setGroupRightLst(groupLst);
//			portalBranchModel.setGroupLeftLst(groupNotLst);
//		} else {
//			// Get listAll group for the groupNotOfTeam
//			portalBranchModel.setGroupRightLst(groupAll);
//			// List of team's group is empty (default value)
//		}
//	}
//}
