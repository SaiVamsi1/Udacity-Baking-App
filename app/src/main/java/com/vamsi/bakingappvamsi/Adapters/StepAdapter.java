package com.vamsi.bakingappvamsi.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vamsi.bakingappvamsi.ItemDetailActivity;
import com.vamsi.bakingappvamsi.ItemDetailFragment;
import com.vamsi.bakingappvamsi.ItemListActivity;
import com.vamsi.bakingappvamsi.ModelClass.StepModel;
import com.vamsi.bakingappvamsi.R;

import java.io.Serializable;
import java.util.List;
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder>
{
        ItemListActivity stepsListActivity;
        List<StepModel> lists;
        public boolean tablet;

        public StepAdapter(ItemListActivity context, List<StepModel> lists,boolean tablet) {
            this.stepsListActivity = context;
            this.lists = lists;
            this.tablet=tablet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(stepsListActivity).inflate(R.layout.step_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.steps.setText(lists.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            if(lists!=null) {
                return lists.size();
            }
            else
            {
                return 0;
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView steps;
            CardView stepcv;

            public ViewHolder(View itemView) {
                super(itemView);
                steps = itemView.findViewById(R.id.shortdesc);
                stepcv=itemView.findViewById(R.id.stepscardview);
                stepcv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position=getAdapterPosition();

                        if(tablet)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putInt("pos",position);
                            bundle.putSerializable("step", (Serializable) lists);
                            ItemDetailFragment itemDetailFragment= new ItemDetailFragment();
                            itemDetailFragment.setArguments(bundle);
                            stepsListActivity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container,itemDetailFragment).commit();
                        }
                        else
                        {
                            Intent intent = new Intent(stepsListActivity, ItemDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("step", (Serializable) lists);
                            bundle.putInt("pos",position);
                            intent.putExtra("bk",bundle);
                            stepsListActivity.startActivity(intent);
                        }
                    }
                });

            }
        }
}