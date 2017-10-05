//package vn.vccb.portal.controller;
//
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.dao.DataIntegrityViolationException;
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
//import vn.shp.portal.model.PortalWorkflowModel;
//import vn.shp.portal.service.PortalWorkflowService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.Locale;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//
//@Controller
//@RequestMapping("portal/workflow")
//public class PortalWorkflowController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//
//
//	@Autowired
//	PortalWorkflowService portalWorkflowService;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = "/list", method = GET)
//	public String getList(Model model, HttpServletRequest request) {
//
//
//		PortalWorkflowModel portalWorkflowModel = new PortalWorkflowModel();
//		portalWorkflowModel.setData(portalWorkflowService.findAll());
//		portalWorkflowModel.setPageMode(PageMode.LIST);
//		model.addAttribute("portalWorkflowModel", portalWorkflowModel);
//		return "portal/workflow/workflow_list";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = { "/create", "/edit" }, method = RequestMethod.GET)
//	public String getCreateEdit(@RequestParam(required = false) Long id,
//			@ModelAttribute(value = "portalWorkflowModel") PortalWorkflowModel portalWorkflowModel, Model model)
//			throws Exception {
//
//		portalWorkflowModel.setPageMode(PageMode.CREATE);
//		if (id != null) {
//			portalWorkflowModel.setPageMode(PageMode.EDIT);
//		}
//		if (portalWorkflowModel.getEntity() == null) {
//		}
//		model.addAttribute("portalWorkflowModel", portalWorkflowModel);
//		return "portal/workflow/workflow_edit";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = { "/create", "/edit" }, method = RequestMethod.POST)
//	public String postCreateEdit(@ModelAttribute(value = "portalWorkflowModel") @Valid PortalWorkflowModel portalWorkflowModel, BindingResult bindingResult,
//			RedirectAttributes redirectAttributes, Model model, Locale locale) {
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			if (portalWorkflowModel.getPageMode() == PageMode.CREATE) {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//				messageLst.add(msgInfo);
//				redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//				redirectAttributes.addFlashAttribute("portalWorkflowModel", portalWorkflowModel);
//				return "redirect:/portal/workflow/list";
//			}
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//
//		} catch (Exception e) {
//			messageLst.setStatus(Message.ERROR);
//			if (portalWorkflowModel.getPageMode() == PageMode.CREATE) {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			} else {
//				msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			}
//			if (e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.code", "Exists.error.code", null, null);
//			} else if (StringUtils.isNotEmpty(e.getMessage())) {
//				msgInfo = messageSource.getMessage(e.getMessage(), null, locale);
//			}
//		}
//
//		messageLst.add(msgInfo);
//		model.addAttribute(CoreConstant.MSG_LST, messageLst);;
//		model.addAttribute("portalWorkFlowModel", portalWorkflowModel);
//		return "portal/workflow/workflow_edit";
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
//	public String postDelete(@RequestParam(value = "id") Long id, @ModelAttribute(value = "portalWorkflowModel") PortalWorkflowModel portalWorkflowModel,
//			Locale locale, RedirectAttributes redirectAttributes) {
//		portalWorkflowService.delete(id);
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		messageLst.add(messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale));
//		redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		redirectAttributes.addFlashAttribute("portalWorkflowModel", portalWorkflowModel);
//		return "redirect:/portal/workflow/list";
//	}
//}
