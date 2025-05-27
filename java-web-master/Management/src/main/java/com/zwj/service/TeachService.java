package com.zwj.service;

import com.zwj.controller.request.SearchPageRequest;
import com.zwj.controller.request.TeachPageRequest;
import com.zwj.pojo.Teach;

import java.util.List;


public interface TeachService {

    List<Teach> list();

    Object listByCondition(TeachPageRequest pageRequest);

    int delete(int id);

    int save(Teach teach);

    List<Teach> isRight(Teach teach);

    Object getSearchList(SearchPageRequest pageRequest);

    List<Teach> getMySearchList(SearchPageRequest searchPageRequest);

    int updateAdd(int id);

    int updateDelete(int id);

    Teach getTeachById(int id);

    List<Teach> getMyTeachList(String username);

}
