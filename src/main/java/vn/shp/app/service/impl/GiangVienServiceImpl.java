package vn.shp.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.shp.app.entity.GiangVien;
import vn.shp.app.constant.CoreConstant;
import vn.shp.app.repository.GiangVienRepository;
import vn.shp.app.repository.KinhNghiemLamViecRepository;
import vn.shp.app.service.GiangVienService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("giangVienService")
public class GiangVienServiceImpl implements GiangVienService {

	@Autowired
	private GiangVienRepository giangVienRepository;

	@Autowired
	private KinhNghiemLamViecRepository kinhNghiemLamViecRepository;

	@Override
	public List<GiangVien> findAll() {
		Pageable pageable = new PageRequest(0, CoreConstant.DATA_TABLE_LIMIT);
		List<GiangVien> entityLst = giangVienRepository.findAll(pageable).getContent();
		return entityLst;
	}

	@Override
	@Transactional
	public void save(GiangVien entity) {
		giangVienRepository.save(entity);
		if(entity.getId() != null && !entity.getMaGiangVien().startsWith("GV")){
			String maHV = "GV" + String.format("%05d", entity.getId());
			entity.setMaGiangVien(maHV);
			giangVienRepository.save(entity);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		giangVienRepository.delete(id);
	}

	@Override
	@Transactional
	public void delete(GiangVien entity) {
		giangVienRepository.delete(entity);
	}

	@Override
	public GiangVien findOne(Long id) {
		return giangVienRepository.findOne(id);
	}

	@Override
	public List<GiangVien> findByThuAndCaHoc(String thu, String caHocCode) {
		List<GiangVien> result = new ArrayList<GiangVien>();
		List<GiangVien> alls = findAll();
		for(GiangVien each : alls) {
			if("2".equals(thu)){
				if (each.getCaHocT2() != null) {
					List<String> list = Arrays.asList(each.getCaHocT2().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else if("3".equals(thu)){
				if (each.getCaHocT3() != null) {
					List<String> list = Arrays.asList(each.getCaHocT3().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else if("4".equals(thu)){
				if (each.getCaHocT4() != null) {
					List<String> list = Arrays.asList(each.getCaHocT4().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else if("5".equals(thu)){
				if (each.getCaHocT5() != null) {
					List<String> list = Arrays.asList(each.getCaHocT5().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else if("6".equals(thu)){
				if (each.getCaHocT6() != null) {
					List<String> list = Arrays.asList(each.getCaHocT6().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else if("7".equals(thu)){
				if (each.getCaHocT7() != null) {
					List<String> list = Arrays.asList(each.getCaHocT7().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			} else {
				if (each.getCaHocCn() != null) {
					List<String> list = Arrays.asList(each.getCaHocCn().split(","));
					if(list.contains(caHocCode)) {
						result.add(each);
					}
				}
			}
		}

		return result;
	}
}
