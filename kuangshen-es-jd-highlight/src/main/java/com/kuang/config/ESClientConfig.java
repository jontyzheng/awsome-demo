package com.kuang.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Elasticsearch 与 spring boot集成
@Configuration
//xml文件
public class ESClientConfig {

    //返回RestHighLevelClient (使用工厂管理)，其它类通过 IOC 可直接使用
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        return client;
    }
    //@Bean 相当于xml中注册的 bean
    //方法名相当于xml中的 id，返回值类型就相当于xml中的class
}
