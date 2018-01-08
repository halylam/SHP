package vn.shp.app.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.LopHoc;
import vn.shp.app.entity.LopHocHocVien;

@Setter
@Getter
public class LopHocBean extends AbstractBean<LopHoc> {

	@Getter
	@Setter
	private LopHocHocVien lhhv;

	@Getter
	@Setter
	private List<LopHocHocVien> listLhhv;
}
