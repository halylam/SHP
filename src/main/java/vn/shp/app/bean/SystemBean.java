package vn.shp.app.bean;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import vn.shp.app.entity.PortalUser;

import java.util.List;

@Data
public class SystemBean extends AbstractBean<T>{

    private List<PortalUser> lstUser;
}
