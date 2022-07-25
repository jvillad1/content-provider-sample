package com.jvillad1.contentprovidersample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AndroidIdEntity::class], version = 1)
abstract class AndroidIdDatabase : RoomDatabase() {

    abstract fun androidIdDao(): AndroidIdDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AndroidIdDatabase? = null

        fun getInstance(context: Context): AndroidIdDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    it.populateInitialData()
                    instance = it
                }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AndroidIdDatabase = Room
            .databaseBuilder(context, AndroidIdDatabase::class.java, "android-id-db")
            .allowMainThreadQueries()
            .build()
    }

    private fun populateInitialData() {
        androidIdDao().insertAndroidId(AndroidIdEntity(androidId = createRandomId()))
    }

    private fun createRandomId(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..16)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
