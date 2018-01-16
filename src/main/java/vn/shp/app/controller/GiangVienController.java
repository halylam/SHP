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
import vn.shp.app.bean.GiangVienBean;
import vn.shp.app.config.Constants;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.*;
import vn.shp.app.service.*;
import vn.shp.app.utils.Utils;
import vn.shp.app.constant.CoreConstant;
import vn.shp.core.Message;
import vn.shp.core.MessageList;
import vn.shp.app.entity.AlfFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("portal/giangvien")
public class GiangVienController  extends AbstractController {
    
    @Value("${dm.toDirectory.hososv}")
    private String toDirectory;
    
    @Autowired
    GiangVienService giangVienService;

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

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, Role.ROLE_GIANGVIEN_LIST)")
    @RequestMapping(value = "/list", method = GET)
    public String getList(Model model, HttpServletRequest request) {
        GiangVienBean bean = new GiangVienBean();
        List<GiangVien> lstData = giangVienService.findAll();
        bean.setLstData(lstData);
        model.addAttribute("bean", bean);
        return "portal/giangvien/giangvien_list";
    }
    
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, Role.ROLE_GIANGVIEN_CREATE)")
    @RequestMapping(value = "/create", method = GET)
    public String getCreate(Model model) {
        GiangVienBean bean = new GiangVienBean();
        GiangVien entity = new GiangVien();
        bean.setEntity(entity);
        model.addAttribute("bean", bean);
        return "portal/giangvien/giangvien_create";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_GIANGVIEN_CREATE')")
    @RequestMapping(value = "/create", method = POST)
    public String postCreate(Model model, GiangVienBean bean) {
        GiangVien entity = bean.getEntity();
        if (entity != null && entity.getId() == null) {
            entity.setMaGiangVien(String.valueOf(System.currentTimeMillis()));
            entity.setNgayTao(new Date());
            entity.setNgayCapNhat(new Date());
            giangVienService.save(entity);
        }

        if (entity.getId() != null) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Lưu thông tin giảng viên thành công, vui lòng bổ sung thêm thông tin nếu cần.");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        bean.setEntity(entity);
        KinhNghiemLamViec knlv = new KinhNghiemLamViec(entity.getId());
        bean.setKnlv(knlv);
        model.addAttribute("bean", bean);
        return "portal/giangvien/giangvien_create_step2";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_GIANGVIEN_EDIT')")
    @RequestMapping(value = "/info/{id}", method = GET)
    public String getAddInfo(Model model, GiangVienBean bean, @PathVariable(value = "id") Long id) {
        GiangVien entity = giangVienService.findOne(id);
        if (entity != null) {
            KinhNghiemLamViec knlvNew = new KinhNghiemLamViec();
            knlvNew.setMaLienKet(entity.getId());
            bean.setKnlv(knlvNew);
            bean.setEntity(entity);
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(id, Constants.GIANG_VIEN);
            bean.setLstKnlv(lstKnlv);
            List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.GIANG_VIEN, entity.getId());
            bean.setLstAlfFiles(lstFile);
            model.addAttribute("bean", bean);
        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin học viên.");
        }
        return "portal/giangvien/giangvien_create_step2";
    }

    @RequestMapping(value = "/ajax_new_knlv", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCustDetail(Model model, @ModelAttribute(value = "bean") GiangVienBean bean, Locale locale) {

        KinhNghiemLamViec entity = bean.getKnlv();
        if (entity.getMaLienKet() != null) {
            entity.setLoaiLienKet(Constants.GIANG_VIEN);
            kinhNghiemLamViecService.save(entity);
        }
        List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(entity.getMaLienKet(), Constants.GIANG_VIEN);
        bean.setLstKnlv(lstKnlv);
        bean.setKnlv(new KinhNghiemLamViec(entity.getMaLienKet()));
        model.addAttribute("bean", bean);
        model.addAttribute(CoreConstant.MSG_LST, "");
        return new ModelAndView("/portal/giangvien/giangvien_knlv :: content");
    }

    @RequestMapping(value = "/ajax_delete_knlv", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getLocationDetail(Model model, @RequestParam(value = "id") Long id) {
        GiangVienBean bean = new GiangVienBean();
        KinhNghiemLamViec entity = kinhNghiemLamViecService.findOneKnlv(id);
        Long maLienKet = entity.getMaLienKet();
        kinhNghiemLamViecService.deleteKnlvById(id);
        List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(maLienKet, Constants.GIANG_VIEN);
        bean.setLstKnlv(lstKnlv);
        bean.setKnlv(new KinhNghiemLamViec(entity.getMaLienKet()));
        model.addAttribute("bean", bean);
        model.addAttribute(CoreConstant.MSG_LST, "");
        return new ModelAndView("/portal/giangvien/giangvien_knlv :: content");
    }
    
    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_GIANGVIEN_UPLOAD')")
    @RequestMapping(value = "/upload", method = POST)
    public String postUploadList(Model model, Locale locale, @ModelAttribute(value = "bean") GiangVienBean bean, @RequestParam List<MultipartFile> txtFile) {
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
            String newName = type + "." + bean.getEntity().getMaGiangVien() + "." + sdf.format(timeProcess);

            try {
                if (Utils.isNotNullOrEmpty(type)) {
                    Map<String, Object> properties = propertyMapper.mapProperties(file, type);
                    Document aDoc = ecmService.upload(file, toDirectory, properties);
                    if (aDoc != null) {
                        AlfFile vFile = new AlfFile(originalFilename, type, aDoc.getId(), newName, Constants.GIANG_VIEN, bean.getEntity().getId());
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
        GiangVien entity = giangVienService.findOne(bean.getEntity().getId());
        if (entity != null) {
            KinhNghiemLamViec knlvNew = new KinhNghiemLamViec();
            knlvNew.setMaLienKet(entity.getId());
            bean.setKnlv(knlvNew);
            bean.setEntity(entity);
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(bean.getEntity().getId(), Constants.GIANG_VIEN);
            bean.setLstKnlv(lstKnlv);

            List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.GIANG_VIEN, entity.getId());
            bean.setLstAlfFiles(lstFile);
            model.addAttribute("bean", bean);


        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin giảng viên.");
            model.addAttribute(CoreConstant.MSG_LST, messageList);
        }

        return "portal/giangvien/giangvien_create_step2";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String getEditInfo(Model model, GiangVienBean bean, @PathVariable(value = "id") Long id) {
        bean.setSystemConfig(systemConfig);
        GiangVien entity = giangVienService.findOne(id);
        bean.setEntity(entity);
        if (entity != null) {

        } else {
            MessageList messageList = new MessageList(Message.ERROR, "Không tìm thấy thông tin giảng viên.");
        }
        model.addAttribute("bean", bean);
        return "portal/giangvien/giangvien_edit";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_HOCVIEN_EDIT')")
    @RequestMapping(value = "/edit", method = POST)
    public String postEdit(Model model, GiangVienBean bean) {
        GiangVien entity = bean.getEntity();
        GiangVien dbEntity = giangVienService.findOne(entity.getId());
        dbEntity.update(entity);
        dbEntity.setNgayCapNhat(new Date());
        dbEntity.setNgayCapNhat(new Date());
        giangVienService.save(dbEntity);

        if (entity.getId() != null) {
            MessageList messageLst = new MessageList(Message.INFO);
            messageLst.add("Lưu thông tin giảng viên thành công, vui lòng bổ sung thêm thông tin nếu cần.");
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
            model.addAttribute(CoreConstant.MSG_LST, messageLst);
        }
        bean.setEntity(entity);
        KinhNghiemLamViec knlv = new KinhNghiemLamViec(entity.getId());
        bean.setKnlv(knlv);
        List<AlfFile> lstFile = alfFileService.findBySourceAndSourceId(Constants.GIANG_VIEN, entity.getId());
        bean.setLstAlfFiles(lstFile);
        model.addAttribute("bean", bean);
        return "portal/giangvien/giangvien_create_step2";
    }

    @PreAuthorize("hasAnyRole(Role.ROLE_ADMIN, 'ROLE_GIANGVIEN_DELETE')")
    @RequestMapping(value = "/delete", method = GET)
    public String getDelete() {
        return "redirect:/portal/giangvien/list";
    }


    @RequestMapping(value = "/hoso/{id}", method = RequestMethod.GET)
    public void template3Download(@PathVariable("id") Long id, HttpServletResponse response, Model model,
                                  Locale locale) throws Exception {

        GiangVien entity = giangVienService.findOne(id);

        if (entity != null) {

            String maGiangVien = entity.getMaGiangVien();
            String fileName = maGiangVien + "_HO_SO_" + System.currentTimeMillis() + ".xml";
            InputStream is = getClass().getResourceAsStream("/print/THONG_TIN_GV.xml");

            String content = Utils.readFile(is);




            if(entity.getGioiTinh().equals("M")){
                content = content.replaceAll(Constants.PRI_GT_NAM, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_GT_NU, Constants.PRI_UNCHECK);
            }else{
                content = content.replaceAll(Constants.PRI_GT_NU, Constants.PRI_CHECKED);
                content = content.replaceAll(Constants.PRI_GT_NAM, Constants.PRI_UNCHECK);
            }

            content = content.replaceAll(Constants.PRI_DAN_TOC, Utils.nullCheck(entity.getDanToc().getDanTocName()));
            content = content.replaceAll(Constants.PRI_TON_GIAO, Utils.nullCheck(entity.getTonGiao().getTonGiaoName()));

            content = content.replaceAll(Constants.PRI_EMAIL_SHP, Utils.nullCheck(entity.getEmailShp()));
            content = content.replaceAll(Constants.PRI_EMAIL, Utils.nullCheck(entity.getEmail()));
            content = content.replaceAll(Constants.PRI_SO_CMND, Utils.nullCheck(entity.getCmnd()));
            content = content.replaceAll(Constants.PRI_MA_GIANG_VIEN, Utils.nullCheck(entity.getMaGiangVien()));
            content = content.replaceAll(Constants.PRI_HO_TEN, Utils.nullCheck(entity.getHoTen()));
            content = content.replaceAll(Constants.PRI_NGAY_SINH, Utils.nullCheck(entity.getStrNgaySinh()));
            content = content.replaceAll(Constants.PRI_SO_DIEN_THOAI, Utils.nullCheck(entity.getSoDienThoai()));
            content = content.replaceAll(Constants.PRI_NGAY_CAP_CMND, Utils.nullCheck(entity.getStrNgayCapCmnd()));
            content = content.replaceAll(Constants.PRI_NOI_CAP_CMND, Utils.nullCheck(entity.getNoiCapCmnd()));

            content = content.replaceAll(Constants.PRI_HOC_VI, Utils.nullCheck(entity.getHocVi().getHocViName()));
            content = content.replaceAll(Constants.PRI_CHUYEN_NGANH, Utils.nullCheck(entity.getChuyenNganh().getChuyenNganhName()));

            content = content.replaceAll(Constants.PRI_NGOAI_NGU_1, Utils.nullCheck(entity.getNgoaiNgu1()));
            content = content.replaceAll(Constants.PRI_NGOAI_NGU_2, Utils.nullCheck(entity.getNgoaiNgu2()));

            content = content.replaceAll(Constants.PRI_DC_THUONG_TRU, Utils.nullCheck(entity.getDiaChiThuongTru()) + ", " + entity.getXaThuongTruLoc().getLocName() + ", " + entity.getQuanThuongTruLoc().getLocName() + ", " + entity.getTinhThuongTruLoc().getLocName());
            content = content.replaceAll(Constants.PRI_DC_TAM_TRU, Utils.nullCheck(entity.getDiaChiTamTru()) + ", " + entity.getXaTamTruLoc().getLocName() + ", " + entity.getQuanTamTruLoc().getLocName() + ", " + entity.getTinhTamTruLoc().getLocName());

            String xmlKnlv = "";
            List<KinhNghiemLamViec> lstKnlv = kinhNghiemLamViecService.findAllByMaLienKetAndLoaiLienKet(entity.getId(),Constants.GIANG_VIEN);
            if(!CollectionUtils.isEmpty(lstKnlv)){
                InputStream isKnlv = getClass().getResourceAsStream("/print/KINH_NGHIEM_LAM_VIEC_GV.xml");
                String contentKnlv = Utils.readFile(isKnlv);
                for (KinhNghiemLamViec kinhNghiemLamViec : lstKnlv) {
                    String tmp = contentKnlv;
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_TU, kinhNghiemLamViec.getStrTuNgay());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_DEN, kinhNghiemLamViec.getStrDenNgay());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_VI_TRI, kinhNghiemLamViec.getViTri());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_DIA_CHI, kinhNghiemLamViec.getDiaChi());
                    tmp = tmp.replaceAll(Constants.PRI_KNLV_TEN_CTY, kinhNghiemLamViec.getTenCongTy());
                    xmlKnlv+=tmp;
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

    @Autowired
    BoMonService boMonService;

    @Autowired
    TonGiaoService tonGiaoService;

    @Autowired
    DanTocService danTocService;

    @Autowired
    PhanHeService phanHeService;

    @Autowired
    HocViService hocViService;

    @Autowired
    ChuyenMonService chuyenMonService;

    @Autowired
    CaHocService caHocService;

    @Autowired
    ChuyenNganhService chuyenNganhService;

    @ModelAttribute("lstBoMon")
    public List<BoMon> getLstBoMon(){
        return boMonService.findAll();
    }

    @ModelAttribute("lstTonGiao")
    public List<TonGiao> getLstTonGiao(){
        return tonGiaoService.findAll();
    }

    @ModelAttribute("lstDanToc")
    public List<DanToc> getLstDanToc(){
        return danTocService.findAll();
    }

    @ModelAttribute("lstPhanHe")
    public List<PhanHe> getLstPhanHe(){
        return phanHeService.findAll();
    }

    @ModelAttribute("lstHocVi")
    public List<HocVi> getLstHocVi(){
        return hocViService.findAll();
    }

    @ModelAttribute("lstChuyenNganh")
    public List<ChuyenNganh> getLstChuyenNganh(){
        return chuyenNganhService.findAll();
    }

    @ModelAttribute("lstCaHoc")
    public List<CaHoc> getLstCaHoc(){
        return caHocService.findAll();
    }
}
