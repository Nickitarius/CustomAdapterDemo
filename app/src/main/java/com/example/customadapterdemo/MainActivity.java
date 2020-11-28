package com.example.customadapterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);

        ArrayList<User> users = new ArrayList<>();

        // TODO: реализовать загрузку данных из JSON-файла
        // который загрузить в папку assets

        InputStream is = null;
        try {
            is = getAssets().open("data.json");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ER", "json not found");
        }
        String usersBufferString = "";
        Scanner sc = new Scanner(is);
        while (sc.hasNext()) {
            usersBufferString += sc.next();
        }

        GsonBuilder GB = new GsonBuilder();
        Gson gson = GB.create();
        User[] usersBuffer;
        //Log.d("F",usersBufferString);
        usersBuffer = gson.fromJson(usersBufferString, User[].class);
        for (User u : usersBuffer) {
            users.add(u);
        }
        /*for (int i = 0; i < 10; i++) {
            users.add(new User("Petya", "123", Sex.MAN));
            users.add(new User("Vasya", "234", Sex.MAN));
            users.add(new User("Valya", "456", Sex.WOMAN));
            users.add(new User("UFO", "@@@", Sex.UNKNOWN));
        }*/


        adapter = new UserListAdapter(this, users);
        adapter.sortByName();
        adapter.sortByPhoneNumber();
        adapter.sortBySex();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}
