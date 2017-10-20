package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.GiangVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.app.entity.Location;
import vn.shp.portal.entity.AlfFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GiangVienBean extends AbstractBean<GiangVien> {

    SystemConfig systemConfig;

    private KinhNghiemLamViec knlv;

    private List<KinhNghiemLamViec> lstKnlv;

    private List<AlfFile> lstAlfFiles;

    public List<Location> locationByParentCode(String parentCode){
        if(systemConfig != null && parentCode != null){
            List<Location> result = new ArrayList<>();
            List<Location> lst = systemConfig.getLstDebLoc();
            for (Location location : lst) {
                if(parentCode.equals(location.getParentCode())){
                    result.add(location);
                }
            }
            return result;
        }
        return null;
    }

}
