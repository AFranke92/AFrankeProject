package android.c196.afrankeproject.dao;

import android.c196.afrankeproject.entities.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getAllCourses();


//    Maybe use for getting associated list
//    @Query("SELECT * FROM courses WHERE termID = :termID ORDER BY courseID ASC")
//    List<Course> getAllAssociatedCourses(int termID);

}
