package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.PortalGroup;
import vn.shp.app.entity.PortalRole;

import java.util.List;

@Setter
@Getter
public class PortalGroupBean extends AbstractBean<PortalGroup> {

	List<PortalRole> roleLeftLst;
	
	List<PortalRole> roleRightLst;
	
}
