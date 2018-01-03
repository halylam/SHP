//package vn.shp.portal.entity;
//
//import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "PORTAL_SYSTEM_GROUP", uniqueConstraints=@UniqueConstraint(columnNames="GROUP_CODE"))
//@Data
//public class PortalSystemGroup {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GROUP_ID_SEQ")
//	@SequenceGenerator(name = "GROUP_ID_SEQ", sequenceName = "PORTAL_GROUP_SEQ", allocationSize = 1)
//	@Column(name = "GROUP_ID")
//	private Long groupId;
//
//	@Column(name = "STATUS", length = 1)
//	private String status;
//
//	@NotEmpty
//	@Column(name = "GROUP_CODE", length = 255)
//	private String groupCode;
//
//	@NotEmpty
//	@Column(name = "GROUP_NAME", length = 100)
//	private String groupName;
//
//	@Column(name = "REMARK", length = 255)
//	private String remark;
//
//	@OneToMany(cascade = { CascadeType.ALL })
//	@JoinColumn(name = "GROUP_ID", updatable = false, insertable = true)
//	private List<PortalSystem> systemLst;
//
//}
