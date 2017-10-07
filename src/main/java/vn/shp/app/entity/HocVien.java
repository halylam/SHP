package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HOC_VIEN", uniqueConstraints=@UniqueConstraint(columnNames="CMND"))
@Data
public class HocVien implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HOC_VIEN_ID_SEQ")
    @SequenceGenerator(name = "HOC_VIEN_ID_SEQ", sequenceName = "HOC_VIEN_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

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





}
