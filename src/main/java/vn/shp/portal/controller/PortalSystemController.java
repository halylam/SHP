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
//import vn.shp.portal.entity.PortalGroup;
//import vn.shp.portal.entity.PortalRole;
//import vn.shp.portal.entity.PortalSystem;
//import vn.shp.portal.entity.PortalSystemGroup;
//import vn.shp.portal.model.PortalSystemModel;
//import vn.shp.portal.service.PortalRoleService;
//import vn.shp.portal.service.PortalSystemGroupService;
//import vn.shp.portal.service.PortalSystemService;
//import vn.shp.portal.utils.Common;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Locale;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//@Controller
//@RequestMapping("portal/system")
//public class PortalSystemController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//	@Autowired
//	PortalSystemService portalSystemService;
//
//	@Autowired
//	PortalSystemGroupService portalGroupService;
//
//	@Autowired
//	PortalRoleService portalRoleService;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_LIST')")
//	@RequestMapping(value = "/list", method = GET)
//	public ModelAndView getList(Model model, HttpServletRequest request, Locale locale) {
//
//
//		PortalSystemModel portalSystemModel = new PortalSystemModel();
//		portalSystemModel.setPageMode(PageMode.LIST);
//
//		ModelAndView mav = portalSystemService.initSearch(portalSystemModel, request);
//		mav.addObject("portalSystemModel", portalSystemModel);
//		mav.setViewName("portal/system/system_list");
//		return mav;
//	}
//
//	@RequestMapping(value = "/ajaxList", method = GET)
//	@ResponseBody
//	public ModelAndView ajaxList(@ModelAttribute(value = "portalSystemModel") PortalSystemModel portalSystemModel,
//			HttpServletRequest request) {
//
//		ModelAndView mav = portalSystemService.initSearch(portalSystemModel, request);
//		mav.setViewName("portal/system/system_table");
//		return mav;
//	}
//
//	@RequestMapping(value = "/list", method = POST)
//	public ModelAndView postList(@ModelAttribute(value = "portalSystemModel") PortalSystemModel portalSystemModel,
//			Model model, HttpServletRequest request) {
//
//		ModelAndView mav = portalSystemService.initSearch(portalSystemModel, request);
//		mav.setViewName("portal/system/system_list");
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_CREATE')")
//	@RequestMapping(value = "/create", method = GET)
//	public ModelAndView getCreate(Model model, HttpServletRequest request) {
//
//		List<PortalSystemGroup> portalGroupLst = portalGroupService.findAll();
//
//		// =========
//		PortalSystemModel portalSystemModel = new PortalSystemModel();
//		portalSystemModel.setPageMode(PageMode.CREATE);
//
//		ModelAndView mav = new ModelAndView("portal/system/system_create");
//		mav.addObject("portalGroupLst", portalGroupLst);
//		mav.addObject("portalSystemModel", portalSystemModel);
//
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = POST)
//	public ModelAndView postCreate(
//			@ModelAttribute(value = "portalSystemModel") @Valid PortalSystemModel portalSystemModel,
//			BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
//			Locale locale) {
//
//		ModelAndView mav = new ModelAndView("redirect:/portal/system/list");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalSystem portalSystem = portalSystemModel.getEntity();
//			PortalSystemGroup portalGroup = portalGroupService.findOne(portalSystem.getGroup().getGroupId());
//			portalSystem.setGroup(portalGroup);
//			portalSystem.setSystemCode(Common.deAccent(portalSystem.getSystemCode()).replaceAll(" ", "").toUpperCase());
//			portalSystem.setSystemName(portalSystem.getSystemName().toUpperCase());
//
//			// Save system
//			portalSystemService.save(portalSystem);
//
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//
//		} catch (Exception e) {
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.systemCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			List<PortalSystemGroup> portalGroupLst = portalGroupService.findAll();
//
//			mav.setViewName("portal/system/system_create");
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.addObject("portalGroupLst", portalGroupLst);
//		}
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_EDIT')")
//	@RequestMapping(value = "/edit", method = GET)
//	public ModelAndView getEdit(@RequestParam(value = "systemId") Long systemId,
//			@ModelAttribute(value = "portalSystemModel") PortalSystemModel portalSystemModel, Model model) {
//
//
//		List<PortalSystemGroup> portalGroupLst = portalGroupService.findAll();
//
//		PortalSystem portalSystem = portalSystemService.findOne(systemId);
//		portalSystemModel.setEntity(portalSystem);
//
//		portalSystemModel.setPageMode(PageMode.EDIT);
//		ModelAndView mav = new ModelAndView("portal/system/system_edit");
//		mav.addObject("portalGroupLst", portalGroupLst);
//		mav.addObject("portalSystemModel", portalSystemModel);
//		return mav;
//
//	}
//
//	/**
//	 * EDIT - POST
//	 */
//	@RequestMapping(value = "/edit", method = POST)
//	public ModelAndView postEdit(
//			@ModelAttribute(value = "portalSystemModel") @Valid PortalSystemModel portalSystemModel,
//			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes) {
//
//
//		ModelAndView mav = new ModelAndView("redirect:/portal/system/edit");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalSystem portalSystem = portalSystemModel.getEntity();
//			PortalSystemGroup portalGroup = portalGroupService.findOne(portalSystem.getGroup().getGroupId());
//			portalSystem.setGroup(portalGroup);
//			portalSystem.setSystemCode(portalSystem.getSystemCode().toUpperCase());
//			portalSystem.setSystemName(portalSystem.getSystemName().toUpperCase());
//			portalSystemService.save(portalSystem);
//
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//
//		} catch (Exception e) {
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.systemCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			List<PortalSystemGroup> portalGroupLst = portalGroupService.findAll();
//
//			mav.setViewName("portal/system/system_edit");
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.addObject("portalGroupLst", portalGroupLst);
//		}
//		redirectAttributes.addAttribute("systemId", portalSystemModel.getEntity().getSystemId());
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_DELETE')")
//	@RequestMapping(value = "/delete", method = GET)
//	public String getDelete(@RequestParam(value = "systemId") Long systemId, Model model, HttpServletRequest request,
//			Locale locale, RedirectAttributes redirectAttributes) {
//		try {
//			PortalSystem portalSystem = portalSystemService.findOne(systemId);
//			for (PortalRole role : portalSystem.getRoleLst()) {
//				for (PortalGroup group : role.getGroups()) {
//					group.getRoleGroupLst().remove(role);
//				}
//				portalRoleService.delete(role.getRoleId());
//			}
//
//			portalSystemService.delete(systemId);
//			MessageList messageLst = new MessageList(Message.SUCCESS);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/portal/system/list";
//	}
//
//}
