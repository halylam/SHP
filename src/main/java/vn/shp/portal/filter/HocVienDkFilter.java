package vn.shp.portal.filter;

import java.util.Date;

/**
 * Created by Lam_D on 07/11/2017.
 */
public class HocVienDkFilter {

	private Date fromDate;
	private Date toDate;
	private Long ctdtId;
	private Long chuyenNganhId;
	private Long khoaHocId;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getCtdtId() {
		return ctdtId;
	}

	public void setCtdtId(Long ctdtId) {
		this.ctdtId = ctdtId;
	}

	public Long getChuyenNganhId() {
		return chuyenNganhId;
	}

	public void setChuyenNganhId(Long chuyenNganhId) {
		this.chuyenNganhId = chuyenNganhId;
	}

	public Long getKhoaHocId() {
		return khoaHocId;
	}

	public void setKhoaHocId(Long khoaHocId) {
		this.khoaHocId = khoaHocId;
	}
}
