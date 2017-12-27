//package vn.shp.portal.controller;
//
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
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
//import vn.shp.portal.entity.PortalDepartment;
//import vn.shp.portal.model.PortalDepartmentModel;
//import vn.shp.portal.service.PortalDepartmentService;
//import vn.shp.portal.service.PortalGroupService;
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
//@RequestMapping("portal/department")
//public class PortalDepartmentController {
//
//	@Autowired
//	private MessageSource messageSource;
//
//
//	@Autowired
//	PortalDepartmentService portalDepartmentService;
//
//	@Autowired
//	PortalGroupService portalGroupService;
//
//	@ModelAttribute("portalDepartmentModel")
//	public PortalDepartmentModel portalDepartmentModel() {
//
//		PortalDepartmentModel portalDepartmentModel = new PortalDepartmentModel();
//		PortalDepartment portalDepartment = new PortalDepartment();
//		portalDepartmentModel.setEntity(portalDepartment);
//
//		return portalDepartmentModel;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEPARTMENT_LIST')")
//	@RequestMapping(value = "/list", method = GET)
//	public ModelAndView getList(Model model, HttpServletRequest request) {
//		;
//		ModelAndView mav = new ModelAndView("portal/department/department_list");
//
//		PortalDepartmentModel portalDepartmentModel = new PortalDepartmentModel();
//		portalDepartmentModel.setPageMode(PageMode.LIST);
//		List<PortalDepartment> portalDepartmentLst = portalDepartmentService.findAll();
//
//		mav.addObject("portalDepartmentModel", portalDepartmentModel);
//		mav.addObject("portalDepartmentLst", portalDepartmentLst);
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEPARTMENT_CREATE')")
//	@RequestMapping(value = "/create", method = GET)
//	public ModelAndView getCreate(
//			@ModelAttribute(value = "portalDepartmentModel") PortalDepartmentModel portalDepartmentModel, Model model,
//			HttpServletRequest request, ModelMap modelMap) {
//		;
//		modelMap.put(BindingResult.MODEL_KEY_PREFIX + "portalDepartmentModel", modelMap.get(CoreConstant.ERRORS));
//
//		portalDepartmentModel.setLstGroup(portalGroupService.findAll());
//		portalDepartmentModel.setPageMode(PageMode.CREATE);
//		ModelAndView mav = new ModelAndView("portal/department/department_create");
//		mav.addObject("portalDepartmentModel", portalDepartmentModel);
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = POST)
//	public ModelAndView postCreate(
//			@ModelAttribute(value = "portalDepartmentModel") @Valid PortalDepartmentModel portalDepartmentModel,
//			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model,
//			Locale locale) {
//		;
//		ModelAndView mav = new ModelAndView("redirect:/portal/department/list");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		String msgInfo = "";
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalDepartment portalDepartment = portalDepartmentModel.getEntity();
//			portalDepartment.setDepartmentCode(
//					Common.deAccent(portalDepartment.getDepartmentCode()).replaceAll(" ", "").toUpperCase());
//			portalDepartment.setDepartmentName(portalDepartment.getDepartmentName().toUpperCase());
//			portalDepartmentService.save(portalDepartment);
//
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//
//		} catch (Exception e) {
//			if (e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.departmentCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//			redirectAttributes.addFlashAttribute("portalDepartmentModel", portalDepartmentModel);
//			redirectAttributes.addFlashAttribute(CoreConstant.ERRORS, bindingResult);
//			mav.setViewName("redirect:/portal/department/create");
//		}
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEPARTMENT_EDIT')")
//	@RequestMapping(value = "/edit", method = GET)
//	public ModelAndView getEdit(@RequestParam(value = "departmentId") Long departmentId,
//			@ModelAttribute(value = "portalDepartmentModel") PortalDepartmentModel portalDepartmentModel, Model model,
//			ModelMap modelMap) {
//
//		;
//		modelMap.put(BindingResult.MODEL_KEY_PREFIX + "portalDepartmentModel", modelMap.get(CoreConstant.ERRORS));
//
//		PortalDepartment portalDepartment = portalDepartmentService.findOne(departmentId);
//		portalDepartmentModel.setEntity(portalDepartment);
//
//		portalDepartmentModel.setLstGroup(portalGroupService.findAll());
//		portalDepartmentModel.setPageMode(PageMode.EDIT);
//		ModelAndView mav = new ModelAndView("portal/department/department_edit");
//		mav.addObject("portalDepartmentModel", portalDepartmentModel);
//		return mav;
//
//	}
//
//	/**
//	 * EDIT - POST
//	 */
//	@RequestMapping(value = "/edit", method = POST)
//	public ModelAndView postEdit(
//			@ModelAttribute(value = "portaldepartmentModel") PortalDepartmentModel portalDepartmentModel,
//			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes) {
//
//		;
//		ModelAndView mav = new ModelAndView("redirect:/portal/department/edit");
//
//		MessageList messageLst = new MessageList(Message.SUCCESS);
//		try {
//
//			if (bindingResult.hasErrors()) {
//				throw new Exception();
//			}
//
//			PortalDepartment portalDepartment = portalDepartmentModel.getEntity();
//			portalDepartment.setDepartmentCode(portalDepartment.getDepartmentCode().toUpperCase());
//			portalDepartment.setDepartmentName(portalDepartment.getDepartmentName().toUpperCase());
//			portalDepartmentService.save(portalDepartment);
//
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			if (e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException) {
//				bindingResult.rejectValue("entity.departmentCode", "Exists.error.code", null, "");
//			}
//			messageLst.setStatus(Message.ERROR);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//			redirectAttributes.addFlashAttribute("portalDepartmentModel", portalDepartmentModel);
//			redirectAttributes.addFlashAttribute(CoreConstant.ERRORS, bindingResult);
//			mav.setViewName("redirect:/portal/department/edit");
//		}
//		redirectAttributes.addAttribute("departmentId", portalDepartmentModel.getEntity().getDepartmentId());
//		return mav;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DEPARTMENT_DELETE')")
//	@RequestMapping(value = "/delete", method = GET)
//	public String getDelete(@RequestParam(value = "departmentId") Long departmentId, Model model,
//			HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) {
//		try {
//			portalDepartmentService.delete(departmentId);
//
//			MessageList messageLst = new MessageList(Message.SUCCESS);
//			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
//			messageLst.add(msgInfo);
//			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:/portal/department/list";
//	}
//
//}
