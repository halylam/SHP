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
@Excel(name = "Danh Sach Ca Hoc", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class CaHocXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Mã ca học", export = true, index = 1)
	private String caHocCode;

	@EColumn(name = "Tên ca học", export = true, index = 2)
	private String caHocName;

	@EColumn(name = "Loại ca học", export = true, index = 3)
	private String loaiCaHoc;

	@EColumn(name = "Từ giờ", export = true, index = 4)
	private String tuGio;

	@EColumn(name = "Đến giờ", export = true, index = 5)
	private String denGio;

}
