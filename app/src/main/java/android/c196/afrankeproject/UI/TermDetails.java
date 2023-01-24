package android.c196.afrankeproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.c196.afrankeproject.Database.Repository;
import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Course;
import android.c196.afrankeproject.entities.Term;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    EditText editName;
    EditText editStart;
    EditText editEnd;

    String name;
    String start;
    String end;
    int id;

    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.termName);
        editStart = findViewById(R.id.termStart);
        editEnd = findViewById(R.id.termEnd);

        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        id = getIntent().getIntExtra("id", -1);

        editName.setText(name);
        editStart.setText(start);
        editEnd.setText(end);

        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(repository.getAllCourses());

        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getAllCourses()) {

            if (c.getTermID() == id) {

                filteredCourses.add(c);

            }
        }

        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1){

                    term = new Term(0, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.insert(term);
                    //Toast.makeText(this, "Term is saved", Toast.LENGTH_LONG).show());

                }
                else {

                    term = new Term(id, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);


                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);

            }
        });
    }
}