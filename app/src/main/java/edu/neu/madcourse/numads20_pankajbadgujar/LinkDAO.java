package edu.neu.madcourse.numads20_pankajbadgujar;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LinkDAO {

    @Insert
    void addLink(Link link);

    @Update
    void updateLink(Link link);

    @Delete
    void deleteLink(Link link);

    @Query("DELETE FROM links")
    void deleteAllLinks();

    @Query("SELECT * FROM links")
    LiveData<List<Link>> getAllLinks();


}
