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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "monhoc", uniqueConstraints=@UniqueConstraint(columnNames="mamon"))
@Data
public class MonHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "monhoc_ID_SEQ")
	@SequenceGenerator(name = "monhoc_ID_SEQ", sequenceName = "monhoc_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long monHocId;
	
	@NotEmpty
	@Column(name = "tenmon")
	private String monHocName;
	
	@NotEmpty
	@Column(name = "mamon")
	private String monHocCode;

	@NotNull
	@Column(name = "tonggioday")
	private int tongGioDay;

	@NotNull
	@Column(name = "heso")
	private int heSo;

	@ManyToOne
	@JoinColumn(name = "id_bomon")
	private BoMon boMon;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;

}
