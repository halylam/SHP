package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.portal.entity.AlfFile;
import vn.shp.portal.repository.AlfFileRepository;
import vn.shp.portal.service.AlfFileService;

@Service("vccbFileServiceImpl")
@Transactional
public class AlfFileServiceImpl implements AlfFileService {

    @Autowired
    AlfFileRepository alfFileRepository;

    @Override
    public AlfFile save(AlfFile entity) {
        return alfFileRepository.save(entity);
    }
}
