package android.c196.afrankeproject.UI;

import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Assessment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {


    public class AssessmentViewHolder extends RecyclerView.ViewHolder {


        private final TextView name;
        private final TextView start;
        private final TextView end;

        private AssessmentViewHolder(View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.textViewAssessmentName);
            start = itemView.findViewById(R.id.textViewAssessmentStart);
            end = itemView.findViewById(R.id.textViewAssessmentEnd);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("start", current.getAssessmentStart());
                    intent.putExtra("end", current.getAssessmentEnd());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);

                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter (Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {

        if (mAssessments != null) {

            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            String start = current.getAssessmentStart();
            String end = current.getAssessmentEnd();

            holder.name.setText(name);
            holder.start.setText(start);
            holder.end.setText(end);

        }
        else {

            holder.name.setText("No assessment name");
            holder.start.setText("No assessment start");
            holder.end.setText("No assessment end");

        }
    }

    public void setAssessments(List<Assessment> assessments) {

        mAssessments = assessments;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mAssessments.size();

    }

}
