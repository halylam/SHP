package vn.shp.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "bomon", uniqueConstraints=@UniqueConstraint(columnNames="mabomon"))
@Data
public class BoMon implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bomon_ID_SEQ")
	@SequenceGenerator(name = "bomon_ID_SEQ", sequenceName = "bomon_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long boMonId;
	
	@NotEmpty
	@Column(name = "tenbomon")
	private String boMonName;
	
	@NotEmpty
	@Column(name = "mabomon")
	private String boMonCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="TINYINT(1) DEFAULT 1")
	private boolean trangThai;
}
