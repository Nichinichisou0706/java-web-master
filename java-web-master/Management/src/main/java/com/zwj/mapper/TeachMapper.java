package com.zwj.mapper;

import com.zwj.controller.request.SearchPageRequest;
import com.zwj.controller.request.TeachPageRequest;
import com.zwj.pojo.Teach;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeachMapper {

    List<Teach> list();

    List<Teach> listByCondition(TeachPageRequest pageRequest);

    int delete(int id);

    int save(Teach teach);

    //判断当前要加入的是否满足条件，这个时间段是否有其他占用
    List<Teach> isRight(Teach teach);

    List<Teach> getSearchList(SearchPageRequest searchPageRequest);

    List<Teach> getMySearchList(SearchPageRequest searchPageRequest);

    int updateAdd(int id);

    int updateDelete(int id);

    Teach getTeachById(int id);

    List<Teach> getMyTeachList(String username);

}
