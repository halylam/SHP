package vn.shp.app.config;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.shp.app.entity.Location;
import vn.shp.app.service.LocationService;
import vn.shp.app.entity.PortalConfig;
import vn.shp.app.entity.PortalGroup;
import vn.shp.app.entity.PortalRole;
import vn.shp.app.entity.PortalUser;
import vn.shp.app.service.PortalConfigService;
import vn.shp.app.service.PortalGroupService;
import vn.shp.app.service.PortalRoleService;
import vn.shp.app.service.PortalUserService;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by hant1 on 03/10/2017.
 */

@Component(value = "systemConfig")
@Scope(value = "singleton")
@Log4j
@Data
public class SystemConfig {

    @Autowired
    LocationService locationService;

    @Autowired
    PortalConfigService portalConfigService;

    @Autowired
    PortalUserService portalUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PortalRoleService portalRoleService;

    @Autowired
    PortalGroupService portalGroupService;

    Map<String, String> lstCardType = new LinkedHashMap<>();

    Map<String, String> lstCardCustType = new LinkedHashMap<>();

    Map<String, String> lstReceiveCard = new LinkedHashMap<>();

    Map<String, String> lstDebBranch = new LinkedHashMap<>();

    List<Location> lstDebLoc = new ArrayList<>();

    List<PortalConfig> lstConfig = new ArrayList<PortalConfig>();

    Map<String, String> mapDebLoc = new LinkedHashMap<>();

    Map<String, String> lstRelationship = new LinkedHashMap<>();

    public static Integer DATA_TABLE_LIMIT = 300;


    @PostConstruct
    public void init() {
        initData();
        log.info("__________ Run PostConstruct to init value __________");


//        log.info("Init value name: " + String.format("%30s", "portalDepartments") + "\t - With value: departmentService.findAllBrnOrTso()");
//        portalDepartments = departmentService.findAllBrnOrTso();
//
//        log.info("Init value name: " + String.format("%30s", "lstCardType") + "\t - With value: catalogService.findCatalog(\"COB\", \"CARD_TYPE\", null)");
//        List<Catalog> lstCat = catalogService.findCatalog("COB", "CARD_TYPE", null);
//        if (lstCat != null) {
//            for (Catalog catalog : lstCat) {
//                lstCardType.put(catalog.getCode(), catalog.getName().toUpperCase());
//            }
//        }
//
//        log.info("Init value name: " + String.format("%30s", "lstCardCustType") + "\t - With value: catalogService.findCatalog(\"COB\", \"CARD_CUST_TYPE\", null)");
//        List<Catalog> lstCatCustType = catalogService.findCatalog("COB", "CARD_CUST_TYPE", null);
//        if (lstCatCustType != null) {
//            for (Catalog catalog : lstCatCustType) {
//                lstCardCustType.put(catalog.getCode(), catalog.getName().toUpperCase());
//            }
//        }
//
//        log.info("Init value name: " + String.format("%30s", "lstReceiveCard") + "\t - With value: catalogService.findCatalog(\"COB\", \"BRN_RECEIVE_CARD\", null)");
//        List<Catalog> lstCatReceiveCard = catalogService.findCatalog("COB", "BRN_RECEIVE_CARD", null);
//        if (lstCatReceiveCard != null) {
//            for (Catalog catalog : lstCatReceiveCard) {
//                lstReceiveCard.put(catalog.getCode(), catalog.getName().toUpperCase());
//            }
//        }
//
        log.info("Init value name: " + String.format("%30s", "lstDebLoc") + "\t - With value: locationService.findAllLocation(true)");
        lstDebLoc = locationService.findAllLocation(true);

        for (Location loc : lstDebLoc) {
            mapDebLoc.put(loc.getLocCode(), loc.getLocName());
        }

        PortalConfig portalConfig = portalConfigService.find("SHP", "DATA_TABLE_LIMIT");
        if (portalConfig != null) {
            DATA_TABLE_LIMIT = Integer.parseInt(portalConfigService.find("SHP", "DATA_TABLE_LIMIT").getConfigValue());
        }


        log.info("__________ Finish PostConstruct to init value __________");
    }

    private void initData(){
        log.info("__________ Check data for system __________");
        log.info("Check Admin role........");
        PortalRole role = portalRoleService.findByRoleCode(Constants.ROLE_ADMIN);
        if(role == null){
            log.info("Create Admin role........");
            role = new PortalRole();
            role.setRoleCode(Constants.ROLE_ADMIN);
            role.setRoleName("QUAN TRI HE THONG");
            role.setUserCreated("SYSTEM");
            role.setTimeCreated(new Date());
            role.setStatus(Constants.RECORD_STATUS_OPEN);
            portalRoleService.save(role);
        }
        log.info("Check Admin group role........");
        PortalGroup group = portalGroupService.findByGroupCode(Constants.GROUP_SYSTEM_ADMIN);
        if(group == null){
            log.info("Create Admin group role........");
            group = new PortalGroup();
            group.setGroupCode(Constants.GROUP_SYSTEM_ADMIN);
            group.setGroupName("QUAN TRI HE THONG");
            group.setUserCreated("SYSTEM");
            group.setTimeCreated(new Date());
            group.setStatus(Constants.RECORD_STATUS_OPEN);
            group.setRoleGroupLst(Arrays.asList(role));
            portalGroupService.save(group);
        }
        log.info("Check Admin user........");
        PortalUser user = portalUserService.findByUsername("admin");
        if(user == null){
            log.info("Create Admin user........");
            user = new PortalUser();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("nguyenha"));
            user.setTimeCreated(new Date());
            user.setEnabled(true);
            user.setBirthday(new Date());
            user.setEmail("admin@shp.com");
            user.setFullName("Admin Shp");
            user.setMobile("0908990558");
            user.setGroups(Arrays.asList(group));
            portalUserService.save(user);
        }
        log.info("__________ Check completed __________");
    }

}
