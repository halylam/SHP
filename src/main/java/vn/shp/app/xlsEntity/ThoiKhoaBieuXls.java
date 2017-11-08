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
@Excel(name = "Danh Sach Thoi Khoa Bieu", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class ThoiKhoaBieuXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã môn học", export = true, index = 1)
	private String monHocCode;

	@EColumn(name = "Tên môn học", export = true, index = 2)
	private String monHocName;

	@EColumn(name = "Tổng giờ học", export = true, index = 3)
	private String tongGioHoc;

	@EColumn(name = "Thứ", export = true, index = 4)
	private String thu;

	@EColumn(name = "Ca", export = true, index = 5)
	private String ca;

	@EColumn(name = "Lớp", export = true, index = 6)
	private String lop;

	@EColumn(name = "Giảng Viên", export = true, index = 7)
	private String giangVien;

	@EColumn(name = "Lặp", export = true, index = 8)
	private String lap;
}
