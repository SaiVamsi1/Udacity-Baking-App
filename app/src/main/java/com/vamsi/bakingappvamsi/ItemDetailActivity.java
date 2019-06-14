package com.vamsi.bakingappvamsi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.vamsi.bakingappvamsi.ModelClass.StepModel;

import java.io.Serializable;
import java.util.List;


public class ItemDetailActivity extends AppCompatActivity {

    int position;
    List<StepModel> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (savedInstanceState == null) {
            Intent intent=getIntent();
            Bundle arguments =intent.getBundleExtra("bk");
            position=arguments.getInt("pos",0);
            steps= (List<StepModel>) arguments.getSerializable("step");
            arguments.putInt("pos",position);
            arguments.putSerializable("step", (Serializable) steps);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finish();
            return true;
        }
        return true;
    }
}