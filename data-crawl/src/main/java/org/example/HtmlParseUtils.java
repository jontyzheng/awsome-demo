package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

//案例：京东网页爬取
public class HtmlParseUtils {
    public static void main(String[] args) throws Exception {
        // 输入 URL
        String url = "https://search.jd.com/Search?keyword=java";
        // 传递 URL 和解析超时时间给 Jsoup，进行解析，返回一个网页元素
        Document document = Jsoup.parse(new URL(url),30000);
        // document 可进行任何 js 操作
        Element element = document.getElementById("J_goodsList");
        // 测试：输出 element 对象
        //System.out.println(element);
        // 测试：获取该元素中 li 标签
        Elements elements = element.getElementsByTag("li");
        // 遍历 li 标签，获取具体的网页信息
        for (Element el : elements) {
            System.out.println("=======================================");
            // 按照 class 获取图书书名信息
            String name = el.getElementsByClass("p-name").text();
            System.out.println(name);
            // 同理，获取图书价格
            String price = el.getElementsByClass("p-price").text();
            System.out.println(price);
            // 同理，获取图书销售店
            String shop = el.getElementsByClass("p-shopnum").text();
            System.out.println(shop);
        }
    }

}
