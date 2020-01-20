package com.controlerapp

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var pendingIntent: PendingIntent
    private lateinit var manager : AlarmManager
    private lateinit var hardwareProperties: HardwareProperties

    private var ram_text: TextView? = null
    private var garbage_collector_text: TextView? = null
    private var battery_text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hardwareProperties = HardwareProperties(this)

        initComponents()
    }

    fun activeOrDisableAdmin(view: View)
    {
        hardwareProperties.saveAvailableRam()
        hardwareProperties.saveGarbageCollector()
        hardwareProperties.saveStatusBattery()

        ram_text?.setText(hardwareProperties.getRam())
        garbage_collector_text?.setText(hardwareProperties.getGarbageCollector())
        battery_text?.setText(hardwareProperties.getBattery())
    }

    fun initComponents()
    {
        ram_text = findViewById<TextView>(R.id.ram_text)
        garbage_collector_text = findViewById<TextView>(R.id.garbage_collector_text)
        battery_text = findViewById<TextView>(R.id.battery_text)

        ram_text?.setText(hardwareProperties.getRam())
        garbage_collector_text?.setText(hardwareProperties.getGarbageCollector())
        battery_text?.setText(hardwareProperties.getBattery())
    }
}
