package com.controlerapp

import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.*

class HardwareProperties (private  val context: Context){

    companion object{
        private const val PREFS_NAME = "com.controlerapp.ControllerWidget"
        private const val PREF_GARBAGE_COLLECTOR = "garbage_collector"
        private const val PREF_BATTERY = "battery"
        private const val PREF_RAM = "ram"
    }

    fun getRam() : String?
    {
        return loadFromPreferences(PREF_RAM)
    }

    fun getBattery() : String?
    {
        return loadFromPreferences(PREF_BATTERY)
    }

    fun getGarbageCollector() : String?
    {
        return loadFromPreferences(PREF_GARBAGE_COLLECTOR)
    }

    fun saveGarbageCollector()
    {
       var freeSpaceInGarbageCollectorProcent =   (Runtime.getRuntime().freeMemory().toDouble()
             .div(Runtime.getRuntime().totalMemory().toDouble())).toDouble() * 100

        saveToPreferences(PREF_GARBAGE_COLLECTOR, String.format("%.2f", freeSpaceInGarbageCollectorProcent)+" %")
    }


    fun saveAvailableRam() {
        var availableMemory = getAvailableMemory().availMem
        var averageMemory = getAvailableMemory().totalMem


        var procentInMemory : Double
        procentInMemory = (availableMemory.toDouble().div(averageMemory.toDouble())).toDouble()

        saveToPreferences(PREF_RAM, String.format("%.2f", procentInMemory)+" %")
    }

    fun saveStatusBattery() {

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val batteryPct: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }

        saveToPreferences(PREF_BATTERY, batteryPct.toString() + "%")
    }

    private fun getAvailableMemory(): ActivityManager.MemoryInfo {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        return ActivityManager.MemoryInfo().also { memoryInfo ->
            activityManager.getMemoryInfo(memoryInfo)
        }
    }

    private fun loadFromPreferences(valueName: String): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getString(valueName, "")
    }


    private fun saveToPreferences(valueName: String, value: String) {
        val prefs =  context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putString(valueName, value)
        prefs.apply()
    }

}