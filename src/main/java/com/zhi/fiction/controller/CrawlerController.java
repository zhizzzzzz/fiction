package com.zhi.fiction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhi.fiction.biz.CrawlerBiz;

/** 
* @author 作者 yongzhi.zhao: 
* @version 创建时间：2017年11月10日 上午10:48:15 
* 类说明 
*/
@RestController
@CrossOrigin
@RequestMapping("crawler")
public class CrawlerController {

    @Autowired
    private CrawlerBiz crawlerBiz;

    @ResponseBody
    @RequestMapping(value = "/findSource")
    public void findSource() {
        crawlerBiz.resolveHtmlStr();
    }

}
