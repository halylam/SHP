package vn.shp.app.bean;

import lombok.Getter;
import lombok.Setter;
import vn.shp.app.config.SystemConfig;
import vn.shp.app.entity.HocVien;
import vn.shp.app.entity.KinhNghiemLamViec;
import vn.shp.app.entity.Location;
import vn.shp.app.entity.AlfFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HocVienBean extends AbstractBean<HocVien> {

    SystemConfig systemConfig;

    public HocVienBean(){}


    public HocVienBean(SystemConfig systemConfig){
        this.systemConfig = systemConfig;
    }


    @Getter
    @Setter
    private KinhNghiemLamViec knlv;

    @Getter
    @Setter
    private List<KinhNghiemLamViec> lstKnlv;

    @Getter
    @Setter
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
