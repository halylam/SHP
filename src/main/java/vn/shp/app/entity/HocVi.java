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
@Table(name = "hocvi", uniqueConstraints=@UniqueConstraint(columnNames="ma"))
@Data
public class HocVi implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "hocvi_ID_SEQ")
	@SequenceGenerator(name = "hocvi_ID_SEQ", sequenceName = "hocvi_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long hocViId;

	@NotEmpty
	@Column(name = "ma")
	private String hocViCode;
	
	@NotEmpty
	@Column(name = "ten")
	private String hocViName;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
