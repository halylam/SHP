package vn.shp.app.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "KN_LAM_VIEC")
@Data
public class KinhNghiemLamViec implements Serializable {

    public KinhNghiemLamViec(){}

    public KinhNghiemLamViec(Long maLienKet){
        this.maLienKet = maLienKet;
    }

    public KinhNghiemLamViec(String viTri, String tenCongTy, Date tuNgay, Date denNgay, String diaChi){
        this.viTri = viTri;
        this.tenCongTy = tenCongTy;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.diaChi = diaChi;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "KN_LAM_VIEC_ID_SEQ")
    @SequenceGenerator(name = "KN_LAM_VIEC_ID_SEQ", sequenceName = "KN_LAM_VIEC_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VI_TRI", length = 250)
    private String viTri;

    @Column(name = "TEN_CONG_TY", length = 250)
    private String tenCongTy;

    @Column(name = "TU_NGAY")
    private Date tuNgay;

    @Column(name = "DEN_NGAY")
    private Date denNgay;

    @Column(name = "DIA_CHI", length = 300)
    private String diaChi;

    @NotNull
    @Column(name = "MA_LIEN_KET")
    private Long maLienKet;

    @NotNull
    @Column(name = "LOAI_LIEN_KET", length = 10)
    private String loaiLienKet;

    @Column(name = "NGAY_TAO")
    private Date ngayTao;

    @Column(name = "NGUOI_TAO",length = 50)
    private String nguoiTao;
    
}
