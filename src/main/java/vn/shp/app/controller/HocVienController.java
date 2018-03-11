package vn.shp.app.controller;

import ecm.service.EcmPropertyMapper;
import ecm.service.EcmService;
import org.apache.chemistry.opencmis.client.api.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.shp.app.bean.HocVienBean;
import vn.shp.app.config.Constants;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.*;
import vn.shp.app.service.*;
import vn.shp.app.utils.Utils;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.entity.AlfFile;
import vn.shp.app.entity.JsonReturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/hocvien")
public class HocVienController extends AbstractController {

    @Value("${dm.toDirectory.hososv}")
    private String toDirectory;

    @Autowired
    HocVienService hocVienService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private EcmService ecmService;

    @Autowired
    AlfFileService alfFileService;

    @Autowired
    KinhNghiemLamViecService kinhNghiemLamViecService;

    @Autowired
    private EcmPropertyMapper propertyMapper;

    @Autowired
    HocVienDkService hocVienDkService;

    @Autowired
    TonGiaoService tonGiaoService;

    @Autowired
    DanTocService danTocService;


    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_LIST')")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        HocVienBean bean = new HocVienBean();
        List<HocVien> lstData = hocVienService.findAll();
        bean.setLstData(lstData);
        model.addAttribute("bean", bean);
        return "portal/hocvien/hocvien_list";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_CREATE')")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model, HttpServletRequest request) {
        HocVienBean bean = new HocVienBean();
        HocVien entity = new HocVien();
        entity.setNdhp(Constants.NDHP_HOC_VIEN);
        entity.setHp1(Constants.HP_TRON_KHOA);
        entity.setHp2(Constants.HP_TRON_KHOA);
        bean.setEntity(entity);
        model.addAttribute("bean", bean);
        return "portal/hocvien/hocvien_create";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_CREATE')")
    @RequestMapping(value = "/create", method = POST)
    public String postCreate(Model model, HocVienBean bean) {
        HocVien entity = bean.getEntity();
        if (entity != null && entity.getId() == null) {
            entity.setMaHocVien(String.valueOf(System.currentTimeMillis()));
            entity.setNgayTao(new Date());
            entity.setNgayCapNhat(new Date());
            hocVienService.save(entity);
        }

        if (entity.getId() != null) {
            MessageList messageLst = new MessageList(Message.INFO, "Lưu thông tin học viên thành công, vui lòng bổ sung thêm thông tin nếu cần.");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        bean.setEntity(entity);
        KinhNghiemLamViec knlv = new KinhNghiemLamViec(entity.getId());
        bean.setKnlv(knlv);
        model.addAttribute("bean", bean);
        return "portal/hocvien/hocvien_create_step2";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/info/{id}", method = GET)
    public String getAddInfo(Model model, HocVienBean bean, @PathVariable(value = "id") Long id) {
        HocVien entity = hocVienService.findOne(id);
        if (entity != null) {
            KinhNghiemLamViec knlvNew = new KinhNghiemLamViec();
            knlvNew.setMaLienKet(entity.getId());
            bean.setKnlv(knlvNew);
            bean.setEntity(entity);
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(id, Constants.HOC_VIEN);
            bean.setLstKnlv(lstKnlv);
            List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.HOC_VIEN, entity.getId());
            bean.setLstAlfFiles(lstFile);
            model.addAttribute("bean", bean);
        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin học viên.");
            model.addAttribute(CoreConstant.MSG_LST, messageList);
        }
        return "portal/hocvien/hocvien_create_step2";
    }


    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEditInfo(Model model, HocVienBean bean, @PathVariable(value = "id") Long id) {
        bean.setSystemConfig(systemConfig);
        HocVien entity = hocVienService.findOne(id);
        bean.setEntity(entity);
        if (entity != null) {

        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin học viên.");
            model.addAttribute(CoreConstant.MSG_LST, messageList);
        }
        model.addAttribute("bean", bean);
        return "portal/hocvien/hocvien_edit";
    }

    @RequestMapping(value = "/ajax_new_knlv", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustDetail(Model model, @ModelAttribute(value = "bean") HocVienBean bean, Locale locale) {

        KinhNghiemLamViec entity = bean.getKnlv();
        if (entity.getMaLienKet() != null) {
            entity.setLoaiLienKet(Constants.HOC_VIEN);
            kinhNghiemLamViecService.save(entity);
        }
        List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(entity.getMaLienKet(), Constants.HOC_VIEN);
        bean.setLstKnlv(lstKnlv);
        bean.setKnlv(new KinhNghiemLamViec(entity.getMaLienKet()));
        model.addAttribute("bean", bean);
        model.addAttribute(CoreConstant.MSG_LST, "");
        return new ModelAndView("/portal/hocvien/hocvien_knlv :: content");
    }

    @RequestMapping(value = "/ajax_delete_knlv", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getLocationDetail(Model model, @RequestParam(value = "id") Long id) {
        HocVienBean bean = new HocVienBean();
        KinhNghiemLamViec entity = kinhNghiemLamViecService.findOneKnlv(id);
        Long maLienKet = entity.getMaLienKet();
        kinhNghiemLamViecService.deleteKnlvById(id);
        List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(maLienKet, Constants.HOC_VIEN);
        bean.setLstKnlv(lstKnlv);
        bean.setKnlv(new KinhNghiemLamViec(entity.getMaLienKet()));
        model.addAttribute("bean", bean);
        return new ModelAndView("/portal/hocvien/hocvien_knlv :: content");
    }


    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(Model model, HocVienBean bean) {
        HocVien entity = bean.getEntity();
        HocVien dbEntity = hocVienService.findOne(entity.getId());
        dbEntity.update(entity);
        dbEntity.setNgayCapNhat(new Date());
        dbEntity.setNgayCapNhat(new Date());
        hocVienService.save(dbEntity);

        if (entity.getId() != null) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Lưu thông tin học viên thành công, vui lòng bổ sung thêm thông tin nếu cần.");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        bean.setEntity(entity);
        KinhNghiemLamViec knlv = new KinhNghiemLamViec(entity.getId());
        bean.setKnlv(knlv);
        List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.HOC_VIEN, entity.getId());
        bean.setLstAlfFiles(lstFile);
        model.addAttribute("bean", bean);
        return "portal/hocvien/hocvien_create_step2";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete() {
        return "redirect:/portal/hocvien/list";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_UPLOAD')")
    @RequestMapping(value = "/upload", method = POST)
    public String postUploadList(Model model, Locale locale, @ModelAttribute(value = "bean") HocVienBean bean, @RequestParam List<MultipartFile> txtFile) {
        List<AlfFile> lstDocResult = bean.getLstAlfFiles();

        Date timeProcess = new Date();
        for (MultipartFile file : txtFile) {
            String type = "";
            String note = "";
            String originalFilename = file.getOriginalFilename();
            for (AlfFile alfFile : lstDocResult) {
                if (alfFile != null && alfFile.getFileName() != null) {
                    if (originalFilename.equals(alfFile.getFileName())) {
                        type = alfFile.getType();
                        note = alfFile.getNote();
                        break;
                    }
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
            String newName = type + "." + bean.getEntity().getMaHocVien() + "." + sdf.format(timeProcess);

            try {
                if (Utils.isNotNullOrEmpty(type)) {
                    Map<String, Object> properties = propertyMapper.mapProperties(file, type);
                    Document aDoc = ecmService.upload(file, toDirectory, properties);
                    if (aDoc != null) {
                        AlfFile vFile = new AlfFile(originalFilename, type, aDoc.getId(), newName, Constants.HOC_VIEN, bean.getEntity().getId());
                        vFile.setNote(note);
                        vFile.setDateUpload(new Date());
                        alfFileService.save(vFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String msgInfo = messageSource.getMessage("message.error.filename", null, locale);
                MessageList messageList = new MessageList(Message.ERROR, msgInfo);
                model.addAttribute(CoreConstant.MSG_LST, messageList);
            }

        }
        HocVien entity = hocVienService.findOne(bean.getEntity().getId());
        if (entity != null) {
            KinhNghiemLamViec knlvNew = new KinhNghiemLamViec();
            knlvNew.setMaLienKet(entity.getId());
            bean.setKnlv(knlvNew);
            bean.setEntity(entity);
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(bean.getEntity().getId(), Constants.HOC_VIEN);
            bean.setLstKnlv(lstKnlv);

            List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.HOC_VIEN, entity.getId());
            bean.setLstAlfFiles(lstFile);
            model.addAttribute("bean", bean);


        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin học viên.");
            model.addAttribute(CoreConstant.MSG_LST, messageList);
        }

        return "portal/hocvien/hocvien_create_step2";
    }

    @RequestMapping(value = "/ajax_get_hocvien", method = RequestMethod.GET)
    public @ResponseBody
    JsonReturn deleteFile(@RequestParam(value = "maHocVien") String maHocVien) {
        JsonReturn jsonReturn = new JsonReturn();
        jsonReturn.setStatus(Constants.FAIL);
        HocVien entity = hocVienService.findByMaHocVien(maHocVien);
        if (entity != null) {
            jsonReturn.setStatus(Constants.SUCCESS);
            jsonReturn.setResult(entity);
        }

        return jsonReturn;
    }

    @RequestMapping(value = "/donnhaphoc/{id}", method = RequestMethod.GET)
    public void template3Download(@PathVariable("id") Long id, HttpServletResponse response, Model model,
                                  Locale locale) throws Exception {

        HocVien entity = hocVienService.findOne(id);

        if (entity != null) {


            String maHocVien = entity.getMaHocVien();
            String fileName = maHocVien + "_DON_DANG_KY_NHAP_HOC_" + System.currentTimeMillis() + ".xml";
            InputStream is = getClass().getResourceAsStream("/print/DON_NHAP_HOC.xml");

            String content = Utils.readFile(is);

            List<HocVienDk> lstHvdk = hocVienDkService.findByMaHocVien(entity.getMaHocVien());
            if (!CollectionUtils.isEmpty(lstHvdk)) {
                if (lstHvdk.size() > 0) {
                    content = content.replaceAll(Constants.PRI_KHOA_HOC_1, lstHvdk.get(0).getKhoaHoc().getKhoaHocName());
                }
                String xmlKhoaHocContent = "";
                for (int i = 1; i < lstHvdk.size(); i++) {
                    xmlKhoaHocContent += Constants.PRI_KHOA_HOC_XML_CONTENT.replaceAll("KHOA_HOC", Utils.nullCheck(lstHvdk.get(i).getKhoaHoc().getKhoaHocName()));
                }
                content = content.replaceAll(Constants.PRI_KHOA_HOC_XML, xmlKhoaHocContent);
            }

            content = content.replaceAll(Constants.PRI_KHOA_HOC_1, "");
            content = content.replaceAll(Constants.PRI_KHOA_HOC_XML, "");

            if (entity.getGioiTinh().equals("M")) {
                content = content.replaceAll(Constants.PRI_GT_NAM, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_GT_NU, Constants.PRI_UNCHECK);
            } else {
                content = content.replaceAll(Constants.PRI_GT_NU, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_GT_NAM, Constants.PRI_UNCHECK);
            }

            if (entity.getTinhTrangCaNhan().equals("DH")) {
                content = content.replaceAll(Constants.PRI_TTCN_DIHOC, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_TTCN_DILAM, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_TTCN_TUDO, Constants.PRI_UNCHECK);
            } else if (entity.getTinhTrangCaNhan().equals("DL")) {
                content = content.replaceAll(Constants.PRI_TTCN_DIHOC, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_TTCN_DILAM, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_TTCN_TUDO, Constants.PRI_UNCHECK);
            } else {
                content = content.replaceAll(Constants.PRI_TTCN_DIHOC, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_TTCN_DILAM, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_TTCN_TUDO, Constants.PRI_CHECKED);
            }

            if (entity.getHp1().equals(Constants.HP_TRON_KHOA)) {
                content = content.replaceAll(Constants.PRI_HP1_TRON_KHOA, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_HP1_NHIEU_DOT, Constants.PRI_UNCHECK);
            } else if (entity.getHp1().equals(Constants.HP_NHIEU_DOT)) {
                content = content.replaceAll(Constants.PRI_HP1_TRON_KHOA, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP1_NHIEU_DOT, Constants.PRI_CHECKED);
            } else {
                content = content.replaceAll(Constants.PRI_HP1_TRON_KHOA, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP1_NHIEU_DOT, Constants.PRI_UNCHECK);
            }

            if (entity.getHp2().equals(Constants.HP_TRON_KHOA)) {
                content = content.replaceAll(Constants.PRI_HP2_TRON_KHOA, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_HP2_NHIEU_DOT, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_HOC_KY, Constants.PRI_UNCHECK);
            } else if (entity.getHp2().equals(Constants.HP_NHIEU_DOT)) {
                content = content.replaceAll(Constants.PRI_HP2_TRON_KHOA, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_NHIEU_DOT, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_HP2_HOC_KY, Constants.PRI_UNCHECK);
            } else if (entity.getHp2().equals(Constants.HP_HOC_KY)) {
                content = content.replaceAll(Constants.PRI_HP2_TRON_KHOA, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_NHIEU_DOT, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_HOC_KY, Constants.PRI_CHECKED);
            } else {
                content = content.replaceAll(Constants.PRI_HP2_TRON_KHOA, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_NHIEU_DOT, Constants.PRI_UNCHECK);
                content = content.replaceAll(Constants.PRI_HP2_HOC_KY, Constants.PRI_UNCHECK);
            }

            content = content.replaceAll(Constants.PRI_LLKC_HO_TEN, Utils.nullCheck(entity.getHoTenQh1()));
            content = content.replaceAll(Constants.PRI_LLKC_QUAN_HE, Utils.nullCheck(entity.getLoaiQh1()));
            content = content.replaceAll(Constants.PRI_LLKC_EMAIL, Utils.nullCheck(entity.getEmailQh1()));
            content = content.replaceAll(Constants.PRI_LLKC_SO_DT, Utils.nullCheck(entity.getSdtQh1()));


            content = content.replaceAll(Constants.PRI_PTHP_HO_TEN, Utils.nullCheck(entity.getNdhpHoTen()));
            content = content.replaceAll(Constants.PRI_PTHP_DIACHI, Utils.nullCheck(entity.getNdhpDiaChi()));
            content = content.replaceAll(Constants.PRI_PTHP_EMAIL, Utils.nullCheck(entity.getNdhpEmail()));
            content = content.replaceAll(Constants.PRI_PTHP_SDT, Utils.nullCheck(entity.getNdhpSdt()));

            content = content.replaceAll(Constants.PRI_HO_TEN_GH1, Utils.nullCheck(entity.getHoTenQh1()));
            content = content.replaceAll(Constants.PRI_HO_TEN_GH2, Utils.nullCheck(entity.getHoTenQh2()));

            content = content.replaceAll(Constants.PRI_SO_DT1, Utils.nullCheck(entity.getSdtQh1()));
            content = content.replaceAll(Constants.PRI_SO_DT2, Utils.nullCheck(entity.getSdtQh2()));

            content = content.replaceAll(Constants.PRI_EMAIL_GH1, Utils.nullCheck(entity.getEmailQh1()));
            content = content.replaceAll(Constants.PRI_EMAIL_GH2, Utils.nullCheck(entity.getEmailQh2()));

            content = content.replaceAll(Constants.PRI_QUAN_HE_1, Utils.nullCheck(entity.getLoaiQh1()));
            content = content.replaceAll(Constants.PRI_QUAN_HE_2, Utils.nullCheck(entity.getLoaiQh2()));

            content = content.replaceAll(Constants.PRI_EMAIL_SHP, Utils.nullCheck(entity.getEmailShp()));
            content = content.replaceAll(Constants.PRI_EMAIL, Utils.nullCheck(entity.getEmail()));
            content = content.replaceAll(Constants.PRI_SO_CMND, Utils.nullCheck(entity.getCmnd()));
            content = content.replaceAll(Constants.PRI_MA_HOC_VIEN, Utils.nullCheck(entity.getMaHocVien()));
            content = content.replaceAll(Constants.PRI_HO_TEN, Utils.nullCheck(entity.getHoTen()));
            content = content.replaceAll(Constants.PRI_NGAY_SINH, Utils.nullCheck(entity.getStrNgaySinh()));
            content = content.replaceAll(Constants.PRI_SO_DIEN_THOAI, Utils.nullCheck(entity.getSoDienThoai()));
            content = content.replaceAll(Constants.PRI_NGAY_CAP_CMND, Utils.nullCheck(entity.getStrNgayCapCmnd()));
            content = content.replaceAll(Constants.PRI_NOI_CAP_CMND, Utils.nullCheck(entity.getNoiCapCmnd()));
            content = content.replaceAll(Constants.PRI_TDTA, Utils.nullCheck(entity.getTrinhDoTiengAnh()));

            if (Utils.isAllNotNullOrEmpty(entity.getDiaChiThuongTru(), entity.getXaThuongTruLoc(), entity.getQuanThuongTruLoc(), entity.getTinhThuongTruLoc()))
                content = content.replaceAll(Constants.PRI_DC_THUONG_TRU, Utils.nullCheck(entity.getDiaChiThuongTru()) + ", " + entity.getXaThuongTruLoc().getLocName() + ", " + entity.getQuanThuongTruLoc().getLocName() + ", " + entity.getTinhThuongTruLoc().getLocName());
            if (Utils.isAllNotNullOrEmpty(entity.getDiaChiTamTru(), entity.getXaTamTruLoc(), entity.getQuanTamTruLoc(), entity.getTinhTamTruLoc()))
                content = content.replaceAll(Constants.PRI_DC_TAM_TRU, Utils.nullCheck(entity.getDiaChiTamTru()) + ", " + entity.getXaTamTruLoc().getLocName() + ", " + entity.getQuanTamTruLoc().getLocName() + ", " + entity.getTinhTamTruLoc().getLocName());

            String xmlKnlv = "";
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(entity.getId(), Constants.HOC_VIEN);
            if (!CollectionUtils.isEmpty(lstKnlv)) {
                InputStream isKnlv = getClass().getResourceAsStream("/print/KINH_NGHIEM_LAM_VIEC.xml");
                String contentKnlv = Utils.readFile(isKnlv);
                for (KinhNghiemLamViec kinhNghiemLamViec : lstKnlv) {
                    String tmp = contentKnlv;
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_TU, kinhNghiemLamViec.getStrTuNgay());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_DEN, kinhNghiemLamViec.getStrDenNgay());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_VI_TRI, kinhNghiemLamViec.getViTri());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_DIA_CHI, kinhNghiemLamViec.getDiaChi());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_TEN_CTY, kinhNghiemLamViec.getTenCongTy());
                    xmlKnlv += tmp;
                }


            }
            content = content.replaceAll(Constants.PRI_KNLVIEC, xmlKnlv);
            // --------BEGIN TABLE ACC ----------

//            String lstAccString = entity.getLstAccount();
//            String finalTrContent1 = fillTable(maHocVien, lstAccString, "MB03_SUB_1.xml", entity);
//            content = content.replaceAll(Constants.AP_MB03_SUB_1, finalTrContent1);
//
//            finalTrContent1 = fillTable(maHocVien, lstAccString, "MB03_SUB_2.xml", entity);
//            content = content.replaceAll(Constants.AP_MB03_SUB_2, finalTrContent1);


            // --------END TABLE ACC ----------
            byte[] bFile = content.getBytes();

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                response.getOutputStream().write(bFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bos.close();
            }
        }

    }

    //------ModelAttribute-----
    @ModelAttribute(value = "locationCatalog")
    public List<Location> getListLocation() {
        List<Location> result = new ArrayList<>();
        List<Location> lstDebLoc = systemConfig.getLstDebLoc();
        for (Location location : lstDebLoc) {
            if (Utils.isNullOrEmpty(location.getParentCode())) {
                result.add(location);
            }
        }
        return result;
    }

    @ModelAttribute("lstTonGiao")
    public List<TonGiao> getLstTonGiao() {
        return tonGiaoService.findAll();
    }

    @ModelAttribute("lstDanToc")
    public List<DanToc> getLstDanToc() {
        return danTocService.findAll();
    }
}
