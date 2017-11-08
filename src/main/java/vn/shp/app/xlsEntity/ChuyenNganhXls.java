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
@Excel(name = "Danh Sach Chuyen Nganh", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class ChuyenNganhXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã chuyên ngành", export = true, index = 1)
	private String chuyenNganhCode;

	@EColumn(name = "Tên chuyên ngành", export = true, index = 2)
	private String chuyenNganhName;

	@EColumn(name = "Tên chương trình đào tạo", export = true, index = 3)
	private String ctdtName;
}
