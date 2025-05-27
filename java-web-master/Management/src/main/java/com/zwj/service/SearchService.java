package com.zwj.service;

import com.zwj.controller.TeacherController;
import com.zwj.controller.request.ManageScoreRequest;
import com.zwj.controller.request.ScoreRequest;
import com.zwj.pojo.Search;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SearchService {

    int save(Search search);

    Search getSearchById(int id);

    int delete(int id);

    List<Search> getScores(ScoreRequest scoreRequest);

    List<Search> getStuByTeachId(ManageScoreRequest request);

    int update(TeacherController.SearchDto search);

    List<Search> getListByStudentId(String studentID);

}
