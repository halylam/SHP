package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import vn.shp.app.utils.Utils;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "HOC_VIEN", uniqueConstraints = @UniqueConstraint(columnNames = "MA_HOC_VIEN"))
@Data
@Audited
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

    @OneToOne(optional=true)
    @JoinColumn(name = "TINH_THUONG_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location tinhThuongTruLoc;


    @Column(name = "QUAN_THUONG_TRU", length = 10)
    private String quanThuongTru;

    @OneToOne(optional=true)
    @JoinColumn(name = "QUAN_THUONG_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location quanThuongTruLoc;

    @Column(name = "XA_THUONG_TRU", length = 10)
    private String xaThuongTru;

    @OneToOne(optional=true)
    @JoinColumn(name = "XA_THUONG_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location xaThuongTruLoc;

    @Column(name = "DIA_CHI_THUONG_TRU", length = 250)
    private String diaChiThuongTru;

    @Column(name = "TINH_TAM_TRU", length = 10)
    private String tinhTamTru;

    @OneToOne(optional=true)
    @JoinColumn(name = "TINH_TAM_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location tinhTamTruLoc;

    @Column(name = "QUAN_TAM_TRU", length = 10)
    private String quanTamTru;

    @OneToOne(optional=true)
    @JoinColumn(name = "QUAN_TAM_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location quanTamTruLoc;

    @Column(name = "XA_TAM_TRU", length = 10)
    private String xaTamTru;

    @OneToOne(optional=true)
    @JoinColumn(name = "XA_TAM_TRU", referencedColumnName = "LOC_CODE", updatable = false, insertable = false, nullable=true)
    private Location xaTamTruLoc;

    @Column(name = "DIA_CHI_TAM_TRU", length = 250)
    private String diaChiTamTru;

    @Column(name = "HO_TEN_QH1", length = 50)
    private String hoTenQh1;

    @Column(name = "EMAIL_QH1", length = 50)
    private String emailQh1;

    @Column(name = "SDT_QH1", length = 15)
    private String sdtQh1;

    @Column(name = "LOAI_QH1", length = 15)
    private String loaiQh1;

    @Column(name = "HO_TEN_QH2", length = 50)
    private String hoTenQh2;

    @Column(name = "EMAIL_QH2", length = 50)
    private String emailQh2;

    @Column(name = "SDT_QH2", length = 15)
    private String sdtQh2;

    @Column(name = "LOAI_QH2", length = 15)
    private String loaiQh2;

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

    @Column(name = "MA_TON_GIAO", length = 10)
    private String maTonGiao;

    @OneToOne(optional=true)
    @JoinColumn(name = "MA_TON_GIAO", referencedColumnName = "ma", updatable = false, insertable = false, nullable=true)
    private TonGiao tonGiao;

    @Column(name = "MA_DAN_TOC", length = 10)
    private String maDanToc;

    @OneToOne(optional=true)
    @JoinColumn(name = "MA_DAN_TOC", referencedColumnName = "ma", updatable = false, insertable = false, nullable=true)
    private DanToc danToc;

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
        this.hoTenQh1 = hv.hoTenQh1;
        this.emailQh1 = hv.emailQh1;
        this.sdtQh1 = hv.sdtQh1;
        this.loaiQh1 = hv.loaiQh1;
        this.hoTenQh2 = hv.hoTenQh2;
        this.emailQh2 = hv.emailQh2;
        this.sdtQh2 = hv.sdtQh2;
        this.loaiQh2    = hv.loaiQh2;
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
        this.maDanToc = hv.maDanToc;
        this.maTonGiao = hv.maTonGiao;

    }

}
