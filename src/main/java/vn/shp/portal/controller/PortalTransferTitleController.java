package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalDepartment;
import vn.shp.portal.entity.PortalUser;
import vn.shp.portal.model.PortalTransferTitleModel;
import vn.shp.portal.service.PortalDepartmentService;
import vn.shp.portal.service.PortalUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("portal/transfer-title")
public class PortalTransferTitleController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PortalUserService portalUserService;

	@Autowired
	PortalDepartmentService portalDepartmentService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(method = GET)
	public String get(Model model, HttpServletRequest request) {
		

		PortalTransferTitleModel portalTransferTitleModel = new PortalTransferTitleModel();
		model.addAttribute("portalWorkflowModel", portalTransferTitleModel);
		return "portal/transfer/transfer";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public String postDelete(@RequestParam(value = "userLeft") String userLeft,
			@RequestParam(value = "userRight") String userRight, @RequestParam(value = "title") String title,
			Locale locale, RedirectAttributes redirectAttributes) {
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			PortalUser portalUserLeft = portalUserService.findByUsername(userLeft);
			PortalUser portalUserRight = portalUserService.findByUsername(userRight);
			if (portalUserLeft == null || portalUserRight == null) {
				throw new Exception();
			}

			if (StringUtils.equals(title, messageSource.getMessage("department.manager", null, null))) {
				PortalDepartment department = portalDepartmentService.findByManager(userLeft);
				if (department != null) {
					department.setManager(userRight);
					portalDepartmentService.save(department);
				}

			} else if (StringUtils.equals(title, messageSource.getMessage("department.vicedirector", null, null))) {
				PortalDepartment department = portalDepartmentService.findByViceDirector(userLeft);
				if (department != null) {
					department.setViceDirector(userRight);
					portalDepartmentService.save(department);
				}
			}

			messageLst.add(messageSource.getMessage("message.transfer.success", null, locale));
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			messageLst.add(messageSource.getMessage("message.transfer.error", null, locale));
			if (StringUtils.isNotEmpty(e.getMessage())) {
				messageLst.add(e.getMessage());
			}
		}
		redirectAttributes.addFlashAttribute(CoreConstant.MSG_LST, messageLst);
		return "redirect:/portal/transfer-title";
	}

}
