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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {

    Term currentTerm;
    EditText editName;
    EditText editStart;
    EditText editEnd;

    String name;
    String start;
    String end;
    int id;
    int numCourses;

    Term term;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

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
                    Toast.makeText(TermDetails.this, "New term successfully added", Toast.LENGTH_LONG).show();

                }
                else {

                    term = new Term(id, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
                    repository.update(term);
                    Toast.makeText(TermDetails.this, "Term successfully updated", Toast.LENGTH_LONG).show();

                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("termID", id);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();

        for (Course c : repository.getAllCourses()) {

            if (c.getTermID() == id) {

                filteredCourses.add(c);
            }
        }
        courseAdapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.deleteterm, menu);
        return true;

    }

    public boolean onOptionsItemSelected (MenuItem item) {

        switch (item.getItemId()) {

            case R.id.deleteTerm:
                for (Term t : repository.getAllTerms()) {
                    if (t.getTermID() == id) {
                        currentTerm = t;
                    }
                }
                numCourses = 0;
                for (Course c : repository.getAllCourses()) {
                    if (c.getTermID() == id) {
                        ++numCourses;
                    }
                }
                if (numCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(TermDetails.this, "Can not delete a term with courses", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}