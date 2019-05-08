package com.zhi.fiction.controller;

import java.io.IOException;
import java.util.HashMap;

import org.redisson.api.RBuckets;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhi.fiction.biz.CrawlerBiz;
import com.zhi.fiction.util.RedissonUtil;

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
    private CrawlerBiz   crawlerBiz;

    @Autowired
    private RedissonUtil redissonUtil;

    @ResponseBody
    @RequestMapping(value = "/findSource")
    public void findSource() {
        crawlerBiz.resolveHtmlStr();
    }

    @RequestMapping(value = "/test")
    public void test() {

        try {
            RedissonClient redisson = redissonUtil.redissonClient();
            HashMap m = new HashMap();
            m.put("zhitest", "1312");
            RBuckets rb = redisson.getBuckets();
            rb.set(m);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
