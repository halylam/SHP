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
@Table(name = "chuongtrinhdaotao", uniqueConstraints=@UniqueConstraint(columnNames="machuongtrinh"))
@Data
public class ChuongTrinhDaoTao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "chuongtrinhdaotao_ID_SEQ")
	@SequenceGenerator(name = "chuongtrinhdaotao_ID_SEQ", sequenceName = "chuongtrinhdaotao_SEQ", allocationSize = 1)
	@Column(name = "id")
	private Long chuongTrinhDaoTaoId;
	
	@NotEmpty
	@Column(name = "tenchuongtrinh")
	private String chuongTrinhDaoTaoName;
	
	@NotEmpty
	@Column(name = "machuongtrinh")
	private String chuongTrinhDaoTaoCode;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "id_chuongtrinh", updatable = false, insertable = true)
	private List<ChuyenNganh> chuyenNganhList;

}
