package vn.shp.app.entity;

import java.io.Serializable;

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

@Entity
@Table(name = "LOPHOC_HOCVIEN", uniqueConstraints=@UniqueConstraint(columnNames={"id_lophoc", "id_hocvien"}))
@Data
public class LopHocHocVien implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LOPHOC_HOCVIEN_ID_SEQ")
    @SequenceGenerator(name = "LOPHOC_HOCVIEN_ID_SEQ", sequenceName = "LOPHOC_HOCVIEN_SEQ", allocationSize = 1)
    @Column(name = "ID", columnDefinition = "INT(10) UNSIGNED")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_lophoc", columnDefinition = "INT(10) UNSIGNED")
    private LopHoc lopHoc;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_hocvien", columnDefinition = "BIGINT(20)")
    private HocVien hocVien;
    
}
