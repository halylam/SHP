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
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "lophoc", uniqueConstraints=@UniqueConstraint(columnNames="malophoc"))
@Data
@Audited
public class LopHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lophoc_ID_SEQ")
	@SequenceGenerator(name = "lophoc_ID_SEQ", sequenceName = "lophoc_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long lopHocId;
	
	@NotEmpty
	@Column(name = "tenlophoc")
	private String lopHocName;
	
	@NotEmpty
	@Column(name = "malophoc")
	private String lopHocCode;

	@NotNull
	@Column(name = "succhua")
	private int sucChua;

	@NotNull
	@Column(name = "soluonghv", columnDefinition="INT(10) DEFAULT 0")
	private int soLuongHV = 0;

	@ManyToOne
	@JoinColumn(name = "id_loailophoc")
	private LoaiLopHoc loaiLopHoc;

	@ManyToOne
	@JoinColumn(name = "id_khoahoc")
	private KhoaHoc khoaHoc;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
