package com.zhi.fiction.model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

    private static final long serialVersionUID = -4669259891062048042L;

    private Long              id;

    private String            title;

    private String            chapter;

    private Date              addTime;

    private String            content;

    private String            href;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}