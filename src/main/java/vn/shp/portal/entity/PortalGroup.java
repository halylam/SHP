package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PORTAL_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "GROUP_NAME"))
@Data
public class PortalGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PORTAL_GROUP_ID_SEQ")
	@SequenceGenerator(name = "PORTAL_GROUP_ID_SEQ", sequenceName = "PORTAL_GROUP_SEQ", allocationSize = 1)
	@Column(name = "GROUP_ID")
	private Long groupId;

	@Column(name = "GROUP_KEYCLOAK_ID")
	private String groupKeycloakId;
	
	@NotEmpty
	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "NOTES")
	private String notes;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PORTAL_GROUP_ROLE", joinColumns = {
			@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
	private List<PortalRole> roleGroupLst;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
	private List<PortalUser> users;

	@Transient
	private String codeName;
	
}
