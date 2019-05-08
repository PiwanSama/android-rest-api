package com.example.jsonsample;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

public class Main extends AppCompatActivity{

    UserRecyclerView userAdapter;
    OthersRecyclerView othersAdapter;
    UsersVM usersVM;
    OthersVM othersVM;
    SnapHelper snap, bnap;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        orientation();
        snap = new PagerSnapHelper();
        bnap = new PagerSnapHelper();
        RecyclerView usersRV = findViewById(R.id.users_recycler_view);
        usersRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        snap.attachToRecyclerView(usersRV);
        usersVM = ViewModelProviders.of(this).get(UsersVM.class);
        ArrayList<User> users = usersVM.getUser();
        userAdapter = new UserRecyclerView(this, users);
        usersRV.setAdapter(userAdapter);

        RecyclerView otherRV = findViewById(R.id.second_recycler_view);
        otherRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        bnap.attachToRecyclerView(otherRV);
        othersVM = ViewModelProviders.of(this).get(OthersVM.class);
        ArrayList<Other> others = othersVM.getOther();
        othersAdapter = new OthersRecyclerView(this, others);
        otherRV.setAdapter(othersAdapter);

    }

    private void orientation(){
        int orientation = getResources().getConfiguration().orientation;
        switch (orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.main);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.main_land);
                break;
        }
    }
}
