package com.example.jsonsample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.List;

public class Main extends AppCompatActivity{

    UserRecyclerView adapter;
    UsersVM usersVM;
    SnapHelper snap;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        snap = new PagerSnapHelper();
        RecyclerView rv = findViewById(R.id.users_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        snap.attachToRecyclerView(rv);
        usersVM = ViewModelProviders.of(this).get(UsersVM.class);
        List<User> users = usersVM.getUser();
        adapter = new UserRecyclerView(this, users);
        rv.setAdapter(adapter);
    }
}
