package vn.shp.app.entity;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "cahoc", uniqueConstraints=@UniqueConstraint(columnNames="macahoc"))
@Data
public class CaHoc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cahoc_ID_SEQ")
	@SequenceGenerator(name = "cahoc_ID_SEQ", sequenceName = "cahoc_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long caHocId;
	
	@NotEmpty
	@Column(name = "tencahoc")
	private String caHocName;
	
	@NotEmpty
	@Column(name = "macahoc")
	private String caHocCode;

	@NotEmpty
	@Column(name = "tugio")
	private Time tuGio;

	@NotEmpty
	@Column(name = "dengio")
	private Time denGio;

	@NotEmpty
	@Column(name = "loaica")
	private String loaiCa;


}
