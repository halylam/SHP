package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "phanhe", uniqueConstraints=@UniqueConstraint(columnNames="ma"))
@Data
@Audited
public class PhanHe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "phanhe_ID_SEQ")
	@SequenceGenerator(name = "phanhe_ID_SEQ", sequenceName = "phanhe_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long phanHeId;
	
	@NotEmpty
	@Column(name = "ten")
	private String phanHeName;

	@NotEmpty
	@Column(name = "ma")
	private String phanHeCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
