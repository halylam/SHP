package vn.shp.portal.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity 
@Table(name = "PORTAL_DEPARTMENT", uniqueConstraints=@UniqueConstraint(columnNames="DEPARTMENT_CODE"))
@Data
public class PortalDepartment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "DEPARTMENT_ID_SEQ")
	@SequenceGenerator(name = "DEPARTMENT_ID_SEQ", sequenceName = "PORTAL_DEPARTMENT_SEQ", allocationSize = 1)
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;

	@Column(name = "STATUS", length = 1)
	private String status;

	@NotEmpty
	@Column(name = "DEPARTMENT_CODE", length = 255)
	private String departmentCode;

	@NotEmpty
	@Column(name = "DEPARTMENT_NAME", length = 100)
	private String departmentName;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "DEPARTMENT_ID", updatable = false, insertable = true)
	private List<PortalUser> userLst;
	

	@Column(name = "MANAGER")
	private String manager;
	

	@Column(name = "VICE_DIRECTOR")
	private String viceDirector;
	

	@Column(name = "GROUP_ROLE_ID")
	private Long groupRoleId;

	//hant1
	@Column(name = "TRANS_CODE", length = 3)
	private String transCode;

	@Column(name = "ADDRESS1", length = 150)
	private String address1;

	@Column(name = "ADDRESS2", length = 150)
	private String address2;

	@Column(name = "ADDRESS3", length = 150)
	private String address3;

	@Column(name = "ADDRESS4", length = 150)
	private String address4;

	@Column(name = "PHONE_NUMBER", length = 12)
	private String phoneNumber;

	@Column(name = "PARENT_CODE", length = 3)
	private String parentCode;

	@Column(name = "DEPARTMENT_TYPE", length = 3)
	private String departmentType;
	//end
}
