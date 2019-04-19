package com.zhi.fiction.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhi.fiction.model.Article;

public interface ArticleMapper {


    int insertSelective(Article record);


    void insertBatch(@Param("list") List<Article> articleList);

}