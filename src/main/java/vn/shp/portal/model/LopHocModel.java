package vn.shp.portal.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.entity.LopHocHocVien;

@Setter
@Getter
public class LopHocModel extends AbstractModel<LopHoc> {

	@Getter
	@Setter
	private LopHocHocVien lhhv;

	@Getter
	@Setter
	private List<LopHocHocVien> listLhhv;
}
