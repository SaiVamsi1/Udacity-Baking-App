package com.vamsi.bakingappvamsi;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vamsi.bakingappvamsi.Adapters.RecipeAdapter;
import com.vamsi.bakingappvamsi.ModelClass.RecipeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecyclerView mainRecipeRv;
    List<RecipeModel> recipesList;
    RecipeModel[] recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainRecipeRv = findViewById(R.id.recyclerview);

        if(doIhaveInternet())
        {
            fetchrecipes();
        }else
        {
            final Snackbar snackbar=Snackbar.make(findViewById(R.id.recyclerview),"Sorry! You are Offline",Snackbar.LENGTH_LONG)
                    .setAction("RELOAD", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchrecipes();
                        }
                    });
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.show();
        }
    }

    private boolean doIhaveInternet()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network=connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }

    private void fetchrecipes()
    {
        StringRequest request = new StringRequest(URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                recipes = gson.fromJson(response, RecipeModel[].class);
                recipesList = new ArrayList<>();
                recipesList.addAll(Arrays.asList(recipes));
                RecipeAdapter recipeAdapter=new RecipeAdapter(MainActivity.this,recipesList);
                mainRecipeRv.setAdapter(recipeAdapter);
                final LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                mainRecipeRv.setLayoutManager(manager);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}