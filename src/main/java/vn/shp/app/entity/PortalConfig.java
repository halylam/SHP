package vn.shp.app.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PORTAL_CONFIG" ,
		uniqueConstraints = @UniqueConstraint(columnNames = {"CONFIG_NAME", "SYSTEM"}))
@Data
public class PortalConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PORTAL_CONFIG_ID_SEQ")
	@SequenceGenerator(name = "PORTAL_CONFIG_ID_SEQ", sequenceName = "PORTAL_CONFIG_SEQ", allocationSize = 1)
	@Column(name = "CONFIG_ID")
	private Long id;

	@Column(name = "STATUS", length = 1)
	private String status;

	@NotEmpty
	@Column(name = "CONFIG_NAME")
	private String configName;
	
	@NotEmpty
	@Column(name = "CONFIG_VALUE")
	private String configValue;

	@NotEmpty
	@Column(name = "SYSTEM", length = 100)
	private String system;

	@NotEmpty
	@Column(name = "ENABLE", length = 1)
	private String enable;

	@Column(name = "EXPIRED_DATE")
	private Date expiredDate;

	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;


}
