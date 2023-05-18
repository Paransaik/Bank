package com.back.miru.model.service;

import com.back.miru.model.dao.ConcernAreaDAO;
import com.back.miru.model.dao.UserDAO;
import com.back.miru.model.dto.ConcernArea;
import com.back.miru.model.dto.ConcernCloud;
import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.PageNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConcernAreaServiceImpl implements ConcernAreaService {
	private ConcernAreaDAO concernAreaDao;

	private ConcernAreaServiceImpl(UserDAO userDao) {
		this.concernAreaDao = concernAreaDao;
	}

	@Override
	public void registerConcern(ConcernArea area) throws Exception {
		concernAreaDao.concernAreaInsert(area);
		concernAreaDao.plusUpdateCloud(area.getAddress());
	}

	@Override
	public void deleteConcern(ConcernArea area) throws Exception {
		concernAreaDao.concernAreaDelete(area);
//		concernAreaDao.minusUpdateCloud(name);
//		if (concernAreaDao.selectCloud(name) == 0) {
//			concernAreaDao.deleteCloud(name);
//		}
	}

	@Override
	public List<ConcernArea> selectAllConcern(String id) throws Exception {
		return concernAreaDao.selectAllConcernArea(id);
	}
	
	@Override
	public List<ConcernArea> selectConcern(String id, String pg) throws Exception {

		int pgno = Integer.parseInt(pg);
		int countPerPage = 5; // 한 페이지당 보여줄 갯수
		int start = (pgno - 1) * countPerPage;

		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setStart(start);
		listParameterDto.setCurrentPerPage(countPerPage);
		listParameterDto.setId(id);
		return concernAreaDao.selectConcernArea(listParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(String id, String pg) {
		PageNavigation pageNavigation = new PageNavigation();
		int pgno = Integer.parseInt(pg);
		int currentPage = pgno;
		int naviSize = 5; // 이동할 페이지 링크 범위
		int countPerPage = 5;
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setCountPerPage(countPerPage);
		pageNavigation.setNaviSize(naviSize);

		int totalCount = concernAreaDao.getTotalCount(id);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / countPerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);

		pageNavigation.setStartRange(currentPage <= naviSize);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public List<ConcernCloud> listCloud() throws Exception {
		List<ConcernCloud> list = concernAreaDao.listCloud();
		int len = list.size();
		ConcernCloud[] src = new ConcernCloud[len];
		ConcernCloud[] tmp = new ConcernCloud[len];

		for (int i = 0; i < len; i++)
			src[i] = list.get(i);

		List<ConcernCloud> res = new ArrayList<>();
		for (int i = 0; i < len; i++)
			res.add(src[i]);
		return res;
	}

}
