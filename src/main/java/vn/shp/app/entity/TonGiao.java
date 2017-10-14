package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tongiao", uniqueConstraints=@UniqueConstraint(columnNames="id"))
@Data
public class TonGiao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tongiao_ID_SEQ")
	@SequenceGenerator(name = "tongiao_ID_SEQ", sequenceName = "tongiao_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long tonGiaoId;
	
	@NotEmpty
	@Column(name = "ten")
	private String tonGiaoName;

	@NotEmpty
	@Column(name = "ma")
	private String tonGiaoCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;

}
