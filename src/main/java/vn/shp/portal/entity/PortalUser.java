package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PORTAL_USER", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@Data
@Audited
public class PortalUser implements Serializable {

	public PortalUser(){}

	public PortalUser(String userName, String pass){
		this.username = userName;
		this.password = pass;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ID_SEQ")
	@SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "PORTAL_USER_SEQ", allocationSize = 1)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "EMAIL", length = 255)
	private String email;

	@Column(name = "ENABLED")
	private Boolean enabled;

	@Column(name = "PASSWORD", length = 128)
	private String password;

	@NotEmpty
	@Column(name = "USERNAME")
	private String username;


	@Column(name = "MOBILE", length = 255)
	private String mobile;

	@Column(name = "BIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "FULLNAME", length = 255)
	private String fullName;

	@Column(name = "IPADDRESS", length = 255)
	private String ipAddress;

	@Column(name = "LAST_LOGIN_DATE")
	private Date lastLoginDate;

	@Transient
	private String password1;

	@Transient
	private String password2;

	@Autowired
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PORTAL_USER_GROUP", joinColumns = {
			@JoinColumn(name = "USERNAME", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) })
	private List<PortalGroup> groups;

	@Transient
	private boolean currentActive;

	@Transient
	private String sessionId;

	@Transient
	private Date lastAction;

	@Column(name = "USER_CREATED", length = 100)
	private String userCreated;

	@Column(name = "TIME_CREATED")
	private Date timeCreated;


}
