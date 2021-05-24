#### 案例：从京东搜索页爬取商品信息并存入 ElasticSearch 索引

IDE：IDEA

项目类型：spring boot

1.引入依赖 web，fast-json(将爬取下来的数据以 json 格式录入 es 索引中)，Jsoup，Thymeleaf

2.前提：本文使用到 es 和 es 图形化界面 es-head，所以请先保证 ElasticSearch 和 ElasticSearch-head 以提前开启，并在 ElasticSearch-head 中新建索引 `jd_goods` 

（这里的索引名可为任意值，对应业务类中的 `indexRequest` 参数值）

3.应用启动后，根据 controller 中的拦截请求，访问 `localhost:9090/parse/xx`，跳转 es-head 索引页刷新并查看。

4.是的，这里并没有用到 template 下漂亮的前端页面。

[参考来源](https://www.bilibili.com/video/BV17a4y1x7zq?p=17)

