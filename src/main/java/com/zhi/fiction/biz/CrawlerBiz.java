package com.zhi.fiction.biz;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.zhi.fiction.mapper.ArticleMapper;
import com.zhi.fiction.model.Article;
import com.zhi.fiction.util.kafka.KafkaProducer;

/**
 * @author 作者 yongzhi.zhao:
 * @version 创建时间：2017年11月10日 上午11:00:49 类说明
 */
@Service
public class CrawlerBiz {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private KafkaProducer KafkaProducer;

    ExecutorService       ex = Executors.newCachedThreadPool();

    /**
     * 多线程抓取
     * 
     * @return
     */
    @Transactional
    public void resolveHtmlStr() {
        //生产
        ex.execute(new Runnable() {
            @Override
            public void run() {
                getArticleListByConfig("http://www.mushenji.com");
            }
        });
    }

    /**
     * 获取页面
     * 
     * @param Url
     * @return
     */
    private static Document getDoc(String Url) {
        Connection con = Jsoup.connect(Url);
        con.header("userAgent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        Document doc = null;
        try {
            doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 抓取页面上小说的页面链接
     * 
     * @return
     */
    private void getArticleListByConfig(String baseUrl) {
        Document doc = getDoc(baseUrl);
        // 章节链接
        Set<String> set = new HashSet<String>();
        String title = "牧神记";
        Elements elements = doc.select(".chapterlist").select("a:matchesOwn([第])");
        List<Article> articleList = Lists.newArrayList();
        for (Element link : elements) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            String[] temp = StringUtils.split(linkText, " ");
            Article article = new Article();
            article.setHref(baseUrl + linkHref);
            article.setTitle(title);
            article.setChapter(temp[0]);
            if (set.add(linkText)) {
                articleList.add(article);
            }
        }
        KafkaProducer.send("article", articleList);
    }

    @KafkaListener(topics = { "article" })
    public void receive(String msg) {

        List<Article> articleList = JSON.parseObject(msg, new TypeReference<List<Article>>() {
        });

        List<List<Article>> partList = Lists.partition(articleList, 20);

        for (List<Article> list : partList) {
            ex.execute(new Runnable() {
                @Override
                public void run() {
                    for (Article article : list) {
                        String content = getDoc(article.getHref()).select("div#BookText>p").outerHtml();
                        article.setContent(content);
                    }
                    insertBath(list);
                }
            });
        }
    }

    /**
     * 批量插入
     * 
     * @param articleList
     */
    public void insertBath(List<Article> articleList) {
        articleMapper.insertBatch(articleList);
    }
}
