package vn.shp.app.xlsEntity;

/**
 * Created by Lam_D on 07/11/2017.
 */

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import vn.hcm.mcr35.excel.annotation.EColumn;
import vn.hcm.mcr35.excel.annotation.Excel;

@Data
@Excel(name = "Danh Sach Khoa Hoc", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class KhoaHocXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã khóa học", export = true, index = 1)
	private String khoaHocCode;

	@EColumn(name = "Tên khóa học", export = true, index = 2)
	private String khoaHocName;

	@EColumn(name = "Tên ctdt", export = true, index = 3)
	private String ctdtName;

	@EColumn(name = "Tên chuyên ngành", export = true, index = 4)
	private String chuyenNganhName;

	@EColumn(name = "Tên bậc đào tạo", export = true, index = 5)
	private String bacDaoTaoName;

	@EColumn(name = "Bắt đầu", export = true, index = 6)
	private String batDau;

	@EColumn(name = "Kết thúc", export = true, index = 7)
	private String ketThuc;
}
