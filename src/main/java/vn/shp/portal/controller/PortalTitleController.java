package vn.shp.portal.controller;

import org.hibernate.exception.ConstraintViolationException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalTitle;
import vn.shp.portal.model.PortalTitleModel;
import vn.shp.portal.service.PortalTitleService;
import vn.shp.portal.utils.Common;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/title")
public class PortalTitleController {

	@Autowired
	private MessageSource messageSource;


	@Autowired
	PortalTitleService portalTitleService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TITLE_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public ModelAndView getList(Model model, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("portal/title/title_list");

		PortalTitleModel portalTitleModel = new PortalTitleModel();
		portalTitleModel.setPageMode(PageMode.LIST);
		List<PortalTitle> portalTitleLst = portalTitleService.findAll();
		
		mav.addObject("portalTitleModel", portalTitleModel);
		mav.addObject("portalTitleLst", portalTitleLst);
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TITLE_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(Model model, HttpServletRequest request) {
		
		PortalTitleModel portalTitleModel = new PortalTitleModel();
		portalTitleModel.setPageMode(PageMode.CREATE);

		ModelAndView mav = new ModelAndView("portal/title/title_create");
		mav.addObject("portalTitleModel", portalTitleModel);

		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "portalTitleModel") @Valid PortalTitleModel portalTitleModel,
			BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model,
			Locale locale) {
		
		ModelAndView mav = new ModelAndView("redirect:/portal/title/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {

			if (bindingResult.hasErrors()) {
				throw new Exception();
			}

			PortalTitle portalTitle = portalTitleModel.getEntity();
			portalTitle.setTitleCode(Common.deAccent(portalTitle.getTitleCode()).replaceAll(" ", "").toUpperCase());
			portalTitle.setTitleName(portalTitle.getTitleName().toUpperCase());
			portalTitleService.save(portalTitle);

			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

		} catch (Exception e) {
			if (e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.titleCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("portal/title/title_create");
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TITLE_EDIT')")
	@RequestMapping(value = "/edit", method = GET)
	public ModelAndView getEdit(@RequestParam(value = "titleId") Long titleId,
			@ModelAttribute(value = "portalTitleModel") PortalTitleModel portalTitleModel, Model model) {

		

		PortalTitle portalTitle = portalTitleService.findOne(titleId);
		portalTitleModel.setEntity(portalTitle);

		portalTitleModel.setPageMode(PageMode.EDIT);
		ModelAndView mav = new ModelAndView("portal/title/title_edit");
		mav.addObject("portalTitleModel", portalTitleModel);
		return mav;

	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public ModelAndView postEdit(@ModelAttribute(value = "portalTitleModel") @Valid PortalTitleModel portalTitleModel,
			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes) {

		
		ModelAndView mav = new ModelAndView("redirect:/portal/title/edit");
				
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {

			if (bindingResult.hasErrors()) {
				throw new Exception();
			}

			PortalTitle portalTitle = portalTitleModel.getEntity();
			portalTitle.setTitleCode(portalTitle.getTitleCode().toUpperCase());
			portalTitle.setTitleName(portalTitle.getTitleName().toUpperCase());
			portalTitleService.save(portalTitle);

			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.titleCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("portal/title/title_edit");
		}
		redirectAttributes.addAttribute("titleId", portalTitleModel.getEntity().getTitleId());
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TITLE_DELETE')")
	@RequestMapping(value = "/delete", method = GET)
	public String getDelete(@RequestParam(value = "titleId") Long titleId, Model model, HttpServletRequest request,
			Locale locale, RedirectAttributes redirectAttributes) {
		try {
			portalTitleService.delete(titleId);

			MessageList messageLst = new MessageList(Message.SUCCESS);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/portal/title/list";
	}

}
