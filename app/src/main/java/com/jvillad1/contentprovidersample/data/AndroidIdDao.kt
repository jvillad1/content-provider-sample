package com.jvillad1.contentprovidersample.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AndroidIdDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndroidId(androidIdEntity: AndroidIdEntity): Long

    @Query("SELECT * FROM AndroidId LIMIT 1")
    fun getAndroidId(): Cursor
}
