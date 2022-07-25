package com.jvillad1.contentprovidersample.data

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AndroidIdEntity.TABLE_NAME)
data class AndroidIdEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = ANDROID_ID_COLUMN_NAME) val androidId: String
) {

    companion object {
        const val TABLE_NAME = "AndroidId"
        const val ANDROID_ID_COLUMN_NAME = "androidId"

        fun fromContentValues(values: ContentValues?): AndroidIdEntity? {
            values ?: return null
            return if (values.containsKey(ANDROID_ID_COLUMN_NAME)) {
                return AndroidIdEntity(androidId = values.getAsString(ANDROID_ID_COLUMN_NAME))
            } else {
                null
            }
        }
    }
}
