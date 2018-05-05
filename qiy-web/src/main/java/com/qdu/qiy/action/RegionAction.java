package com.qdu.qiy.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.aspectj.bridge.MessageWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.qdu.qiy.action.base.BaseAction;
import com.qdu.qiy.pojo.Region;
import com.qdu.qiy.service.IRegionService;
import com.qdu.qiy.utils.PageBean;
import com.qdu.qiy.utils.PinYin4jUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	//属性驱动，接收上传的文件
	private File regionFile;
	@Autowired
	private IRegionService regionService;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	/**
	 * 区域导入
	 */
	public String importXls()throws Exception{
		List<Region> regionList = new ArrayList<>();
		//使用poi解析Excel
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//根据名称获得标签页
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		//遍历标签页中所有的行
		for (Row row : sheet) {
			int rowNum = row.getRowNum();
			if(rowNum == 0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			
			String shortcode = StringUtils.join(headByString);
			//城市编码---->>shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			
			regionList.add(region);
			
		}
		regionService.saveBath(regionList);
		return NONE;
	}

	/**
	 * 分页查询
	 */
	public String pageQuery()throws Exception{
		
		regionService.pageQuery(pageBean);
		this.java2json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}


}