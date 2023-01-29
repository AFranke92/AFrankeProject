package android.c196.afrankeproject.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.c196.afrankeproject.Database.Repository;
import android.c196.afrankeproject.R;
import android.c196.afrankeproject.entities.Assessment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    Assessment currentAssessment;
    EditText editType;
    EditText editName;
    EditText editStart;
    EditText editEnd;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    final Calendar myCalenderStart = Calendar.getInstance();
    final Calendar myCalenderEnd = Calendar.getInstance();

    String type;
    String name;
    String start;
    String end;
    int assessmentID;
    int courseID;

    Assessment assessment;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        editType = findViewById(R.id.assessType);
        editName = findViewById(R.id.assessName);
        editStart = findViewById(R.id.editAssessStartDate);
        editEnd = findViewById(R.id.editAssessEndDate);

        String mFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.US);
        editStart.setText(simpleDateFormat.format(new Date()));
        editEnd.setText(simpleDateFormat.format(new Date()));

        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);

        editType.setText(type);
        editName.setText(name);
        editStart.setText(start);
        editEnd.setText(end);

        Spinner typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setSelection(typeAdapter.getPosition(type));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                editType.setText(typeAdapter.getItem(i).toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                editType.setText("Nothing selected");
            }
        });


        repository = new Repository(getApplication());

        Button button = findViewById(R.id.saveAssessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (assessmentID == -1) {

                    assessment = new Assessment(0, editType.getText().toString(), editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                    repository.insert(assessment);
                    Toast.makeText(AssessmentDetails.this, "New assessment was successfully added", Toast.LENGTH_SHORT).show();

                }
                else {

                    assessment = new Assessment(assessmentID, editType.getText().toString(), editName.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), courseID);
                    repository.update(assessment);
                    Toast.makeText(AssessmentDetails.this,"Assessment was successfully updated", Toast.LENGTH_LONG).show();

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
                    myCalenderStart.setTime(simpleDateFormat.parse(information));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalenderStart.get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH), myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();

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

                    myCalenderEnd.setTime(simpleDateFormat.parse(information));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalenderStart.get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH), myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                myCalenderStart.set(Calendar.YEAR, year);
                myCalenderStart.set(Calendar.MONTH, month);
                myCalenderStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStart();

            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                myCalenderEnd.set(Calendar.YEAR, year);
                myCalenderEnd.set(Calendar.MONTH, month);
                myCalenderEnd.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();

            }
        };
    }

    private void updateLabelStart() {

        String mformat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mformat, Locale.US);
        editStart.setText(simpleDateFormat.format(myCalenderStart.getTime()));

    }

    private void updateLabelEnd() {

        String mformat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mformat, Locale.US);
        editEnd.setText(simpleDateFormat.format(myCalenderEnd.getTime()));

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            case R.id.notifyStart:
                boolean aName = true;

                for (Assessment aStart : repository.getAllAssessments()) {

                    if (aStart.getAssessmentID() == assessmentID) {

                        currentAssessment = aStart;
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
                        Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                        intent.putExtra("key", currentAssessment.getAssessmentName() + " is starting today.");
                        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                        aName = false;

                    }
                }

                if (aName) {
                    Toast.makeText(AssessmentDetails.this, "Unable to verify assessment", Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.notifyEnd:
                boolean aName2 = true;

                for (Assessment aEnd : repository.getAllAssessments()) {

                    if (aEnd.getAssessmentID() == assessmentID) {

                        currentAssessment = aEnd;
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
                        Intent intent2 = new Intent(AssessmentDetails.this, MyReceiver.class);
                        intent2.putExtra("key", currentAssessment.getAssessmentName() + " is ending today.");
                        PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent2, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                        aName2 = false;
                    }
                }

                if (aName2) {
                    Toast.makeText(AssessmentDetails.this, "Unable to verify assessment", Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.deleteAssessment:
                boolean assessNotFound = true;
                for (Assessment a : repository.getAllAssessments()) {

                    if (a.getAssessmentID() == assessmentID) {

                        currentAssessment = a;
                        repository.delete(a);
                        Toast.makeText(AssessmentDetails.this, a.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
                        assessNotFound = false;

                    }
                }

                if (assessNotFound) {
                    Toast.makeText(AssessmentDetails.this, "Unable to delete assessment.", Toast.LENGTH_LONG).show();
                }
                return true;

        }
       return super.onOptionsItemSelected(item);
    }

}