package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "cahoc", uniqueConstraints=@UniqueConstraint(columnNames="macahoc"))
@Data
@Audited
public class CaHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cahoc_ID_SEQ")
	@SequenceGenerator(name = "cahoc_ID_SEQ", sequenceName = "cahoc_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long caHocId;
	
	@NotEmpty
	@Column(name = "tencahoc")
	private String caHocName;
	
	@NotEmpty
	@Column(name = "macahoc")
	private String caHocCode;

	@NotEmpty
	@Column(name = "tugio")
	private String tuGio;

	@NotEmpty
	@Column(name = "dengio")
	private String denGio;

	@NotEmpty
	@Column(name = "loaica")
	private String loaiCa;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;


}
