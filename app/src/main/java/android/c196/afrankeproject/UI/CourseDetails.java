package android.c196.afrankeproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.c196.afrankeproject.Database.Repository;
import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Assessment;
import android.c196.afrankeproject.entities.Course;
import android.c196.afrankeproject.entities.Term;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    Course currentCourse;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstName;
    EditText editInstPhone;
    EditText editInstEmail;
    EditText editNote;


    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    String name;
    String start;
    String end;
    String status;
    String instName;
    String instPhone;
    String instEmail;
    String note;
    int courseID;
    int termID;

    Course course;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        editName = findViewById(R.id.courseName);
        editStart = findViewById(R.id.editCourseStartDate);
        editEnd = findViewById(R.id.editCourseEndDate);
        editStatus = findViewById(R.id.courseProgress);
        editInstName = findViewById(R.id.courseInstName);
        editInstPhone = findViewById(R.id.courseInstPhone);
        editInstEmail = findViewById(R.id.courseInstEmail);
        editNote = findViewById(R.id.courseNote);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editStart.setText(simpleDateFormat.format(new Date()));
        editEnd.setText(simpleDateFormat.format(new Date()));

        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instName = getIntent().getStringExtra("instName");
        instPhone = getIntent().getStringExtra("instPhone");
        instEmail = getIntent().getStringExtra("instEmail");
        note = getIntent().getStringExtra("note");
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        editName.setText(name);
        editStatus.setText(status);
        editStart.setText(start);
        editEnd.setText(end);
        editInstName.setText(instName);
        editInstPhone.setText(instPhone);
        editInstEmail.setText(instEmail);
        editNote.setText(note);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(repository.getAllAssessments());

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {

            if (a.getCourseID() == courseID) {

                filteredAssessments.add(a);

            }
        }

        Spinner progressSpinner = findViewById(R.id.progress_spinner);
        ArrayAdapter<CharSequence> progressAdapter = ArrayAdapter.createFromResource(this, R.array.progress_array, android.R.layout.simple_spinner_item);
        progressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        progressSpinner.setAdapter(progressAdapter);

        progressSpinner.setSelection(progressAdapter.getPosition(status));

        progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                editStatus.setText(progressAdapter.getItem(i).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                editStatus.setText("Nothing selected");

            }
        });



        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (courseID == -1) {

                    course = new Course(0, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString(), editNote.getText().toString(), termID);
                    repository.insert(course);
                    Toast.makeText(CourseDetails.this, "New course was successfully saved", Toast.LENGTH_LONG).show();

                } else {

                    course = new Course(courseID, editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstName.getText().toString(), editInstPhone.getText().toString(), editInstEmail.getText().toString(), editNote.getText().toString(), termID);
                    repository.update(course);
                    Toast.makeText(CourseDetails.this, "Course was successfully updated", Toast.LENGTH_SHORT).show();

                }
            }
        });

        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Date date;
                String information = editStart.getText().toString();
                if (information.equals("")) {
                    information = "01/01/2023";
                }

                try {
                    myCalendarStart.setTime(simpleDateFormat.parse(information));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Date date;
                String information = editEnd.getText().toString();
                if (information.equals("")) {
                    information = "01/01/2023";
                }

                try {

                    myCalendarEnd.setTime(simpleDateFormat.parse(information));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStart();

            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();

            }
        };

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);

            }
        });
    }

    private void updateLabelStart() {

        String mFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.US);
        editStart.setText(simpleDateFormat.format(myCalendarStart.getTime()));

    }

    private void updateLabelEnd() {

        String mFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.US);
        editEnd.setText(simpleDateFormat.format(myCalendarEnd.getTime()));

    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getAllAssessments()) {

            if (a.getCourseID() == courseID) {

                filteredAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Course Notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.notifyStart:
                boolean cName = true;

                for (Course cStart : repository.getAllCourses()) {

                    if (cStart.getCourseID() == courseID) {

                        currentCourse = cStart;
                        String screenStartDate = editStart.getText().toString();
                        String sFormat = "MM/dd/yy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat, Locale.US);
                        Date sDate = null;
                        try {
                            sDate = simpleDateFormat.parse(screenStartDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Long trigger = sDate.getTime();
                        Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                        intent.putExtra("key", currentCourse.getCourseName() + " is starting today.");
                        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                        cName = false;

                    }
                }
                if (cName) {
                    Toast.makeText(CourseDetails.this, "Unable to verify course", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.notifyEnd:
                boolean cName2 = true;

                for (Course cEnd : repository.getAllCourses()) {

                    if (cEnd.getCourseID() == courseID) {

                        currentCourse = cEnd;
                        String screenEndDate = editEnd.getText().toString();
                        String eFormat = "MM/dd/yy";
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(eFormat, Locale.US);
                        Date eDate = null;
                        try {
                            eDate = simpleDateFormat1.parse(screenEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Long trigger2 = eDate.getTime();
                        Intent intent2 = new Intent(CourseDetails.this, MyReceiver.class);
                        intent2.putExtra("key", currentCourse.getCourseName() + " is ending today.");
                        PendingIntent sender2 = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent2, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                        cName2 = false;

                    }
                }
                if (cName2) {

                    Toast.makeText(CourseDetails.this, "Unable to verify course", Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.deleteCourse:
                boolean courseNotFound = true;
                for (Course c : repository.getAllCourses()) {

                    if (c.getCourseID() == courseID) {

                        currentCourse = c;
                        repository.delete(c);
                        Toast.makeText(CourseDetails.this, c.getCourseName() + " was deleted", Toast.LENGTH_LONG).show();
                        courseNotFound = false;

                    }
                }

                if (courseNotFound) {
                    Toast.makeText(CourseDetails.this, "Unable to delete course", Toast.LENGTH_LONG).show();
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

}