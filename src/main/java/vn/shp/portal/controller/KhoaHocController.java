package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import vn.shp.app.bean.HocVienBean;
import vn.shp.app.config.Constants;
import vn.shp.app.entity.*;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.app.utils.Utils;
import vn.shp.app.xlsEntity.KhoaHocXls;
import vn.shp.portal.common.PageMode;
import vn.shp.portal.constant.CoreConstant;
import vn.shp.portal.core.Message;
import vn.shp.portal.core.MessageList;
import vn.shp.portal.model.KhoaHocModel;
import vn.shp.portal.model.KhoaHocModel;
import vn.shp.portal.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/khoahoc")
public class KhoaHocController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	KhoaHocService khoaHocService;

	@Autowired
	BacDaoTaoService bacDaoTaoService;

	@Autowired
	MonHocService monHocService;

	@Autowired
	KhoaHocMonHocService khoaHocMonHocService;

	@Autowired
	HocVienService hocVienService;

	@Autowired
	HocVienDkService hocVienDkService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		KhoaHocModel bean = new KhoaHocModel();
		List<KhoaHoc> lstData = khoaHocService.findAll();
		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "";
		for (KhoaHoc each : lstData) {
			listExport += each.getKhoaHocId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/khoahoc/khoahoc_list";
	}

	@RequestMapping(value = "/list", method = POST)
	public String postList(@ModelAttribute(value = "bean") @Valid KhoaHocModel bean, BindingResult bindingResult, Model model,
						   HttpServletRequest request,
						   RedirectAttributes redirectAttributes)
	{
		List<KhoaHoc> lstData = new ArrayList<>();
		if (bean != null) {
			lstData.addAll(khoaHocService.searchByFilters(bean.getEntity().getKhoaHocName(), bean.getEntity().getKhoaHocCode()));
		} else {
			lstData.addAll(khoaHocService.findAll());
		}

		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "";
		for (KhoaHoc each : lstData) {
			listExport += each.getKhoaHocId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/khoahoc/khoahoc_list";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		KhoaHocModel bean = new KhoaHocModel();
		bean.setPageMode(PageMode.CREATE);
		model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
		model.addAttribute("khoaHocModel", bean);
		return "portal/khoahoc/khoahoc_create";
	}

	@RequestMapping(value = "/create", method = POST)
	public String postCreate(@ModelAttribute(value = "khoaHocModel") @Valid KhoaHocModel bean,
							 BindingResult bindingResult, Model model, HttpServletRequest request,
							 RedirectAttributes redirectAttributes, Locale locale)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			KhoaHoc khoaHoc = bean.getEntity();
			khoaHoc.setKhoaHocCode(khoaHoc.getKhoaHocCode().toUpperCase());
			khoaHoc.setKhoaHocName(khoaHoc.getKhoaHocName().toUpperCase());
			if (khoaHoc.getTimeFrom().after(khoaHoc.getTimeTo())) {
				messageLst.setStatus(Message.ERROR);
				msgInfo = "Khóa học bắt đầu phải nhỏ hơn kết thúc";
			} else {
				khoaHocService.save(khoaHoc);
				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
			}
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.khoaHocCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
		return "portal/khoahoc/khoahoc_create";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_EDIT')")
	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id, KhoaHocModel bean, Model model) {
		KhoaHoc khoaHoc = khoaHocService.findOne(id);
		bean.setEntity(khoaHoc);
		model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
		model.addAttribute("lstMonHoc", monHocService.findAll());
		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(khoaHoc.getKhoaHocId());
		bean.setListKhmh(khoaHocMonHocList);
		KhoaHocMonHoc khmh = new KhoaHocMonHoc();
		khmh.setKhoaHoc(khoaHoc);
		bean.setKhmh(khmh);
		model.addAttribute("khoaHocModel", bean);
		bean.setPageMode(PageMode.EDIT);
		return "portal/khoahoc/khoahoc_edit";
	}

	@RequestMapping(value = "/ajax_new_khmh", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView addKhoaHocMonHoc(Model model, KhoaHocModel bean, Locale locale) {
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			khoaHocMonHocService.save(bean.getKhmh());
			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
		}
		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(bean.getKhmh().getKhoaHoc().getKhoaHocId());
		bean.setListKhmh(khoaHocMonHocList);
		KhoaHocMonHoc khmh = new KhoaHocMonHoc();
		khmh.setKhoaHoc(khoaHocService.findOne(bean.getKhmh().getKhoaHoc().getKhoaHocId()));
		bean.setKhmh(khmh);
		model.addAttribute("lstMonHoc", monHocService.findAll());
		model.addAttribute("khoaHocModel", bean);
		messageLst.add(msgInfo);
		model.addAttribute(CoreConstant.MSG_LST, messageLst);
		return new ModelAndView("/portal/khoahoc/khoahoc_monhoc :: content");
	}

	@RequestMapping(value = "/ajax_delete_khmh", method = RequestMethod.GET)
	public
	@ResponseBody
	ModelAndView removeKhoaHocMonHoc(Model model, @RequestParam(value = "id") Long id, Locale locale) {
		KhoaHocModel bean = new KhoaHocModel();
		KhoaHocMonHoc entity = khoaHocMonHocService.findOne(id);
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			khoaHocMonHocService.delete(id);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_DELETE, null, locale);
		} catch (Exception e) {
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
		}

		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(entity.getKhoaHoc().getKhoaHocId());
		bean.setListKhmh(khoaHocMonHocList);

		KhoaHocMonHoc khmh = new KhoaHocMonHoc();
		khmh.setKhoaHoc(entity.getKhoaHoc());
		bean.setKhmh(khmh);

		model.addAttribute("lstMonHoc", monHocService.findAll());
		model.addAttribute("khoaHocModel", bean);
		messageLst.add(msgInfo);
		model.addAttribute(CoreConstant.MSG_LST, messageLst);
		return new ModelAndView("/portal/khoahoc/khoahoc_monhoc :: content");
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(KhoaHocModel bean, Model model, Locale locale, BindingResult bindingResult) {
		KhoaHoc entity = bean.getEntity();
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		try {
			entity.setKhoaHocCode(entity.getKhoaHocCode().toUpperCase());
			entity.setKhoaHocName(entity.getKhoaHocName().toUpperCase());
			if (entity.getTimeFrom().after(entity.getTimeTo())) {
				messageLst.setStatus(Message.ERROR);
				msgInfo = "Khóa học bắt đầu phải nhỏ hơn kết thúc";
			} else {
				khoaHocService.save(entity);
				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_UPDATE, null, locale);
			}
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			e.printStackTrace();
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_UPDATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lstBacDaoTao", bacDaoTaoService.findAll());
		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(entity.getKhoaHocId());
		bean.setListKhmh(khoaHocMonHocList);
		KhoaHocMonHoc khmh = new KhoaHocMonHoc();
		khmh.setKhoaHoc(entity);
		bean.setKhmh(khmh);
		model.addAttribute("khoaHocModel", bean);
		model.addAttribute("lstMonHoc", monHocService.findAll());
		return "portal/khoahoc/khoahoc_edit";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MONHOC_DELETE')")
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			khoaHocService.delete(id);
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
		return "redirect:/portal/khoahoc/list";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
	@RequestMapping(value = "/dangky/{id}", method = GET)
	public String getLstDangKy(Model model, KhoaHocModel bean, @PathVariable("id") Long id) {
		HocVien hocVien = hocVienService.findOne(id);
		bean.setLstKhoaHocDangKy(khoaHocService.findKhoaHocDangKy());
		bean.setHocVien(hocVien);
		List<HocVienDk> lstHvdk = hocVienDkService.findByMaHocVien(hocVien.getMaHocVien());
		List<KhoaHoc> lstKhoaHoc = new ArrayList<>();
		for (HocVienDk hocVienDk : lstHvdk) {
			lstKhoaHoc.add(hocVienDk.getKhoaHoc());
		}
		bean.setLstKhoaHocSv(lstKhoaHoc);
		model.addAttribute("bean", bean);
		return "/portal/khoahoc/khoahoc_dangkykhoahoc";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
	@RequestMapping(value = "/dangky", method = GET)
	public String getDangKy(Model model, KhoaHocModel bean) {
		bean.setLstKhoaHocDangKy(khoaHocService.findKhoaHocDangKy());
		bean.setHocVien(new HocVien());
		model.addAttribute("bean", bean);
		return "/portal/khoahoc/khoahoc_dangkykhoahoc";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HOCVIEN_DELETE')")
	@RequestMapping(value = "/dangky", method = POST)
	public String postDangKy(Model model, KhoaHocModel bean) {
		bean.setLstKhoaHocDangKy(khoaHocService.findKhoaHocDangKy());
		HocVien hocVien = hocVienService.findByMaHocVien(bean.getHocVien().getMaHocVien());
		String lstMaKhoaHocDangKy = bean.getLstMaKhoaHocDangKy();
		try {
			if (Utils.isNotNullOrEmpty(lstMaKhoaHocDangKy)) {
				List<String> lstMaKhoaHoc = new ArrayList<>(Arrays.asList(lstMaKhoaHocDangKy.split(",")));
				if (!CollectionUtils.isEmpty(lstMaKhoaHoc)) {
					Set<String> hs = new HashSet<>();
					hs.addAll(lstMaKhoaHoc);
					lstMaKhoaHoc.clear();
					lstMaKhoaHoc.addAll(hs);
					for (String maKhoaHoc : lstMaKhoaHoc) {
						KhoaHoc khoaHoc = khoaHocService.findByKhoaHocCode(maKhoaHoc);
						HocVienDk hocVienDk = new HocVienDk();
						hocVienDk.setHocVien(hocVien);
						hocVienDk.setKhoaHoc(khoaHoc);
						hocVienDk.setNgayTao(new Date());
						hocVienDk.setTrangThai("Y");
						hocVienDkService.save(hocVienDk);
					}
				} else {
					MessageList messageList = new MessageList(Message.ERROR, "Không có khoá học nào được chọn");
					model.addAttribute(CoreConstant.MSG_LST, messageList);
				}
			}
		} catch (DataIntegrityViolationException e) {
			MessageList messageList = new MessageList(Message.ERROR, "Trùng khoá học.");
			model.addAttribute(CoreConstant.MSG_LST, messageList);
		} catch (Exception e) {

			e.getMessage();
		}

		bean.setHocVien(hocVien);
		List<HocVienDk> lstHvdk = hocVienDkService.findByMaHocVien(hocVien.getMaHocVien());
		List<KhoaHoc> lstKhoaHoc = new ArrayList<>();
		for (HocVienDk hocVienDk : lstHvdk) {
			lstKhoaHoc.add(hocVienDk.getKhoaHoc());
		}
		bean.setLstKhoaHocSv(lstKhoaHoc);

		model.addAttribute("bean", bean);
		return "/portal/khoahoc/khoahoc_dangkykhoahoc";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<KhoaHocXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						KhoaHoc each = khoaHocService.findOne(Long.parseLong(arr[i]));
						KhoaHocXls item = new KhoaHocXls();
						item.setKhoaHocCode(each.getKhoaHocCode());
						item.setKhoaHocName(each.getKhoaHocName());
						item.setCtdtName(each.getBacDaoTao().getChuyenNganh().getChuongTrinhDaoTao().getChuongTrinhDaoTaoName());
						item.setChuyenNganhName(each.getBacDaoTao().getChuyenNganh().getChuyenNganhName());
						item.setBacDaoTaoName(each.getBacDaoTao().getBacDaoTaoName());
						item.setBatDau(dateFormat.format(each.getTimeFrom()));
						item.setKetThuc(dateFormat.format(each.getTimeTo()));
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<KhoaHocXls> excelCreator = new ExcelCreator<KhoaHocXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachKhoaHoc.xls" + "\"");
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
