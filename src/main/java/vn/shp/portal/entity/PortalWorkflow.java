//package vn.vccb.portal.entity;
//
//import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "PORTAL_WORKFLOW", uniqueConstraints=@UniqueConstraint(columnNames="CODE"))
//@Data
//public class PortalWorkflow {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "WORKFLOW_ID_SEQ")
//	@SequenceGenerator(name = "WORKFLOW_ID_SEQ", sequenceName = "PORTAL_WORKFLOW_SEQ", allocationSize = 1)
//	@Column(name = "WORKFLOW_ID")
//	private Long id;
//
//	@NotEmpty
//	@Column(name = "CODE")
//	private String code;
//
//	@NotEmpty
//	@Column(name = "NAME")
//	private String name;
//
//	@Column(name = "LINK")
//	private String link;
//
//}
