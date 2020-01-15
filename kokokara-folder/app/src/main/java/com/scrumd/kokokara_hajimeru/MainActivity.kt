package com.scrumd.kokokara_hajimeru

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Button
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val startTime:Long = 30000

    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: Button? = null
    private var mButtonReset: Button? = null

    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning:Boolean = false

    private var mTimeLeftInMillis = startTime


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        println("mTimerRunningの初期値は？ $mTimerRunning")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextViewCountDown = findViewById(R.id.text_view_countdown)
        mButtonStartPause = findViewById(R.id.button_start_pause)
        mButtonReset = findViewById(R.id.button_reset)

        mButtonStartPause.setOnClickListener(View.OnClickListener {
            println("mTimerRunningの値は？ $mTimerRunning")
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        })

        mButtonReset.setOnClickListener(View.OnClickListener { resetTimer() })

        updateCountDownText()

    }

    private fun startTimer() {
        mCountDownTimer = object:CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished:Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }
            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause?.text = "スタート"
                mButtonReset?.visibility = View.INVISIBLE
            }
        }.start()
        mTimerRunning = true
        mButtonStartPause?.text = "一時停止"
        mButtonReset?.visibility = View.INVISIBLE
    }

    private fun pauseTimer() {
        println("一時停止処理前のmTimerRunningは？ $mTimerRunning")
        mCountDownTimer?.cancel()
        mTimerRunning = false
        println("一時停止処理後のmTimerRunningは？ $mTimerRunning")
        mButtonStartPause?.text = "スタート"
        mButtonReset?.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        mTimeLeftInMillis = startTime
        updateCountDownText()
        mButtonStartPause?.visibility = View.VISIBLE
        mButtonReset?.visibility = View.INVISIBLE
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000) as Int / 60
        val seconds = (mTimeLeftInMillis / 1000) as Int % 60
        val timerLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown?.text = timerLeftFormatted
    }
}
