package vn.shp.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dm_xa", uniqueConstraints=@UniqueConstraint(columnNames="id_xa"))
@Data
public class DmXa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dm_xa_ID_SEQ")
	@SequenceGenerator(name = "dm_xa_ID_SEQ", sequenceName = "dm_xa_SEQ", allocationSize = 1)
	@Column(name = "id_xa")
	private Long dmXaId;
	
	@NotEmpty
	@Column(name = "ten")
	private String dmXaName;

	@ManyToOne
	@JoinColumn(name = "id_quan")
	private DmQuan dmQuan;

}
