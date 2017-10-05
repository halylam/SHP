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

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dm_tinh", uniqueConstraints=@UniqueConstraint(columnNames="id_tinh"))
@Data
public class DmTinh implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "dm_tinh_ID_SEQ")
	@SequenceGenerator(name = "dm_tinh_ID_SEQ", sequenceName = "dm_tinh_SEQ", allocationSize = 1)
	@Column(name = "id_tinh")
	private Long dmTinhId;
	
	@NotEmpty
	@Column(name = "ten")
	private String dmTinhName;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_tinh", updatable = false, insertable = true)
	private List<DmQuan> dmQuanList;

}
