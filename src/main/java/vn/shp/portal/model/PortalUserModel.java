package vn.shp.portal.model;

import lombok.Getter;
import lombok.Setter;
import vn.shp.portal.entity.*;

import java.util.List;

@Setter
@Getter
public class PortalUserModel extends AbstractModel<PortalUser> {

	List<PortalGroup> groupLeftLst;
	
	List<PortalGroup> groupRightLst;

	List<PortalBranch> branchLst;
	
	List<PortalUser> userLst;
	
	List<PortalDepartment> departmentLst;

//	List<PortalTitle> titleLst;
	
	String email;
	
}
