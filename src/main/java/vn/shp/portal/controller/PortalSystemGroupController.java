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
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import vn.shp.portal.common.PageMode;
//import vn.shp.portal.constant.CoreConstant;
//import vn.shp.portal.core.Message;
//import vn.shp.portal.core.MessageList;
//import vn.shp.portal.entity.PortalSystem;
//import vn.shp.portal.entity.PortalSystemGroup;
//import vn.shp.portal.model.PortalSystemGroupModel;
//import vn.shp.portal.service.PortalRoleService;
//import vn.shp.portal.service.PortalSystemGroupService;
//import vn.shp.portal.service.PortalSystemService;
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
//@RequestMapping("portal/systemgroup")
//public class PortalSystemGroupController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//	@Autowired
//	PortalSystemGroupService portalGroupService;
//
//	@Autowired
//	PortalRoleService portalRoleService;
//
//	@Autowired
//	PortalSystemService portalSystemService;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_GROUP_LIST')")
//	@RequestMapping(value = "/list", method = GET)
//	public ModelAndView getList(Model model, HttpServletRequest request) {
//
//		ModelAndView mav = new ModelAndView("portal/systemgroup/systemgroup_list");
//
//		PortalSystemGroupModel portalGroupModel = new PortalSystemGroupModel();
//		portalGroupModel.setPageMode(PageMode.LIST);
//		List<PortalSystemGroup> portalGroupLst = portalGroupService.findAll();
//
//		mav.addObject("portalGroupModel", portalGroupModel);
//		mav.addObject("portalGroupLst", portalGroupLst);
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_GROUP_CREATE')")
//	@RequestMapping(value = "/create", method = GET)
//	public ModelAndView getCreate(Model model, HttpServletRequest request) {
//
//		PortalSystemGroupModel portalGroupModel = new PortalSystemGroupModel();
//		portalGroupModel.setPageMode(PageMode.CREATE);
//
//		ModelAndView mav = new ModelAndView("portal/systemgroup/systemgroup_create");
//		mav.addObject("portalGroupModel", portalGroupModel);
//
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = POST)
//	public ModelAndView postCreate(@ModelAttribute(value = "portalGroupModel") @Valid PortalSystemGroupModel portalGroupModel,
//			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model,
//			Locale locale) {
//
//		ModelAndView mav = new ModelAndView("redirect:/portal/systemgroup/list");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalSystemGroup portalGroup = portalGroupModel.getEntity();
//			portalGroup.setGroupCode(portalGroup.getGroupCode().toUpperCase());
//			portalGroup.setGroupName(portalGroup.getGroupName().toUpperCase());
//			portalGroupService.save(portalGroup);
//
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.groupCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			messageLst.add(msgInfo);
//
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.setViewName("portal/systemgroup/systemgroup_create");
//		}
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_GROUP_EDIT')")
//	@RequestMapping(value = "/edit", method = GET)
//	public ModelAndView getEdit(@RequestParam(value = "groupId") Long groupId,
//			@ModelAttribute(value = "portalGroupModel") PortalSystemGroupModel portalGroupModel, Model model) {
//
//
//
//		PortalSystemGroup portalGroup = portalGroupService.findOne(groupId);
//		portalGroupModel.setEntity(portalGroup);
//
//		portalGroupModel.setPageMode(PageMode.EDIT);
//		ModelAndView mav = new ModelAndView("portal/systemgroup/systemgroup_edit");
//		mav.addObject("portalGroupModel", portalGroupModel);
//		return mav;
//
//	}
//
//	/**
//	 * EDIT - POST
//	 */
//	@RequestMapping(value = "/edit", method = POST)
//	public ModelAndView postEdit(@ModelAttribute(value = "portalGroupModel") @Valid PortalSystemGroupModel portalGroupModel,
//			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes) {
//
//
//		ModelAndView mav = new ModelAndView("redirect:/portal/systemgroup/edit");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalSystemGroup portalGroup = portalGroupModel.getEntity();
//			portalGroup.setGroupCode(portalGroup.getGroupCode().toUpperCase());
//			portalGroup.setGroupName(portalGroup.getGroupName().toUpperCase());
//			portalGroupService.save(portalGroup);
//
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			if (e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.groupCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//
//			mav.addObject(CoreConstant.MSG_LST, messageLst);
//			mav.setViewName("portal/systemgroup/systemgroup_edit");
//		}
//		redirectAttributes.addAttribute("groupId", portalGroupModel.getEntity().getGroupId());
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_GROUP_DELETE')")
//	@RequestMapping(value = "/delete", method = GET)
//	public String getDelete(@RequestParam(value = "groupId") Long groupId, Model model, HttpServletRequest request,
//			Locale locale, RedirectAttributes redirectAttributes) {
//		try {
//
//			PortalSystemGroup portalSystemGroup = portalGroupService.findOne(groupId);
//			for (PortalSystem portalSystem : portalSystemGroup.getSystemLst()) {
//
//				portalSystemService.delete(portalSystem.getSystemId());
//			}
//
//			portalGroupService.delete(groupId);
//			MessageList messageLst = new MessageList(Message.SUCCESS);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/portal/systemgroup/list";
//	}
//
//}
