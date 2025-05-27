package com.zwj.service;

import com.zwj.controller.TeacherController;
import com.zwj.controller.request.ManageScoreRequest;
import com.zwj.controller.request.ScoreRequest;
import com.zwj.mapper.SearchMapper;
import com.zwj.pojo.Search;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    SearchMapper searchMapper;

    @Override
    public int save(Search search) {
        return searchMapper.save(search);
    }

    @Override
    public Search getSearchById(int id) {
        return searchMapper.getSearchById(id);
    }

    @Override
    public int delete(int id) {
        return searchMapper.delete(id);
    }

    @Override
    public List<Search> getScores(ScoreRequest scoreRequest) {
        return searchMapper.getScores(scoreRequest);
    }

    @Override
    public List<Search> getStuByTeachId(ManageScoreRequest request) {
        return new ArrayList<>(searchMapper.getStuByTeachId(request));
    }

    @Override
    public int update(TeacherController.SearchDto search) {
        return searchMapper.update(search);
    }

    @Override
    public List<Search> getListByStudentId(String studentID) {
        return searchMapper.getListByStudentId(studentID);
    }
}
