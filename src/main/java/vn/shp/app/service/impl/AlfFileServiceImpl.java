package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.AlfFile;
import vn.shp.app.repository.AlfFileRepository;
import vn.shp.app.service.AlfFileService;

import java.util.List;

@Service("vccbFileServiceImpl")
@Transactional
public class AlfFileServiceImpl implements AlfFileService {

    @Autowired
    AlfFileRepository alfFileRepository;

    @Override
    public AlfFile save(AlfFile entity) {
        return alfFileRepository.save(entity);
    }

    @Override
    public List<AlfFile> findBySourceAndSourceId(String source, Long sourceId){
        return alfFileRepository.findBySourceAndSourceId(source,sourceId);
    }

    @Override
    public AlfFile findByUuid(String uuid){
        return alfFileRepository.findByUuid(uuid);
    }

    @Override
    public void deleteById(Long id){
         alfFileRepository.deleteById(id);
    }

    @Override
    public AlfFile findOne(Long id){
        return alfFileRepository.findOne(id);
    }
}
