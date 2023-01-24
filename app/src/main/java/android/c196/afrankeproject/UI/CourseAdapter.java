package android.c196.afrankeproject.UI;

import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Course;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseName;
        private final TextView courseStart;
        private final TextView courseEnd;
        private final TextView courseStatus;
        private final TextView courseInstName;
        private final TextView courseInstPhone;
        private final TextView courseInstEmail;
        private final TextView courseNote;

        private CourseViewHolder(View itemView) {

            super(itemView);
            courseName = itemView.findViewById(R.id.textViewCourseName);
            courseStart = itemView.findViewById(R.id.textViewCourseStart);
            courseEnd = itemView.findViewById(R.id.textViewCourseEnd);
            courseStatus = itemView.findViewById(R.id.textViewCourseStatus);
            courseInstName = itemView.findViewById(R.id.textViewCourseInstName);
            courseInstPhone = itemView.findViewById(R.id.textViewCourseInstPhone);
            courseInstEmail = itemView.findViewById(R.id.textViewCourseInstEmail);
            courseNote = itemView.findViewById(R.id.textViewCourseNote);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("instName", current.getCourseIntName());
                    intent.putExtra("instPhone", current.getCourseIntPhone());
                    intent.putExtra("instEmail", current.getCourseIntEmail());
                    intent.putExtra("note", current.getCourseNote());
                    //intent.putExtra("termId", current.getTermID());
                    context.startActivity(intent);

                }
            });

        }

    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter (Context context) {

        mInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {

        if (mCourses != null) {

            Course current = mCourses.get(position);
            String name = current.getCourseName();
            String start = current.getCourseStart();
            String end = current.getCourseEnd();
            String status = current.getCourseStatus();
            String instName = current.getCourseIntName();
            String instPhone = current.getCourseIntPhone();
            String instEmail = current.getCourseIntEmail();
            String note = current.getCourseNote();
            holder.courseName.setText(name);
            holder.courseStart.setText(start);
            holder.courseEnd.setText(end);
            holder.courseStatus.setText(status);
            holder.courseInstName.setText(instName);
            holder.courseInstPhone.setText(instPhone);
            holder.courseInstEmail.setText(instEmail);
            holder.courseNote.setText(note);

        }
        else {

            holder.courseName.setText("No course name");
            holder.courseStart.setText("No course start");
            holder.courseEnd.setText("No course end");
            holder.courseStatus.setText("No course status");
            holder.courseInstName.setText("No course instructor name");
            holder.courseInstPhone.setText("No course instructor phone");
            holder.courseInstEmail.setText("No course instructor email");
            holder.courseNote.setText("No course note");

        }

    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mCourses.size();

    }

}
