package com.kuang.utils;

import com.kuang.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
// 京东爬取类
@Component
//投入 spring 环境使用
public class HtmlParseUtil {

//    public static void main(String[] args) throws Exception {
//        List<Content> goodsList = parse("js");
//        System.out.println(goodsList.get(0).toString());
//       parse("java").forEach(System.out::println);
//    }

    // 核心代码：爬取目标网页，指定内容批量存入 ES，用户通过 ES-head 查看
    public static List<Content> parse(String keyword) throws Exception {
        // 获取请求 https://search.jd.com/Search?keyword=java
        // 前提：联网
        String url = "https://search.jd.com/Search?keyword="+keyword+"&enc=utf-8";
        // （末尾追加编码参数，支持中文关键词）
        // 传递解析目标，超时时间给 Jsoup 解析爬取
        Document document = Jsoup.parse(new URL(url),3000);
        // 解析返回一个 document 对象，document 可进行任何 js 操作
        // 获取 id 为 J_goodsList 的 DOM 对象
        Element element = document.getElementById("J_goodsList");
        //System.out.println("element 是否为空: " + element.isEmpty);

        // Content 封装单本图书信息，列表批量保存
        List<Content> list = new ArrayList<Content>();

        // 对爬取下来的网页元素进行操作
        // 获取子元素 li
        Elements elements = element.getElementsByTag("li");
        // 遍历 li 元素，获取各个子元素
        for (Element el :elements) {

            String name = el.getElementsByClass("p-name").eq(0).text();
            String price = el.getElementsByClass("p-price").eq(0).text();
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            // 封装
            Content content = new Content(name, price, img);
            list.add(content);
        }
        return list;
    }
}
