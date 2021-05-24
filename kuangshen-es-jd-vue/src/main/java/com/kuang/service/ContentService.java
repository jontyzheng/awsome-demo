package com.kuang.service;

import com.alibaba.fastjson.JSON;
import com.kuang.pojo.Content;
import com.kuang.utils.HtmlParseUtil;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// 业务层
@Service
public class ContentService {

    // 使用容器里注册的 RestHighLevelClient 对象
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 1，爬取内容，放入 ES 的索引里
    public Boolean parse(String keyword) throws Exception {
        List<Content> contents = HtmlParseUtil.parse(keyword);
        // 批量存储
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        for (int i = 0; i < contents.size(); i++) {
            bulkRequest.add(
                    new IndexRequest("jd_goods")
                    .source(JSON.toJSONString(contents.get(i)), XContentType.JSON)
            );
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    // 2，查询
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws Exception {
        if (pageNo <= 1)
            pageNo = 1;

        // 1，构建一个搜索请求集合
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        // 2，构建一个 SourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 分页
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);

        // 4，给一个查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
        // 只要 name 中含有关键字，就搜索出来
        // 这里使用 Ctrl+Alt+v 快速输出返回值类型

        // 3，使用 sourceBuilder 精准查询
        sourceBuilder.query(termQueryBuilder);

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 设置查询的超时时间为 60s

        // 6，通过客户端执行查询
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        ArrayList<Map<String, Object>> list = new ArrayList<>();

        // 7，解析查询
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            list.add(documentFields.getSourceAsMap());
        }

        return list;
    }
    // 将查询结果封装成Map<String, Object> 格式
    // 实现分页查询，实现的 3 个基本参数：keyword，pageNo，pageSize

}
