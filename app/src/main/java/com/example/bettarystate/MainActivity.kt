package com.example.bettarystate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private var text_view:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        
        text_view=findViewById(R.id.percent_view)

        this.registerReceiver(myBroadcastReceiver,intentFilter)
    }
   private val myBroadcastReceiver = object : BroadcastReceiver(){
       override fun onReceive(context: Context, intent: Intent) {

       val stringBuilder = StringBuilder()
           val batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0)
           stringBuilder.append("Battery Percentage:\n$batteryPercentage %\n")
           stringBuilder.append("\nBattery Condition:\n")
           when(intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0)){
               BatteryManager.BATTERY_HEALTH_OVERHEAT ->stringBuilder.append("over heat\n")
               BatteryManager.BATTERY_HEALTH_GOOD ->stringBuilder.append("Good\n")
               BatteryManager.BATTERY_HEALTH_COLD ->stringBuilder.append("Cold\n")
               BatteryManager.BATTERY_HEALTH_DEAD ->stringBuilder.append("Dead\n")
               BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ->stringBuilder.append("Over Voltage\n")
               BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE ->stringBuilder.append("Failure\n")
               else -> stringBuilder.append("Unknown\n")
           }

           stringBuilder.append("\nCharging Status:\n")
           when(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){
               BatteryManager.BATTERY_STATUS_CHARGING ->stringBuilder.append("Charging\n")
               BatteryManager.BATTERY_STATUS_DISCHARGING ->stringBuilder.append("DisCharging\n")
               BatteryManager.BATTERY_STATUS_FULL ->stringBuilder.append("Full Charge\n")
               BatteryManager.BATTERY_STATUS_NOT_CHARGING ->stringBuilder.append("Not Charging\n")
               BatteryManager.BATTERY_STATUS_UNKNOWN ->stringBuilder.append("Unknown\n")
               else ->stringBuilder.append("No Charge Status\n")
           }
           text_view?.text =stringBuilder
       }
       }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }
   }

