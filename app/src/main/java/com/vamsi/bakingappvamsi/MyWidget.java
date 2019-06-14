package com.vamsi.bakingappvamsi;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider{

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("main key",0);
        String text = sharedPreferences.getString("full ingredients list key","Ingredients Not found");
        String recipe_name = sharedPreferences.getString("recipe title key","Recipe Name not found");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.widget_text_view, recipe_name+"\n"+text);
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.launchapp,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds)
        {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

}
