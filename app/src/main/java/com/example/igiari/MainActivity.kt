package com.example.igiari

import android.app.Activity
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.igiari.ui.theme.IgiariTheme

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : ComponentActivity() {
    lateinit var sensorHelper: SensorManagerHelper
    var index = 0
    val mapping = arrayOf(arrayOf(R.raw.igiari, R.mipmap.igiari_popup_foreground, Color.parseColor("#395c94")),
        arrayOf(R.raw.matta, R.mipmap.matta_popup_foreground, Color.parseColor("#395c94")),
        arrayOf(R.raw.kurae, R.mipmap.kurae_popup_foreground, Color.parseColor("#395c94")))
    var sound = mapping[index][0]
    var image = mapping[index][1]
    var color = mapping[index][2]


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<ImageButton>(R.id.activeSound)
        val change = findViewById<ImageButton>(R.id.change)
        sensorHelper = SensorManagerHelper(this)
        sensorHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {
            override fun onShake() {
                active(button)
            }
        })
        button.setOnClickListener {
            active(button)
        }
        change.setOnClickListener {
            index = if (index == 2) 0 else index + 1
            sound = mapping[index][0]
            image = mapping[index][1]
            color = mapping[index][2]
            change.setImageResource(image)
        }
    }

    fun active(view: ImageButton) {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(view.context, Uri.parse("android.resource://" + view.context.packageName + "/" + sound))
        mediaPlayer.prepare()
        mediaPlayer.setOnPreparedListener {
            view.setImageResource(image)
            view.setBackgroundColor(color)
        }
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
            view.setImageResource(0)
            view.setBackgroundColor(Color.WHITE)
        }
    }
}