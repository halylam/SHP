package vn.shp.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hcm.mcr35.excel.ExcelCreator;
import vn.hcm.mcr35.excel.entity.ECell;
import vn.shp.app.entity.CaHoc;
import vn.shp.app.entity.GiangVien;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.app.entity.MonHoc;
import vn.shp.app.entity.ThoiKhoaBieu;
import vn.shp.app.xlsEntity.ThoiKhoaBieuXls;
import vn.shp.app.constant.PageMode;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.bean.ThoiKhoaBieuBean;
import vn.shp.app.service.CaHocService;
import vn.shp.app.service.GiangVienService;
import vn.shp.app.service.KhoaHocMonHocService;
import vn.shp.app.service.KhoaHocService;
import vn.shp.app.service.LopHocService;
import vn.shp.app.service.MonHocService;
import vn.shp.app.service.ThoiKhoaBieuService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/thoikhoabieu")
public class ThoiKhoaBieuController  extends AbstractController{

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ThoiKhoaBieuService thoiKhoaBieuService;

	@Autowired
	KhoaHocService khoaHocService;

	@Autowired
	LopHocService lopHocService;

	@Autowired
	CaHocService caHocService;

	@Autowired
	KhoaHocMonHocService khoaHocMonHocService;

	@Autowired
	GiangVienService giangVienService;

	@Autowired
	MonHocService monHocService;

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_THOIKHOABIEU_LIST')")
	@RequestMapping(value = "/list", method = GET)
	public String getList(Model model, HttpServletRequest request) {
		ThoiKhoaBieuBean bean = new ThoiKhoaBieuBean();
		List<ThoiKhoaBieu> lstData = thoiKhoaBieuService.findAll();
		bean.setData(lstData);
		if (CollectionUtils.isEmpty(lstData)) {
			MessageList messageLst = new MessageList(Message.INFO);
			messageLst.add("Không tìm thấy thông tin");
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("bean", bean);
		String listExport = "-";
		for (ThoiKhoaBieu each : lstData) {
			listExport += each.getId() + "-";
		}
		model.addAttribute("listExport", listExport);
		return "portal/thoikhoabieu/thoikhoabieu_list";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_THOIKHOABIEU_CREATE')")
	@RequestMapping(value = "/create", method = GET)
	public String getCreate(Model model, HttpServletRequest request) {
		ThoiKhoaBieuBean bean = new ThoiKhoaBieuBean();
		bean.setPageMode(PageMode.CREATE);
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lstCaHoc", caHocService.findAll());
		model.addAttribute("lstLopHoc", lopHocService.findAll());
		model.addAttribute("lstMonHoc", new ArrayList<MonHoc>());
		model.addAttribute("lstGiangVien", new ArrayList<GiangVien>());
		model.addAttribute("thoiKhoaBieuModel", bean);
		return "portal/thoikhoabieu/thoikhoabieu_create";
	}

	@RequestMapping(value = "/create", method = POST)
	public String postCreate(@ModelAttribute(value = "thoiKhoaBieuModel") @Valid ThoiKhoaBieuBean bean,
							 BindingResult bindingResult, Model model, HttpServletRequest request,
							 RedirectAttributes redirectAttributes, Locale locale)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		String msgInfo = "";
		List<MonHoc> listMonHoc = new ArrayList<MonHoc>();
		List<GiangVien> listGiangVien = new ArrayList<GiangVien>();
		try {
			if (bindingResult.hasErrors()) {
				throw new Exception();
			}
			ThoiKhoaBieu thoiKhoaBieu = bean.getEntity();

			if (thoiKhoaBieu.getTuNgay().after(thoiKhoaBieu.getDenNgay())) {
				messageLst.setStatus(Message.ERROR);
				msgInfo = "Khóa học bắt đầu phải nhỏ hơn kết thúc";
			} else {
				thoiKhoaBieuService.save(thoiKhoaBieu);
				msgInfo = messageSource.getMessage(CoreConstant.MSG_SUCCESS_CREATE, null, locale);

				List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(thoiKhoaBieu.getKhoaHoc().getKhoaHocId());
				for (KhoaHocMonHoc each : khoaHocMonHocList) {
					listMonHoc.add(each.getMonHoc());
				}

				CaHoc caHoc = caHocService.findOne(thoiKhoaBieu.getCaHoc().getCaHocId());
				List<GiangVien> acc = giangVienService.findByThuAndCaHoc(thoiKhoaBieu.getThu(), caHoc.getCaHocCode());
				for (GiangVien each : acc) {
					GiangVien gv = new GiangVien();
					gv.setId(each.getId());
					gv.setMaGiangVien(each.getMaGiangVien());
					gv.setHoTen(each.getHoTen());
					listGiangVien.add(gv);
				}
			}
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				bindingResult.rejectValue("entity.thoiKhoaBieuCode", "Exists.error.code", null, "");
			}
			messageLst.setStatus(Message.ERROR);
			msgInfo = messageSource.getMessage(CoreConstant.MSG_ERROR_CREATE, null, locale);
			messageLst.add(msgInfo);
			model.addAttribute(CoreConstant.MSG_LST, messageLst);
		}
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lstCaHoc", caHocService.findAll());
		model.addAttribute("lstLopHoc", lopHocService.findAll());
		model.addAttribute("lstMonHoc", listMonHoc);
		model.addAttribute("lstGiangVien", listGiangVien);
		return "portal/thoikhoabieu/thoikhoabieu_create";
	}

