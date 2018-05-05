package com.qdu.qiy.service;

import java.util.List;

import com.qdu.qiy.pojo.Region;
import com.qdu.qiy.utils.PageBean;

public interface IRegionService {

	void saveBath(List<Region> regionList);

	void pageQuery(PageBean pageBean);

}
