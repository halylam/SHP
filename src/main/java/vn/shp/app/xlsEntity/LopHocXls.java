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
@Excel(name = "Danh Sach Lop Hoc", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class LopHocXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã lớp học", export = true, index = 1)
	private String lopHocCode;

	@EColumn(name = "Tên lớp học", export = true, index = 2)
	private String lopHocName;

	@EColumn(name = "Loại lớp học", export = true, index = 3)
	private String loaiLopHoc;

	@EColumn(name = "Số HV đang học", export = true, index = 4)
	private String soHv;

	@EColumn(name = "Sức chứa", export = true, index = 5)
	private String sucChua;

	@EColumn(name = "Khóa học", export = true, index = 6)
	private String khoaHocName;

	@EColumn(name = "Bắt đầu", export = true, index = 7)
	private String batDau;

	@EColumn(name = "Kết thúc", export = true, index = 8)
	private String ketThuc;
}
