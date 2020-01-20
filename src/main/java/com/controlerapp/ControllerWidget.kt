package com.controlerapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ControllerWidgetConfigureActivity]
 */
class ControllerWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val intent = Intent(context.applicationContext, ControllerService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        context.startService(intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val hardwareProperties = HardwareProperties(context)

            val views = RemoteViews(context.packageName, R.layout.controller_widget)

            views.setTextViewText(R.id.battery_text_widget, hardwareProperties.getBattery())
            views.setTextViewText(R.id.garbage_collector_text_widget, hardwareProperties.getGarbageCollector())
            views.setTextViewText(R.id.ram_text_widget, hardwareProperties.getRam())

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

