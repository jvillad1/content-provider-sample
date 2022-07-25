package com.jvillad1.contentprovidersample.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.jvillad1.contentprovidersample.BuildConfig

class RoomContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val context = context ?: return null
        val androidIdDao = AndroidIdDatabase.getInstance(context).androidIdDao()
        val cursor: Cursor = androidIdDao.getAndroidId()
        cursor.setNotificationUri(context.contentResolver, uri)

        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val context = context ?: return null
        val androidIdEntity = AndroidIdEntity.fromContentValues(values) ?: return null

        val id: Long = AndroidIdDatabase.getInstance(context)
            .androidIdDao()
            .insertAndroidId(androidIdEntity)
        context.contentResolver.notifyChange(uri, null)

        return ContentUris.withAppendedId(uri, id)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.android_id_provider"
        val uri: Uri = Uri.parse("content://$AUTHORITY/AndroidId")
    }
}
