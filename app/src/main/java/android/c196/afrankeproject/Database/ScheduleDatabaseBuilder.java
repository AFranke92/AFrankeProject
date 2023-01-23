package android.c196.afrankeproject.Database;

import android.c196.afrankeproject.dao.AssessmentDAO;
import android.c196.afrankeproject.dao.CourseDAO;
import android.c196.afrankeproject.dao.TermDAO;
import android.c196.afrankeproject.entities.Assessment;
import android.c196.afrankeproject.entities.Course;
import android.c196.afrankeproject.entities.Term;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class ScheduleDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile ScheduleDatabaseBuilder INSTANCE;

    static ScheduleDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScheduleDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabaseBuilder.class, "SchoolScheduler.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
