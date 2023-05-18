package com.back.miru.model.service;

import com.back.miru.model.dto.ConcernArea;
import com.back.miru.model.dto.ConcernCloud;
import com.back.miru.model.dto.PageNavigation;

import java.util.List;

public interface ConcernAreaService {

	void registerConcern(ConcernArea area) throws Exception;

	void deleteConcern(ConcernArea area) throws Exception;

	List<ConcernArea> selectAllConcern(String id) throws Exception;

	List<ConcernArea> selectConcern(String id, String pg) throws Exception;

	List<ConcernCloud> listCloud() throws Exception;

	PageNavigation makePageNavigation(String id, String pg);

}
