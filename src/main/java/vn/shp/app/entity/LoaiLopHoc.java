package vn.shp.app.entity;

import java.io.Serializable;

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

@Entity
@Table(name = "loailophoc", uniqueConstraints=@UniqueConstraint(columnNames="maloai"))
@Data
public class LoaiLopHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "loailophoc_ID_SEQ")
	@SequenceGenerator(name = "loailophoc_ID_SEQ", sequenceName = "loailophoc_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long loaiLopHocId;
	
	@NotEmpty
	@Column(name = "tenloai")
	private String loaiLopHocName;
	
	@NotEmpty
	@Column(name = "maloai")
	private String loaiLopHocCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;
}
