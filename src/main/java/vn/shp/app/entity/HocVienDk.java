package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HV_KH", uniqueConstraints=@UniqueConstraint(columnNames={"MA_HOC_VIEN", "MA_KHOA_HOC"}))
@Data
@Audited
public class HocVienDk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HV_KH_ID_SEQ")
    @SequenceGenerator(name = "HV_KH_ID_SEQ", sequenceName = "HV_KH_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "MA_HOC_VIEN", referencedColumnName = "MA_HOC_VIEN")
    private HocVien hocVien;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "MA_KHOA_HOC", referencedColumnName = "makhoahoc")
    private KhoaHoc khoaHoc;

    @ManyToOne
    @JoinColumn(name = "MA_LOP_HOC", referencedColumnName = "malophoc")
    private LopHoc lopHoc;

    @Column(name = "TRANG_THAI")
    @NotNull
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
