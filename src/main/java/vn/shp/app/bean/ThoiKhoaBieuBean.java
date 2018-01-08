package vn.shp.app.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.KhoaHocMonHoc;
import vn.shp.app.entity.ThoiKhoaBieu;

@Setter
@Getter
public class ThoiKhoaBieuBean extends AbstractBean<ThoiKhoaBieu> {

	@Setter
	@Getter
	private List<KhoaHocMonHoc> listKhmh;
}
