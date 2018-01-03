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
import vn.shp.app.bean.RoleBean;
import vn.shp.app.config.Constants;
import vn.shp.app.utils.Utils;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.entity.PortalRole;
import vn.shp.portal.model.PortalRoleModel;
import vn.shp.portal.service.PortalRoleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
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
	private PortalRoleService portalRoleService;

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		model.addAttribute("bean", bean);
		return "portal/role/role_create";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/create", method = POST)
	public String postCreate(Model model, RoleBean bean, HttpServletRequest request) {
		PortalRole role = bean.getEntity();
		if(role != null && Utils.isAllNotNullOrEmpty(role.getRoleName(), role.getRoleCode())){
			try {
				role.setRoleCode(role.getRoleCode().toUpperCase());
				role.setRoleName(role.getRoleName().toUpperCase());
				role.setStatus(Constants.RECORD_STATUS_OPEN);
				role.setTimeCreated(new Date());
				//role.setUserCreated();
				portalRoleService.save(role);
				MessageList messageList = new MessageList(Message.SUCCESS,"Thêm mới quyền thành công.");
				model.addAttribute(CoreConstant.MSG_LST,messageList);
				bean.setEntity(new PortalRole());
			}catch (Exception e){
				e.printStackTrace();
				MessageList messageList = new MessageList(Message.ERROR,"Quyền đã tồn tại trong hệ thống");
				model.addAttribute(CoreConstant.MSG_LST,messageList);
			}

		}
		model.addAttribute("bean", bean);
		return "portal/role/role_create";
	}

	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		model.addAttribute("bean", bean);

		List<PortalRole> lstRole = portalRoleService.findAll();
		bean.setLstData(lstRole);

		return "portal/role/role_list";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_CREATE')")
	@RequestMapping(value = "/list", method = POST)
	public String postList(Model model, RoleBean bean, HttpServletRequest request) {
		model.addAttribute("bean", bean);
		return "portal/role/role_list";
	}

}
