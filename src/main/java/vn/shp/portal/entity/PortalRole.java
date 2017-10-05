package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PORTAL_ROLE", uniqueConstraints=@UniqueConstraint(columnNames="ROLE_CODE"))
@Data
public class PortalRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ROLE_ID_SEQ")
	@SequenceGenerator(name = "ROLE_ID_SEQ", sequenceName = "PORTAL_ROLE_SEQ", allocationSize = 1)
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "ROLE_KEYCLOAK_ID")
	private String roleKeycloakId;
	
	@NotEmpty
	@Column(name = "ROLE_CODE", length = 255)
	private String roleCode;

	@Column(name = "ROLE_NAME", length = 100)
	private String roleName;

	@Column(name = "REMARK", length = 255)
	private String remark;

	@ManyToOne
	@JoinColumn(name = "SYSTEM_ID")
	private PortalSystem system;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleGroupLst")
	private List<PortalGroup> groups;
	
}