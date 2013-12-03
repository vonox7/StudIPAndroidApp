/*******************************************************************************
 * Copyright (c) 2013 ELAN e.V.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package de.elanev.studip.android.app.frontend.courses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.elanev.studip.android.app.R;
import de.elanev.studip.android.app.backend.db.CoursesContract;
import de.elanev.studip.android.app.backend.db.SemestersContract;
import de.elanev.studip.android.app.widget.ProgressSherlockListFragment;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * @author joern
 */
public class CoursesFragment extends ProgressSherlockListFragment implements
        LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    public static final String TAG = CoursesFragment.class.getSimpleName();
    protected final ContentObserver mObserver = new ContentObserver(
            new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (getActivity() == null) {
                return;
            }

            Loader<Cursor> loader = getLoaderManager().getLoader(0);
            if (loader != null) {
                loader.forceLoad();
            }
        }
    };
    private CoursesAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getSherlockActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.Courses);

        setEmptyMessage(R.string.no_courses);

        mAdapter = new CoursesAdapter(getActivity(),
                new ArrayList<CourseItem>(),
                new ArrayList<Section>());
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.getContentResolver().registerContentObserver(
                CoursesContract.CONTENT_URI, true, mObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseItem item = (CourseItem) mListView.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), CourseViewActivity.class);
        intent.putExtra(CoursesContract.Columns.Courses.COURSE_ID, item.courseId);
        intent.putExtra(CoursesContract.Columns.Courses._ID, item.id);

        mContext.startActivity(intent);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        setLoadingViewVisible(true);
        return new CursorLoader(getActivity(),
                CoursesContract.CONTENT_URI,
                CourseQuery.PROJECTION,
                CoursesContract.Columns.Courses.COURSE_ID
                        + " != "
                        + "'"
                        + getString(R.string.restip_news_global_identifier)
                        + "'",
                null,
                CoursesContract.Qualified.Courses.COURSES_COURSE_DURATION_TIME
                        + " DESC, "
                        + SemestersContract.Qualified.SEMESTERS_SEMESTER_BEGIN
                        + " DESC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (getActivity() == null) {
            return;
        }

        ArrayList<CourseItem> items = new ArrayList<CourseItem>();
        ArrayList<CourseItem> longRunningCourses = new ArrayList<CourseItem>();
        ArrayList<Section> sections = new ArrayList<Section>();
        String prevSemesterId = "";
        String currSemesterId = "";

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String title = cursor.getString(1);
            int type = cursor.getInt(cursor
                    .getColumnIndex(CoursesContract.Columns.Courses.COURSE_TYPE));
            long duration = cursor.getLong(cursor
                    .getColumnIndex(CoursesContract.Columns.Courses.COURSE_DURATION_TIME));
            String color = cursor.getString(cursor
                    .getColumnIndex(CoursesContract.Columns.Courses.COURSE_COLOR));
            long id = cursor.getLong(cursor
                    .getColumnIndex(CoursesContract.Columns.Courses._ID));
            String courseId = cursor.getString(cursor
                    .getColumnIndex(CoursesContract.Columns.Courses.COURSE_ID));
            currSemesterId = cursor.getString(cursor.getColumnIndex(CoursesContract
                    .Columns
                    .Courses
                    .COURSE_SEMESERT_ID));


            if (duration != 0) {
                longRunningCourses.add(new CourseItem(id, courseId, title, type, color));
            } else {
                if (!TextUtils.equals(prevSemesterId, currSemesterId)) {
                    sections.add(new Section(items.size(),
                            cursor.getString(
                                    cursor.getColumnIndex(SemestersContract
                                            .Columns
                                            .SEMESTER_TITLE))));
                }

                items.add(new CourseItem(id, courseId, title, type, color));
            }
            prevSemesterId = currSemesterId;
            cursor.moveToNext();
        }

        if (!longRunningCourses.isEmpty()) {
            sections.add(new Section(items.size(),
                    getString(R.string.course_without_duration_limit)));
            items.addAll(longRunningCourses);
        }

        mAdapter.updateData(items, sections);

        setLoadingViewVisible(false);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    /*
     * Interface which encapsulates the content provider query projection array
     */
    private interface CourseQuery {

        String[] PROJECTION = {CoursesContract.Qualified.Courses.COURSES_ID,
                CoursesContract.Qualified.Courses.COURSES_COURSE_TITLE,
                CoursesContract.Qualified.Courses.COURSES_COURSE_ID,
                CoursesContract.Qualified.Courses.COURSES_COURSE_TYPE,
                CoursesContract.Qualified.Courses.COURSES_COURSE_COLOR,
                CoursesContract.Qualified.Courses.COURSES_COURSE_DURATION_TIME,
                SemestersContract.Qualified.SEMESTERS_SEMESTER_ID,
                SemestersContract.Qualified.SEMESTERS_SEMESTER_TITLE,
                SemestersContract.Qualified.SEMESTERS_SEMESTER_BEGIN};

    }

    private class CoursesAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private final Context mContext;
        private LayoutInflater mInflater;
        private List<CourseItem> mObjects;
        private List<Section> mSections;

        public CoursesAdapter(Context context,
                              List<CourseItem> objects,
                              List<Section> sections) {
            this.mContext = context;
            this.mObjects = objects;
            this.mInflater = LayoutInflater.from(context);
            this.mSections = sections;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                row = mInflater.inflate(R.layout.list_item_single_text_icon, parent, false);
                CourseHolder holder = new CourseHolder();
                holder.title = (TextView) row.findViewById(R.id.text1);
                holder.icon = (ImageView) row.findViewById(R.id.icon1);
                row.setTag(holder);
            }

            // Get resources for the position
            String title = mObjects.get(position).title;
            int type = mObjects.get(position).type;
            String color = mObjects.get(position).color;

            // get holder and update views with positions informations
            CourseHolder holder = (CourseHolder) row.getTag();
            holder.title.setText(title);
            if (type == 99) {
                if (TextUtils.equals(color, "#ffffff"))
                    holder.icon.setImageResource(R.drawable.ic_studygroup_blue);
                else
                    holder.icon.setImageResource(R.drawable.ic_studygroup);
            } else {
                if (TextUtils.equals(color, "#ffffff"))
                    holder.icon.setImageResource(R.drawable.ic_seminar_blue);
                else
                    holder.icon.setImageResource(R.drawable.ic_menu_courses);
            }

            if (color != null)
                try {
                    int c = Color.parseColor(color);
                    holder.icon.setBackgroundColor(c);
                } catch (Exception e) {
                    Log.wtf(TAG, e.getMessage());
                }

            return row;
        }

        @Override
        public int getCount() {
            return mObjects == null ? 0 : mObjects.size();
        }

        @Override
        public CourseItem getItem(int position) {
            return mObjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEmpty() {
            return mObjects.isEmpty();
        }

        @Override
        public View getHeaderView(int position, View view, ViewGroup viewGroup) {
            HeaderViewHolder holder;

            if (view == null) {
                holder = new HeaderViewHolder();
                view = mInflater.inflate(R.layout.list_item_header, viewGroup, false);
                holder.text = (TextView) view.findViewById(R.id.list_item_header_textview);
                view.setTag(holder);
            } else {
                holder = (HeaderViewHolder) view.getTag();
            }

            int headerPos = (int) getHeaderId(position);
            String headerText = mSections.get(headerPos).title;
            holder.text.setText(headerText);

            return view;
        }

        @Override
        public long getHeaderId(int position) {
            if (mSections.isEmpty())
                return 0;

            for (int i = 0; i < mSections.size(); i++) {
                if (position < mSections.get(i).index) {
                    return i - 1;
                }
            }
            return mSections.size() - 1;
        }

        public void updateData(List<CourseItem> items, List<Section> sections) {
            mObjects.clear();
            mSections.clear();
            mObjects.addAll(items);
            mSections.addAll(sections);
            this.notifyDataSetChanged();
        }

        class CourseHolder {
            ImageView icon;
            TextView title;
        }

        class HeaderViewHolder {
            TextView text;
        }
    }

    private static class CourseItem {
        public String title, color, courseId;
        public int type;
        long id;

        public CourseItem() {
        }


        public CourseItem(long id, String courseId, String title, int type, String color) {
            this.title = title;
            this.type = type;
            this.color = color;
            this.id = id;
            this.courseId = courseId;
        }
    }

    /**
     * A section for the adapter, has to have a title and a section starting index
     */
    public static class Section {
        String title;
        int index;

        public Section(int index, String title) {
            this.index = index;
            this.title = title;
        }
    }
}


