/*
 * Copyright (c) 2016 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package de.elanev.studip.android.app.news.repository;

/**
 * @author joern
 */
public class UserModel {
  private String mTitlePre;
  private String mFirstName;
  private String mLastName;
  private String mTitlePost;
  private String mUserImageUrl;

  public UserModel(String titlePre, String firstName, String lastName, String titlePost,
      String userImageUrl) {
    mTitlePre = titlePre;
    mFirstName = firstName;
    mLastName = lastName;
    mTitlePost = titlePost;
    mUserImageUrl = userImageUrl;
  }

  public UserModel() {}

  public String getTitlePre() {
    return mTitlePre;
  }

  public void setTitlePre(String titlePre) {
    mTitlePre = titlePre;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public void setFirstName(String firstName) {
    mFirstName = firstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public void setLastName(String lastName) {
    mLastName = lastName;
  }

  public String getTitlePost() {
    return mTitlePost;
  }

  public void setTitlePost(String titlePost) {
    mTitlePost = titlePost;
  }

  public String getFullName() {
    return mTitlePre + " " + mFirstName + " " + mLastName + " " + mTitlePost;
  }

  public String getUserImageUrl() {
    return mUserImageUrl;
  }

  public void setUserImageUrl(String userImageUrl) {
    mUserImageUrl = userImageUrl;
  }
}
