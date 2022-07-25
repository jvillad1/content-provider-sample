package com.jvillad1.contentprovidersample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    val cursor = contentResolver.query(
                        RoomContentProvider.uri,
                        null,
                        null,
                        null,
                        null
                    )

                    if (cursor != null && cursor.moveToFirst()) {
                        val strBuild = StringBuilder()
                        while (!cursor.isAfterLast) {
                            val columnIndex = cursor.getColumnIndex(AndroidIdEntity.ANDROID_ID_COLUMN_NAME)
                            check(columnIndex > 0)

                            strBuild.append(
                                cursor.getString(columnIndex).trimIndent()
                            )
                            cursor.moveToNext()
                        }
                        Greeting(name = strBuild.toString())
                    } else {
                        Greeting(name = "No Records Found")
                    }

                    cursor?.close()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Android Id Stored: $name")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContentProviderSampleTheme {
        Greeting("Android")
    }
}
