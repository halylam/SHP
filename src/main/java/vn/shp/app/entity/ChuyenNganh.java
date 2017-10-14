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
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "chuyennganh", uniqueConstraints=@UniqueConstraint(columnNames="machuyennganh"))
@Data
public class ChuyenNganh implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "chuyennganh_ID_SEQ")
	@SequenceGenerator(name = "chuyennganh_ID_SEQ", sequenceName = "chuyennganh_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long chuyenNganhId;
	
	@NotEmpty
	@Column(name = "tenchuyennganh")
	private String chuyenNganhName;
	
	@NotEmpty
	@Column(name = "machuyennganh")
	private String chuyenNganhCode;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;

	@ManyToOne
	@JoinColumn(name = "id_chuongtrinh")
	private ChuongTrinhDaoTao chuongTrinhDaoTao;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_chuyennganh", updatable = false, insertable = true)
	private List<BacDaoTao> bacDaoTaoList;

}
