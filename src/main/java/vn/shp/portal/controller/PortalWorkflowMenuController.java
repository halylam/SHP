//package vn.vccb.portal.controller;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import vn.shp.portal.common.PageMode;
//import vn.shp.portal.constant.CoreConstant;
//import vn.shp.portal.core.Message;
//import vn.shp.portal.core.MessageList;
//import vn.shp.portal.entity.PortalWorkflowMenu;
//import vn.shp.portal.model.PortalWorkflowMenuModel;
//import vn.shp.portal.service.PortalWorkflowMenuService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.Locale;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//
//@Controller
//@RequestMapping("portal/workflow-menu")
//public class PortalWorkflowMenuController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//	@Autowired
//	PortalWorkflowMenuService portalWorkflowMenuService;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = "/list", method = GET)
//	public String getList(Model model, HttpServletRequest request) {
//
//
//		PortalWorkflowMenuModel portalWorkflowMenuModel = new PortalWorkflowMenuModel();
//		portalWorkflowMenuModel.setData(portalWorkflowMenuService.findAll());
//		portalWorkflowMenuModel.setPageMode(PageMode.LIST);
//		model.addAttribute("portalWorkflowMenuModel", portalWorkflowMenuModel);
//		return "portal/workflow_menu/workflow_menu_list";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = { "/create", "/edit" }, method = RequestMethod.GET)
//	public String getCreateEdit(@RequestParam(required = false) Long id,
//			@ModelAttribute(value = "portalWorkflowMenuModel") PortalWorkflowMenuModel portalWorkflowMenuModel,
//			Model model) throws Exception {
//
//		portalWorkflowMenuModel.setPageMode(PageMode.CREATE);
//		PortalWorkflowMenu entity = new PortalWorkflowMenu();
//		if (id != null) {
//			portalWorkflowMenuModel.setPageMode(PageMode.EDIT);
//			entity = portalWorkflowMenuService.findOne(id);
//			if (entity == null) {
//				throw new Exception();
//			}
//		}
//		if (portalWorkflowMenuModel.getEntity() == null) {
//			portalWorkflowMenuModel.setEntity(entity);
//		}
//		model.addAttribute("portalWorkflowMenuModel", portalWorkflowMenuModel);
//		return "portal/workflow_menu/workflow_menu_edit";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = { "/create", "/edit" }, method = RequestMethod.POST)
//	public String postCreateEdit(
//			@ModelAttribute(value = "portalWorkflowMenuModel") @Valid PortalWorkflowMenuModel portalWorkflowMenuModel,
//			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Locale locale) {
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//			PortalWorkflowMenu entity = portalWorkflowMenuModel.getEntity();
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			portalWorkflowMenuService.save(entity);
//			if (portalWorkflowMenuModel.getPageMode() == PageMode.CREATE) {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//				messageLst.add(msgInfo);
//				redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//				redirectAttributes.addFlashAttribute("portalWorkflowMenuModel", portalWorkflowMenuModel);
//				return "redirect:/portal/workflow-menu/list";
//			}
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//
//		} catch (Exception e) {
//			messageLst.setStatus(Message.ERROR);
//			if (portalWorkflowMenuModel.getPageMode() == PageMode.CREATE) {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			} else {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			}
//			if (StringUtils.isNotEmpty(e.getMessage())) {
//				msgInfo = messageSource.getMessage(e.getMessage(), null, locale);
//			}
//		}
//
//		messageLst.add(msgInfo);
//		model.addAttribute(CoreConstant.MSG_LST, messageLst);
//		;
//		model.addAttribute("portalWorkFlowMenuModel", portalWorkflowMenuModel);
//		return "portal/workflow_menu/workflow_menu_edit";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
//	public String postDelete(@RequestParam(value = "id") Long id,
//			@ModelAttribute(value = "portalWorkflowMenuModel") PortalWorkflowMenuModel portalWorkflowMenuModel,
//			Locale locale, RedirectAttributes redirectAttributes) {
//		portalWorkflowMenuService.delete(id);
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		messageLst.add(messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale));
//		redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		redirectAttributes.addFlashAttribute("portalWorkflowMenuModel", portalWorkflowMenuModel);
//		return "redirect:/portal/workflow-menu/list";
//	}
//}
