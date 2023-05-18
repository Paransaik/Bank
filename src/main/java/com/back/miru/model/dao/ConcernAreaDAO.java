package com.back.miru.model.dao;

import com.back.miru.model.dto.ConcernArea;
import com.back.miru.model.dto.ConcernCloud;
import com.back.miru.model.dto.ListParameterDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ConcernAreaDAO {

    public int concernAreaInsert(ConcernArea area) throws SQLException;

    public int concernAreaDelete(ConcernArea area) throws SQLException;

    public List<ConcernArea> selectAllConcernArea(String id);

    public ArrayList<ConcernArea> selectConcernArea(ListParameterDto listParameterDto) throws SQLException;

    public List<ConcernCloud> listCloud();

    public int selectCloud(String name);

    public void plusUpdateCloud(String name);

    public void minusUpdateCloud(String name);

    public void deleteCloud(String name);

    public int getTotalCount(String id);

}
