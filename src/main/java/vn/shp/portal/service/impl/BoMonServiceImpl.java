package vn.shp.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.BoMon;
import vn.shp.portal.repository.BoMonRepository;
import vn.shp.portal.service.BoMonService;

import java.util.List;

@Service("BoMonService")
public class BoMonServiceImpl implements BoMonService {

	@Autowired
	private BoMonRepository boMonRepo;

	@Override
	public List<BoMon> findAll() {
		List<BoMon> boMonLst = boMonRepo.findAll();
		return boMonLst;
	}

	@Override
	@Transactional
	public void save(BoMon boMon) {
		boMonRepo.save(boMon);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		boMonRepo.delete(id);
	}

	@Override
	@Transactional
	public void delete(BoMon entity) {
		boMonRepo.delete(entity);
	}

	@Override
	public BoMon findOne(Long id) {
		return boMonRepo.findOne(id);
	}

	@Override
	public BoMon findByBoMonCode(String boMonCode) {
		return boMonRepo.findByBoMonCode(boMonCode);
	}
	
}
