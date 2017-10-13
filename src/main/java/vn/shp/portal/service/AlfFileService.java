package vn.shp.portal.service;

import vn.shp.portal.entity.AlfFile;

import java.util.List;

public interface AlfFileService {

    AlfFile save(AlfFile entity);


    List<AlfFile> findBySourceAndSourceId(String source, Long sourceId);

    AlfFile findByUuid(String uuid);

    void deleteById(Long id);

    AlfFile findOne(Long id);
}
