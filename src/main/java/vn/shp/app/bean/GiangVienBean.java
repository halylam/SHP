package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.entity.GiangVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.portal.entity.AlfFile;

import java.util.List;

@Getter
@Setter
public class GiangVienBean extends AbstractBean<GiangVien> {

    private KinhNghiemLamViec knlv;

    private List<KinhNghiemLamViec> lstKnlv;

    private List<AlfFile> lstAlfFiles;



}
