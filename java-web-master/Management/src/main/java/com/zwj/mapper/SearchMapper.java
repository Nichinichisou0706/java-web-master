package com.zwj.mapper;

import com.zwj.controller.TeacherController;
import com.zwj.controller.request.ManageScoreRequest;
import com.zwj.controller.request.ScoreRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Search;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SearchMapper {

    int save(Search search);

    Search getSearchById(int id);

    int delete(int id);

    List<Search> getScores(ScoreRequest scoreRequest);

    List<Search> getStuByTeachId(ManageScoreRequest request);

    int update(TeacherController.SearchDto search);

    List<Search> getListByStudentId(String studentID);

}
