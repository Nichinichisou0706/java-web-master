package com.zwj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.SearchPageRequest;
import com.zwj.controller.request.TeachPageRequest;
import com.zwj.mapper.TeachMapper;
import com.zwj.pojo.Teach;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TeachServiceImpl implements TeachService{

    @Resource
    TeachMapper teachMapper;

    @Override
    public List<Teach> list() {
        return teachMapper.list();
    }

    @Override
    public Object listByCondition(TeachPageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Teach> teaches = teachMapper.listByCondition(pageRequest);
        return new PageInfo<>(teaches);
    }

    @Override
    public int delete(int id) {
        return teachMapper.delete(id);
    }

    @Override
    public int save(Teach teach) {
        return teachMapper.save(teach);
    }

    @Override
    public List<Teach> isRight(Teach teach) {
        return teachMapper.isRight(teach);
    }

    @Override
    public Object getSearchList(SearchPageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Teach> teaches = teachMapper.getSearchList(pageRequest);
        return new PageInfo<>(teaches);
    }

    @Override
    public List<Teach> getMySearchList(SearchPageRequest searchPageRequest) {
        return teachMapper.getMySearchList(searchPageRequest);
    }

    @Override
    public int updateAdd(int id) {
        return teachMapper.updateAdd(id);
    }

    @Override
    public int updateDelete(int id) {
        return teachMapper.updateDelete(id);
    }

    @Override
    public Teach getTeachById(int id) {
        return teachMapper.getTeachById(id);
    }

    @Override
    public List<Teach> getMyTeachList(String username) {
        return teachMapper.getMyTeachList(username);
    }
}
