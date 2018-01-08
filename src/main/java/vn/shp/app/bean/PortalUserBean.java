package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.PortalGroup;
import vn.shp.app.entity.PortalUser;

import java.util.List;

@Setter
@Getter
public class PortalUserBean extends AbstractBean<PortalUser> {

	List<PortalGroup> groupLeftLst;
	
	List<PortalGroup> groupRightLst;

	List<PortalUser> userLst;
	
	String email;
	
}
