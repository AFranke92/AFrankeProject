package android.c196.afrankeproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseIntName;
    private String courseIntPhone;
    private String courseIntEmail;
    private String courseNote;
    private int termID;

    public Course(int courseID, String courseName, String courseStart, String courseEnd, String courseStatus, String courseIntName, String courseIntPhone, String courseIntEmail, String courseNote, int termID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseIntName = courseIntName;
        this.courseIntPhone = courseIntPhone;
        this.courseIntEmail = courseIntEmail;
        this.courseNote = courseNote;
        this.termID = termID;
    }

    public Course() {

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseIntName() {
        return courseIntName;
    }

    public void setCourseIntName(String courseIntName) {
        this.courseIntName = courseIntName;
    }

    public String getCourseIntPhone() {
        return courseIntPhone;
    }

    public void setCourseIntPhone(String courseIntPhone) {
        this.courseIntPhone = courseIntPhone;
    }

    public String getCourseIntEmail() {
        return courseIntEmail;
    }

    public void setCourseIntEmail(String courseIntEmail) {
        this.courseIntEmail = courseIntEmail;
    }

    public String getCourseNote() {
        return courseNote;
    }
    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

}
