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

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dantoc", uniqueConstraints=@UniqueConstraint(columnNames="id"))
@Data
public class DanToc implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dantoc_ID_SEQ")
	@SequenceGenerator(name = "dantoc_ID_SEQ", sequenceName = "dantoc_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long danTocId;
	
	@NotEmpty
	@Column(name = "ten")
	private String danTocName;

}
