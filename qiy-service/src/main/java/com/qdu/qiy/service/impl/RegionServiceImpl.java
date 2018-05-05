package com.qdu.qiy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qdu.qiy.dao.IRegionDao;
import com.qdu.qiy.pojo.Region;
import com.qdu.qiy.service.IRegionService;
import com.qdu.qiy.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {

	@Autowired
	private IRegionDao regionDao;
	@Override
	public void saveBath(List<Region> regionList) {
		for (Region region : regionList) {
			regionDao.saveOrUpdate(region);
		}
		
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

}
