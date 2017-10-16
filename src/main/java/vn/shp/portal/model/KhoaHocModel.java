package vn.shp.portal.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.app.entity.KhoaHocMonHoc;

@Setter
@Getter
public class KhoaHocModel extends AbstractModel<KhoaHoc> {
	@Getter
	@Setter
	private KhoaHocMonHoc khmh;

	@Getter
	@Setter
	private List<KhoaHocMonHoc> listKhmh;

//	List<PortalGroup> groupLeftLst;
//
//	List<PortalGroup> groupRightLst;
}
