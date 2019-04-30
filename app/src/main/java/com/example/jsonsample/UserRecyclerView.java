package com.example.jsonsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRecyclerView extends RecyclerView.Adapter <UserRecyclerView.ViewHolder>{

    private List<String> userss;
    private LayoutInflater mInflater;

    //Create Constructor and pass data to it

    UserRecyclerView(Context context, List<String> data){
        this.userss = data;
        this.mInflater = LayoutInflater.from(context);
    }


//Create ViewHolder Class and set onClickListener for the item view
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myNameView;
        public ViewHolder (View itemView){
            super(itemView);
            myNameView = itemView.findViewById(R.id.mname);
        }
    }


    //Bind the view that will hold the data to the recycler view
    //i.e the row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_row, parent, false);
        return new ViewHolder(view);
    }

    //Bind one data item in the array list to an item in the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String user = userss.get(position);
        holder.myNameView.setText(user);
    }

    @Override
    public int getItemCount() {
        return userss.size();
    }

}
