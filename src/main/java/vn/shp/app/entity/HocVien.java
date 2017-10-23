package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import vn.shp.app.utils.Utils;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "HOC_VIEN", uniqueConstraints = @UniqueConstraint(columnNames = "MA_HOC_VIEN"))
@Data
public class HocVien implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HOC_VIEN_ID_SEQ")
    @SequenceGenerator(name = "HOC_VIEN_ID_SEQ", sequenceName = "HOC_VIEN_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "MA_HOC_VIEN", length = 50)
    private String maHocVien;

    @Column(name = "LOAI_HOC_VIEN", length = 1)
    private String loaiHocVien;

    @NotEmpty
    @Column(name = "HO_TEN", length = 50)
    private String hoTen;

    @Column(name = "GIOI_TINH", length = 1)
    private String gioiTinh;

    public String getTenGioiTinh(){
        if(Utils.isNotNullOrEmpty(gioiTinh)){
            if(gioiTinh.equals("F")){
                return "Nữ";
            }else {
                return "Nam";
            }
        }
        return gioiTinh;
    }

    @Column(name = "SO_DIEN_THOAI", length = 15)
    private String soDienThoai;

    @Column(name = "NGAY_SINH")
    private Date ngaySinh;

    @Transient
    private String strNgaySinh;

    public String getStrNgaySinh(){
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(ngaySinh != null){
            strNgaySinh = sdf.format(ngaySinh);
        }
        return strNgaySinh;
    }

    @Column(name = "EMAIL_SHP", length = 30)
    private String emailShp;

    @Column(name = "EMAIL", length = 30)
    private String email;

    @Column(name = "CMND", length = 20)
    private String cmnd;

    @Column(name = "NGAY_CAP_CMND")
    private Date ngayCapCmnd;

    @Transient
    private String strNgayCapCmnd;

    public String getStrNgayCapCmnd(){
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(ngayCapCmnd != null){
            strNgayCapCmnd = sdf.format(ngayCapCmnd);
        }
        return strNgayCapCmnd;
    }

    @Column(name = "NOI_CAP_CMND", length = 50)
    private String noiCapCmnd;

    @Column(name = "TINH_TRANG_CN", length = 10)
    private String tinhTrangCaNhan;

    @Column(name = "TRINH_DO_TA", length = 150)
    private String trinhDoTiengAnh;

    @Column(name = "TINH_THUONG_TRU", length = 10)
    private String tinhThuongTru;

    @Column(name = "QUAN_THUONG_TRU", length = 10)
    private String quanThuongTru;

    @Column(name = "XA_THUONG_TRU", length = 10)
    private String xaThuongTru;

    @Column(name = "DIA_CHI_THUONG_TRU", length = 250)
    private String diaChiThuongTru;

    @Column(name = "TINH_TAM_TRU", length = 10)
    private String tinhTamTru;

    @Column(name = "QUAN_TAM_TRU", length = 10)
    private String quanTamTru;

    @Column(name = "XA_TAM_TRU", length = 10)
    private String xaTamTru;

    @Column(name = "DIA_CHI_TAM_TRU", length = 250)
    private String diaChiTamTru;

    @Column(name = "HO_TEN_CHA", length = 50)
    private String hoTenCha;

    @Column(name = "EMAIL_CHA", length = 50)
    private String emailCha;

    @Column(name = "SDT_CHA", length = 15)
    private String sdtCha;

    @Column(name = "HO_TEN_ME", length = 50)
    private String hoTenMe;

    @Column(name = "EMIAL_ME", length = 50)
    private String emailMe;

    @Column(name = "SDT_ME", length = 15)
    private String sdtMe;

    @Column(name = "TRUONG_THCS", length = 250)
    private String truongThcs;

    @Column(name = "TRUONG_THPT", length = 250)
    private String truongThpt;

    @Column(name = "TRUONG_KHAC", length = 250)
    private String truongKhac;

    @Column(name = "NDHP", length = 3)
    private String ndhp;

    @Column(name = "NDHP_HO_TEN", length = 50)
    private String ndhpHoTen;

    @Column(name = "NDHP_SDT", length = 15)
    private String ndhpSdt;

    @Column(name = "NDHP_EMAIL", length = 50)
    private String ndhpEmail;

    @Column(name = "NDHP_TINH", length = 10)
    private String ndhpTinh;

    @Column(name = "NDHP_QUAN", length = 10)
    private String ndhpQuan;

    @Column(name = "NDHP_XA", length = 10)
    private String ndhpXa;

    @Column(name = "NDHP_DIA_CHI", length = 250)
    private String ndhpDiaChi;

    @Column(name = "HOC_PHI_1", length = 10)
    private String hp1;

    @Column(name = "HOC_PHI_2", length = 10)
    private String hp2;

    @Column(name = "NGAY_TAO")
    private Date ngayTao;

    @Column(name = "NGUOI_TAO",length = 50)
    private String nguoiTao;

    @Column(name = "NGAY_CAP_NHAT")
    private Date ngayCapNhat;

    @Column(name = "NGUOI_CAP_NHAP",length = 50)
    private String nguoiCapNhat;

    public void update(HocVien hv){
        this.loaiHocVien = hv.loaiHocVien;
        this.hoTen = hv.hoTen;
        this.gioiTinh = hv.gioiTinh;
        this.soDienThoai = hv.soDienThoai;
        this.ngaySinh = hv.ngaySinh;
        this.emailShp = hv.emailShp;
        this.email = hv.email;
        this.cmnd = hv.getCmnd();
        this.ngayCapCmnd = hv.ngayCapCmnd;
        this.noiCapCmnd = hv.noiCapCmnd;
        this.tinhTrangCaNhan = hv.tinhTrangCaNhan;
        this.trinhDoTiengAnh = hv.trinhDoTiengAnh;
        this.tinhThuongTru = hv.tinhThuongTru;
        this.quanThuongTru=hv.quanThuongTru;
        this.xaThuongTru = hv.xaThuongTru;
        this.diaChiThuongTru = hv.diaChiThuongTru;
        this.tinhTamTru = hv.tinhTamTru;
        this.quanTamTru = hv.quanTamTru;
        this.xaTamTru = hv.xaTamTru;
        this.diaChiTamTru = hv.diaChiTamTru;
        this.hoTenCha = hv.hoTenCha;
        this.emailCha = hv.emailCha;
        this.sdtCha = hv.sdtCha;
        this.hoTenMe = hv.hoTenMe;
        this.emailMe = hv.emailMe;
        this.sdtMe = hv.sdtMe;
        this.truongThpt = hv.truongThpt;
        this.truongThcs = hv.truongThcs;
        this.truongKhac = hv.truongKhac;
        this.ndhp = hv.ndhp;
        this.ndhpHoTen = hv.ndhpHoTen;
        this.ndhpSdt = hv.ndhpSdt;
        this.ndhpEmail = hv.ndhpEmail;
        this.ndhpTinh = hv.ndhpTinh;
        this.ndhpQuan = hv.ndhpQuan;
        this.ndhpXa = hv.ndhpXa;
        this.ndhpDiaChi = hv.ndhpDiaChi;
        this.hp1  = hv.hp1;
        this.hp2= hv.hp2;

    }

}
