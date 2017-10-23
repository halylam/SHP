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
@Table(name = "chuyenmon", uniqueConstraints=@UniqueConstraint(columnNames="ma"))
@Data
public class ChuyenMon implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "chuyenmon_ID_SEQ")
	@SequenceGenerator(name = "chuyenmon_ID_SEQ", sequenceName = "chuyenmon_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long chuyenMonId;

	@NotEmpty
	@Column(name = "ma")
	private String chuyenMonCode;

	@NotEmpty
	@Column(name = "ten")
	private String chuyenMonName;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
