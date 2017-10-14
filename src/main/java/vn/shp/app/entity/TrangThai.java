package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "trangthai", uniqueConstraints=@UniqueConstraint(columnNames="id"))
@Data
public class TrangThai implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "trangthai_ID_SEQ")
	@SequenceGenerator(name = "trangthai_ID_SEQ", sequenceName = "trangthai_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long trangThaiId;
	
	@NotEmpty
	@Column(name = "ten")
	private String trangThaiName;

	@NotEmpty
	@Column(name = "ma")
	private String trangThaiCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;

}
