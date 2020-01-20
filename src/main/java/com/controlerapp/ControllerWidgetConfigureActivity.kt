package com.controlerapp

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.controlerapp.ControllerWidget.Companion.updateAppWidget

/**
 * The configuration screen for the [ControllerWidget] AppWidget.
 */
class ControllerWidgetConfigureActivity : Activity() {
    private lateinit var appWidgetText: EditText
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val hardwareProperties = HardwareProperties(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.controller_widget_configure)

        appWidgetText = findViewById(R.id.appwidget_text)

        setResult(Activity.RESULT_CANCELED)

        findViewById<View>(R.id.add_button).setOnClickListener(onClickListener)

    }

    private var onClickListener: View.OnClickListener = View.OnClickListener {

        val extras = intent.extras
        appWidgetId = extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)!!

        updateWidget()

        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        // 4
        setResult(RESULT_OK, resultValue)
        // 5
        finish()
    }



    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        ControllerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
    }


}