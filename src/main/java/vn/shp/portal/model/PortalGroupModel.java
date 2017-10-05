package vn.shp.portal.model;

import lombok.Getter;
import lombok.Setter;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.entity.PortalRole;

import java.util.List;

@Setter
@Getter
public class PortalGroupModel extends AbstractModel<PortalGroup> {

	List<PortalRole> roleLeftLst;
	
	List<PortalRole> roleRightLst;
	
}
