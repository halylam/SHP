package vn.shp.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.app.xlsEntity.LopHocXls;
import vn.shp.app.constant.PageMode;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.bean.LopHocBean;
import vn.shp.app.service.HocVienDkService;
import vn.shp.app.service.HocVienService;
import vn.shp.app.service.KhoaHocService;
import vn.shp.app.service.LoaiLopHocService;
import vn.shp.app.service.LopHocHocVienService;
import vn.shp.app.service.LopHocService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/lophoc")
public class LopHocController  extends AbstractController{

	@Autowired
	private MessageSource messageSource;

	@Autowired
	LopHocService lopHocService;

	@Autowired
	LoaiLopHocService loaiLopHocService;

	@Autowired
	KhoaHocService khoaHocService;

	@Autowired
	HocVienService hocVienService;

	@Autowired
	LopHocHocVienService lopHocHocVienService;

	@Autowired
	HocVienDkService hocVienDkService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		LopHocBean bean = new LopHocBean();
		bean.setEntity(new LopHoc());
		List<LopHoc> lstData = lopHocService.findAll();
		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lopHocModel", bean);
		String listExport = "-";
		for (LopHoc each : lstData) {
			listExport += each.getLopHocId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/lophoc/lophoc_list";
	}
	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_LIST')")
	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "lopHocModel") @Valid LopHocBean bean, BindingResult bindingResult, Model model, HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<LopHoc> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.addAll(lopHocService.searchByFilters(bean.getEntity().getLopHocName(), bean.getEntity().getLopHocCode()));
		} else {
			lstData.addAll(lopHocService.findAll());
		}

		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lopHocModel", bean);
		String listExport = "-";
		for (LopHoc each : lstData) {
			listExport += each.getLopHocId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/lophoc/lophoc_list";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		LopHocBean bean = new LopHocBean();
		bean.setPageMode(PageMode.CREATE);
		model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lopHocModel", bean);
		return "portal/lophoc/lophoc_create";
	}
	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_CREATE')")
	@RequestMapping(value = "/create", method = POST)
	public String postCreate(@ModelAttribute(value = "lopHocModel") @Valid LopHocBean bean,
							 BindingResult bindingResult, Model model, HttpServletRequest request,
							 RedirectAttributes redirectAttributes, Locale locale)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			LopHoc lopHoc = bean.getEntity();
			lopHoc.setLopHocCode(lopHoc.getLopHocCode().toUpperCase());
			lopHoc.setLopHocName(lopHoc.getLopHocName().toUpperCase());
			lopHocService.save(lopHoc);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.lopHocCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		return "portal/lophoc/lophoc_create";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_EDIT')")
	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
						  LopHocBean bean, Model model)
	{
		LopHoc lopHoc = lopHocService.findOne(id);
		bean.setEntity(lopHoc);
		model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lstHocVien", hocVienDkService.findByMaKhoaHoc(lopHoc.getKhoaHoc().getKhoaHocId()));

		List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(lopHoc.getLopHocId());
		bean.setListLhhv(lopHocHocVienList);
		LopHocHocVien lhhv = new LopHocHocVien();
		lhhv.setLopHoc(lopHoc);
		bean.setLhhv(lhhv);
		model.addAttribute("lopHocModel", bean);
		bean.setPageMode(PageMode.EDIT);
		return "portal/lophoc/lophoc_edit";
	}

	@RequestMapping(value = "/ajax_new_lhhv", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView addLopHocHocVien(Model model, LopHocBean bean, Locale locale) {
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		LopHoc lopHoc = null;
		try {
			lopHoc = lopHocService.findOne(bean.getLhhv().getLopHoc().getLopHocId());
			if (lopHoc != null && lopHoc.getSoLuongHV() < lopHoc.getSucChua()) {
				lopHocHocVienService.save(bean.getLhhv());
				lopHoc.setSoLuongHV(lopHoc.getSoLuongHV() + 1);
				lopHocService.save(lopHoc);
				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			} else {
				messageLst.setStatus(Message.ERROR);
				msgInfo = "Đã đủ sức chứa lớp học";
			}
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
		}

		List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(bean.getLhhv().getLopHoc().getLopHocId());
		bean.setListLhhv(lopHocHocVienList);
		LopHocHocVien lhhv = new LopHocHocVien();
		lhhv.setLopHoc(lopHoc);
		bean.setLhhv(lhhv);
		model.addAttribute("lstHocVien", hocVienDkService.findByMaKhoaHoc(lopHoc.getKhoaHoc().getKhoaHocId()));
		model.addAttribute("lopHocModel", bean);
		messageLst.add(msgInfo);
		model.addAttribute(CoreConstant.MSG_LST, messageLst);
		return new ModelAndView("/portal/lophoc/lophoc_hocvien :: content");
	}

	@RequestMapping(value = "/ajax_delete_lhhv", method = RequestMethod.GET)
	public
	@ResponseBody
	ModelAndView removeLopHocHocVien(Model model, @RequestParam(value = "id") Long id, Locale locale) {
		LopHocBean bean = new LopHocBean();
		LopHocHocVien entity = lopHocHocVienService.findOne(id);
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			lopHocHocVienService.delete(id);
			entity.getLopHoc().setSoLuongHV(entity.getLopHoc().getSoLuongHV() - 1);
			lopHocService.save(entity.getLopHoc());
			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
		}

		List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(entity.getLopHoc().getLopHocId());
		bean.setListLhhv(lopHocHocVienList);

		LopHocHocVien lhhv = new LopHocHocVien();
		lhhv.setLopHoc(entity.getLopHoc());
		bean.setLhhv(lhhv);

		model.addAttribute("lstHocVien", hocVienDkService.findByMaKhoaHoc(entity.getLopHoc().getKhoaHoc().getKhoaHocId()));
		model.addAttribute("lopHocModel", bean);
		messageLst.add(msgInfo);
		model.addAttribute(CoreConstant.MSG_LST, messageLst);
		return new ModelAndView("/portal/lophoc/lophoc_hocvien :: content");
	}

	/**
	 * EDIT - POST
	 */
	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_EDIT')")
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(LopHocBean bean, Model model, Locale locale, BindingResult bindingResult) {
		LopHoc entity = bean.getEntity();
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			entity.setLopHocCode(entity.getLopHocCode().toUpperCase());
			entity.setLopHocName(entity.getLopHocName().toUpperCase());
			lopHocService.save(entity);
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
		model.addAttribute("lstLoaiLopHoc", loaiLopHocService.findAll());
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());

		List<LopHocHocVien> lopHocHocVienList = lopHocHocVienService.findByLopHocId(entity.getLopHocId());
		bean.setListLhhv(lopHocHocVienList);
		LopHocHocVien lhhv = new LopHocHocVien();
		lhhv.setLopHoc(entity);
		bean.setLhhv(lhhv);
		model.addAttribute("lstHocVien", hocVienDkService.findByMaKhoaHoc(entity.getKhoaHoc().getKhoaHocId()));
		model.addAttribute("khoaHocModel", bean);
		return "portal/lophoc/lophoc_edit";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_DELETE')")
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			lopHocService.delete(id);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			e.printStackTrace();
			messageLst.setStatus(Message.ERROR);
			String msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		return "redirect:/portal/lophoc/list";
	}
	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_LOPHOC_EXPORT')")
	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<LopHocXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						LopHoc each = lopHocService.findOne(Long.parseLong(arr[i]));
						LopHocXls item = new LopHocXls();
						item.setLopHocCode(each.getLopHocCode());
						item.setLopHocName(each.getLopHocName());
						item.setLoaiLopHoc(each.getLoaiLopHoc().getLoaiLopHocName());
						item.setSoHv(each.getSoLuongHV()+"");
						item.setSucChua(each.getSucChua()+"");
						item.setKhoaHocName(each.getKhoaHoc().getKhoaHocName());
						item.setBatDau(dateFormat.format(each.getKhoaHoc().getTimeFrom()));
						item.setKetThuc(dateFormat.format(each.getKhoaHoc().getTimeTo()));
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<LopHocXls> excelCreator = new ExcelCreator<LopHocXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachLopHoc.xls" + "\"");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				response.getOutputStream().write(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
