package com.zhi.fiction.biz;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zhi.fiction.mapper.ArticleMapper;
import com.zhi.fiction.model.Article;
import com.zhi.fiction.util.NumberUtil;
import com.zhi.fiction.util.RedisUtil;

/**
 * @author 作者 yongzhi.zhao:
 * @version 创建时间：2017年11月10日 上午11:00:49 类说明
 */
@Service
public class CrawlerBiz {
	@Autowired
	private ArticleMapper articleMapper;

	private static final int pageSize = 100;
	
	 private static final ExecutorService ex   = Executors.newFixedThreadPool(1000);
	
	// 查询
	public Object getContent(String id) {
		return articleMapper.selectByPrimaryKey(Long.valueOf(id));
	}
	
	boolean isEnd = false;

	/**
	 * 多线程抓取并上传到FTP服务器
	 * 
	 * @return
	 */
	@Transactional
	public void resolveHtmlStr() {
        //生产
        ex.execute(new Runnable() {
            @Override
            public void run() {
                getArticleListByConfig("http://www.mushenji.com", "a:matchesOwn([第])", "牧神记");
            }
        });
        //消费
        for (int i = 0; i < 10; i++) {
            ex.execute(new Runnable() {
                @Override
                public void run() {
                    while (!isEnd) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        MultiResolve();
                    }
                }
            });
        }
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return doc;
		}
		return doc;
	}

	/**
	 * 多线程抓取
	 * 
	 * @param articleList
	 */
	public void MultiResolve() {
	    
	    final List<Article> articleList = (List<Article>) RedisUtil.getListValueByJson("article",Article.class);
	    
	    if(CollectionUtils.isEmpty(articleList)) {
	        return;
	    }
	    
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(Executors.newFixedThreadPool(10));
		completionService.submit(new Producer(articleList, 1));
	}

	/**
	 * 抓取页面上小说的页面链接
	 * 
	 * @return
	 */
	private void getArticleListByConfig(String baseUrl, String pattern, String title) {
		Document doc = getDoc(baseUrl);
		// 章节链接
		List<Article> articleList = Lists.newArrayList();
		Set<Integer> set = new HashSet<Integer>();
		Integer max = articleMapper.getMaxChapter();
		Elements elements = doc.select(pattern);
		for (Element link : elements) {
			String linkHref = link.attr("href");
			String linkText = link.text();
			String[] temp = linkText.split(" ");
			Article article = new Article();
			article.setHref(baseUrl + linkHref);
			article.setTitle(title);
			article.setChapter(NumberUtil.chineseNumber2Int(temp[0]));
			if (article.getChapter() <= (max == null ? 0 : max.intValue()))
				continue;
			article.setChapterName(temp[1]);
			article.setAddTime(new Date());
			if (set.add(article.getChapter())) {
				articleList.add(article);
			}
			RedisUtil.lpush("article", article);
		}
		isEnd = true;
	}

	// callable类型的生产者
	private class Producer implements Callable<Integer> {
		private List<Article> articleList;
		private int i;

		public Producer(List<Article> articleList, int i) {
			this.articleList = articleList;
			this.i = i;
		}

		@Override
		public Integer call() {
			for (Article article : articleList) {
				String content = getDoc(article.getHref()).select("div#BookText>p").outerHtml();
				article.setContent(content);
				System.out.println(article.getChapterName()+":"+content.substring(0,10));
			}
			//insertBath(articleList);
			System.out.println("线程" + i + "执行完毕！" + new Date());
			return 1;
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
