/*******************************************************************************
 * Copyright (c) 2013 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package de.elanev.studip.android.app.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;


import de.elanev.studip.android.app.R;
import de.elanev.studip.android.app.util.ApiUtils;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by joern on 09.10.13.
 */
public class ProgressListFragment extends Fragment implements
    StickyListHeadersListView.OnStickyHeaderOffsetChangedListener {

  protected Context mContext;
  protected StickyListHeadersListView mListView;
  protected SwipeRefreshLayout mSwipeRefreshLayoutListView;
  private TextView mEmptyMessageTextView;

  public ProgressListFragment() {}

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = getActivity();

  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.list, container, false);
    mEmptyMessageTextView = (TextView) v.findViewById(R.id.empty);
    mListView = (StickyListHeadersListView) v.findViewById(R.id.list);
    mListView.setEmptyView(mEmptyMessageTextView);
    mSwipeRefreshLayoutListView = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);
    mSwipeRefreshLayoutListView.setColorSchemeResources(android.R.color.white,
        R.color.studip_mobile_light,
        R.color.studip_mobile_dark,
        R.color.studip_mobile_darker);

    return v;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    mListView.setDrawingListUnderStickyHeader(true);
    mListView.setAreHeadersSticky(true);
    mListView.setOnStickyHeaderOffsetChangedListener(this);
  }

  /**
   * Sets the message resource to be displayed when the ListView is empty
   *
   * @param messageRes string resource for the empty message
   */
  protected void setEmptyMessage(int messageRes) {
    mEmptyMessageTextView.setText(messageRes);
  }

  /**
   * Toggles the visibility of the list container and progress bar
   *
   * @param visible progress bar visibility
   */
  protected void setLoadingViewVisible(boolean visible) {
    //TODO: REMOVE, NOT USED ANYMORE
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
  public void onStickyHeaderOffsetChanged(StickyListHeadersListView stickyListHeadersListView,
      View view,
      int offset) {
    if (ApiUtils.isOverApi11()) view.setAlpha(1 - (offset / (float) view.getMeasuredHeight()));
  }

}
