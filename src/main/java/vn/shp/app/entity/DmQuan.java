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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dm_quan", uniqueConstraints=@UniqueConstraint(columnNames="id"))
@Data
public class DmQuan implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dm_quan_ID_SEQ")
	@SequenceGenerator(name = "dm_quan_ID_SEQ", sequenceName = "dm_quan_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long dmQuanId;
	
	@NotEmpty
	@Column(name = "ten")
	private String dmQuanName;

	@ManyToOne
	@JoinColumn(name = "id_tinh")
	private DmTinh dmTinh;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_quan", updatable = false, insertable = true)
	private List<DmXa> dmXaList;

}
