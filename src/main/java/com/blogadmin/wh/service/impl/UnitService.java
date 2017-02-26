package com.blogadmin.wh.service.impl;

import com.blogadmin.wh.dao.IUnitDao;
import com.blogadmin.core.utils.response.ListJsonResult;
import com.blogadmin.wh.model.Unit;
import com.blogadmin.wh.service.IUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UnitService implements IUnitService {

	@Autowired
	private IUnitDao unitDao;

	@Override
	public ListJsonResult<Unit> queryUnitPage(Map<String, Object> paramMap) {
		return unitDao.queryDTPage(paramMap);
	}

	@Override
	public int deleteUnit(Long id) {
		return unitDao.logicRemove(id);
	}

	@Override
	public Unit getUnitU(String ucode) {
		return unitDao.getU(ucode);
	}

	@Override
	public List<String> getUnames() {
		return unitDao.getUnames();
	}


	@Override
	public List<Unit> queryFirst() {
		return unitDao.queryFirst();
	}

	@Override
	public List<Unit> querySecond(Map<String, Object> paramMap) {
		return unitDao.querySecond(paramMap);
	}

	@Override
	public Unit getUnit(Long uid) {
		return unitDao.get(uid);
	}

	@Override
	public String saveUnit(Unit unit) {
		if(unit.getId() == null){
			unitDao.saveEntity(unit);
		}else{
			unitDao.updateEntity(unit);
		}
		return null;
	}

	@Override
	public String saveUnits(List<Unit> units) {
		if(units.size()!=0&&units!=null){
			unitDao.saveBatchEntity(units);
		}
		System.out.println("存储成功-------");

		return null;
	}

	@Override
	public List<Unit> getMuUnit() {
		return unitDao.getMuUnit();
	}
}
