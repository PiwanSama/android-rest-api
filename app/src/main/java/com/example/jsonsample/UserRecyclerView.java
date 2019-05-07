package com.example.jsonsample;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UserRecyclerView extends RecyclerView.Adapter <UserRecyclerView.ViewHolder>{

    private List<User> userList;
    Context context;
    private LayoutInflater mInflater;

    //Create Constructor and pass data to it

    UserRecyclerView(Context context, List<User> userList){
        this.context=context;
        this.userList = userList;
        this.mInflater = LayoutInflater.from(context);
    }


//Create ViewHolder Class and set onClickListener for the item view
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myNameView, mMail;
        CircleImageView img;
        CardView card;
        public ViewHolder (View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.usercard);
            myNameView = itemView.findViewById(R.id.mname);
            mMail = itemView.findViewById(R.id.memail);
            img = itemView.findViewById(R.id.icon_img);
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
                        inflate((R.layout.user_row_land), parent, false);
                //view = mInflater.inflate(R.layout.user_row_land, parent, false);
                Log.i("Adapter", "Land");
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                itemView = LayoutInflater.from(parent.getContext()).
                        inflate((R.layout.user_row), parent, false);
                //view = mInflater.inflate(R.layout.user_row, parent, false);
                Log.i("Adapter", "Portrait");
                break;
        }
        return new ViewHolder(itemView);
    }

    //Bind one data item in the array list to an item in the row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User u = userList.get(position);
        String user = u.getName();
        String email = u.getEmail();
        Picasso.with(context).load("http://192.168.43.220/JSONSam/Images/"+u.getPhoto()).into(holder.img);
        holder.myNameView.setText(user);
        holder.mMail.setText(email);

    }

    @Override
    public int getItemCount() {
        try {
            return userList.size();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }
}