	@RequestMapping(value = "/ajax_getMonHoc", method = RequestMethod.GET)
	public
	@ResponseBody
	List<MonHoc> getMonHocFromKhoaHocId(@RequestParam(value = "khoaHocId") Long khoaHocId) {
		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(khoaHocId);
		List<MonHoc> result = new ArrayList<MonHoc>();
		for (KhoaHocMonHoc each : khoaHocMonHocList) {
			result.add(each.getMonHoc());
		}
		return result;
	}

	@RequestMapping(value = "/ajax_getGiangVien", method = RequestMethod.GET)
	public
	@ResponseBody
	List<GiangVien> getGiangVienFromThuAndCaHocCode(@RequestParam(value = "caHocId") Long caHocId,
													@RequestParam(value = "thu") String thu)
	{
		List<GiangVien> result = new ArrayList<GiangVien>();
		CaHoc caHoc = caHocService.findOne(caHocId);
		List<GiangVien> acc = giangVienService.findByThuAndCaHoc(thu, caHoc.getCaHocCode());
		for (GiangVien each : acc) {
			GiangVien gv = new GiangVien();
			gv.setId(each.getId());
			gv.setMaGiangVien(each.getMaGiangVien());
			gv.setHoTen(each.getHoTen());
			result.add(gv);
		}
		return result;
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_THOIKHOABIEU_EDIT')")
	@RequestMapping(value = "/edit/{id}", method = GET)
	public String getEdit(@PathVariable(value = "") Long id,
                          ThoiKhoaBieuBean bean, Model model)
	{
		ThoiKhoaBieu thoiKhoaBieu = thoiKhoaBieuService.findOne(id);
		bean.setEntity(thoiKhoaBieu);
		List<MonHoc> listMonHoc = new ArrayList<MonHoc>();
		List<GiangVien> listGiangVien = new ArrayList<GiangVien>();
		List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(thoiKhoaBieu.getKhoaHoc().getKhoaHocId());
		for (KhoaHocMonHoc each : khoaHocMonHocList) {
			listMonHoc.add(each.getMonHoc());
		}

		CaHoc caHoc = caHocService.findOne(thoiKhoaBieu.getCaHoc().getCaHocId());
		List<GiangVien> acc = giangVienService.findByThuAndCaHoc(thoiKhoaBieu.getThu(), caHoc.getCaHocCode());
		for (GiangVien each : acc) {
			GiangVien gv = new GiangVien();
			gv.setId(each.getId());
			gv.setMaGiangVien(each.getMaGiangVien());
			gv.setHoTen(each.getHoTen());
			listGiangVien.add(gv);
		}
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lstCaHoc", caHocService.findAll());
		model.addAttribute("lstLopHoc", lopHocService.findAll());
		model.addAttribute("lstMonHoc", listMonHoc);
		model.addAttribute("lstGiangVien", listGiangVien);
		bean.setPageMode(PageMode.EDIT);
		return "portal/thoikhoabieu/thoikhoabieu_edit";
	}

	/**
	 * EDIT - POST
	 */
	@RequestMapping(value = "/edit", method = POST)
	public String postEdit(ThoiKhoaBieuBean bean, Model model, Locale locale, BindingResult bindingResult) {
		ThoiKhoaBieu entity = bean.getEntity();
		MessageList messageLst = new MessageList(Message.SUCCESS);
		List<MonHoc> listMonHoc = new ArrayList<MonHoc>();
		List<GiangVien> listGiangVien = new ArrayList<GiangVien>();
		String msgInfo = "";
		try {
			if (entity.getTuNgay().after(entity.getDenNgay())) {
				messageLst.setStatus(Message.ERROR);
				msgInfo = "Khóa học bắt đầu phải nhỏ hơn kết thúc";
			} else {
				thoiKhoaBieuService.save(entity);
				List<KhoaHocMonHoc> khoaHocMonHocList = khoaHocMonHocService.findByKhoaHocId(entity.getKhoaHoc().getKhoaHocId());
				for (KhoaHocMonHoc each : khoaHocMonHocList) {
					listMonHoc.add(each.getMonHoc());
				}

				CaHoc caHoc = caHocService.findOne(entity.getCaHoc().getCaHocId());
				List<GiangVien> acc = giangVienService.findByThuAndCaHoc(entity.getThu(), caHoc.getCaHocCode());
				for (GiangVien each : acc) {
					GiangVien gv = new GiangVien();
					gv.setId(each.getId());
					gv.setMaGiangVien(each.getMaGiangVien());
					gv.setHoTen(each.getHoTen());
					listGiangVien.add(gv);
				}
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
		model.addAttribute("lstKhoaHoc", khoaHocService.findAll());
		model.addAttribute("lstCaHoc", caHocService.findAll());
		model.addAttribute("lstLopHoc", lopHocService.findAll());
		model.addAttribute("lstMonHoc", listMonHoc);
		model.addAttribute("lstGiangVien", listGiangVien);
		return "portal/thoikhoabieu/thoikhoabieu_edit";
	}

	@PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_THOIKHOABIEU_DELETE')")
	@RequestMapping(value = "/delete/{id}", method = GET)
	public String getDelete(@PathVariable(value = "") Long id, Model model, HttpServletRequest request,
							Locale locale, RedirectAttributes redirectAttributes)
	{
		MessageList messageLst = new MessageList(Message.SUCCESS);
		try {
			thoiKhoaBieuService.delete(id);
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
		return "redirect:/portal/thoikhoabieu/list";
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "/exportXls/{list}", method = GET)
	public void postReportGeneral(@PathVariable("list") String list, Model model, Locale locale, HttpServletResponse response) {
		List<ThoiKhoaBieuXls> lstResGen = new ArrayList<>();
		try {
			if (StringUtils.isNotEmpty(list)) {
				String[] arr = list.split("-");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtils.isNotEmpty(arr[i])) {
						ThoiKhoaBieu each = thoiKhoaBieuService.findOne(Long.parseLong(arr[i]));
						ThoiKhoaBieuXls item = new ThoiKhoaBieuXls();
						item.setMonHocCode(each.getMonHoc().getMonHocCode());
						item.setMonHocName(each.getMonHoc().getMonHocName());
						item.setTongGioHoc(each.getMonHoc().getTongGioDay()+"");
						item.setCa(each.getCaHoc().getCaHocName());
						item.setThu(each.getThu());
						item.setGiangVien(each.getGiangVien().getMaGiangVien());
						item.setLap(each.getLapLai());
						item.setLop(each.getLopHoc().getLopHocName());
						item.setSeq(i + 1);
						lstResGen.add(item);
					}
				}
			}
			InputStream file = getClass().getResourceAsStream("/print/TEMPLATE.xls");
			List<ECell> lstECells = new ArrayList<ECell>();
			ExcelCreator<ThoiKhoaBieuXls> excelCreator = new ExcelCreator<ThoiKhoaBieuXls>();
			byte[] bytes = excelCreator.exportExcel(lstResGen, file, true, false, false, 0, lstECells);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + "DanhSachThoiKhoaBieu.xls" + "\"");
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
