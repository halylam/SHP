package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity 
@Table(name = "PORTAL_TITLE", uniqueConstraints=@UniqueConstraint(columnNames="TITLE_CODE"))
@Data
public class PortalTitle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TITLE_ID_SEQ")
	@SequenceGenerator(name = "TITLE_ID_SEQ", sequenceName = "PORTAL_TITLE_SEQ", allocationSize = 1)
	@Column(name = "TITLE_ID")
	private Long titleId;

	@Column(name = "STATUS", length = 1)
	private String status;

	@NotEmpty
	@Column(name = "TITLE_CODE", length = 255)
	private String titleCode;

	@NotEmpty
	@Column(name = "TITLE_NAME", length = 100)
	private String titleName;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "TITLE_ID", updatable = false, insertable = true)
	private List<PortalUser> userLst;
	
}
