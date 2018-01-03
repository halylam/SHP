package vn.shp.portal.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import vn.shp.app.entity.LopHocHocVien;
import vn.shp.portal.entity.PortalGroup;
import vn.shp.portal.repository.PortalGroupRepository;

import java.util.List;

/**
 * Created by hant1 on 16/10/2017.
 */
public interface RoleGroupDao extends GenericDAO<PortalGroup, Long> {
}
