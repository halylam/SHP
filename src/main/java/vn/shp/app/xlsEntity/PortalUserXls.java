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
@Excel(name = "Danh Sach User", autowidth = true, sortByIndex = true, selExport = true)
@Setter
@Getter
public class PortalUserXls implements Serializable {
	@EColumn(name = "STT", export = true, index = 0)
	private int seq;

	@EColumn(name = "Username", export = true, index = 1)
	private String username;

	@EColumn(name = "Họ tên", export = true, index = 2)
	private String fullName;

	@EColumn(name = "Email", export = true, index = 3)
	private String email;

	@EColumn(name = "Ngày sinh", export = true, index = 4)
	private String birthday;

	@EColumn(name = "Số điện thoại", export = true, index = 5)
	private String mobile;
}
