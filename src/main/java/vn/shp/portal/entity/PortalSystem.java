//package vn.shp.portal.entity;
//
//import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "PORTAL_SYSTEM", uniqueConstraints=@UniqueConstraint(columnNames="SYSTEM_CODE"))
//@Data
//public class PortalSystem{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SYSTEM_ID_SEQ")
//	@SequenceGenerator(name = "SYSTEM_ID_SEQ", sequenceName = "PORTAL_SYSTEM_SEQ", allocationSize = 1)
//	@Column(name = "SYSTEM_ID")
//	private Long systemId;
//
//	@NotEmpty
//	@Column(name = "SYSTEM_NAME", length = 50)
//	private String systemName;
//
//	@NotEmpty
//	@Column(name = "SYSTEM_CODE")
//	private String systemCode;
//
//	@Column(name = "URL", length = 500)
//	private String url;
//
//	@Column(name = "PARAM", length = 100)
//	private String param;
//
//	@Column(name = "STATUS", length = 1)
//	private String status;
//
//	@Column(name = "BROWSER", length = 255)
//	private String browser;
//
//	@Column(name = "REMARK", length = 255)
//	private String remark;
//
//	@Lob @Basic(fetch = FetchType.LAZY)
//	@Column(name = "ICON")
//	private String icon;
//
//	@OneToMany(cascade = { CascadeType.ALL })
//	@JoinColumn(name = "SYSTEM_ID", updatable = false, insertable = true)
//	private List<PortalRole> roleLst;
//
//	@ManyToOne
//	@JoinColumn(name = "GROUP_ID")
//	private PortalSystemGroup group;
//
//}
