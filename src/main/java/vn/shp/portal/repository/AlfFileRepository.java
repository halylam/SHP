package vn.shp.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.AlfFile;

@Repository
public interface AlfFileRepository extends JpaRepository<AlfFile, Long> {
    public AlfFile findByUuid(String uuid);
}
