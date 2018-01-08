package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tongiao", uniqueConstraints=@UniqueConstraint(columnNames="ma"))
@Data
@Audited
public class TonGiao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tongiao_ID_SEQ")
	@SequenceGenerator(name = "tongiao_ID_SEQ", sequenceName = "tongiao_SEQ", allocationSize = 1)
	@Column(name = "id", columnDefinition = "INT(10) UNSIGNED")
	private Long tonGiaoId;
	
	@NotEmpty
	@Column(name = "ten")
	private String tonGiaoName;

	@NotEmpty
	@Column(name = "ma")
	private String tonGiaoCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai = true;

}
