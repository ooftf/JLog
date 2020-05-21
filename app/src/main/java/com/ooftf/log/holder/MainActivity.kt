package com.ooftf.log.holder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ooftf.log.JLog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JLog.e(this)
        JLog.e(intent)
    }
}
