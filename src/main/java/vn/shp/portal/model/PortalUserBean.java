package vn.shp.portal.model;

import lombok.Getter;
import lombok.Setter;
import vn.shp.portal.entity.*;

import java.util.List;

@Setter
@Getter
public class PortalUserBean extends AbstractModel<PortalUser> {

	List<PortalGroup> groupLeftLst;
	
	List<PortalGroup> groupRightLst;

	List<PortalUser> userLst;
	
	String email;
	
}
