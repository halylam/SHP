package vn.shp.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lophoc", uniqueConstraints=@UniqueConstraint(columnNames="malophoc"))
@Data
public class LopHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lophoc_ID_SEQ")
	@SequenceGenerator(name = "lophoc_ID_SEQ", sequenceName = "lophoc_SEQ", allocationSize = 1)
	@Column(name = "id")
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
	@Column(name = "soluonghv")
	private int soLuongHV;

	@NotNull
	@Column(name = "id_loailophoc")
	private LoaiLopHoc loaiLopHoc;

	@NotNull
	@Column(name = "id_khoahoc")
	private KhoaHoc khoaHoc;

}
