package com.sys.network.proc;

import java.util.Iterator;
import java.util.List;

import com.sys.network.bean.WebsitesStatistics;
import com.sys.network.dao.NtRecordCountDao;
import com.sys.network.dao.NtSubCategoryDao;
import com.sys.network.dao.NtWebsitesDao;
import com.sys.network.dao.WebsitesStatisticsDao;
import com.sys.network.servlet.Calculate;

public class WebsitesStatisticsPro {

	/**
	 * 想数据库中添加WebsitesStatistics
	 * 1.先查询出来子类访问信息
	 * 2.再添加进数据库
	 */
	public static void addWebsitesStatistics(){
		WebsitesStatisticsDao.clear();
		List list = NtSubCategoryDao.GetAllSubCategories();
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			int sid = Integer.parseInt(obj[0].toString());
			//根据subid查询其完整的名称
			List fullnameList = NtSubCategoryDao.GetNameBySid(sid);
			Object[] obj1 = (Object[])fullnameList.get(0);
			String fullname = obj1[0]+"-"+obj1[1]+"-"+obj1[2];
			//再根据sid遍历所有的weburl
			List urlList = NtWebsitesDao.GetNtWebsitesSub(sid);
			//根据sid查询访问量
			int accesscount = NtRecordCountDao.GetAccessCountBySubId(sid);
			Iterator urlit = urlList.iterator();
			while(urlit.hasNext()){
				String url = (String) urlit.next();
				WebsitesStatistics ws = new WebsitesStatistics(url, fullname, accesscount,sid);
				WebsitesStatisticsDao.AddWebsitesStatistics(ws);
			}
		}
		Calculate.setFlag();
	}
	
	public static void main(String[] args){
		addWebsitesStatistics();
	}
}
