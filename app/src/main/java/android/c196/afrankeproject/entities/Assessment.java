package android.c196.afrankeproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private String assessmentStart;
    private String assessmentEnd;
    private int courseID;

    public Assessment(int assessmentID, String assessmentName, String assessmentStart, String assessmentEnd, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.courseID = courseID;
    }

    public Assessment() {

    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
