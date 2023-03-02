package com.example.clapapp

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar : SeekBar
    private lateinit var runnable: Runnable
    private lateinit var  handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.sbSong)
        handler = Handler(Looper.getMainLooper())
        mediaPlayer = MediaPlayer.create(this, R.raw.piano)
        val buttonPlay = findViewById<FloatingActionButton>(R.id.fabPlay)
        val buttonCheckVideo = findViewById<Button>(R.id.btnVideo)

        //intent for second activity
        val intent = Intent(this, VideoActivity::class.java)
        buttonCheckVideo.setOnClickListener{
            startActivity(intent)
        }

        buttonPlay.setOnClickListener{
          val currentPos =  mediaPlayer.currentPosition
            when {
                mediaPlayer.isPlaying -> {
                    mediaPlayer.pause()
                    buttonPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play))
                }
                else -> {
                    mediaPlayer.start()
                    initializeSeekBar()
                    buttonPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_pause))
                }
            }

        }
    }

    private fun initializeSeekBar() {
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }
        })
        val tvPlayed = findViewById<TextView>(R.id.tvPlay)
        val tvDue = findViewById<TextView>(R.id.tvDue)
        seekBar.max = mediaPlayer.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
            val playedTime = mediaPlayer.currentPosition / 1000
            tvPlayed.text = "${playedTime.toString()} sec"
            val totalTime = mediaPlayer.duration / 1000
            val duePlay = totalTime - playedTime
            tvDue.text ="-${ duePlay.toString()} sec"
        }
        handler.postDelayed(runnable,1000)
    }
}