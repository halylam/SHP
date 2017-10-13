package vn.shp.app.entity;

import java.io.Serializable;
import java.util.Date;

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
import org.springframework.format.annotation.DateTimeFormat;

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

	@NotNull
	@Column(name = "tugio")
	@DateTimeFormat(pattern="hh:mm a")
	private Date tuGio;

	@NotNull
	@Column(name = "dengio")
	@DateTimeFormat(pattern="hh:mm a")
	private Date denGio;

	@NotEmpty
	@Column(name = "loaica")
	private String loaiCa;


}
