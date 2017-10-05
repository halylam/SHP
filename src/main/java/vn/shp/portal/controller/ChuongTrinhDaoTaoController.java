package vn.shp.portal.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import vn.shp.app.entity.ChuongTrinhDaoTao;
import vn.shp.portal.model.ChuongTrinhDaoTaoModel;
import vn.shp.portal.service.ChuongTrinhDaoTaoService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/chuongtrinhdaotao")
public class ChuongTrinhDaoTaoController {

	@Autowired
	private MessageSource messageSource;
	
	
	@Autowired
	ChuongTrinhDaoTaoService chuongTrinhDaoTaoService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public ModelAndView getList(Model model, HttpServletRequest request) {
		

		ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel = new ChuongTrinhDaoTaoModel();
		chuongTrinhDaoTaoModel.setPageMode(PageMode.LIST);

		ModelAndView mav = chuongTrinhDaoTaoService.initSearch(chuongTrinhDaoTaoModel, request);
		mav.addObject("chuongTrinhDaoTaoModel", chuongTrinhDaoTaoModel);
		mav.setViewName("portal/chuongtrinhdaotao/chuongtrinhdaotao_list");
		return mav;
	}

	@RequestMapping(value = "/ajaxList", method = GET)
	@ResponseBody
	public ModelAndView ajaxList(@ModelAttribute(value = "chuongTrinhDaoTaoModel") ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel,
			HttpServletRequest request) {

		ModelAndView mav = chuongTrinhDaoTaoService.initSearch(chuongTrinhDaoTaoModel, request);
		mav.setViewName("portal/chuongtrinhdaotao/chuongtrinhdaotao_table");
		return mav;
	}

	@RequestMapping(value = "/list", method = POST)
	public ModelAndView postList(@ModelAttribute(value = "chuongTrinhDaoTaoModel") ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel,
			Model model, HttpServletRequest request) {
		;
		ModelAndView mav = chuongTrinhDaoTaoService.initSearch(chuongTrinhDaoTaoModel, request);
		mav.setViewName("portal/chuongtrinhdaotao/chuongtrinhdaotao_list");
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public ModelAndView getCreate(Model model, HttpServletRequest request) {
		;
		ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel = new ChuongTrinhDaoTaoModel();

		// =========
		chuongTrinhDaoTaoModel.setPageMode(PageMode.CREATE);

		ModelAndView mav = new ModelAndView("portal/chuongtrinhdaotao/chuongtrinhdaotao_create");
		mav.addObject("chuongTrinhDaoTaoModel", chuongTrinhDaoTaoModel);

		return mav;
	}

	@RequestMapping(value = "/create", method = POST)
	public ModelAndView postCreate(@ModelAttribute(value = "chuongTrinhDaoTaoModel") @Valid ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel,
			BindingResult bindingResult, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Locale locale) {
		;
		ModelAndView mav = new ModelAndView("redirect:/portal/chuongtrinhdaotao/list");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			// create user
			ChuongTrinhDaoTao chuongTrinhDaoTao = chuongTrinhDaoTaoModel.getEntity();
			chuongTrinhDaoTao.setChuongTrinhDaoTaoCode(chuongTrinhDaoTao.getChuongTrinhDaoTaoCode().toUpperCase());
			chuongTrinhDaoTao.setChuongTrinhDaoTaoName(chuongTrinhDaoTao.getChuongTrinhDaoTaoName().toUpperCase());
			chuongTrinhDaoTaoService.save(chuongTrinhDaoTao);

			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.chuongTrinhDaoTaoCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("portal/chuongtrinhdaotao/chuongtrinhdaotao_create");
		}
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_EDIT')")
	@RequestMapping(value = "/edit", method = GET)
	public ModelAndView getEdit(@RequestParam(value = "chuongTrinhDaoTaoId") Long id,
			@ModelAttribute(value = "chuongTrinhDaoTaoModel") ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel, Model model) {

		;

		ChuongTrinhDaoTao chuongTrinhDaoTao = chuongTrinhDaoTaoService.findOne(id);
		chuongTrinhDaoTaoModel.setEntity(chuongTrinhDaoTao);

		chuongTrinhDaoTaoModel.setPageMode(PageMode.EDIT);
		ModelAndView mav = new ModelAndView("portal/chuongtrinhdaotao/chuongtrinhdaotao_edit");
		mav.addObject("chuongTrinhDaoTaoModel", chuongTrinhDaoTaoModel);
		return mav;

	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public ModelAndView postEdit(@ModelAttribute(value = "chuongTrinhDaoTaoModel") @Valid ChuongTrinhDaoTaoModel chuongTrinhDaoTaoModel,
			Model model, Locale locale, HttpServletRequest request, RedirectAttributes redirectAttributes,
			BindingResult bindingResult) {

		;
		ModelAndView mav = new ModelAndView("redirect:/portal/chuongtrinhdaotao/edit");

		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			ChuongTrinhDaoTao chuongtrinhdaotao = chuongTrinhDaoTaoModel.getEntity();
			chuongtrinhdaotao.setChuongTrinhDaoTaoCode(chuongtrinhdaotao.getChuongTrinhDaoTaoCode().toUpperCase());
			chuongtrinhdaotao.setChuongTrinhDaoTaoName(chuongtrinhdaotao.getChuongTrinhDaoTaoName().toUpperCase());
			chuongTrinhDaoTaoService.save(chuongtrinhdaotao);

			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.chuongTrinhDaoTaoCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			mav.addObject(CoreConstant.MSG_LST, messageLst);
			mav.setViewName("redirect:/portal/chuongtrinhdaotao/edit");
		}

		redirectAttributes.addAttribute("chuongTrinhDaoTaoId", chuongTrinhDaoTaoModel.getEntity().getChuongTrinhDaoTaoId());
		return mav;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CHUONGTRINHDAOTAO_DELETE')")
	@RequestMapping(value = "/delete", method = GET)
	public String getDelete(@RequestParam(value = "chuongTrinhDaoTaoId") Long chuongTrinhDaoTaoId, Model model, HttpServletRequest request,
			Locale locale, RedirectAttributes redirectAttributes) {
		try {
			chuongTrinhDaoTaoService.delete(chuongTrinhDaoTaoId);

			MessageList messageLst = new MessageList(Message.SUCCESS);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
			messageLst.add(msgInfo);
			redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/portal/chuongtrinhdaotao/list";
	}
}
