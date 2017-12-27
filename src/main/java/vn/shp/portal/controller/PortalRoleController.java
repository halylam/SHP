package vn.shp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.entity.PortalSystem;
import vn.shp.portal.model.PortalRoleModel;
import vn.shp.portal.service.PortalRoleService;
import vn.shp.portal.service.PortalSystemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/role")
public class PortalRoleController {

	@Autowired
	private MessageSource messageSource;



	@Autowired
	PortalRoleService portalRoleService;

	@Autowired
	PortalSystemService portalSystemService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ROLE_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public ModelAndView getList(Model model, HttpServletRequest request) {
		

		PortalRoleModel portalRoleModel = new PortalRoleModel();
		portalRoleModel.setPageMode(PageMode.LIST);

		ModelAndView mav = portalRoleService.initSearch(portalRoleModel, request);
		mav.addObject("portalRoleModel", portalRoleModel);
		mav.setViewName("portal/role/role_list");
		return mav;
	}

	@RequestMapping(value = "/ajaxList", method = GET)
	@ResponseBody
	public ModelAndView ajaxList(@ModelAttribute(value = "portalRoleModel") PortalRoleModel portalRoleModel,
			HttpServletRequest request) {

		ModelAndView mav = portalRoleService.initSearch(portalRoleModel, request);
		mav.setViewName("portal/role/role_table");
		return mav;
	}

	@RequestMapping(value = "/list", method = POST)
	public ModelAndView postList(@ModelAttribute(value = "portalRoleModel") PortalRoleModel portalRoleModel,
			Model model, HttpServletRequest request) {
		
		ModelAndView mav = portalRoleService.initSearch(portalRoleModel, request);
		mav.setViewName("portal/role/role_list");
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ROLE_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(Model model, HttpServletRequest request) {
		
		List<PortalSystem> portalSystemLst = portalSystemService.findAll();

		// =========
		PortalRoleModel portalRoleModel = new PortalRoleModel();
		portalRoleModel.setPageMode(PageMode.CREATE);

		ModelAndView mav = new ModelAndView("portal/role/role_create");
		mav.addObject("portalSystemLst", portalSystemLst);
		mav.addObject("portalRoleModel", portalRoleModel);

		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "portalRoleModel") @Valid PortalRoleModel portalRoleModel,
			BindingResult bindingResult, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			Locale locale) {
		
		ModelAndView mav = new ModelAndView("redirect:/portal/role/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {

			if (bindingResult.hasErrors()) {
				throw new Exception();
			}

			PortalRole portalRole = portalRoleModel.getEntity();
			PortalSystem portalSystem = portalSystemService.findOne(portalRole.getSystem().getSystemId());
			portalRole.setSystem(portalSystem);
			

			portalRoleService.save(portalRole);
			
			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.roleCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			List<PortalSystem> portalSystemLst = portalSystemService.findAll();

			portalRoleModel.setPageMode(PageMode.CREATE);
			mav.setViewName("portal/role/role_create");
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.addObject("portalSystemLst", portalSystemLst);
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ROLE_EDIT')")
	@RequestMapping(value = "/edit", method = GET)
	public ModelAndView getEdit(@RequestParam(value = "roleId") Long roleId,
			@ModelAttribute(value = "portalRoleModel") PortalRoleModel portalRoleModel, Model model) {

		
		List<PortalSystem> portalSystemLst = portalSystemService.findAll();

		PortalRole portalRole = portalRoleService.findOne(roleId);
		portalRoleModel.setEntity(portalRole);

		portalRoleModel.setPageMode(PageMode.EDIT);
		ModelAndView mav = new ModelAndView("portal/role/role_edit");
		mav.addObject("portalSystemLst", portalSystemLst);
		mav.addObject("portalRoleModel", portalRoleModel);
		return mav;

	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public ModelAndView postEdit(@ModelAttribute(value = "portalRoleModel") @Valid PortalRoleModel portalRoleModel,
			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes) {

		
		ModelAndView mav = new ModelAndView("redirect:/portal/role/edit");
		
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			
			PortalRole portalRole = portalRoleModel.getEntity();
			PortalSystem portalSystem = portalSystemService.findOne(portalRole.getSystem().getSystemId());
			portalRole.setSystem(portalSystem);
			portalRole.setRoleName(portalRole.getRoleName().toUpperCase());
			

			// save portalRole
			portalRoleService.save(portalRole);

			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.roleCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			List<PortalSystem> portalSystemLst = portalSystemService.findAll();
			
			mav.setViewName("portal/role/role_edit");
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.addObject("portalSystemLst", portalSystemLst);
		}
		
		redirectAttributes.addAttribute("roleId", portalRoleModel.getEntity().getRoleId());
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ROLE_DELETE')")
	@RequestMapping(value = "/delete", method = GET)
	public String getDelete(@RequestParam(value = "roleId") Long roleId, Model model, HttpServletRequest request,
			Locale locale, RedirectAttributes redirectAttributes) {
		try {
			PortalRole portalRole = portalRoleService.findOne(roleId);

			portalRoleService.delete(roleId);

			MessageList messageLst = new MessageList(Message.SUCCESS);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/portal/role/list";
	}
}
