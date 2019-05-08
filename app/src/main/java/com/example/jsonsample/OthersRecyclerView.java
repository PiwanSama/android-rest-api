package com.example.jsonsample;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class OthersRecyclerView extends RecyclerView.Adapter <OthersRecyclerView.ViewHolder>{

    private List<Other> otherList;
    Context context;

    //Create Constructor and pass data to it

    OthersRecyclerView(Context context, List<Other> otherList){
        this.context=context;
        this.otherList = otherList;
    }


    //Create ViewHolder Class and set onClickListener for the item view
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myName, myGen;
        CardView card;
        public ViewHolder (View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.othercard);
            myName = itemView.findViewById(R.id.gname);
            myGen = itemView.findViewById(R.id.ggender);
        }
    }


    //Bind the view that will hold the data to the recycler view
    //i.e the row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        int orientation = context.getResources().getConfiguration().orientation;
        switch (orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate((R.layout.other_row_land), parent, false);
                //view = mInflater.inflate(R.layout.other_row_land, parent, false);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate((R.layout.other_row), parent, false);
                //view = mInflater.inflate(R.layout.other_row_land, parent, false);
                break;
        }
        return new ViewHolder(itemView);
    }

    //Bind one data item in the array list to an item in the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Other o = otherList.get(position);
        String other = o.getName();
        String gender = o.getGender();
        holder.myName.setText(other);
        holder.myGen.setText(gender);

    }

    @Override
    public int getItemCount() {
        try {
            return otherList.size();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }
}
