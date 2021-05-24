package com.kuang.controller;

import com.kuang.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

// 前端请求
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/parse/{keyword}")
    @ResponseBody
    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception {
        return contentService.parse(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    @ResponseBody
    public List<Map<String, Object>> searchPage(@PathVariable("keyword") String keyword,
                                                @PathVariable("pageNo") int pageNo,
                                                @PathVariable("pageSize") int pageSize) throws Exception {
        if (pageNo <= 0)
            pageNo = 1;
        System.out.println(keyword);
        System.out.println(pageNo);
        System.out.println(pageSize);
        return contentService.searchPage(keyword, pageNo, pageSize);
    }


}
