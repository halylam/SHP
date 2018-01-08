package vn.shp.app.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KhoaHoc;
import vn.shp.app.entity.KhoaHocMonHoc;

@Setter
@Getter
public class KhoaHocBean extends AbstractBean<KhoaHoc> {
	@Getter
	@Setter
	private KhoaHocMonHoc khmh;

	@Getter
	@Setter
	private List<KhoaHocMonHoc> listKhmh;

	private List<KhoaHoc> lstKhoaHocDangKy;

	private List<KhoaHoc> lstKhoaHocSv;

	private String lstMaKhoaHocDangKy;

	private HocVien hocVien;

//	List<PortalGroup> groupLeftLst;
//
//	List<PortalGroup> groupRightLst;
}
