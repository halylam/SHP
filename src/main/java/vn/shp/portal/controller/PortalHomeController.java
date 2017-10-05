package vn.shp.portal.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.shp.portal.entity.*;
import vn.shp.portal.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("portal")
@PreAuthorize("isAuthenticated()")
public class PortalHomeController {

	@Autowired
	PortalSystemService portalSystemService;

	@Autowired
	PortalUserService portalUserService;

	@Autowired
	PortalSystemGroupService portalSystemGroupService;





    @RequestMapping(method = GET)
    public String getHome(Model model, HttpServletRequest request) {
        String username = "hant1";
        model.addAttribute("username", username);

        //
        PortalSystem portalSystem = new PortalSystem();
        model.addAttribute("portalSystem", portalSystem);

        Map<String, List<PortalSystem>> portalSystemLstMap = new HashMap<String, List<PortalSystem>>();
        List<PortalSystem> systemLstTemp = new ArrayList<PortalSystem>();
        List<PortalSystem> systemLstExsist = new ArrayList<PortalSystem>();

        List<PortalSystem> portalSystemLst = new ArrayList<PortalSystem>();
        PortalUser portalUser = portalUserService.findByUsername(username);
        if (portalUser != null) {
        	for (PortalGroup group : portalUser.getGroups()) {
        		if (group.getStatus().equals("0")) {
        			continue;
        		}
        		for (PortalRole role : group.getRoleGroupLst()) {
                    if (role.getStatus().equals("1") && role.getSystem().getStatus().equals("1")
                            && role.getSystem().getGroup().getStatus().equals("1")) {
                        if (!CollectionUtils.isEmpty(portalSystemLst) && portalSystemLst.contains(role.getSystem())) {
                            continue;
                        }
                        portalSystemLst.add(role.getSystem());
                    }
                }
        	}
        }

        for (int i = 0; i < portalSystemLst.size(); i++) {

            PortalSystem item1 = portalSystemLst.get(i);
            if (systemLstExsist.contains(item1)) {
                continue;
            }

            systemLstTemp.add(item1);
            systemLstExsist.add(item1);
            for (int j = i + 1; j < portalSystemLst.size(); j++) {
                PortalSystem item2 = portalSystemLst.get(j);
                if (item1.getGroup().getGroupName().equals(item2.getGroup().getGroupName())) {
                    systemLstTemp.add(item2);
                    systemLstExsist.add(item2);
                }
            }
            
            portalSystemLstMap.put(item1.getGroup().getGroupName(), systemLstTemp);
            systemLstTemp = new ArrayList<PortalSystem>();
        }
        
        // sort
        Map<String, List<PortalSystem>> portalSystemLstMapTemp = new LinkedHashMap<String, List<PortalSystem>>();
        List<PortalSystemGroup> lstSystemGroup = new ArrayList<PortalSystemGroup>();
        for(Map.Entry<String, List<PortalSystem>> entry : portalSystemLstMap.entrySet()) {
            String key = entry.getKey();
            PortalSystemGroup systemGroup = portalSystemGroupService.findByGroupName(key);
            lstSystemGroup.add(systemGroup);
        }
        sortLstSystemGroup(lstSystemGroup);
        for (PortalSystemGroup item : lstSystemGroup) {
        	List<PortalSystem> lstPortalSystem = portalSystemLstMap.get(item.getGroupName());
        	sortLstSystem(lstPortalSystem);
        	portalSystemLstMapTemp.put(item.getGroupName(), lstPortalSystem);
        }
        
        model.addAttribute("portalSystemLstMap", portalSystemLstMapTemp);
        return "portal/index";
    }

    @RequestMapping(value = "/logout", method = GET)
    public String getList(Model model, HttpServletRequest request) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/error", method = GET)
    public String getError(Model model, HttpServletRequest request) {

        String username = request.getUserPrincipal().getName();
        model.addAttribute("username", username);
        return "portal/error";
    }


    @RequestMapping(value =  "/detail", method = RequestMethod.GET)
    public String gethelpdeskAllProcessingRequest1(Model model) {

        return "portal/index_processing :: frprocessing";

    }

    @RequestMapping("/requests/hist/{id}")
    public String getRequestHistById(@PathVariable Long id) {
            return "redirect:/document/request/completed/" + id ;
    }




    @RequestMapping("/requests/{id}/created")
    public String getCreatedRequestById(@PathVariable Long id, Model model) {
        return "redirect:portal";
    }

    public void sortLstSystemGroup(List<PortalSystemGroup> list) {
        sortLstSystemGroup(list, 0, list.size() - 1);
    }

    public void sortLstSystemGroup(List<PortalSystemGroup> list, int from, int to) {
    	for (int i = 0; i < list.size(); i++) {
        	if (StringUtils.isEmpty(list.get(i).getRemark())) {
        		list.get(i).setRemark("1000");
        	}
        }
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            int pivotValue = Integer.parseInt(list.get(pivot).getRemark());
            while (left <= right) {
                // left <= to -> limit protection
                while (left <= to && pivotValue >= Integer.parseInt(list.get(left).getRemark())) {
                    left++;
                }
                // right > from -> limit protection
                while (right > from && pivotValue < Integer.parseInt(list.get(right).getRemark())) {
                    right--;
                }
                if (left < right) {
                	java.util.Collections.swap(list, left, right);
                }
            }
            java.util.Collections.swap(list, pivot, left - 1);
            sortLstSystemGroup(list, from, right - 1); // <-- pivot was wront!
            sortLstSystemGroup(list, right + 1, to);   // <-- pivot was wront!
        }
    }

    public void sortLstSystem(List<PortalSystem> list) {
    	sortLstSystem(list, 0, list.size() - 1);
    }
    
    public void sortLstSystem(List<PortalSystem> list, int from, int to) {
    	for (int i = 0; i < list.size(); i++) {
        	if (StringUtils.isEmpty(list.get(i).getRemark())) {
        		list.get(i).setRemark("1000");
        	}
        }
    	if (from < to) {
    		int pivot = from;
    		int left = from + 1;
    		int right = to;
    		int pivotValue = Integer.parseInt(list.get(pivot).getRemark());
    		while (left <= right) {
    			// left <= to -> limit protection
    			while (left <= to && pivotValue >= Integer.parseInt(list.get(left).getRemark())) {
    				left++;
    			}
    			// right > from -> limit protection
    			while (right > from && pivotValue < Integer.parseInt(list.get(right).getRemark())) {
    				right--;
    			}
    			if (left < right) {
    				java.util.Collections.swap(list, left, right);
    			}
    		}
    		java.util.Collections.swap(list, pivot, left - 1);
    		sortLstSystem(list, from, right - 1); // <-- pivot was wront!
    		sortLstSystem(list, right + 1, to);   // <-- pivot was wront!
    	}
    }
}

