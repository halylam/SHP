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

@Entity
@Table(name = "KHOAHOC_MONHOC", uniqueConstraints=@UniqueConstraint(columnNames={"id_khoahoc", "id_monhoc"}))
@Data
public class KhoaHocMonHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "KHOAHOC_MONHOC_ID_SEQ")
    @SequenceGenerator(name = "KHOAHOC_MONHOC_ID_SEQ", sequenceName = "KHOAHOC_MONHOC_SEQ", allocationSize = 1)
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
    
}
