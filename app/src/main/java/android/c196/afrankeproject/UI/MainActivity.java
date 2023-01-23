package android.c196.afrankeproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.appsearch.ReportSystemUsageRequest;
import android.c196.afrankeproject.Database.Repository;
import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Assessment;
import android.c196.afrankeproject.entities.Course;
import android.c196.afrankeproject.entities.Term;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

//          Term term = new Term(0, "Term 1", "01/01/2023", "06/30/2023");
//          Repository repository = new Repository(getApplication());
//          repository.insert(term);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.addSampleData:
                Term term = new Term(0, "Term 1", "01/01/2023", "06/30/2023");
                Repository repository = new Repository(getApplication());
                repository.insert(term);
                Course course = new Course(0, "Algebra", "01/10/2023", "02/01/2023", "In Progress", "Abigail Franke", "6146200230", "afra480@wgu.edu", "", 1);
                repository.insert(course);
                Assessment assessment = new Assessment(0, "Performance", "01/15/2023", "01/15/2023", 1);
                repository.insert(assessment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}