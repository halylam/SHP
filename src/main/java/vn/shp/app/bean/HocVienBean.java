package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.portal.entity.AlfFile;

import java.util.List;

@Getter
@Setter
public class HocVienBean extends AbstractBean<HocVien> {

    @Getter
    @Setter
    private KinhNghiemLamViec knlv;

    @Getter
    @Setter
    private List<KinhNghiemLamViec> lstKnlv;

    @Getter
    @Setter
    private List<AlfFile> lstAlfFiles;



}
