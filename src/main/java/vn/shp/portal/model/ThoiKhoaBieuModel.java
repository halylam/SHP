package vn.shp.portal.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.app.entity.ThoiKhoaBieu;

@Setter
@Getter
public class ThoiKhoaBieuModel extends AbstractModel<ThoiKhoaBieu> {

	@Setter
	@Getter
	private List<KhoaHocMonHoc> listKhmh;
}
