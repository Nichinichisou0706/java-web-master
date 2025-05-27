package com.zwj.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zwj.controller.request.ScoreRequest;
import com.zwj.controller.request.SearchPageRequest;
import com.zwj.pojo.Search;
import com.zwj.pojo.Teach;
import com.zwj.service.CourseServiceImpl;
import com.zwj.service.SearchServiceImpl;
import com.zwj.service.TeachServiceImpl;
import com.zwj.utils.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/stu")
public class SearchController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Score {
        private String courseName;
        private String courseCode;
        private String teacherName;
        private String score;
    }

    @Resource
    TeachServiceImpl teachService;

    @Resource
    SearchServiceImpl searchService;

    @Resource
    CourseServiceImpl courseService;

    @GetMapping("/searchList")
    public Result<?> getSearchList(SearchPageRequest searchPageRequest){
        return Result.success(teachService.getSearchList(searchPageRequest));
    }

    @GetMapping("/mySearchList")
    public Result<?> getMySearchList(SearchPageRequest searchPageRequest){
        return Result.success(teachService.getMySearchList(searchPageRequest));
    }

    @PostMapping("/add")
    @Transactional
    public Result<?> save(@RequestBody Search search){
        Teach teach = teachService.getTeachById(search.getTeachId());
        if (teach.getNow() < teach.getMax() && isValid(search)){         //当前选购人数 < 限购人数 而且 时间合理
            teachService.updateAdd(teach.getId());
            int res = searchService.save(search);
            return res == 1 ? Result.success() : Result.error("未知错误");
        } else {
            return Result.error("该景点人数已达上限或与您现有景点冲突，请检查重试");
        }
    }

    @DeleteMapping("/search/delete/{id}")
    @Transactional
    public Result<?> delete(@PathVariable Integer id){
        Search search = searchService.getSearchById(id);
        Teach teach = teachService.getTeachById(search.getTeachId());
        if (courseService.getCourseByCourseCode(teach.getCourseCode()).getStatus() == 0){
            return Result.error("该景点选购已经截止了哦~");
        }
        teachService.updateDelete(teach.getId());
        int res = searchService.delete(id);
        return res == 1 ? Result.success() : Result.error("未知错误");
    }

    @GetMapping("/score")
    public Result<?> getScores(ScoreRequest scoreRequest){
        return Result.success(searchService.getScores(scoreRequest));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response,ScoreRequest scoreRequest) throws IOException {
        List<Search> list = searchService.getScores(scoreRequest);
        List<Score> scores =  getData(list);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.getStyleSet().setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER); //水平左对齐，垂直中间对齐
        writer.setColumnWidth(0, 40);
        writer.setColumnWidth(1, 40);
        writer.setColumnWidth(2, 40);
        writer.setColumnWidth(3, 40);
        writer.addHeaderAlias("courseName","景点名");
        writer.addHeaderAlias("courseCode","景点代码");
        writer.addHeaderAlias("teacherName","商家姓名");
        writer.addHeaderAlias("score","价格");
        writer.write(scores,true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = URLEncoder.encode( "价格","UTF-8");
        response.setHeader( "Content-Disposition","attachment;filename="+ fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }



    private List<Score> getData(List<Search> list) {
        List<Score> scores = new ArrayList<>();
        for (Search value : list) {
            Score score;
            if (value.getScore() == -1) {
                score = new Score(value.getCourseName(), value.getCourseCode(), value.getTeacherName(), "成绩未录入");
            } else {
                score = new Score(value.getCourseName(), value.getCourseCode(), value.getTeacherName(), String.valueOf(value.getScore()));
            }
            scores.add(score);
        }
        return scores;
    }

    private boolean isValid(Search search){         //判断用户选择是否合理（时间）
        boolean flag = true;
        Teach teach = teachService.getTeachById(search.getTeachId());
        List<Search> searchList = searchService.getListByStudentId(search.getStudentId());
        for (Search item : searchList) {
            Teach temp = teachService.getTeachById(item.getTeachId());
            if (temp.getDay().equals(teach.getDay())
                    && temp.getTime().equals(teach.getTime())
                    && (temp.getEnd() > teach.getBegin() || temp.getBegin() < teach.getEnd()) )

            {
                flag = false;
                break;
            }
        }
        return flag;
    }

}
