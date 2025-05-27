package com.zwj.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zwj.controller.request.ManageScoreRequest;
import com.zwj.pojo.Search;
import com.zwj.service.SearchServiceImpl;
import com.zwj.service.TeachServiceImpl;
import com.zwj.utils.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/tea")
public class TeacherController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Score{
        private String username;
        private String name;
        private String score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchDto{
        private String id;
        private String score;
    }

    @Resource
    TeachServiceImpl teachService;

    @Resource
    SearchServiceImpl searchService;

    @GetMapping("/myTeachList/{username}")
    public Result<?> getTeachList(@PathVariable String username){
        return Result.success(teachService.getMyTeachList(username));
    }

    @GetMapping("/manageScore")
    public Result<?> getStuByTeachId(ManageScoreRequest request){
        return Result.success(searchService.getStuByTeachId(request));
    }

    @PostMapping("/update")
    public Result<?> updateScore(@RequestBody SearchDto search){
        return Result.success(searchService.update(search));
    }

    @PostMapping("/updateAll")
    public Result<?> updateAll(@RequestBody List<SearchDto> list){
        for (SearchDto searchDto : list) {
            searchService.update(searchDto);
        }
        return Result.success("录入成功！");
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, ManageScoreRequest request) throws IOException {
        List<Search> list = searchService.getStuByTeachId(request);
        List<TeacherController.Score> scores =  getData(list);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.getStyleSet().setAlign(HorizontalAlignment.LEFT, VerticalAlignment.CENTER); //水平左对齐，垂直中间对齐
        writer.setColumnWidth(0, 30);
        writer.setColumnWidth(1, 20);
        writer.setColumnWidth(2, 25);
        writer.addHeaderAlias("username","用户号");
        writer.addHeaderAlias("name","姓名");
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

    private List<TeacherController.Score> getData(List<Search> list) {
        List<TeacherController.Score> scores = new ArrayList<>();
        for (Search value : list) {
            TeacherController.Score score;
            if (value.getScore() == -1) {
                score = new TeacherController.Score(value.getStudentId(), value.getStudentName(),  "未录入");
            } else {
                score = new TeacherController.Score(value.getStudentId(), value.getStudentName(), String.valueOf(value.getScore()));
            }
            scores.add(score);
        }
        return scores;
    }

}
