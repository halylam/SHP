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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "khoahoc", uniqueConstraints=@UniqueConstraint(columnNames="makhoahoc"))
@Data
@Audited
public class KhoaHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "khoahoc_ID_SEQ")
	@SequenceGenerator(name = "khoahoc_ID_SEQ", sequenceName = "khoahoc_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long khoaHocId;
	
	@NotEmpty
	@Column(name = "tenkhoahoc")
	private String khoaHocName;
	
	@NotEmpty
	@Column(name = "makhoahoc")
	private String khoaHocCode;

	@NotNull
	@Column(name = "thoigiantu")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date timeFrom;

	@NotNull
	@Column(name = "thoigianden")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date timeTo;

	@ManyToOne
	@JoinColumn(name = "id_bacdaotao")
	private BacDaoTao bacDaoTao;


	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
