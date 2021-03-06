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
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dantoc", uniqueConstraints=@UniqueConstraint(columnNames="ma"))
@Data
@Audited
public class DanToc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dantoc_ID_SEQ")
	@SequenceGenerator(name = "dantoc_ID_SEQ", sequenceName = "dantoc_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long danTocId;
	
	@NotEmpty
	@Column(name = "ten")
	private String danTocName;

	@NotEmpty
	@Column(name = "ma")
	private String danTocCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
