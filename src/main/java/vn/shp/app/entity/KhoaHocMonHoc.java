package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "KHOAHOC_MONHOC", uniqueConstraints=@UniqueConstraint(columnNames={"id_khoahoc", "id_monhoc"}))
@Data
@Audited
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
