package com.vamsi.bakingappvamsi.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.vamsi.bakingappvamsi.ItemDetailActivity;
import com.vamsi.bakingappvamsi.ItemListActivity;
import com.vamsi.bakingappvamsi.R;
import com.vamsi.bakingappvamsi.ModelClass.RecipeModel;

import java.io.Serializable;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeListView>
{

    Context context;
    List<RecipeModel> list;
    public RecipeAdapter(Context context,List<RecipeModel> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecipeListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new RecipeListView(LayoutInflater.from(context).inflate(R.layout.main_recipe_item,viewGroup,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecipeListView recipeListView, int i) {
        recipeListView.recipeNames.setText(list.get(i).getName());
        recipeListView.rServings.setText("Servings: "+list.get(i).getServings());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecipeListView extends RecyclerView.ViewHolder
    {
        TextView recipeNames;
        TextView rServings;
        CardView cardView;
        public RecipeListView(View itemView)
        {
            super(itemView);
            recipeNames=itemView.findViewById(R.id.recipe_name_tv);
            rServings=itemView.findViewById(R.id.servings_tv);
            cardView=itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int i=getAdapterPosition();
                    Intent intent=new Intent(context, ItemListActivity.class);
                    intent.putExtra("title",list.get(i).getName());
                    intent.putExtra("ingredients",(Serializable)list.get(i).getIngredients());
                    intent.putExtra("steps",(Serializable)list.get(i).getSteps());
                    context.startActivity(intent);
                }
            });

        }
    }
}
