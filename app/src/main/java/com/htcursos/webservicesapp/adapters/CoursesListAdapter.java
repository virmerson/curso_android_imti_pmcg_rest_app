package com.htcursos.webservicesapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.htcursos.webservicesapp.R;
import com.htcursos.webservicesapp.models.Course;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CoursesListAdapter extends ArrayAdapter<Course> {

    public CoursesListAdapter(Context context, List<Course> courses) {
        super(context, R.layout.item_courses_list, courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View
                    .inflate(getContext(), R.layout.item_courses_list, null);
            holder = new ViewHolder();
            ButterKnife.bind(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Course course = getItem(position);
        if(course != null){
            holder.courseName.setText(course.getTitle());
        }
        return convertView;
    }

    public class ViewHolder{

        @Bind(R.id.name)
        TextView courseName;

    }
}
