package com.zql.android.led.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by scott on 2017/6/30.
 */

@Dao
public interface LEDDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLEDEntities(LEDEntity ...entities);

    @Update
    public void updateLEDEntities(LEDEntity ...entities);

    @Delete
    public void deleteLEDEntities(LEDEntity ...entities);

    @Query("select * from LEDEntity")
    public List<LEDEntity> getAllLEDEntities();
}
