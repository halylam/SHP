//package vn.vccb.portal.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "PORTAL_WORKFLOW_MENU")
//@Data
//public class PortalWorkflowMenu {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "WORKFLOW_MENU_ID_SEQ")
//	@SequenceGenerator(name = "WORKFLOW_MENU_ID_SEQ", sequenceName = "PORTAL_WORKFLOW_SEQ", allocationSize = 1)
//	@Column(name = "WORKFLOW_MENU_ID")
//	private Long id;
//
//	@Column(name = "NAME")
//	private String name;
//
//	@Column(name = "LINK")
//	private String link;
//
//	@Column(name = "REMARK")
//	private Integer remark;
//
//	@Column(name = "ROLES")
//	private String roles;
//}
