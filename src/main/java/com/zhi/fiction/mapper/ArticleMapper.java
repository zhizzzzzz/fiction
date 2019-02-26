package com.zhi.fiction.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhi.fiction.model.Article;

public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    void insertBatch(@Param("list") List<Article> articleList);

    Integer getMaxChapter();
    
    Integer countChapter();

    Integer getCount();
}