package vn.shp.app.bean;

import lombok.Data;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.portal.entity.AlfFile;

import java.util.List;

@Data
public class HocVienBean extends AbstractBean<HocVien> {

    private KinhNghiemLamViec knlv;

    private List<KinhNghiemLamViec> lstKnlv;

    private List<AlfFile> lstAlfFiles;

}
