package vn.shp.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HV_KH")
@Data
public class HocVienDk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HV_KH_ID_SEQ")
    @SequenceGenerator(name = "HV_KH_ID_SEQ", sequenceName = "HV_KH_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MA_HV_KH", referencedColumnName = "MA_HOC_VIEN", insertable = false, updatable = false)
    private HocVien hocVien;

    @ManyToOne
    @JoinColumn(name = "MA_KHOA_HOC", referencedColumnName = "makhoahoc", insertable = false, updatable = false)
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "MA_LOP_HOC", referencedColumnName = "malophoc", insertable = false, updatable = false)
    private LopHoc lopHoc;

    @Column(name = "TRANG_THAI")
    private String trangThai;

    @Column(name = "NGAY_TAO")
    private Date ngayTao;

    @Column(name = "NGUOI_TAO",length = 50)
    private String nguoiTao;

    @Column(name = "NGAY_CAP_NHAT")
    private Date ngayCapNhat;

    @Column(name = "NGUOI_CAP_NHAP",length = 50)
    private String nguoiCapNhat;
}
