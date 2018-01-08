package vn.shp.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.app.constant.PageMode;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.entity.PortalConfig;
import vn.shp.app.bean.PortalConfigBean;
import vn.shp.app.bean.PortalConfigBean;
import vn.shp.app.service.PortalConfigService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/config")
public class PortalConfigController extends AbstractController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PortalConfigService portalConfigService;

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_CONFIG_EDIT')")
	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
						  PortalConfigBean bean, Model model)
	{
		PortalConfig portalConfig = portalConfigService.findOne(id);
		bean.setEntity(portalConfig);
		bean.setPageMode(PageMode.EDIT);
		return "portal/config/config_edit";
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(PortalConfigBean bean, Model model, Locale locale, BindingResult bindingResult) {
		PortalConfig config = bean.getEntity();
		MessageList messageLst = new MessageList(Message.SUCCESS);
		if (config != null) {
			try {
				portalConfigService.save(config);
				String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
				messageLst.add(msgInfo);
				model.addAttribute(CoreConstant.MSG_LST, messageLst);
			} catch (Exception e) {
				e.printStackTrace();
				messageLst.setStatus(Message.ERROR);
				String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
				messageLst.add(msgInfo);
				model.addAttribute(CoreConstant.MSG_LST, messageLst);
			}
		}
		return "portal/config/config_edit";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_CONFIG_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		PortalConfigBean bean = new PortalConfigBean();
		List<PortalConfig> lstConfig = portalConfigService.findAll();
		bean.setData(lstConfig);
		model.addAttribute("bean", bean);
		return "portal/config/config_list";
	}

	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "bean") @Valid PortalConfigBean bean, BindingResult bindingResult, Model model,
						   HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<PortalConfig> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.add(portalConfigService.find(bean.getEntity().getSystem(), bean.getEntity().getConfigName()));
		} else {
			lstData.addAll(portalConfigService.findAll());
		}

		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		return "portal/config/config_list";
	}
}
