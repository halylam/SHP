package vn.shp.portal.model;

import lombok.Getter;
import lombok.Setter;
import vn.shp.portal.entity.PortalBranch;
import vn.shp.portal.entity.PortalGroup;

import java.util.List;

@Setter
@Getter
public class PortalBranchModel extends AbstractModel<PortalBranch> {

	List<PortalGroup> groupLeftLst;
	
	List<PortalGroup> groupRightLst;
}
