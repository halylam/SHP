package vn.shp.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.shp.portal.entity.PortalUser;

import java.util.List;

@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {

	@Query("select u from PortalUser u where UPPER(u.username) like UPPER(CONCAT('%',:username,'%')) and UPPER(u.email) like UPPER(CONCAT('%',:email,'%')) and UPPER(u.fullName) like UPPER(CONCAT('%',:fullName,'%'))")
	Page<PortalUser> findBy(@Param("username") String username, @Param("email") String email, @Param("fullName") String fullName, Pageable pageable);

	@Query("select u from PortalUser u where UPPER(u.username) like UPPER(CONCAT('%',:username,'%')) and UPPER(u.email) like UPPER(CONCAT('%',:email,'%')) and UPPER(u.fullName) like UPPER(CONCAT('%',:fullName,'%'))")
	List<PortalUser> findBy(@Param("username") String username, @Param("email") String email, @Param("fullName") String fullName);

	PortalUser findByUsername(String username);


}
