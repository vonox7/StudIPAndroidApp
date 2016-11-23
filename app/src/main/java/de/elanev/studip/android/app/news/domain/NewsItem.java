/*
 * Copyright (c) 2016 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package de.elanev.studip.android.app.news.domain;

import de.elanev.studip.android.app.data.datamodel.Course;
import de.elanev.studip.android.app.user.domain.User;

/**
 * @author joern
 */
public class NewsItem {
  // Required property news id
  private final String newsId;
  private String title;
  private Long date;
  private String body;
  private User author;
  private String mRange;
  private Course course;

  public NewsItem(String newsId) {this.newsId = newsId;}

  @Override public String toString() {
    String newsStr = "**************** News **************\n";
    newsStr += "id=" + this.getNewsId() + "\n";
    newsStr += "topic=" + this.getTitle() + "\n";
    newsStr += "date=" + this.getDate() + "\n";
    newsStr += "body=" + this.getBody() + "\n";
    newsStr += "author=" + this.getAuthor() + "\n";
    newsStr += "course=" + this.getCourse() + "\n";
    newsStr += "**************************************";

    return newsStr;
  }

  public String getNewsId() {
    return newsId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getRange() {
    return mRange;
  }

  public void setRange(String range) {
    mRange = range;
  }
}