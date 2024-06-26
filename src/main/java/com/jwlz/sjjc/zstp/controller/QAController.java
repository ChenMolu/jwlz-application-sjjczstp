package com.jwlz.sjjc.zstp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jwlz.sjjc.zstp.common.R;
import com.jwlz.sjjc.zstp.entity.Relation;
import com.jwlz.sjjc.zstp.entity.vo.Answer;
import com.jwlz.sjjc.zstp.entity.vo.Point;
import com.jwlz.sjjc.zstp.mapper.RelationMapper;
import com.jwlz.sjjc.zstp.service.NlpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/zstp/nlp")
public class QAController {

    @Resource
    private NlpService nlpService;

    @Resource
    private RelationMapper relationMapper;

    @GetMapping(value = "/getAnswer", produces = "application/json;charset=UTF-8")
    public R<Answer> getAnswer(@RequestParam String question) {
        log.info("question:{}", question);
        Answer answer = new Answer();
        List<String> structuredQueryList = nlpService.getStructuredQueryList(question);
        log.info("structuredQueryList:{}", structuredQueryList.toString());
        if (structuredQueryList.size() <= 1) {
            return R.error("抱歉，未查询到相关信息！");
        } else {
            String entity = structuredQueryList.get(0);
            QueryWrapper<Relation> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SUBJECT", entity);
            List<Relation> relations = relationMapper.selectList(queryWrapper);
            if (relations.isEmpty()) {
                return R.error("抱歉，未查询到相关信息！");
            }
            log.info(relations.toString());
            List<Point> points = new ArrayList<>();
            points.add(new Point(0, entity, "", -1));
            for (int i = 1; i <= relations.size(); i++) {
                Point point = new Point(i,relations.get(i-1).getObjectItem(),relations.get(i-1).getRelation(),0);
                points.add(point);

            }
            answer.setGraph(points);
            Map<String, String> res = new HashMap<>(16);
//            Graph graph = new Graph();
//            graph.setNode(entity);
//            Map<String, String> map = new HashMap<>(16);
//            for (Relation relation : relations) {
//                map.put(relation.getRelation(), relation.getObjectItem());
//            }
//            graph.setRelation(map);
            for (int i = 1; i < structuredQueryList.size(); i++) {

                for (Relation relation : relations) {
                    if (relation.getRelation().equals(structuredQueryList.get(i))) {
                        res.put(structuredQueryList.get(i), relation.getObjectItem());
                    }
                }
            }
            answer.setAnswer(res);
//            answer.setGraph(graph);
        }
        return R.success(answer);
    }

}
