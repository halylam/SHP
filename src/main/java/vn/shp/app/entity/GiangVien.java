package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "GIANG_VIEN", uniqueConstraints = @UniqueConstraint(columnNames = "MA_GIANG_VIEN"))
@Data
public class GiangVien implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GIANG_VIEN_ID_SEQ")
    @SequenceGenerator(name = "GIANG_VIEN_ID_SEQ", sequenceName = "GIANG_VIEN_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "MA_GIANG_VIEN", length = 50)
    private String maGiangVien;

    @Column(name = "LOAI_GIANG_VIEN", length = 1)
    private String loaiGiangVien;

    @NotEmpty
    @Column(name = "HO_TEN", length = 50)
    private String hoTen;

    @Column(name = "GIOI_TINH", length = 1)
    private String gioiTinh;

    @Column(name = "SO_DIEN_THOAI", length = 15)
    private String soDienThoai;

    @Column(name = "NGAY_SINH")
    private Date ngaySinh;

    @Column(name = "EMAIL_SHP", length = 30)
    private String emailShp;

    @Column(name = "EMAIL", length = 30)
    private String email;

    @Column(name = "CMND", length = 20)
    private String cmnd;

    @Column(name = "NGAY_CAP_CMND")
    private Date ngayCapCmnd;

    @Column(name = "NOI_CAP_CMND", length = 50)
    private String noiCapCmnd;

    @Column(name = "MA_TON_GIAO", length = 10)
    private String maTonGiao;

    @OneToOne
    @JoinColumn(name = "MA_TON_GIAO",referencedColumnName = "ma", updatable = false, insertable = false)
    private TonGiao tonGiao;

    @Column(name = "MA_DAN_TOC", length = 10)
    private String maDanToc;

    @OneToOne
    @JoinColumn(name = "MA_DAN_TOC",referencedColumnName = "ma", updatable = false, insertable = false)
    private DanToc danToc;

    @Column(name = "MA_BO_MON")
    private String maBoMon;

    @OneToOne
    @JoinColumn(name = "MA_BO_MON",referencedColumnName = "mabomon", updatable = false, insertable = false)
    private BoMon boMon;

    @Column(name = "MA_PHAN_HE")
    private String maPhanHe;

    @OneToOne
    @JoinColumn(name = "MA_PHAN_HE",referencedColumnName = "ma", updatable = false, insertable = false)
    private PhanHe phanHe;

    @Column(name = "MA_HOC_VI")
    private String maHocVi;

    @OneToOne
    @JoinColumn(name = "MA_HOC_VI",referencedColumnName = "ma", updatable = false, insertable = false)
    private HocVi hocVi;

    @Column(name = "MA_CHUYEN_NGANH")
    private String maChuyenNganh;

    @OneToOne
    @JoinColumn(name = "MA_CHUYEN_NGANH",referencedColumnName = "machuyennganh", updatable = false, insertable = false)
    private ChuyenNganh chuyenNganh;

    @Column(name = "CHUYEN_MGANH_KHAC", length = 255)
    private String chuyenNganhKhac;

    @Column(name = "NGOAI_NGU_1", length = 255)
    private String ngoaiNgu1;

    @Column(name = "NGOAI_NGU_2", length = 255)
    private String ngoaiNgu2;

    @Column(name = "TRANG_THAI_DAY", length = 30)
    private String trangThaiDay;

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

    @Column(name = "CA_HOC_T2", length = 250)
    private String caHocT2;

    @Column(name = "CA_HOC_T3", length = 250)
    private String caHocT3;

    @Column(name = "CA_HOC_T4", length = 250)
    private String caHocT4;

    @Column(name = "CA_HOC_T5", length = 250)
    private String caHocT5;

    @Column(name = "CA_HOC_T6", length = 250)
    private String caHocT6;

    @Column(name = "CA_HOC_T7", length = 250)
    private String caHocT7;

    @Column(name = "CA_HOC_CN", length = 250)
    private String caHocCn;

    @Column(name = "NGAY_TAO")
    private Date ngayTao;

    @Column(name = "NGUOI_TAO",length = 50)
    private String nguoiTao;

    @Column(name = "NGAY_CAP_NHAT")
    private Date ngayCapNhat;

    @Column(name = "NGUOI_CAP_NHAP",length = 50)
    private String nguoiCapNhat;

}
