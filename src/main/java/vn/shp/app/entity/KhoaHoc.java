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

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "khoahoc", uniqueConstraints=@UniqueConstraint(columnNames="makhoahoc"))
@Data
public class KhoaHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "khoahoc_ID_SEQ")
	@SequenceGenerator(name = "khoahoc_ID_SEQ", sequenceName = "khoahoc_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long khoaHocId;
	
	@NotEmpty
	@Column(name = "tenkhoahoc")
	private String khoaHocName;
	
	@NotEmpty
	@Column(name = "makhoahoc")
	private String khoaHocCode;

	@NotEmpty
	@Column(name = "thoigiantu")
	private Date Timefrom;

	@NotEmpty
	@Column(name = "thoigianden")
	private Date TimeTo;

	@ManyToOne
	@JoinColumn(name = "id_bacdaotao")
	private BacDaoTao bacDaoTao;

}