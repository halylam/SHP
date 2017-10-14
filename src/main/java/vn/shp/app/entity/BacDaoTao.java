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
@Table(name = "bacdaotao", uniqueConstraints=@UniqueConstraint(columnNames="mabac"))
@Data
public class BacDaoTao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bacdaotao_ID_SEQ")
	@SequenceGenerator(name = "bacdaotao_ID_SEQ", sequenceName = "bacdaotao_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long bacDaoTaoId;
	
	@NotEmpty
	@Column(name = "tenbac")
	private String bacDaoTaoName;
	
	@NotEmpty
	@Column(name = "mabac")
	private String bacDaoTaoCode;

	@ManyToOne
	@JoinColumn(name = "id_chuyennganh")
	private ChuyenNganh chuyenNganh;

	@NotNull
	@Column(name = "trangthai", columnDefinition="BIT DEFAULT 1", length = 1)
	private boolean trangThai;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_bacdaotao", updatable = false, insertable = true)
	private List<KhoaHoc> khoaHocList;

}
