package com.jvillad1.contentprovidersample

import android.R
import android.content.ContentResolver
import android.content.ContentValues
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jvillad1.contentprovidersample.data.AndroidIdEntity
import com.jvillad1.contentprovidersample.data.RoomContentProvider
import com.jvillad1.contentprovidersample.ui.theme.ContentProviderSampleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentProviderSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val showAndroidId = remember { mutableStateOf(false) }

                    ButtonView(content = { showAndroidId.value = true })

                    if (showAndroidId.value) {
                        val (name, stored) = getAndroidId(contentResolver)
                        Greeting(name, stored)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, stored: Boolean) {
    if (stored) {
        Text(text = "Android Id Stored is: $name")
    } else {
        Text(text = "Android Id Created is: $name")
    }
}

@Composable
fun ButtonView(
    content: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(),
        onClick = { content() }
    ) {
        Text(
            text = "Retrieve Android Id",
            fontSize = 16.sp
        )
    }
}

private fun getAndroidId(contentResolver: ContentResolver): Pair<String, Boolean> {
    val cursor = contentResolver.query(
        RoomContentProvider.uri,
        null,
        null,
        null,
        null
    )

    return if (cursor != null && cursor.moveToFirst()) {
        val strBuild = StringBuilder()
        while (!cursor.isAfterLast) {
            val columnIndex = cursor.getColumnIndex(AndroidIdEntity.ANDROID_ID_COLUMN_NAME)
            check(columnIndex > 0)

            strBuild.append(cursor.getString(columnIndex).trimIndent())
            cursor.moveToNext()
        }

        cursor.close()
        Pair(strBuild.toString(), true)
    } else {
        cursor?.close()
        val randomAndroidId = createRandomId()

        ContentValues().also { values ->
            values.put(AndroidIdEntity.ANDROID_ID_COLUMN_NAME, randomAndroidId)
            contentResolver.insert(RoomContentProvider.uri, values)
        }

        Pair(createRandomId(), false)
    }
}

private fun createRandomId(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..16)
        .map { allowedChars.random() }
        .joinToString("")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContentProviderSampleTheme {
        Greeting("Android", true)
    }
}
