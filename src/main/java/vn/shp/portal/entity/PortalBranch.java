package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PORTAL_BRANCH", uniqueConstraints=@UniqueConstraint(columnNames="BRANCH_CODE"))
@Data
public class PortalBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BRANCH_ID_SEQ")
	@SequenceGenerator(name = "BRANCH_ID_SEQ", sequenceName = "PORTAL_BRANCH_SEQ", allocationSize = 1)
	@Column(name = "BRANCH_ID")
	private Long branchId;
	
	@NotEmpty
	@Column(name = "BRANCH_NAME")
	private String branchName;
	
	@NotEmpty
	@Column(name = "BRANCH_CODE")
	private String branchCode;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "BRANCH_ID", updatable = false, insertable = true)
	private List<PortalUser> userLst;

}
