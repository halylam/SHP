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
@Excel(name = "Danh Sach Mon Hoc", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class MonHocXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã môn học", export = true, index = 1)
	private String monHocCode;

	@EColumn(name = "Tên môn học", export = true, index = 2)
	private String monHocName;

	@EColumn(name = "Tên bộ môn", export = true, index = 3)
	private String tenBoMon;

	@EColumn(name = "Tổng giờ dạy", export = true, index = 4)
	private String tongGioDay;

	@EColumn(name = "Hệ số", export = true, index = 5)
	private String heSo;

}
