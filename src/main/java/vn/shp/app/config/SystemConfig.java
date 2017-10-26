package vn.shp.app.config;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.shp.app.entity.Location;
import vn.shp.app.service.LocationService;
import vn.shp.portal.entity.PortalConfig;
import vn.shp.portal.service.PortalConfigService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

//
//        log.info("Init value name: " + String.format("%30s", "lstRelationship") + "\t - With value: bwCatalogService.findCatalog(Constants.BW_TABLE.VCCB_AIM_BWT_RELATIONSHIP)");
//        lstRelationship = bwCatalogService.findCatalog(Constants.BW_TABLE.VCCB_AIM_BWT_RELATIONSHIP);
//
//        log.info("Init value name: " + String.format("%30s", "lstDebBranch") + "\t - With value: bwCatalogService.findCatalog(Constants.BW_TABLE.VCCB_AIM_APPLICATION_SOURCE)");
//        lstDebBranch = bwCatalogService.findCatalog(Constants.BW_TABLE.VCCB_AIM_APPLICATION_SOURCE);
//
//        log.info("Init value name: " + String.format("%30s", "lstBwCountry") + "\t - With value: bwCatalogService.findCatalog(Constants.BW_TABLE.VCCB_AIM_BWT_COUNTRY)");
//        lstBwCountry = bwCatalogService.findBwCountry(Constants.BW_TABLE.VCCB_AIM_BWT_COUNTRY);

        log.info("__________ Finish PostConstruct to init value __________");
    }

}
