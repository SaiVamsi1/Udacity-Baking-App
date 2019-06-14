package com.vamsi.bakingappvamsi;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vamsi.bakingappvamsi.Adapters.StepAdapter;
import com.vamsi.bakingappvamsi.ModelClass.IngredientModel;
import com.vamsi.bakingappvamsi.ModelClass.StepModel;

import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    RecyclerView srv;
    List<IngredientModel> ingredients;
    List<StepModel> steps;
    public boolean Tablet;
    SharedPreferences sp;
    TextView Ingredients;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        if(findViewById(R.id.item_detail_container)!=null)
        {
            Tablet = true;
        }
        setTitle(getIntent().getStringExtra("title"));
        Ingredients=findViewById(R.id.totaligtv);
        srv=findViewById(R.id.steps_list);

        ingredients = (List<IngredientModel>) getIntent().getSerializableExtra("ingredients");

        steps=(List<StepModel>) getIntent().getSerializableExtra("steps");
        srv.setAdapter(new StepAdapter(this,steps,Tablet));
        srv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        StringBuilder ingredientsBuilder = new StringBuilder();
        int stepValue = 0;
        for(int i= 0 ;i<ingredients.size();i++)
        {
            stepValue++;
            ingredientsBuilder.append("").append(stepValue).append(". ").append(ingredients.get(i).getQuantity()).append(" ").append(ingredients.get(i).getMeasure()).append("'s").append(" of ").append(ingredients.get(i).getIngredient()).append("\n");
        }
        String total_ingredients = ingredientsBuilder.toString();
        Ingredients.setText(total_ingredients);

        sp = getSharedPreferences("main key",0);
        SharedPreferences.Editor e = sp.edit();
        e.putString("recipe title key",getIntent().getStringExtra("title"));
        e.putString("full ingredients list key",total_ingredients);
        e.apply();

        Intent widgetintent = new Intent(this, MyWidget.class);
        widgetintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), MyWidget.class));
        widgetintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(widgetintent);
    }
}