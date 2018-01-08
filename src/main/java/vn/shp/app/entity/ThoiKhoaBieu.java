package vn.shp.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "thoikhoabieu")
@Data
@Audited
public class ThoiKhoaBieu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "thoikhoabieu_ID_SEQ")
    @SequenceGenerator(name = "thoikhoabieu_ID_SEQ", sequenceName = "thoikhoabieu_SEQ", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "INT(10) UNSIGNED")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_khoahoc", columnDefinition = "INT(10) UNSIGNED")
    private KhoaHoc khoaHoc;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_monhoc", columnDefinition = "INT(10) UNSIGNED")
    private MonHoc monHoc;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cahoc", columnDefinition = "INT(10) UNSIGNED")
    private CaHoc caHoc;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_lophoc", columnDefinition = "INT(10) UNSIGNED")
    private LopHoc lopHoc;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_giangvien", columnDefinition = "BIGINT(20)")
    private GiangVien giangVien;

    @NotNull
    @Column(name = "tungay")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date tuNgay;

    @NotNull
    @Column(name = "denngay")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date denNgay;

    @NotEmpty
    @Column(name = "thu")
    private String thu;

    @NotEmpty
    @Column(name = "laplai")
    private String lapLai;

    
}
